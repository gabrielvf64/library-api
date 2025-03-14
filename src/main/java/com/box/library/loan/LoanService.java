package com.box.library.loan;

import com.box.library.book.Book;
import com.box.library.book.BookService;
import com.box.library.book.BookStatus;
import com.box.library.customer.Customer;
import com.box.library.customer.CustomerService;
import com.box.library.exception.BookNotFoundException;
import com.box.library.exception.CustomerLoansNotFoundException;
import com.box.library.exception.LoanNotFoundException;
import com.box.library.exception.PendingLoanException;
import com.box.library.report.Exporter;
import com.box.library.request.CreateLoanRequest;
import com.box.library.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository repository;
    private final List<Exporter> exporters;
    private final CustomerService customerService;
    private final BookService bookService;

    public LoanService(LoanRepository repository, List<Exporter> exporters,
                       CustomerService customerService, BookService bookService) {
        this.repository = repository;
        this.exporters = exporters;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    public List<FindAllLoansResponse> findAll() {
        var allLoans = repository.findAll();
        return allLoansToResponse(allLoans);
    }

    public FindLoanByCustomerResponse findByCustomerId(Long id) {
        var customer = customerService.findById(id);
        var loansByCustomerId = repository.findByCustomerId(id);
        if (loansByCustomerId.isEmpty()) {
            throw new CustomerLoansNotFoundException(id);
        }
        return loanByCustomerToResponse(customer, loansByCustomerId);
    }

    public CreateLoanResponse create(CreateLoanRequest request) {
        var customer = customerService.findById(request.customerId());
        if (hasPendingLoan(customer.getId())) {
            throw new PendingLoanException(customer.getId());
        }

        var books = validateAndGetAvailableBooksByIds(request.booksIds());
        var loan = new Loan(customer, new ArrayList<>(books));

        setBorrowedStatusToBooks(books);
        addNewBooksAssociations(loan, books);

        repository.save(loan);
        return createLoanToResponse(loan, new ArrayList<>(books), customer);
    }

    public Loan findById(Long loanId) {
        return repository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
    }

    public ReturnLoanResponse returnLoan(Long loanId) {
        var loan = findById(loanId);
        var books = loan.getBooks();

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.FINISHED);
        setAvailableStatusToBooks(new HashSet<>(books));

        repository.save(loan);
        return returnLoanToResponse(loan, new ArrayList<>(books), loan.getCustomer());
    }

    public ReportResponse generateLoanReport(String format, LoanStatus status) {

        var filteredLoans = repository.findByStatus(status);

        var exporter = getExporterFromFileFormat(format);

        var reportContent = exporter.export(filteredLoans, status);

        var contentType = getContentType(format);

        var extension = getFileExtension(format);

        return new ReportResponse(reportContent, contentType, extension);
    }

    private Exporter getExporterFromFileFormat(String format) {
        return exporters.stream()
                .filter(e -> e.getFileExtension().equalsIgnoreCase(format))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato não suportado: " + format));
    }

    private static String getContentType(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "text/csv";
            case "html" -> "text/html";
            default -> throw new IllegalArgumentException("Formato não suportado: " + format);
        };
    }

    private static String getFileExtension(String format) {
        return switch (format.toLowerCase()) {
            case "csv" -> "csv";
            case "html" -> "html";
            default -> throw new IllegalArgumentException("Formato não suportado: " + format);
        };
    }

    private boolean hasPendingLoan(Long customerId) {
        return repository.existsByCustomerIdAndStatusIn(customerId, List.of(LoanStatus.ACTIVE, LoanStatus.OVERDUE));
    }

    private Set<Book> validateAndGetAvailableBooksByIds(List<Long> booksIds) {
        List<Book> availableBooks = bookService.findAllAvailableByIds(booksIds);
        Set<Long> availableIds = availableBooks.stream()
                .map(Book::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = booksIds.stream()
                .filter(id -> isBookNotAvailable(id, availableIds))
                .collect(Collectors.toSet());

        if (hasMissingBooks(missingIds)) {
            throw new BookNotFoundException(missingIds.stream().toList());
        }

        return new HashSet<>(availableBooks);
    }

    private boolean hasMissingBooks(Set<Long> missingIds) {
        return !missingIds.isEmpty();
    }

    private boolean isBookNotAvailable(Long id, Set<Long> availableIds) {
        return !availableIds.contains(id);
    }

    private void setBorrowedStatusToBooks(Set<Book> books) {
        books.forEach(book -> book.setStatus(BookStatus.BORROWED));
    }

    private void setAvailableStatusToBooks(Set<Book> books) {
        books.forEach(book -> book.setStatus(BookStatus.AVAILABLE));
    }

    private void addNewBooksAssociations(Loan loan, Set<Book> books) {
        books.forEach(book -> book.getLoans().add(loan));
    }

    private CreateLoanResponse createLoanToResponse(Loan loan, List<Book> books, Customer customer) {
        var bookDetailsResponse = books.stream()
                .map(book -> new BookDetailsResponse(book.getTitle(), book.getIsbn()))
                .toList();
        return new CreateLoanResponse(loan.getCustomer().getId(), customer.getName(), bookDetailsResponse, loan.getLoanDate(), loan.getExpectedReturnDate(), loan.getStatus().name());
    }

    private ReturnLoanResponse returnLoanToResponse(Loan loan, List<Book> books, Customer customer) {
        var bookDetailsResponse = books.stream()
                .map(book -> new BookDetailsResponse(book.getTitle(), book.getIsbn()))
                .toList();
        return new ReturnLoanResponse(loan.getId(), customer.getName(), bookDetailsResponse, loan.getLoanDate(), loan.getExpectedReturnDate(), loan.getReturnDate(), loan.getStatus().name());
    }

    private FindLoanByCustomerResponse loanByCustomerToResponse(Customer customer, List<Loan> loans) {
        var loansDetailsResponseList = new ArrayList<LoanDetailsResponse>();
        for (Loan loan : loans) {
            var bookDetailsResponseList = loan.getBooks().stream()
                    .map(book -> new BookDetailsResponse(book.getTitle(), book.getIsbn()))
                    .toList();
            loansDetailsResponseList.add(new LoanDetailsResponse(loan.getId(), bookDetailsResponseList, loan.getLoanDate(), loan.getExpectedReturnDate(), loan.getReturnDate(), loan.getStatus().name()));
        }
        return new FindLoanByCustomerResponse(customer.getName(), loansDetailsResponseList);
    }

    private List<FindAllLoansResponse> allLoansToResponse(List<Loan> loans) {
        var allLoansResponse = new ArrayList<FindAllLoansResponse>();
        for (Loan loan : loans) {
            var bookDetailsResponseList = loan.getBooks().stream()
                    .map(book -> new BookDetailsResponse(book.getTitle(), book.getIsbn()))
                    .toList();
            allLoansResponse.add(new FindAllLoansResponse(loan.getId(), loan.getCustomer().getName(), bookDetailsResponseList,
                    loan.getLoanDate(), loan.getExpectedReturnDate(), loan.getReturnDate(), loan.getStatus().name()));
        }
        return allLoansResponse;
    }
}
