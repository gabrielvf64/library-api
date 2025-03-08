package com.box.library.report;

import com.box.library.loan.Loan;
import com.box.library.loan.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// TODO[8]: Classe concreta - Implementação de interface
@Service
public non-sealed class CsvExporter implements Exporter {

    public static final String CSV = "csv";

    @Override
    public String export(List<Loan> loans, LoanStatus status) {
        if (loans.isEmpty()) {
            return "Não foram encontrados empréstimos com esse status: " + status;
        }

        String header = """
                ID do emprestimo, IDs dos livros, Cliente, Data do emprestimo, Data esperada de devolucao
                """;

        String rows = loans.stream()
                .map(loan -> String.format(
                        "%d,%s,%s,%s,%s",
                        loan.getId(),

                        loan.getBooksIds()
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(" ")),

                        loan.getCustomer().getName(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate()

                ))
                .collect(Collectors.joining("\n"));

        return header + "\n" + rows;
    }

    @Override
    public String getFileExtension() {
        return CSV;
    }
}
