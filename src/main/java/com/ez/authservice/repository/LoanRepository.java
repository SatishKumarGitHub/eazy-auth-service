package com.ez.authservice.repository;

import java.util.List;

import com.ez.authservice.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {


    @PreAuthorize("hasRole('ADMIN')")
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
