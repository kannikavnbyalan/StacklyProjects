package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    // Add book
    @PostMapping
    public ResponseEntity<String> addBook(
            @Valid @RequestBody Book book) {

        String result = service.addBook(book);

        if (result.equals("Book ID already exists")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    // Search book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> searchBook(
            @PathVariable int id) {

        Book book = service.searchBook(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    // Update availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<String> updateAvailability(
            @PathVariable int id,
            @RequestParam boolean available) {

        String result = service.updateAvailability(id, available);

        if (result.equals("Book not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable int id) {

        String result = service.deleteBook(id);

        if (result.equals("Book not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}