import Foundation
import sharedKit

@MainActor
class BooksViewModel: ObservableObject {

    private let bookApiService = HttpClientFactoryKt.createBookApiService()
    private let repository: BookRepository

    @Published var bookList: [BookModel] = []
    @Published var isLoading = false
    @Published var error: String? = nil
    @Published var selectedBook: BookModel? = nil
    @Published var isDetailLoading = false
    @Published var apiKey: String = UserDefaults.standard.string(forKey: "api_key") ?? ""
    @Published var needsApiKey = false
    @Published var userBookStates: [UserBookState] = []

    private let coverColors: [Int64] = [
        Int64(bitPattern: 0xFFA8452A),
        Int64(bitPattern: 0xFF6B4F3A),
        Int64(bitPattern: 0xFFB5793C),
        Int64(bitPattern: 0xFF7C4A2D)
    ]
    func readStatusFromString(_ value: String) -> ReadStatus {
        switch value {
        case "WANT_TO_READ": return ReadStatus.wantToRead
        case "READ": return ReadStatus.read
        default: return ReadStatus.none
        }
    }


    init() {
        let db = HttpClientFactoryKt.createDatabase()
        self.repository = BookRepository(database: db)

        Task {
            await loadInitialData()
        }
    }

    private func loadInitialData() async {
        if apiKey.isEmpty {
            needsApiKey = true
            return
        }
        needsApiKey = false
        let count = try? await repository.getBookCount()
        if (count ?? 0) == 0 {
            await searchBooks(query: "novel")
        } else {
            await loadBooksFromDb()
        }

        // load user book states
        if let states = try? await repository.getUserBookStates() {
            userBookStates = states.map {
                UserBookState(
                    bookId: $0.bookId,
                    readStatus: readStatusFromString($0.readStatus),
                    isFavourite: $0.isFavourite
                )
            }
        }
    }

    func saveApiKey(_ key: String) {
        apiKey = key
        UserDefaults.standard.set(key, forKey: "api_key")
        needsApiKey = false
        Task { await loadInitialData() }
    }

    func loadBooksFromDb() async {
        isLoading = true
        if let books = try? await repository.getBooksFromDb() {
            bookList = books.map { $0.toBookModel() }
        }
        isLoading = false
    }

    func searchBooks(query: String) async {
        isLoading = true
        error = nil
        do {
            let response = try await bookApiService.searchBooks(
                query: query,
                apiKey: apiKey,
                number: 20,
                minRating: nil as KotlinFloat?
            )
            bookList = response.books.compactMap { items in
                guard let book = items.first else { return nil }
                return BookModel(
                    id: book.id,
                    bookName: book.title,
                    authorName: book.authors?.first?.name ?? "Unknown",
                    bookPages: 0,
                    year: "",
                    coverColor: coverColors.randomElement() ?? coverColors[0],
                    description: "",
                    isbn: "",
                    rating: book.rating?.average ?? 0.0
                )
            }
            // Save to db in background
            let booksToSave = bookList
            Task.detached(priority: .background) { [weak self] in
                guard let self = self else { return }
                for book in booksToSave {
                    try? await self.repository.saveBooks(books: [book.toBookEntity()])
                }
            }
        } catch {
            self.error = "Something went wrong: \(error.localizedDescription)"
        }
        isLoading = false
    }


    func fetchBookDetails(bookId: Int64) async {
        isDetailLoading = true
        do {
            let detail = try await bookApiService.getBookDetails(bookId: bookId, apiKey: apiKey)
            if let index = bookList.firstIndex(where: { $0.id == bookId }) {
                let current = bookList[index]
                let pages = Int32(detail.number_of_pages?.doubleValue ?? 0)
                let year = String(Int(detail.publish_date?.doubleValue ?? 0))
                let desc = detail.description_ ?? ""
                let isbn = detail.identifiers?.isbn_13 ?? ""

                let updated = current.doCopy(
                    id: current.id,
                    bookName: current.bookName,
                    authorName: current.authorName,
                    bookPages: pages,
                    year: year,
                    coverColor: current.coverColor,
                    description: desc,
                    isbn: isbn,
                    rating: current.rating
                )
                bookList[index] = updated
                selectedBook = updated
                let entity = updated.toBookEntity()
                try? await repository.updateBookDetails(book: entity)
            }
        } catch {
            print("Detail error: \(error)")
        }
        isDetailLoading = false
    }


    func toggleFavourite(bookId: Int64) {
        if let index = userBookStates.firstIndex(where: { $0.bookId == bookId }) {
            userBookStates[index] = UserBookState(
                bookId: bookId,
                readStatus: userBookStates[index].readStatus,
                isFavourite: !userBookStates[index].isFavourite
            )
        } else {
            userBookStates.append(UserBookState(bookId: bookId, readStatus: ReadStatus.none, isFavourite: true))
        }
        Task { await saveUserBookState(bookId: bookId) }
    }

    func setReadStatus(bookId: Int64, status: ReadStatus) {
        if let index = userBookStates.firstIndex(where: { $0.bookId == bookId }) {
            let newStatus = userBookStates[index].readStatus == status ? ReadStatus.none : status
            userBookStates[index] = UserBookState(
                bookId: bookId,
                readStatus: newStatus,
                isFavourite: userBookStates[index].isFavourite
            )
        } else {
            userBookStates.append(UserBookState(bookId: bookId, readStatus: status, isFavourite: false))
        }
        Task { await saveUserBookState(bookId: bookId) }
    }

    private func saveUserBookState(bookId: Int64) async {
        guard let state = userBookStates.first(where: { $0.bookId == bookId }) else { return }
        try? await repository.saveUserBookState(state: UserBookStateEntity(
            bookId: state.bookId,
            readStatus: state.readStatus.name,
            isFavourite: state.isFavourite
        ))
    }

    func getBookState(bookId: Int64) -> UserBookState? {
        return userBookStates.first { $0.bookId == bookId }
    }
}

extension BookEntity {
    func toBookModel() -> BookModel {
        return BookModel(
            id: self.bookId,
            bookName: self.bookName,
            authorName: self.authorName,
            bookPages: self.bookPages,
            year: self.year,
            coverColor: self.coverColor,
            description: self.description_,
            isbn: self.isbn,
            rating: self.rating
        )
    }
}

extension BookModel {
    func toBookEntity() -> BookEntity {
        return BookEntity(
            bookId: self.id,
            bookName: self.bookName,
            authorName: self.authorName,
            rating: self.rating,
            coverColor: self.coverColor,
            bookPages: self.bookPages,
            year: self.year,
            description: self.description_,
            isbn: self.isbn,
            isDetailsFetched: false
        )
    }
}
