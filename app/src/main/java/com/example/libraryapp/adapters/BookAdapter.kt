package com.example.libraryapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.models.Book

class BookAdapter(
    private val books: ArrayList<Book>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(book: Book)
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val authorTextView: TextView = itemView.findViewById(R.id.textViewAuthor)
        val yearTextView: TextView = itemView.findViewById(R.id.textViewYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = books[position]

        holder.titleTextView.text = currentBook.title
        holder.authorTextView.text = currentBook.author
        holder.yearTextView.text = currentBook.year.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }
    }

    override fun getItemCount() = books.size

    fun updateData(newBooks: ArrayList<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
}