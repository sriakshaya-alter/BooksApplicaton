package com.example.trail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel


val mockBooks = listOf(
    BookModel("The Great Gatsby", "F. Scott Fitzgerald", 180, "1925", 0xFF8B4513, "Hardcover"),
    BookModel("To Kill a Mockingbird", "Harper Lee", 281, "1960", 0xFF2F4F4F,"Hardcover"),
    BookModel("1984", "George Orwell", 328, "1949", 0xFF8B0000,"Hardcover"),
    BookModel("Pride and Prejudice", "Jane Austen", 432, "1813", 0xFF4B0082,"Hardcover"),
    BookModel("The Catcher in the Rye", "J.D. Salinger", 277, "1951", 0xFF006400,"Hardcover"),
    BookModel("Brave New World", "Aldous Huxley", 311, "1932", 0xFF8B008B,"Hardcover"),
    BookModel("The Hobbit", "J.R.R. Tolkien", 310, "1937", 0xFF556B2F,"Hardcover"),
    BookModel("Fahrenheit 451", "Ray Bradbury", 158, "1953", 0xFFB8860B,"Hardcover"),
    BookModel("Jane Eyre", "Charlotte Bronte", 500, "1847", 0xFF800000,"Hardcover"),
    BookModel("Wuthering Heights", "Emily Bronte", 342, "1847", 0xFF483D8B,"Hardcover"),
    BookModel("The Lord of the Rings", "J.R.R. Tolkien", 1178, "1954", 0xFF2E8B57,"Hardcover"),
    BookModel("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 309, "1997", 0xFF8B0000,"Hardcover"),
    BookModel("The Alchemist", "Paulo Coelho", 197, "1988", 0xFFD2691E,"Hardcover"),
    BookModel("Don Quixote", "Miguel de Cervantes", 863, "1605", 0xFF8B4513,"Hardcover"),
    BookModel("War and Peace", "Leo Tolstoy", 1225, "1869", 0xFF2F4F4F,"Hardcover"),
    BookModel("Crime and Punishment", "Fyodor Dostoevsky", 671, "1866", 0xFF800000,"Hardcover"),
    BookModel("Anna Karenina", "Leo Tolstoy", 864, "1878", 0xFF4B0082,"Hardcover"),
    BookModel("The Brothers Karamazov", "Fyodor Dostoevsky", 796, "1880", 0xFF006400,"Hardcover"),
    BookModel("Moby Dick", "Herman Melville", 635, "1851", 0xFF191970,"Hardcover"),
    BookModel("The Odyssey", "Homer", 374, "800 BC", 0xFF8B6914,"Hardcover"),
    BookModel("Hamlet", "William Shakespeare", 342, "1603", 0xFF2F4F4F,"Hardcover"),
    BookModel("Romeo and Juliet", "William Shakespeare", 253, "1597", 0xFF8B0000,"Hardcover"),
    BookModel("Macbeth", "William Shakespeare", 170, "1606", 0xFF1C1C1C,"Hardcover"),
    BookModel("The Divine Comedy", "Dante Alighieri", 798, "1320", 0xFF8B4513,"Hardcover"),
    BookModel("Gone with the Wind", "Margaret Mitchell", 1037, "1936", 0xFF8B2252,"Hardcover"),
    BookModel("The Count of Monte Cristo", "Alexandre Dumas", 1276, "1844", 0xFF556B2F,"Hardcover"),
    BookModel("Les Miserables", "Victor Hugo", 1463, "1862", 0xFF483D8B,"Paperback"),
    BookModel("Oliver Twist", "Charles Dickens", 480, "1837", 0xFF8B4513,"Paperback"),
    BookModel("Great Expectations", "Charles Dickens", 544, "1861", 0xFF2E8B57,"Paperback"),
    BookModel("A Tale of Two Cities", "Charles Dickens", 489, "1859", 0xFF8B0000,"Paperback"),
    BookModel("The Picture of Dorian Gray", "Oscar Wilde", 254, "1890", 0xFF4B0082,"Paperback"),
    BookModel("Dracula", "Bram Stoker", 418, "1897", 0xFF1C1C1C,"Paperback"),
    BookModel("Frankenstein", "Mary Shelley", 280, "1818", 0xFF2F4F4F,"Paperback"),
    BookModel("The Time Machine", "H.G. Wells", 118, "1895", 0xFF006400,"Paperback"),
    BookModel("The War of the Worlds", "H.G. Wells", 192, "1898", 0xFF8B0000,"Paperback"),
    BookModel("Sherlock Holmes", "Arthur Conan Doyle", 307, "1892", 0xFF8B4513,"Paperback"),
    BookModel("Around the World in 80 Days", "Jules Verne", 256, "1872", 0xFF191970,"Paperback"),
    BookModel("Twenty Thousand Leagues", "Jules Verne", 304, "1870", 0xFF2E8B57,"Paperback"),
    BookModel("The Three Musketeers", "Alexandre Dumas", 704, "1844", 0xFF8B0000,"Paperback"),
    BookModel("Treasure Island", "Robert Louis Stevenson", 292, "1883", 0xFFB8860B,"Paperback"),
    BookModel("Robinson Crusoe", "Daniel Defoe", 320, "1719", 0xFF556B2F,"Paperback"),
    BookModel("Gulliver's Travels", "Jonathan Swift", 306, "1726", 0xFF4B0082,"Paperback"),
    BookModel("Alice in Wonderland", "Lewis Carroll", 172, "1865", 0xFF8B008B,"Paperback"),
    BookModel("The Jungle Book", "Rudyard Kipling", 212, "1894", 0xFF2F4F4F,"Paperback"),
    BookModel("Little Women", "Louisa May Alcott", 449, "1868", 0xFF8B2252,"Paperback"),
    BookModel("The Adventures of Tom Sawyer", "Mark Twain", 274, "1876", 0xFFD2691E,"Paperback"),
    BookModel("Adventures of Huckleberry Finn", "Mark Twain", 366, "1884", 0xFF8B4513,"Paperback"),
    BookModel("The Call of the Wild", "Jack London", 232, "1903", 0xFF556B2F,"Paperback"),
    BookModel("White Fang", "Jack London", 329, "1906", 0xFF2F4F4F,"Paperback"),
    BookModel("The Old Man and the Sea", "Ernest Hemingway", 127, "1952", 0xFF191970,"Paperback"),
    BookModel("For Whom the Bell Tolls", "Ernest Hemingway", 480, "1940", 0xFF8B0000,"Paperback"),
    BookModel("A Farewell to Arms", "Ernest Hemingway", 332, "1929", 0xFF483D8B,"Paperback"),
    BookModel("The Sun Also Rises", "Ernest Hemingway", 251, "1926", 0xFFB8860B,"Paperback"),
    BookModel("Of Mice and Men", "John Steinbeck", 187, "1937", 0xFF2E8B57,"Paperback"),
    BookModel("The Grapes of Wrath", "John Steinbeck", 464, "1939", 0xFF8B4513,"Paperback"),
    BookModel("East of Eden", "John Steinbeck", 601, "1952", 0xFF556B2F,"Paperback"),
    BookModel("Cannery Row", "John Steinbeck", 196, "1945", 0xFF006400,"Paperback"),
    BookModel("Animal Farm", "George Orwell", 112, "1945", 0xFF8B6914,"Paperback"),
    BookModel("Lord of the Flies", "William Golding", 224, "1954", 0xFF1C1C1C,"Paperback"),
    BookModel("The Handmaid's Tale", "Margaret Atwood", 311, "1985", 0xFF8B0000,"Paperback"),
    BookModel("Beloved", "Toni Morrison", 321, "1987", 0xFF4B0082,"Paperback"),
    BookModel("Slaughterhouse-Five", "Kurt Vonnegut", 215, "1969", 0xFF2F4F4F,"Paperback"),
    BookModel("Catch-22", "Joseph Heller", 453, "1961", 0xFF006400,"Paperback"),
    BookModel("One Flew Over the Cuckoo's Nest", "Ken Kesey", 325, "1962", 0xFF8B4513,"Paperback"),
    BookModel("The Bell Jar", "Sylvia Plath", 244, "1963", 0xFF8B008B,"Paperback"),
    BookModel("On the Road", "Jack Kerouac", 307, "1957", 0xFFD2691E,"Paperback"),
    BookModel("Invisible Man", "Ralph Ellison", 581, "1952", 0xFF1C1C1C,"Paperback"),
    BookModel("The Color Purple", "Alice Walker", 295, "1982", 0xFF8B2252,"Paperback"),
    BookModel("Their Eyes Were Watching God", "Zora Neale Hurston", 286, "1937", 0xFF556B2F,"Paperback"),
    BookModel("Native Son", "Richard Wright", 504, "1940", 0xFF8B0000,"eBook"),
    BookModel("The Road", "Cormac McCarthy", 287, "2006", 0xFF2F4F4F,"eBook"),
    BookModel("Blood Meridian", "Cormac McCarthy", 351, "1985", 0xFF8B4513,"eBook"),
    BookModel("No Country for Old Men", "Cormac McCarthy", 309, "2005", 0xFF483D8B,"eBook"),
    BookModel("The Secret", "Rhonda Byrne", 198, "2006", 0xFFB8860B,"eBook"),
    BookModel("Thinking Fast and Slow", "Daniel Kahneman", 499, "2011", 0xFF2E8B57,"eBook"),
    BookModel("Sapiens", "Yuval Noah Harari", 443, "2011", 0xFF8B0000,"eBook"),
    BookModel("Homo Deus", "Yuval Noah Harari", 450, "2015", 0xFF191970,"eBook"),
    BookModel("21 Lessons for the 21st Century", "Yuval Noah Harari", 352, "2018", 0xFF4B0082,"eBook"),
    BookModel("Atomic Habits", "James Clear", 320, "2018", 0xFF006400,"eBook"),
    BookModel("Deep Work", "Cal Newport", 296, "2016", 0xFF2F4F4F,"eBook"),
    BookModel("The 7 Habits", "Stephen Covey", 381, "1989", 0xFF8B4513,"eBook"),
    BookModel("How to Win Friends", "Dale Carnegie", 288, "1936", 0xFF556B2F,"eBook"),
    BookModel("Think and Grow Rich", "Napoleon Hill", 238, "1937", 0xFFB8860B,"eBook"),
    BookModel("Rich Dad Poor Dad", "Robert Kiyosaki", 207, "1997", 0xFF8B6914,"eBook"),
    BookModel("The Lean Startup", "Eric Ries", 336, "2011", 0xFF2E8B57,"eBook"),
    BookModel("Zero to One", "Peter Thiel", 224, "2014", 0xFF1C1C1C,"eBook"),
    BookModel("Steve Jobs", "Walter Isaacson", 656, "2011", 0xFF2F4F4F,"eBook"),
    BookModel("Elon Musk", "Walter Isaacson", 688, "2023", 0xFF8B0000,"eBook"),
    BookModel("The Pragmatic Programmer", "David Thomas", 352, "1999", 0xFF483D8B,"Paperback"),
    BookModel("Clean Code", "Robert C. Martin", 431, "2008", 0xFF006400,"Paperback"),
    BookModel("The Clean Coder", "Robert C. Martin", 256, "2011", 0xFF2E8B57,"Paperback"),
    BookModel("Design Patterns", "Gang of Four", 395, "1994", 0xFF8B4513,"Paperback"),
    BookModel("Introduction to Algorithms", "Thomas H. Cormen", 1292, "1990", 0xFF191970,"Paperback"),
    BookModel("The Mythical Man-Month", "Frederick Brooks", 322, "1975", 0xFF556B2F,"Paperback"),
    BookModel("Code Complete", "Steve McConnell", 914, "1993", 0xFF4B0082,"Paperback"),
    BookModel("Refactoring", "Martin Fowler", 448, "1999", 0xFF8B008B,"Paperback"),
    BookModel("Head First Design Patterns", "Eric Freeman", 694, "2004", 0xFF8B0000,"Paperback"),
    BookModel("You Don't Know JS", "Kyle Simpson", 278, "2015", 0xFFD2691E,"Paperback"),
    BookModel("The Art of Computer Programming", "Donald Knuth", 672, "1968", 0xFF2F4F4F,"Paperback")
)



class BooksViewModel : ViewModel(){
    var bookList = mutableStateListOf<BookModel>()
    init {
        bookList.addAll(mockBooks)  // load all 100 books on start
    }
    var selectedFilter =  mutableStateOf("All")
    //var BookSearch = mutableStateOf("")
    var BookSearch = mutableStateOf("")

}
