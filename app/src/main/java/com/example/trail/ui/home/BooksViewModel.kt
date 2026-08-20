package com.example.trail.ui.home

import com.example.trail.data.UserBookState
import com.example.trail.data.ReadStatus
import com.example.trail.data.toBookEntity
import com.example.trail.data.toBookModel



import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trail.data.BookFilter
import com.example.trail.data.BookModel
import com.example.trail.data.RetrofitInstance
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.trail.BookApplication
import com.example.trail.BuildConfig
import com.example.trail.data.MyBooksFilter
import com.example.trail.data.local.UserBookStateEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first



class BooksViewModel(application: Application) : AndroidViewModel(application) {
    private var currentApiKey = "" //BuildConfig.API_KEY

    private val repository = (application as BookApplication).repository

    private val _bookList = mutableStateListOf<BookModel>()
    val bookList: List<BookModel> get() = _bookList

    private val coverColors = listOf(
        0xFFA8452A, 0xFF6B4F3A, 0xFFB5793C, 0xFF7C4A2D
    )
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val settingsDataStore = (application as BookApplication).settingsDataStore
    private val _needsApiKey = mutableStateOf(false)
    val needsApiKey: State<Boolean> = _needsApiKey

    init {

        viewModelScope.launch {
            settingsDataStore.apiKeyFlow.collect { savedKey ->
                if (savedKey.isNotEmpty() && currentApiKey != savedKey) {
                    currentApiKey = savedKey
                    _needsApiKey.value = false

                    val count = repository.getBookCount()
                    if (count == 0) {
                        searchBooks("novel", minRating = 0.8f)
                    } else {
                        loadBooksFromDb()
                    }
                } else if (savedKey.isEmpty()) {
                    _needsApiKey.value = true
                    _isLoading.value = false
                }
            }
        }
        viewModelScope.launch {
            try {
                val savedStates = repository.getUserBookStates()
                savedStates.forEach { entity ->
                    _userBookStates.add(
                        UserBookState(entity.bookId, ReadStatus.valueOf(entity.readStatus), entity.isFavourite)
                    )
                }
            } catch (e: Exception) { }
        }
    }


