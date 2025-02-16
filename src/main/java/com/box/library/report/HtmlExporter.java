package com.box.library.report;

import com.box.library.loan.Loan;
import com.box.library.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HtmlExporter implements Exporter {

    private static final String HTML = "html";

    @Override
    public String export(List<Loan> loans, LoanStatus status) {
        if (loans.isEmpty()) {
            return """
                    <h1>No loans found with status: %s.</h1>
                    """.formatted(status);
        }

        String rows = loans.stream()
                .map(loan -> """
                        <tr>
                            <td>%d</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                        </tr>
                        """.formatted(
                        loan.getId(),
                        loan.getBooksIds(),
                        loan.getUserId(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate()
                ))
                .collect(Collectors.joining());

        return """
                <h1>Loans Report - Status: %s</h1>
                <table border="1">
                    <tr>
                        <th>Loan id</th>
                        <th>Books ids</th>
                        <th>User id</th>
                        <th>Loan Date</th>
                        <th>Expected Return Date</th>
                    </tr>
                %s
                </table>
                """.formatted(status, rows);
    }

    @Override
    public String getFileExtension() {
        return HTML;
    }
}
