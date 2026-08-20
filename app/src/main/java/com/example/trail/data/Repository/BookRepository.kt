package com.example.trail.data.Repository

import com.example.trail.data.RetrofitInstance
import com.example.trail.data.local.AppDatabase
import com.example.trail.data.local.BookEntity
import com.example.trail.data.local.UserBookStateEntity


class BookRepository(private val database: AppDatabase) {

    private val bookDao = database.bookDao()
    private val userBookStateDao = database.userBookStateDao()


    suspend fun getBooksFromDb(): List<BookEntity> {
        return bookDao.getAllBooks()
    }

    suspend fun getBookCount(): Int {
        return bookDao.getBookCount()
    }

    suspend fun saveBooks(books: List<BookEntity>) {
        bookDao.insertBooks(books)
    }

    suspend fun updateBookDetails(book: BookEntity) {
        bookDao.insertOrReplaceBook(book)
    }

    suspend fun getBookById(bookId: Long): BookEntity? {
        return bookDao.getBookById(bookId)
    }

    suspend fun getUserBookStates(): List<UserBookStateEntity> {
        return userBookStateDao.getAllBookStatus()
    }

    suspend fun saveUserBookState(state: UserBookStateEntity) {
        userBookStateDao.insertOrReplaceBookStatus(state)
    }

    suspend fun getUserBookStateById(bookId: Long): UserBookStateEntity? {
        return userBookStateDao.getBookStateById(bookId)
    }

}
