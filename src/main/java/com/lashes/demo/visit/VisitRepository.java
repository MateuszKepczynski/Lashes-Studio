package com.lashes.demo.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit,Long>
{
    public List<Visit> findByDate(Date date);
    public List<Visit> findByCustomerIdOrderByDate(Long id);
    public List<Visit> findByEmployeeIdAndDateOrderByTimeOfVisit(Long id,Date date);
    public List<Visit> findByService(String serviceName);

    @Query("SELECT v FROM Visit v WHERE EXTRACT(MONTH FROM v.date) = :givenMonth AND EXTRACT(YEAR FROM v.date) = :givenYear")
    public List<Visit> findByMonth(@Param("givenMonth")int givenMonth, @Param("givenYear") int givenYear);

    @Query("SELECT v FROM Visit v WHERE EXTRACT(YEAR FROM v.date) = :givenYear")
    public List<Visit> findByYear(@Param("givenYear")int givenYear);
}
