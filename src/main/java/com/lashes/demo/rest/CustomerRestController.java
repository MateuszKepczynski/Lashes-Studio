package com.lashes.demo.rest;

import com.lashes.demo.customer.Customer;
import com.lashes.demo.customer.CustomerRepository;
import com.lashes.demo.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customers")
public class CustomerRestController
{
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public CustomerRestController(CustomerRepository customerRepository, EmployeeRepository employeeRepository)
    {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId)
    {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomers()
    {
        if (customerRepository.findAll().isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        customer.setEmployee(employeeRepository.findById(1L).get());
        customerRepository.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody @Valid Customer customer, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (!customerOptional.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Customer result = customerOptional.get();

        result.setEmail(customer.getEmail());
        result.setFirstName(customer.getFirstName());
        result.setLastName(customer.getLastName());
        result.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(result);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long customerId)
    {
        Optional<Customer> result = customerRepository.findById(customerId);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        customerRepository.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
