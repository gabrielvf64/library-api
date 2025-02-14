package com.box.library.loan;

import com.box.library.book.Book;
import com.box.library.book.BookRepository;
import com.box.library.book.BookStatus;
import com.box.library.exception.*;
import com.box.library.user.LibraryUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LibraryUserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, LibraryUserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan createLoan(Long userId, Long bookId) throws BookAlreadyBorrowedException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (book.getStatus() == BookStatus.BORROWED) {
            throw new BookAlreadyBorrowedException(bookId);
        }

        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        Loan loan = new Loan();
        return loanRepository.save(loan); // Loan loan = new Loan(userId, bookId); // return ERROR
    }
}