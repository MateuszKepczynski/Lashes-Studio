package com.lashes.demo.customer;

import com.lashes.demo.services.Services;
import com.lashes.demo.services.ServicesRepository;
import com.lashes.demo.visit.DateHolderForIncome;
import com.lashes.demo.visit.Visit;
import com.lashes.demo.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController
{
    private static final String VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM = "employee/customers/createOrUpdateCustomerForm";
    private static final String VIEWS_EMPLOYEE_SHOW_INCOME_FORM = "employee/customers/showIncomeForm";

    private CustomerRepository customerRepository;
    private VisitRepository visitRepository;
    private ServicesRepository servicesRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, VisitRepository visitRepository, ServicesRepository servicesRepository)
    {
        this.customerRepository = customerRepository;
        this.visitRepository = visitRepository;
        this.servicesRepository = servicesRepository;
    }

    //#### FOR EMPLOYEE
    @GetMapping("")
    public String showCustomerList(Model model)
    {
        model.addAttribute("customerList", customerRepository.findAll());
        return "employee/customers/showCustomers";
    }

    @GetMapping("/showUpdateForm")
    public String updateCustomerForm(@RequestParam("customerId") Long id, Model model)
    {
        model.addAttribute("customer", customerRepository.findById(id));
        return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer")Customer customer, Model model)
    {
        if(!customer.validateFields(customer))
        {
            model.addAttribute("customer", customer);
            model.addAttribute("invalidFields","Invalid information");
            return VIEWS_CUSTOMER_CREATE_OR_UPDATE_FORM;
        }
        customerRepository.save(customer);
        return "redirect:/customers/";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") Long id)
    {
        customerRepository.deleteById(id);
        return "redirect:/customers/";
    }


    //$$$ INCOME PAGES
    @GetMapping("/show-income-form")
    public String showIncomeForm(Model model)
    {
        DateHolderForIncome tempVisitForIncomeCheck = new DateHolderForIncome();
        model.addAttribute("tempDate", tempVisitForIncomeCheck);
        return VIEWS_EMPLOYEE_SHOW_INCOME_FORM;
    }

    @PostMapping("/show-day-income")
    public String showDayIncome(@ModelAttribute("tempDate") DateHolderForIncome date, BindingResult bindingResult, Model model)
    {
        List<Visit> result = visitRepository.findByDate(date.getDate());

        if (result.isEmpty() || bindingResult.hasErrors())
        {
            model.addAttribute("tempDate", date);
            model.addAttribute("findError", "Nothing is there!");
            return VIEWS_EMPLOYEE_SHOW_INCOME_FORM;
        }

        List<Services> servicesList = new ArrayList<>();
        for (Visit tempVisit : result)
        {
            if(servicesRepository.findByName(tempVisit.getService()) == null)
            {
                Services services = new Services();
                services.setName(tempVisit.getService() + " #### CHECK PRICE THAT SERVICE HAS BEEN DELETED!!!! ####");
                services.setPrice(0.00);
                servicesList.add(services);
                continue;
            }
            servicesList.add(servicesRepository.findByName(tempVisit.getService()));
        }

        double totalPrice = 0.00;

        for (Services tempService : servicesList)
            totalPrice += tempService.getPrice();

        model.addAttribute("services", servicesList);
        model.addAttribute("totalPrice", totalPrice);

        return "employee/customers/showIncome";
    }

    @PostMapping("/show-year-income")
    public String showYearIncome(@ModelAttribute("tempDate") DateHolderForIncome date, Model model)
    {
        List<Visit> result = new ArrayList<>();
        if (date.getTempYearForIncome().matches("^([0-9]){4,}$"))
        {
            result = visitRepository.findByYear(Integer.parseInt(date.getTempYearForIncome()));
        }

        if (result.isEmpty())
        {
            model.addAttribute("tempDate", date);
            model.addAttribute("findError", "Nothing is there!");
            return VIEWS_EMPLOYEE_SHOW_INCOME_FORM;
        }

        List<Services> servicesList = new ArrayList<>();
        for (Visit tempVisit : result)
        {
            if(servicesRepository.findByName(tempVisit.getService()) == null)
            {
                Services services = new Services();
                services.setName(tempVisit.getService() + "#### CHECK PRICE THAT SERVICE HAS BEEN DELETED!!!!");
                services.setPrice(0.00);
                servicesList.add(services);
                continue;
            }
            servicesList.add(servicesRepository.findByName(tempVisit.getService()));
        }


        double totalPrice = 0.00;

        for (Services tempService : servicesList)
            totalPrice += tempService.getPrice();

        model.addAttribute("services", servicesList);
        model.addAttribute("totalPrice", totalPrice);

        return "employee/customers/showIncome";
    }


    @PostMapping("/show-month-income")
    public String showMonthIncome(@ModelAttribute("tempDate") DateHolderForIncome date, Model model)
    {
        List<Visit> result = new ArrayList<>();
        if (date.getTempYearForIncome().matches("^([0-9]){4,}$"))
        {
            result = visitRepository.findByMonth(date.getTempMonthForIncome(), Integer.parseInt(date.getTempYearForIncome()));
        }

        if (result.isEmpty())
        {
            model.addAttribute("tempDate", date);
            model.addAttribute("findError", "Nothing is there!");
            return VIEWS_EMPLOYEE_SHOW_INCOME_FORM;
        }

        List<Services> servicesList = new ArrayList<>();
        for (Visit tempVisit : result)
        {
            if(servicesRepository.findByName(tempVisit.getService()) == null)
            {
                Services services = new Services();
                services.setName(tempVisit.getService() + "#### CHECK PRICE THAT SERVICE HAS BEEN DELETED!!!!");
                services.setPrice(0.00);
                servicesList.add(services);
                continue;
            }
            servicesList.add(servicesRepository.findByName(tempVisit.getService()));
        }


        double totalPrice = 0.00;

        for (Services tempService : servicesList)
            totalPrice += tempService.getPrice();

        model.addAttribute("services", servicesList);
        model.addAttribute("totalPrice", totalPrice);
        return "employee/customers/showIncome";
    }

    //#### FOR CUSTOMER

    @GetMapping("/find-customer")
    public String findCustomerByPhoneNum(Model model)
    {
        model.addAttribute("customer", new Customer());
        return "customers/find-form";
    }


    @PostMapping("/visit-list-form")
    public String findCustomerVisitById(@ModelAttribute("customer") Customer tempCustom, Model model)
    {
        Customer customer = customerRepository.findCustomerByPhoneNumber(tempCustom.getPhoneNumber());
        if (customer == null)//if there is no customer in DB.
        {
            model.addAttribute("customer", new Customer());
            model.addAttribute("findError", "No Customer with given number.");
            return "customers/find-form";
        }
        model.addAttribute("visitList", visitRepository.findByCustomerIdOrderByDate(customer.getId()));
        return "customers/visitsList";
    }
}
