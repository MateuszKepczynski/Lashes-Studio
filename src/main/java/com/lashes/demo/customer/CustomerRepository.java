package com.lashes.demo.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long>
{
    public Customer findCustomerByPhoneNumber(String phoneNUmber);
}
