import SwiftUI
import sharedKit

struct BookDetailView: View {
    let book: BookModel
    @EnvironmentObject var viewModel: BooksViewModel

    var bookState: UserBookState? {
        viewModel.getBookState(bookId: book.id)
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                // Cover
                HStack {
                    Spacer()
                    RoundedRectangle(cornerRadius: 12)
                        .fill(Color(hex: book.coverColor))
                        .frame(width: 120, height: 180)
                    Spacer()
                }
                .padding(.top)

                // Title and Author
                VStack(alignment: .leading, spacing: 6) {
                    Text(book.bookName)
                        .font(.title2)
                        .bold()
                    Text(book.authorName)
                        .font(.subheadline)
                        .foregroundColor(.gray)

                    if book.rating > 0 {
                        HStack(spacing: 2) {
                            Image(systemName: "star.fill")
                                .foregroundColor(.yellow)
                            Text(String(format: "%.1f", book.rating))
                        }
                    }
                }
                .padding(.horizontal)

                // Action buttons
                HStack(spacing: 12) {
                    // Favourite button
                    Button {
                        viewModel.toggleFavourite(bookId: book.id)
                    } label: {
                        HStack {
                            Image(systemName: bookState?.isFavourite == true ? "heart.fill" : "heart")
                            Text(bookState?.isFavourite == true ? "Liked" : "Like")
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(bookState?.isFavourite == true ? Color.red.opacity(0.2) : Color(.systemGray6))
                        .cornerRadius(10)
                    }

                    // Want to Read button
                    Button {
                        viewModel.setReadStatus(bookId: book.id, status: .wantToRead)
                    } label: {
                        HStack {
                            Image(systemName: "bookmark")
                            Text("Want to Read")
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(bookState?.readStatus == .wantToRead ? Color.blue.opacity(0.2) : Color(.systemGray6))
                        .cornerRadius(10)
                    }
                }
                .padding(.horizontal)

                // Read button
                Button {
                    viewModel.setReadStatus(bookId: book.id, status: .read)
                } label: {
                    HStack {
                        Image(systemName: "checkmark.circle")
                        Text("Mark as Read")
                    }
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(bookState?.readStatus == .read ? Color.green.opacity(0.2) : Color(.systemGray6))
                    .cornerRadius(10)
                }
                .padding(.horizontal)

                // Details
                if viewModel.isDetailLoading {
                    HStack {
                        Spacer()
                        ProgressView()
                        Spacer()
                    }
                } else {
                    VStack(alignment: .leading, spacing: 8) {
                        if book.bookPages > 0 {
                            DetailRow(label: "Pages", value: "\(book.bookPages)")
                        }
                        if !book.year.isEmpty && book.year != "0" {
                            DetailRow(label: "Year", value: book.year)
                        }
                        if !book.isbn.isEmpty {
                            DetailRow(label: "ISBN", value: book.isbn)
                        }
                        if !book.description_.isEmpty {
                            Text("Description")
                                .font(.headline)
                                .padding(.top, 8)
                            Text(book.description_)
                                .font(.body)
                                .foregroundColor(.secondary)
                        }
                    }
                    .padding(.horizontal)
                }
            }
        }
        .navigationTitle("Book Details")
        .navigationBarTitleDisplayMode(.inline)
        .task {
            await viewModel.fetchBookDetails(bookId: book.id)
        }
    }
}

struct DetailRow: View {
    let label: String
    let value: String

    var body: some View {
        HStack {
            Text(label)
                .font(.subheadline)
                .foregroundColor(.gray)
            Spacer()
            Text(value)
                .font(.subheadline)
        }
    }
}

