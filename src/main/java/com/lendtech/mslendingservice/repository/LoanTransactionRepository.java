package com.lendtech.mslendingservice.repository;

import com.lendtech.mslendingservice.entity.LoanTransaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTransactionRepository extends R2dbcRepository<LoanTransaction, Long> {
}
