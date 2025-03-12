package com.box.library.loan;

import com.box.library.request.CreateLoanRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toLoan(CreateLoanRequest request);
    CreateLoanRequest toCreateLoanRequest(Loan loan);
}
