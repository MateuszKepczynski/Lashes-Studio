package com.lashes.demo.services;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lashes.demo.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
public class Services extends BaseEntity
{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "services")
    @JsonManagedReference
    List<Images> imagesList;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public List<Images> getImagesList()
    {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList)
    {
        this.imagesList = imagesList;
    }

    public void addImage(Images image)
    {
        if (imagesList==null)
            imagesList=new ArrayList<>();
        imagesList.add(image);
        image.setServices(this);
    }

    public boolean validateFields(Services services)
    {
        //TODO improve validation
        return services.getName().matches("^([A-Za-z0-9\\s]){2,}$") ||
                Double.toString(services.getPrice()).matches("^([0-9]+.[0-9]{2,})$")  ||
                services.getDescription().matches("^([A-Za-z0-9\\s.,]+)$");
    }
}
