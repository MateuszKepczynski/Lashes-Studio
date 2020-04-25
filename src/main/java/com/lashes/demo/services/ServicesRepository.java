package com.lashes.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services,Long>
{
    public Services findByName(String name);
}
