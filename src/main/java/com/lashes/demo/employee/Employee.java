package com.lashes.demo.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lashes.demo.customer.Customer;
import com.lashes.demo.model.Person;
import com.lashes.demo.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee extends Person
{
    @Column(name = "address")

    private String address;

    @Column(name = "city")

    private String city;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonManagedReference
    private List<Customer> customerList;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    @JsonManagedReference
    private List<Visit> visitList;

    public Employee()
    {
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public List<Customer> getCustomerList()
    {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList)
    {
        this.customerList = customerList;
    }

    public void addCustomer(Customer customer)
    {
        if (customerList == null)
            customerList = new ArrayList<>();
        customerList.add(customer);
        customer.setEmployee(this);
    }

    public void addVisit(Visit visit)
    {
        if (visitList == null)
            visitList = new ArrayList<>();
        visitList.add(visit);
        visit.setEmployee(this);
    }

    public boolean validateFields(Employee employee)
    {
        //TODO improve validation
        return employee.getPhoneNumber().matches("[0-9]{9}") ||
                employee.getFirstName().matches("^([A-ZĄĘŁŃÓŚŹŻ]+[a-ząęłńóśźż]{1,20})$") ||
                employee.getLastName().matches("^([A-ZĄĘŁŃÓŚŹŻ]+[a-ząęłńóśźż]{1,25})$");
    }
}
