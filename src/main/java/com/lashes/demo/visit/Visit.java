package com.lashes.demo.visit;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lashes.demo.customer.Customer;
import com.lashes.demo.employee.Employee;
import com.lashes.demo.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "visit")
public class Visit extends BaseEntity
{
    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "time_of_visit")
    private String timeOfVisit;

    @Column(name = "service")
    private String service;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "customerRef")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTimeOfVisit()
    {
        return timeOfVisit;
    }

    public void setTimeOfVisit(String timeOfVisit)
    {
        this.timeOfVisit = timeOfVisit;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }
}
