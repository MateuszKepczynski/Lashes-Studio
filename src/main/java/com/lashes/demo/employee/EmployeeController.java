package com.lashes.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/employees")
public class EmployeeController
{
    private static final String VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM = "admin/employees/createOrUpdateEmployeesForm";

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping("")
    public String showEmployeesListForm(Model model)
    {
        model.addAttribute("employeeList",employeeRepository.findAll());
        return "employee/employeesList";
    }

    @GetMapping("/add")
    public String add(Model model)
    {
        model.addAttribute("employee",new Employee());
        return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping("/update")
    public String update(@RequestParam("employeeId")Long id, Model model)
    {
        model.addAttribute("employee",employeeRepository.findById(id).get());
        return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee")Employee employee,Model model)
    {
        if(employee.validateFields(employee))
        {
            employeeRepository.save(employee);
            return "redirect:/admin/employees/";
        }

        model.addAttribute("fieldError","Invalid Fields!");
        model.addAttribute("employee",employee);
        return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;

    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId")Long id, Model model)
    {
        employeeRepository.deleteById(id);
        return  "redirect:/admin/employees/";
    }

    @GetMapping("/restricted-pages")
    public String restrictedPagesForm()
    {
        return "admin/pageList";
    }
}
