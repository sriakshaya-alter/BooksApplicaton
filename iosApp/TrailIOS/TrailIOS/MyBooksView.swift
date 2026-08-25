import SwiftUI
import sharedKit

struct MyBooksView: View {
    @EnvironmentObject var viewModel: BooksViewModel
    @State private var selectedFilter = 0

    var filteredBooks: [BookModel] {
        let states = viewModel.userBookStates
        switch selectedFilter {
        case 1: // Favourites
            let ids = states.filter { $0.isFavourite }.map { $0.bookId }
            return viewModel.bookList.filter { ids.contains($0.id) }
        case 2: // Want to Read
            let ids = states.filter { $0.readStatus == .wantToRead }.map { $0.bookId }
            return viewModel.bookList.filter { ids.contains($0.id) }
        case 3: // Read
            let ids = states.filter { $0.readStatus == .read }.map { $0.bookId }
            return viewModel.bookList.filter { ids.contains($0.id) }
        default: // All saved
            let ids = states.map { $0.bookId }
            return viewModel.bookList.filter { ids.contains($0.id) }
        }
    }

    var body: some View {
        NavigationView {
            VStack {
                // Filter picker
                Picker("Filter", selection: $selectedFilter) {
                    Text("All").tag(0)
                    Text("Liked").tag(1)
                    Text("Want to Read").tag(2)
                    Text("Read").tag(3)
                }
                .pickerStyle(.segmented)
                .padding(.horizontal)

                if filteredBooks.isEmpty {
                    Spacer()
                    Text("No books here yet")
                        .foregroundColor(.gray)
                    Spacer()
                } else {
                    List(filteredBooks, id: \.id) { book in
                        NavigationLink(destination: BookDetailView(book: book)) {
                            HStack {
                                BookRowView(book: book)
                                Spacer()
                                // Show status badge
                                if let state = viewModel.getBookState(bookId: book.id) {
                                    VStack(spacing: 4) {
                                        if state.isFavourite {
                                            Image(systemName: "heart.fill")
                                                .foregroundColor(.red)
                                                .font(.caption)
                                        }
                                        if state.readStatus == .wantToRead {
                                            Image(systemName: "bookmark.fill")
                                                .foregroundColor(.blue)
                                                .font(.caption)
                                        }
                                        if state.readStatus == .read {
                                            Image(systemName: "checkmark.circle.fill")
                                                .foregroundColor(.green)
                                                .font(.caption)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .listStyle(.plain)
                }
            }
            .navigationTitle("My Books")
        }
    }
}
