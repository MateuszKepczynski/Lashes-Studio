package com.lashes.demo.visit;

import com.lashes.demo.customer.Customer;
import com.lashes.demo.customer.CustomerRepository;
import com.lashes.demo.employee.Employee;
import com.lashes.demo.employee.EmployeeRepository;
import com.lashes.demo.services.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/visits")
public class VisitController
{
    private VisitRepository visitRepository;
    private ServicesRepository servicesRepository;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public VisitController(VisitRepository visitRepository, ServicesRepository servicesRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository)
    {
        this.visitRepository = visitRepository;
        this.servicesRepository = servicesRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/add")
    public String addVisitForm(@ModelAttribute("visit") Visit visit, Model model)
    {
        visit.setEmployee(employeeRepository.findByLastName(visit.getEmployee().getLastName()));
        model.addAttribute("visit", visit);
        model.addAttribute("servicesList", servicesRepository.findAll());
        return "visit/createOrUpdateVisitForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("visit") Visit visit, Model model)
    {
        Customer customer = visit.getCustomer();

        if(!customer.validateFields(customer))
        {
            model.addAttribute("servicesList", servicesRepository.findAll());
            model.addAttribute("visit", visit);
            model.addAttribute("invalidFields","Invalid information");
            return  "visit/createOrUpdateVisitForm";
        }
        Employee employeeResult = employeeRepository.findByLastName(visit.getEmployee().getLastName());
        List<Visit> result = visitRepository.findByEmployeeIdAndDateOrderByTimeOfVisit(employeeResult.getId(),visit.getDate());

        for(Visit tempVisit : result)
        {
            if (tempVisit.getTimeOfVisit().equals(visit.getTimeOfVisit()))
            {
                model.addAttribute("visit",visit);
                model.addAttribute("servicesList", servicesRepository.findAll());
                model.addAttribute("timeError", "This time is already taken!");
                return "visit/createOrUpdateVisitForm";
            }
        }

        Employee employee = employeeRepository.findByLastName(visit.getEmployee().getLastName());
        visit.setEmployee(null);

        if (customerRepository.findCustomerByPhoneNumber(customer.getPhoneNumber()) == null)
        {
            visit.setCustomer(null);
            employee.addCustomer(customer);
            customerRepository.save(customer);
        } else
        {
            customer = customerRepository.findCustomerByPhoneNumber(visit.getCustomer().getPhoneNumber());
        }
        visit.setCustomer(null);
        customer.addVisit(visit);
        employee.addVisit(visit);
        visitRepository.save(visit);
        return "redirect:/";
    }

    @PostMapping("/show-available")
    public String showAvailableVisits(@ModelAttribute("visit") Visit tempVisit,BindingResult bindingResult ,Model model)
    {
        if (tempVisit.getDate() == null || bindingResult.hasErrors())
        {
            model.addAttribute("visit",new Visit());
            model.addAttribute("dateError","Wrong date");
            model.addAttribute("employeeList", employeeRepository.findAll());
            return "visit/chooseMonth";
        } else
        {
            Employee employee = employeeRepository.findByLastName(tempVisit.getEmployee().getLastName());
            tempVisit.setEmployee(employee);
            List<Visit> visitList = visitRepository.findByEmployeeIdAndDateOrderByTimeOfVisit(employee.getId(),tempVisit.getDate());
            List<String> availableTime = new ArrayList<>();

            if (!visitList.isEmpty())
            {
                for (Visit visit : visitList)
                {
                    if (!visit.getTimeOfVisit().equals("9:00"))
                        availableTime.add("9:00");

                    if (!visit.getTimeOfVisit().equals("10:30"))
                        availableTime.add("10:30");

                    if (!visit.getTimeOfVisit().equals("12:00"))
                        availableTime.add("12:00");

                    if (!visit.getTimeOfVisit().equals("13:30"))
                        availableTime.add("13:30");

                    if (!visit.getTimeOfVisit().equals("15:00"))
                        availableTime.add("15:00");

                    if (!visit.getTimeOfVisit().equals("16:30"))
                        availableTime.add("16:00");
                }
            } else
            {
                availableTime.add("9:00");
                availableTime.add("10:30");
                availableTime.add("12:00");
                availableTime.add("13:30");
                availableTime.add("15:00");
                availableTime.add("16:00");
            }

            model.addAttribute("availTimes", availableTime);
            model.addAttribute("visit", tempVisit);
            return "visit/showAvailableVisits";
        }
    }

    @GetMapping("/choose-month")
    public String monthSelectForm(Model model)
    {
        Visit tempVisitForDate = new Visit();
        model.addAttribute("visit", tempVisitForDate);
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "visit/chooseMonth";
    }
}