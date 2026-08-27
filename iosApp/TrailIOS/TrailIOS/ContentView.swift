import SwiftUI
import sharedKit

struct ContentView: View {
    @StateObject private var viewModel = BooksViewModel()

    var body: some View {
        TabView {
            DiscoverView()
                .tabItem {
                    Label("Discover", systemImage: "magnifyingglass")
                }

            MyBooksView()
                .tabItem {
                    Label("My Books", systemImage: "books.vertical")
                }
        }
        .environmentObject(viewModel)  // ← moved here, applies to all tabs
    }
}

#Preview {
    ContentView()
}
