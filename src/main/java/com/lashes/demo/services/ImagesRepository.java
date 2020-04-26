package com.lashes.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images,Long>
{
    List<Images> findByServicesId(Long id);
}
