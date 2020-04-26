package com.lashes.demo.services;

import com.lashes.demo.visit.Visit;
import com.lashes.demo.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServicesController
{
    private ServicesRepository serviceRepository;
    private ImagesRepository imagesRepository;
    @Autowired
    public ServicesController(ServicesRepository serviceRepository, ImagesRepository imagesRepository)
    {
        this.serviceRepository = serviceRepository;
        this.imagesRepository = imagesRepository;
    }

    //#### CUSTOMER SITES ####

    @GetMapping("/services")
    public String showServicesListForm(Model model)
    {
        model.addAttribute("serviceList",serviceRepository.findAll());
        return "services/servicesList";
    }

    @GetMapping("services/detailed-info")
    public String showDetailedForm(@RequestParam("servicesId")Long id,Model model)
    {
        model.addAttribute("service",serviceRepository.findById(id).get());
        return "services/showDetailedInfo";
    }

    //#### ADMIN SITES ####

    @GetMapping("admin/services")
    public String showServicesListFormForAdmin(Model model)
    {
        model.addAttribute("serviceList",serviceRepository.findAll());
        return "admin/services/servicesList";
    }

    @GetMapping("admin/services/add")
    public String addForm(Model model)
    {
        model.addAttribute("imageUrl",new Images());
        model.addAttribute("services",new Services());
        return "admin/services/createOrUpdateServicesForm";
    }

    @GetMapping("admin/services/update")
    public String updateForm(@RequestParam("servicesId")Long id,Model model)
    {
        model.addAttribute("imageUrl",new Images());
        model.addAttribute("services",serviceRepository.findById(id).get());
        return "admin/services/createOrUpdateServicesForm";
    }

    @PostMapping("admin/services/save")
    public String save(@ModelAttribute("services") Services services, @ModelAttribute("imageUrl") Images imageUrl, Model model)
    {
        if(! services.validateFields(services)) //Validating fields
        {
            model.addAttribute("fieldsError","Invalid information in fields!");
            model.addAttribute("imageUrl",imageUrl);
            model.addAttribute("services",services);
            return "admin/services/createOrUpdateServicesForm";
        }

        if(services.getId() != null && services.getId() > 0) // if there's service in DB
        {
            if(services.imagesList == null || services.imagesList.get(0).getLinkUrl().isEmpty()) // when base img is empty
            {
                services.imagesList = serviceRepository.findByName(services.getName()).imagesList;
                model.addAttribute("fieldsError","Invalid information in fields!");
                model.addAttribute("imageUrl",imageUrl);
                model.addAttribute("services",services);
                return "admin/services/createOrUpdateServicesForm";
            }
        }
        else // if there's no service in DB
        {
            if(imageUrl.getLinkUrl().isEmpty()) // if base image is empty
            {
                model.addAttribute("fieldsError","Invalid information in fields!");
                model.addAttribute("imageUrl",imageUrl);
                model.addAttribute("services",services);
                return "admin/services/createOrUpdateServicesForm";
            }
        }

        Services result = serviceRepository.findByName(services.getName());
        if( result!= null )
        {
            if(! imageUrl.getLinkUrl().isEmpty())
            {
                result.addImage(imageUrl);
                imagesRepository.save(imageUrl);
            }

            for(Images tempImage : services.imagesList)
            {
                tempImage.setServices(services);
            }
            services.imagesList.get(0).setId(imagesRepository.findByServicesId(services.getId()).get(0).getId());
            serviceRepository.save(services);
            return "redirect:/admin/services";
        }
        else // if there's no such service
        {
            services.addImage(imageUrl);
            imagesRepository.save(imageUrl);
            serviceRepository.save(services);
            return "redirect:/admin/services";
        }

    }

    @GetMapping("admin/services/delete")
    public String deleteForm(@RequestParam("servicesId")Long id)
    {
        serviceRepository.deleteById(id);
        return "redirect:/admin/services";
    }
}
