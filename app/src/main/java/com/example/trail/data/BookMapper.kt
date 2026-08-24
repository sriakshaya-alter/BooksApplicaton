package com.example.trail.data

import com.example.trail.shared.BookEntity
import com.example.trail.shared.BookModel

fun BookEntity.toBookModel() = BookModel(
    id = bookId,
    bookName = bookName,
    authorName = authorName,
    rating = rating,
    coverColor = coverColor,
    bookPages = bookPages,
    year = year,
    description = description,
    isbn = isbn
)

fun BookModel.toBookEntity() = BookEntity(
    bookId = id,
    bookName = bookName,
    authorName = authorName,
    rating = rating,
    coverColor = coverColor,
    bookPages = bookPages,
    year = year,
    description = description,
    isbn = isbn
)
