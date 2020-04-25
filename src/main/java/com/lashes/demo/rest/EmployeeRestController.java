package com.lashes.demo.rest;

import com.lashes.demo.employee.Employee;
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
@RequestMapping("/api/employees")
public class EmployeeRestController
{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRestController(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(value = "/{employeeId}", produces = "application/json")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId)
    {
        Optional<Employee> result = employeeRepository.findById(employeeId);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get(), HttpStatus.FOUND);
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Employee>> getEmployees()
    {
        if (employeeRepository.findAll().isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        employeeRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{employeeId}", produces = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody @Valid Employee employee, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Employee> result = employeeRepository.findById(employeeId);
        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Employee tempEmployee = result.get();

        tempEmployee.setFirstName(employee.getFirstName());
        tempEmployee.setLastName(employee.getLastName());
        tempEmployee.setAddress(employee.getAddress());
        tempEmployee.setCity(employee.getCity());
        tempEmployee.setPhoneNumber(employee.getPhoneNumber());

        employeeRepository.save(tempEmployee);
        return new ResponseEntity<>(tempEmployee, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{employeeId}", produces = "application/json")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long employeeId)
    {
        Optional<Employee> result = employeeRepository.findById(employeeId);

        if (!result.isPresent())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeRepository.deleteById(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}