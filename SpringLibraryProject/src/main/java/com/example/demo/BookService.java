package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // Add book
    public String addBook(Book book) {

        for (Book b : books) {

            if (b.getBookId() == book.getBookId()) {
                return "Book ID already exists";
            }
        }

        books.add(book);

        return "Book added successfully";
    }

    // Search book by ID
    public Book searchBook(int id) {

        for (Book b : books) {

            if (b.getBookId() == id) {
                return b;
            }
        }

        return null;
    }

    // Update availability
    public String updateAvailability(int id, boolean available) {

        for (Book b : books) {

            if (b.getBookId() == id) {

                b.setAvailable(available);

                return "Book availability updated successfully";
            }
        }

        return "Book not found";
    }

    // Delete book
    public String deleteBook(int id) {

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getBookId() == id) {

                books.remove(i);

                return "Book deleted successfully";
            }
        }

        return "Book not found";
    }
}