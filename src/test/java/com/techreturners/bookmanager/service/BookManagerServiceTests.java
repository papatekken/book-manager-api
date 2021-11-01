package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.exception.BookAlreadyExistsException;
import com.techreturners.bookmanager.exception.BookNotFoundException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.model.Genre;

import com.techreturners.bookmanager.repository.BookManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DataJpaTest
public class BookManagerServiceTests {

    @Mock
    private BookManagerRepository mockBookManagerRepository;

    @InjectMocks
    private BookManagerServiceImpl bookManagerServiceImpl;

    @Test
    public void testGetAllBooksReturnsListOfBooks() {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));
        books.add(new Book(2L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Education));
        books.add(new Book(3L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Education));

        when(mockBookManagerRepository.findAll()).thenReturn(books);

        List<Book> actualResult = bookManagerServiceImpl.getAllBooks();

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(books);
    }

    @Test
    public void testAddABook() {

        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);

        when(mockBookManagerRepository.save(book)).thenReturn(book);
        Book actualResult = bookManagerServiceImpl.insertBook(book);
        assertThat(actualResult).isEqualTo(book);
    }

    @Test
    public void testGetBookById() {

        Long bookId = 5L;
        var book = new Book(5L, "Book Five", "This is the description for Book Five", "Person Five", Genre.Fantasy);

        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(true);
        Book actualResult = bookManagerServiceImpl.getBookById(bookId);

        assertThat(actualResult).isEqualTo(book);
    }

    //User Story 4 - Update Book By Id Solution
    @Test
    public void testUpdateBookById() {

        Long bookId = 5L;
        var book = new Book(5L, "Book Five", "This is the description for Book Five", "Person Five", Genre.Fantasy);

        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(true);
        when(mockBookManagerRepository.save(book)).thenReturn(book);

        bookManagerServiceImpl.updateBookById(bookId, book);

        verify(mockBookManagerRepository, times(1)).save(book);
    }

    //User Story 5 - Delete Book By Id Solution
    @Test
    public void testDeleteBookById() {

        Long bookId = 5L;
        var book = new Book(5L, "Book Five", "This is the description for Book Five", "Person Five", Genre.Fantasy);

        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(true);


        bookManagerServiceImpl.deleteBookById(bookId);

        verify(mockBookManagerRepository, times(1)).delete(book);
    }

    @Test
    public void testGetAllBooksWithNoRecordThrowException() {
        assertThatThrownBy(() -> bookManagerServiceImpl.getAllBooks()).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void testInsertBookWithExistsThrowException() {
        Long bookId = 4L;
        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(true);

        assertThatThrownBy(() -> bookManagerServiceImpl.insertBook(book)).isInstanceOf(BookAlreadyExistsException.class);
    }

    @Test
    public void testGetBookWithNoRecordThrowException() {
        Long bookId = 4L;
        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(false);

        assertThatThrownBy(() -> bookManagerServiceImpl.getBookById(bookId)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void testUpdateBookWithNoRecordThrowException() {
        Long bookId = 4L;
        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(false);
        assertThatThrownBy(() -> bookManagerServiceImpl.updateBookById(bookId, book)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void testDeleteBookWithNoRecordThrowException() {
        Long bookId = 4L;
        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
        when(mockBookManagerRepository.existsById(bookId)).thenReturn(false);
        assertThatThrownBy(() -> bookManagerServiceImpl.deleteBookById(bookId)).isInstanceOf(BookNotFoundException.class);
    }


}