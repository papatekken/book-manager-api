package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.exception.BookAlreadyExistsException;
import com.techreturners.bookmanager.exception.BookNotFoundException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.repository.BookManagerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    BookManagerRepository bookManagerRepository;

    public BookManagerServiceImpl(BookManagerRepository bookManagerRepository) {
        this.bookManagerRepository = bookManagerRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        if(books.isEmpty())
            throw new BookNotFoundException();
        return books;
    }

    @Override
    public Book insertBook(Book book) {

        if (bookManagerRepository.existsById(book.getId()))
              throw new BookAlreadyExistsException();
        return bookManagerRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {

        if (!bookManagerRepository.existsById(id))
            throw new BookNotFoundException();
        return bookManagerRepository.findById(id).get();
    }

    //User Story 4 - Update Book By Id Solution
    @Override
    public void updateBookById(Long id, Book book) {
        if (!bookManagerRepository.existsById(id))
            throw new BookNotFoundException();

        Book retrievedBook = bookManagerRepository.findById(id).get();

        retrievedBook.setTitle(book.getTitle());
        retrievedBook.setDescription(book.getDescription());
        retrievedBook.setAuthor(book.getAuthor());
        retrievedBook.setGenre(book.getGenre());

        bookManagerRepository.save(retrievedBook);
    }

    //User Story 5 - Delete Book By Id Solution
    @Override
    public void deleteBookById(Long id) {
        if (!bookManagerRepository.existsById(id))
            throw new BookNotFoundException();
        Book retrievedBook = bookManagerRepository.findById(id).get();
        bookManagerRepository.delete(retrievedBook);
    }
}
