package com.lendtech.mslendingservice.repository;

import com.lendtech.mslendingservice.entity.LoanTable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LoanRepository extends R2dbcRepository<LoanTable, Long> {
    Mono<LoanTable> findByLoanApplicantIdAndActiveStatusIsTrue(long applicantId);
}
