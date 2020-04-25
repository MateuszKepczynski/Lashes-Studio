package com.lashes.demo.rest;

import com.lashes.demo.visit.Visit;
import com.lashes.demo.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visits")
public class VisitRestController
{
    VisitRepository visitRepository;

    @Autowired
    public VisitRestController(VisitRepository visitRepository)
    {
        this.visitRepository = visitRepository;
    }

    @GetMapping(value = "",produces = "application/json")
    public ResponseEntity<List<Visit>> getVisits()
    {
        if (visitRepository.findAll().isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(visitRepository.findAll(),HttpStatus.FOUND);
    }

    @GetMapping(value = "/{visitId}",produces = "application/json")
    public ResponseEntity<Visit> getVisit(@PathVariable Long visitId)
    {
        Optional<Visit> result = visitRepository.findById(visitId);

        if(!result.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.get(), HttpStatus.FOUND);
    }
}
