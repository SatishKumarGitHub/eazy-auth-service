package com.ez.authservice.service;

import com.ez.authservice.model.Customer;
import com.ez.authservice.repository.CustomerRepository;
import com.ez.authservice.security.SecurityCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" BankUserDetailsService getting called ..!!");

        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.isEmpty())
            throw new UsernameNotFoundException("User not found ");

        return new SecurityCustomer(customers.get(0));
    }
}
