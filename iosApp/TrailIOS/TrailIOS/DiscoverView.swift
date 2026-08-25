import SwiftUI
import sharedKit

struct DiscoverView: View {
    @EnvironmentObject var viewModel: BooksViewModel
    @State private var searchText = ""

    var body: some View {
        NavigationView {
            VStack {
                // API Key prompt
                if viewModel.needsApiKey {
                    ApiKeyView()
                } else {
                    // Search bar
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                        TextField("Search books...", text: $searchText)
                            .onChange(of: searchText) { newValue in
                                Task {
                                    if newValue.count > 2 {
                                        await viewModel.searchBooks(query: newValue)
                                    } else if newValue.isEmpty {
                                        await viewModel.loadBooksFromDb()
                                    }
                                }
                            }
                    }
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(10)
                    .padding(.horizontal)

                    if viewModel.isLoading {
                        Spacer()
                        ProgressView("Loading...")
                        Spacer()
                    } else if let error = viewModel.error {
                        Spacer()
                        Text(error).foregroundColor(.red).padding()
                        Spacer()
                    } else {
                        List(viewModel.bookList, id: \.id) { book in
                            NavigationLink(destination: BookDetailView(book: book)) {
                                BookRowView(book: book)
                            }
                        }
                        .listStyle(.plain)
                    }
                }
            }
            .navigationTitle("Discover")
        }
    }
}

struct BookRowView: View {
    let book: BookModel

    var body: some View {
        HStack(spacing: 12) {
            RoundedRectangle(cornerRadius: 8)
                .fill(Color(hex: book.coverColor))
                .frame(width: 50, height: 70)

            VStack(alignment: .leading, spacing: 4) {
                Text(book.bookName)
                    .font(.headline)
                    .lineLimit(2)
                Text(book.authorName)
                    .font(.subheadline)
                    .foregroundColor(.gray)
                if book.rating > 0 {
                    HStack(spacing: 2) {
                        Image(systemName: "star.fill")
                            .foregroundColor(.yellow)
                            .font(.caption)
                        Text(String(format: "%.1f", book.rating))
                            .font(.caption)
                    }
                }
            }
        }
        .padding(.vertical, 4)
    }
}

struct ApiKeyView: View {
    @EnvironmentObject var viewModel: BooksViewModel
    @State private var keyInput = ""

    var body: some View {
        VStack(spacing: 20) {
            Text("Enter API Key")
                .font(.title2)
                .bold()
            Text("Get your key from bigbookapi.com")
                .foregroundColor(.gray)
            TextField("API Key", text: $keyInput)
                .textFieldStyle(.roundedBorder)
                .padding(.horizontal)
            Button("Save") {
                viewModel.saveApiKey(keyInput)
            }
            .buttonStyle(.borderedProminent)
        }
        .padding()
    }
}

extension Color {
    init(hex: Int64) {
        let r = Double((hex >> 16) & 0xFF) / 255
        let g = Double((hex >> 8) & 0xFF) / 255
        let b = Double(hex & 0xFF) / 255
        self.init(red: r, green: g, blue: b)
    }
}