    private suspend fun loadBooksFromDb() {
            try {
                if (_bookSearch.value.isEmpty()) {
                    _isLoading.value = true
                }
                val books = repository.getBooksFromDb()
                _bookList.clear()
                _bookList.addAll(books.map { it.toBookModel() })
            }catch(e: Exception){
                e.message
            }finally {
                _isLoading.value = false
            }
    }
    fun searchBooks(query: String, minRating: Float? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                Log.d("API", "Searching for: $query")
                val response = RetrofitInstance.api.searchBooks(query, minRating = minRating, apiKey = currentApiKey)
                _bookList.clear()
                Log.d("API", "Books received: ${response.books.size}")
                response.books.forEach { items ->
                    val book = items.firstOrNull() ?: return@forEach
                    Log.d("API", "Adding book: ${book.title}")

                    val bookModel = BookModel(
                            id = book.id,
                            bookName = book.title,
                            authorName = book.authors?.firstOrNull()?.name ?: "Unknown",
                            bookPages = 0,
                            year = "",
                            coverColor = coverColors.random(),
                            description = "",
                            isbn = "",
                            rating = book.rating?.average ?: 0.0
                        )
                    _bookList.add(bookModel)
                    repository.saveBooks(listOf(bookModel.toBookEntity()))
                }
                Log.d("API", "Total books in list: ${_bookList.size}")
            } catch (e: Exception) {
                _error.value = when {
                    e.message?.contains("401") == true -> "Invalid API key. Please check your configuration."
                    e.message?.contains("402") == true -> "Your API is finished. So please generate new API key"
                    e.message?.contains("429") == true -> "Too many requests. Please wait a moment."
                    e.message?.contains("timeout") == true -> "Connection timed out. Please check your internet."
                    e.message?.contains("Unable to resolve") == true -> "No internet connection."
                    else -> "Something went wrong. Please try again."
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _selectedBook = mutableStateOf<BookModel?>(null)
    val selectedBook : State<BookModel?> = _selectedBook

    private val _isDetailLoading = mutableStateOf(false)
    val isDetailLoading: State<Boolean> = _isDetailLoading
    fun fetchBookDetails(bookId:Long){
        viewModelScope.launch {
            _isDetailLoading.value = true
            try{

                val dbBook = repository.getBookById(bookId)
                if(dbBook?.isDetailsFetched == true){
                    val index = _bookList.indexOfFirst { it.id == bookId }
                    if (index != -1) {
                        _bookList[index] = dbBook.toBookModel()
                    }
                    return@launch
                }
                val detail = RetrofitInstance.api.getBookDetails(bookId, apiKey = currentApiKey,)
                val index = _bookList.indexOfFirst { it.id == bookId }
                if(index != -1){
                   val updatedBook = _bookList[index].copy(
                        bookPages = detail.number_of_pages?.toInt() ?: 0,
                        year = detail.publish_date?.toInt()?.toString() ?: "",
                        description = detail.description ?: "",
                        isbn = detail.identifiers?.isbn_13 ?: ""
                    )
                    _bookList[index] = updatedBook
                    _selectedBook.value = _bookList[index]
                    repository.updateBookDetails(updatedBook.toBookEntity().copy(isDetailsFetched = true))
                }
            }catch(e: Exception){
                Log.e("API", "Detail error: ${e.message}")
            }finally {
                _isDetailLoading.value = false
            }
        }
    }

    private var searchJob: kotlinx.coroutines.Job? = null

    fun onSearchChange(query: String) {
        _bookSearch.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (query.length > 2) {
                searchBooks(query)
            } else if (query.isEmpty()) {
                loadBooksFromDb()
            }
        }
    }
    private val _selectedFilter = mutableStateOf(BookFilter.ALL)
    val selectedFilter: State<BookFilter> = _selectedFilter

    private val _bookSearch = mutableStateOf("")
    val bookSearch: State<String> = _bookSearch

    fun onFilterChange(filter: BookFilter) {
        _selectedFilter.value = filter
    }

    private val _userBookStates = mutableStateListOf<UserBookState>()
    val userBookStates: List<UserBookState> get()= _userBookStates
    private fun removeIfNoAction(index: Int) {
        val state = _userBookStates[index]
        if (state.readStatus == ReadStatus.NONE && !state.isFavourite) {
            _userBookStates.removeAt(index)
        }
    }

    fun toggleFavourite(bookID:Long) {
        val index = _userBookStates.indexOfFirst { it.bookId == bookID }
        if (index != -1) {
            _userBookStates[index] = _userBookStates[index].copy(
                isFavourite = !_userBookStates[index].isFavourite
            )
            removeIfNoAction(index)
        } else {
            _userBookStates.add(UserBookState(bookID, isFavourite = true))
        }
        saveUserBookState(bookID)
    }

    fun setReadStatus(bookId: Long, status: ReadStatus) {
        val index = _userBookStates.indexOfFirst { it.bookId == bookId }
        if (index != -1) {
            val newStatus = if (_userBookStates[index].readStatus == status) ReadStatus.NONE else status
            _userBookStates[index] = _userBookStates[index].copy(readStatus = newStatus)
            removeIfNoAction(index)
        } else {
            _userBookStates.add(UserBookState(bookId = bookId, readStatus = status))
        }
        saveUserBookState(bookId)
    }

    private fun saveUserBookState(bookId:Long){
        viewModelScope.launch {
                val state = _userBookStates.find { it.bookId == bookId }
                if(state != null) {
                    repository.saveUserBookState(UserBookStateEntity(
                        state.bookId,state.readStatus.name,state.isFavourite
                    ))
                }
        }
    }

    private val _myBooksFilter = mutableStateOf(MyBooksFilter.ALL)
    val myBooksFilter: State<MyBooksFilter> = _myBooksFilter
    fun onMyBooksFilterChange(filter: MyBooksFilter) {
        _myBooksFilter.value = filter
    }
}

