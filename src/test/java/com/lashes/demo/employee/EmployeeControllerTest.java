package com.lashes.demo.employee;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup()
    {
        Employee mike = new Employee();
        mike.setId(1L);
        mike.setFirstName("Mike");
        mike.setLastName("Wazi");
        mike.setAddress("Some Address");
        mike.setCity("Some City");
        mike.setPhoneNumber("111222333");

        Employee johny = new Employee();
        johny.setId(2L);
        johny.setFirstName("Johny");
        johny.setLastName("Gregor");
        johny.setAddress("Nice Address");
        johny.setCity("Nice City");
        johny.setPhoneNumber("333222111");
        given(this.employeeRepository.findAll()).willReturn(Lists.newArrayList(mike,johny));
    }

    @Test
    void testShowEmployeeListHtml() throws Exception
    {
        mockMvc.perform(get("/admin/employees/")).andExpect(status().isOk()).andExpect(model()
                .attributeExists("employeeList")).andExpect(view().name("employee/employeesList"));
    }
}
