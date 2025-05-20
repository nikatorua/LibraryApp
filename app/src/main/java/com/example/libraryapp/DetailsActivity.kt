package com.example.libraryapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.libraryapp.models.DatabaseHelper

class DetailsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        dbHelper = DatabaseHelper(this)

        titleTextView = findViewById(R.id.textViewDetailTitle)
        authorTextView = findViewById(R.id.textViewDetailAuthor)
        yearTextView = findViewById(R.id.textViewDetailYear)
        descriptionTextView = findViewById(R.id.textViewDetailDescription)

        val bookId = intent.getIntExtra("BOOK_ID", -1)

        if (bookId != -1) {
            loadBookDetails(bookId)
        } else {
            finish()
        }
    }

    private fun loadBookDetails(bookId: Int) {
        val book = dbHelper.getBook(bookId)

        book?.let {
            titleTextView.text = it.title
            authorTextView.text = "Author: ${it.author}"
            yearTextView.text = "Published: ${it.year}"
            descriptionTextView.text = it.description
        }
    }
}