package com.example.trail.ui.home

import com.example.trail.data.mockBooks
import com.example.trail.data.UserBookState

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trail.data.BookModel


class BooksViewModel : ViewModel() {

    // bookList — private write, public read
    private val _bookList = mutableStateListOf<BookModel>()
    val bookList: List<BookModel> = _bookList


    private val _selectedFilter = mutableStateOf("All")
    val selectedFilter: State<String> = _selectedFilter

    // bookSearch — private write, public read
    private val _bookSearch = mutableStateOf("")
    val bookSearch: State<String> = _bookSearch

    init {
        _bookList.addAll(mockBooks)
    }
    fun onFilterChange(filter: String) {
        _selectedFilter.value = filter
    }

    fun onSearchChange(query: String) {
        _bookSearch.value = query
    }

    private val _userBookStates = mutableStateListOf<UserBookState>()
    val userBookStates: List<UserBookState> = _userBookStates

    fun toggleFavourite(isbn: String) {
        val index = _userBookStates.indexOfFirst { it.isbn == isbn }
        if (index != -1) {
            _userBookStates[index] = _userBookStates[index].copy(
                isFavourite = !_userBookStates[index].isFavourite
            )
        } else {
            _userBookStates.add(UserBookState(isbn, isFavourite = true))
        }
    }

    fun setReadStatus(isbn: String, status: String) {
        val index = _userBookStates.indexOfFirst { it.isbn == isbn }
        if (index != -1) {
            _userBookStates[index] = _userBookStates[index].copy(readStatus = status)
        } else {
            _userBookStates.add(UserBookState(isbn, readStatus = status))
        }
    }



}

