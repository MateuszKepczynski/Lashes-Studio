package com.lashes.demo.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lashes.demo.employee.Employee;
import com.lashes.demo.model.Person;
import com.lashes.demo.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends Person
{
    @Column(name = "email")
    @NotNull
    private String email;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    @JsonManagedReference(value ="customerRef")
    private List<Visit> visitList;

    public Customer()
    {
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public List<Visit> getVisitList()
    {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList)
    {
        this.visitList = visitList;
    }

    public void addVisit(Visit visit)
    {
        if(visitList==null)
            visitList=new ArrayList<>();
        visitList.add(visit);
        visit.setCustomer(this);
    }

    public boolean validateFields(Customer customer)
    {
        //TODO improve validation
        if (customer.getPhoneNumber().matches("[0-9]{9}") ||
                customer.getFirstName().matches("^([A-ZĄĘŁŃÓŚŹŻ]+[a-ząęłńóśźż]{1,20})$") ||
                customer.getLastName().matches("^([A-ZĄĘŁŃÓŚŹŻ]+[a-ząęłńóśźż]{1,25})$")||
                customer.getEmail().matches("^[a-z0-9]+@[a-z0-9.-]+\\.[a-z]{2,5}+$"))
            return true;
        return false;
    }

}
