package com.example.libraryapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.libraryapp.models.Book
import com.example.libraryapp.models.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText

class AddBookActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var titleEditText: TextInputEditText
    private lateinit var authorEditText: TextInputEditText
    private lateinit var yearEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        dbHelper = DatabaseHelper(this)

        titleEditText = findViewById(R.id.editTextTitle)
        authorEditText = findViewById(R.id.editTextAuthor)
        yearEditText = findViewById(R.id.editTextYear)
        descriptionEditText = findViewById(R.id.editTextDescription)
        saveButton = findViewById(R.id.buttonSaveBook)

        saveButton.setOnClickListener {
            saveBook()
        }
    }

    private fun saveBook() {
        val title = titleEditText.text.toString().trim()
        val author = authorEditText.text.toString().trim()
        val yearStr = yearEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val year = try {
            yearStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid year", Toast.LENGTH_SHORT).show()
            return
        }

        val book = Book(
            title = title,
            author = author,
            year = year,
            description = description
        )

        val id = dbHelper.addBook(book)

        if (id > 0) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show()
        }
    }
}