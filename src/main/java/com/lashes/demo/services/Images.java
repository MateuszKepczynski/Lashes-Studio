package com.lashes.demo.services;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lashes.demo.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Images extends BaseEntity
{

    @Column(name = "link")
    private String linkUrl;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "services_id")
    @JsonBackReference
    private Services services;

    public String getLinkUrl()
    {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }

    public Services getServices()
    {
        return services;
    }

    public void setServices(Services services)
    {
        this.services = services;
    }

}
