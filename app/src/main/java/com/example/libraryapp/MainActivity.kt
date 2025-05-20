package com.example.libraryapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.adapters.BookAdapter
import com.example.libraryapp.models.Book
import com.example.libraryapp.models.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), BookAdapter.OnItemClickListener {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddBook: FloatingActionButton
    private val bookList = ArrayList<Book>()

    companion object {
        const val ADD_BOOK_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        fabAddBook = findViewById(R.id.fabAddBook)

        setupRecyclerView()
        loadBooks()

        fabAddBook.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE)
        }
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(bookList, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }
    }

    private fun loadBooks() {
        val books = dbHelper.getAllBooks()
        bookAdapter.updateData(books)
    }

    override fun onItemClick(book: Book) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("BOOK_ID", book.id)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            loadBooks()
        }
    }

    override fun onResume() {
        super.onResume()
        loadBooks()
    }
}