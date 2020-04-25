package com.lashes.demo.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeEntityRepositoryTest
{
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee mike;

    @BeforeEach
    void setup()
    {
        mike = new Employee();
        mike.setId(1L);
        mike.setFirstName("Save");
        mike.setLastName("Him");
        mike.setAddress("Address Address");
        mike.setCity("City City");
        mike.setPhoneNumber("22233311");
    }

    @Test
    void injectedComponentsAreNotNull()
    {
        assertThat(employeeRepository).isNotNull();
    }

    @Test
    void whenSavedThenFindByLastName()
    {
        employeeRepository.save(mike);
        assertThat(employeeRepository.findByLastName("Him")).isNotNull();
    }

    @Test
    void whenSavedThenFindBy()
    {
        employeeRepository.save(mike);
        assertThat(employeeRepository.findById(1L).get()).isNotNull();
    }

    @Test
    void whenFindByLastNameIsNull()
    {

        assertNull(employeeRepository.findByLastName("Him"));
    }
}
