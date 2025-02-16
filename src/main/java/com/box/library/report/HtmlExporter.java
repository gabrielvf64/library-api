package com.box.library.report;

import com.box.library.loan.Loan;
import com.box.library.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// TODO[7]: Implementação de interface
@Service
public class HtmlExporter implements Exporter {

    private static final String HTML = "html";

    @Override
    public String export(List<Loan> loans, LoanStatus status) {
        if (loans.isEmpty()) {
            return """
                    <h1>Nenhum empréstimo encontrado com status: %s.</h1>
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
                <h1>Relatorio de emprestimos - Status: %s</h1>
                <table border="1">
                    <tr>
                        <th>Id do emprestimo</th>
                        <th>Ids dos livros</th>
                        <th>Id do usuario</th>
                        <th>Data do emprestimo</th>
                        <th>Data de retorno esperada</th>
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
