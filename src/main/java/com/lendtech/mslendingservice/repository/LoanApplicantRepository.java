package com.lendtech.mslendingservice.repository;

import com.lendtech.mslendingservice.entity.LoanApplicants;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LoanApplicantRepository extends R2dbcRepository<LoanApplicants,Long> {
    Mono<LoanApplicants> findByMsisdn(String msisdn);
}
