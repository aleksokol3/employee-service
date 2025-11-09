package su.aleksokol3.employeeservice.integration;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import su.aleksokol3.employeeservice.IntegrationTestBase;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.service.EmployeeService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class EmployeeControllerIT extends IntegrationTestBase {

    private final EmployeeService employeeService;

    private final MockMvc mockMvc;

    @Test
    void findBy() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/employees"))
                .andExpectAll(
                     status().isOk(),
                     content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void findById() throws Exception {
        UUID id = UUID.fromString("00e5eb40-472e-40d2-9591-036287d20258");
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/api/v1/employees/" + id.toString()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ReadEmployeeDto dto = employeeService.findById(id);
        System.out.println(dto);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(dto)); // ?????
    }

    @Test
    void create() throws Exception {
        CreateEmployeeDto dto = new CreateEmployeeDto("Ivan", "Ivanovich", "Ivanov", 40, BigDecimal.valueOf(456.78));
        MockHttpServletRequestBuilder requestBuilder = post("http://localhost:8080/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").exists()
                );
    }

    @Test
    void create_payloadIsInvalid() throws Exception {
        CreateEmployeeDto dto = new CreateEmployeeDto("", "Ivanovich", "Ivanov", 40, BigDecimal.valueOf(456.78));
        MockHttpServletRequestBuilder requestBuilder = post("http://localhost:8080/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void update() {
    }

    @Test
    void deleteBy() {
    }

    @Test
    void deleteById() {
    }
}