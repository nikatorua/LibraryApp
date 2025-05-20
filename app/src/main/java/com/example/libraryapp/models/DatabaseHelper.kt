package com.example.libraryapp.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BookLibrary.db"

        const val TABLE_BOOKS = "books"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_YEAR = "year"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createBookTable = ("CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT" + ")")
        db.execSQL(createBookTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    fun addBook(book: Book): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, book.title)
        values.put(COLUMN_AUTHOR, book.author)
        values.put(COLUMN_YEAR, book.year)
        values.put(COLUMN_DESCRIPTION, book.description)

        val id = db.insert(TABLE_BOOKS, null, values)
        db.close()
        return id
    }

    fun getAllBooks(): ArrayList<Book> {
        val bookList = ArrayList<Book>()
        val selectQuery = "SELECT * FROM $TABLE_BOOKS ORDER BY $COLUMN_TITLE"

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                )
                bookList.add(book)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return bookList
    }

    fun getBook(id: Int): Book? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_BOOKS,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_YEAR, COLUMN_DESCRIPTION),
            "$COLUMN_ID=?",
            arrayOf(id.toString()),
            null, null, null, null
        )

        var book: Book? = null
        if (cursor.moveToFirst()) {
            book = Book(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            )
        }

        cursor.close()
        db.close()
        return book
    }
}
