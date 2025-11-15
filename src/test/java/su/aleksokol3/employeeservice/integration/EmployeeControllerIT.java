package su.aleksokol3.employeeservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import su.aleksokol3.employeeservice.IntegrationTestBase;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.service.implementaion.SpecBuilder;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest                     // создаёт и загружает полный ApplicationContext
class EmployeeControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Test find by id functionality")
    void givenId_whenFindById_thenSuccessResponse() throws Exception {
        // given
        Employee entity = DataUtils.getJuanRodriguezTransient();
        Employee savedEntity = employeeRepository.save(entity);
        UUID id = savedEntity.getId();
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(entity.getId().toString())))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(entity.getFirstName())))
//                .andExpect(jsonPath("$.patronymic", CoreMatchers.is(entity.getPatronymic())))         // ???
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(entity.getLastName())))
                .andExpect(jsonPath("$.salary").value(entity.getSalary()))
                .andExpect(jsonPath("$.hiringDate", CoreMatchers.is(entity.getHiringDate().toString())));
    }
    @Test
    @DisplayName("Test find by incorrect id functionality")
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() throws Exception {
        // given
        UUID incorrectId = UUID.randomUUID();
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/" + incorrectId)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").exists());
    }
//
    @Test
    @DisplayName("Test find by filter functionality")
    void givenFilter_whenFindByFilter_thenEmployeesAreReturned() throws Exception {
        // given
        Employee entityTransient1 = DataUtils.getJuanRodriguezTransient();
        Employee entityTransient2 = DataUtils.getIvanAlonsoTransient();
        Employee entityTransient3 = DataUtils.getMariaAlonsoTransient();
        employeeRepository.saveAll(List.of(entityTransient1, entityTransient2, entityTransient3));

        Map<String, List<String>> map = new HashMap<>();
        map.put("lastName", List.of("Alonso"));         // ~ searchEmployeeFilter.setLastName("Alonso")
        map.put("sort", List.of("firstName"));
        MultiValueMap<String, String> multiValueMap = new MultiValueMapAdapter<>(map);
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(multiValueMap));
        // then
        result.andExpect(status().isOk())
                .andDo(System.out::println)
                .andExpect(jsonPath("$.employees[0].firstName", CoreMatchers.is("Ivan")))
                .andExpect(jsonPath("$.employees[0].lastName", CoreMatchers.is("Alonso")))
                .andExpect(jsonPath("$.total").value(2));
    }

    @Test
    @DisplayName("Test create functionality")
    void givenCreateEmployeeDto_whenCreateEmployee_thenSuccessResponse() throws Exception {
        // given
        CreateEmployeeDto dto = DataUtils.getJuanRodriguezCreateDto();
        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        // then
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Test create with invalid dto functionality")
    void givenInvalidCreateEmployeeDto_whenCreateEmployee_thenErrorResponse() throws Exception {
        // given
        CreateEmployeeDto dto = DataUtils.getJuanRodriguezInvalidCreateDto();
        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errors", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test update functionality")
    void givenPatchDtoAndId_whenUpdate_thenSuccessResponse() throws Exception {
        // given
        Employee transientEntity = DataUtils.getJuanRodriguezTransient();   // salary = 222.22
        Employee savedEntity = employeeRepository.save(transientEntity);
        UUID id = savedEntity.getId();
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezPatchDto();        // salary = 777.77
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        Employee updatedEntity = employeeRepository.findById(id).orElse(null);
        // then
        result.andExpect(status().isOk());
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedEntity.getSalary()).isEqualByComparingTo(dto.salary().get());
    }

    @Test
    @DisplayName("Test update with incorrect id functionality")
    void givenPatchDtoAndIncorrectId_whenUpdate_thenErrorResponse() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezPatchDto();
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        Employee updatedEntity = employeeRepository.findById(id).orElse(null);
        // then
        assertThat(updatedEntity).isNull();
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Test update with invalid dto functionality")
    void givenInvalidPatchDtoAndId_whenUpdate_thenErrorResponse() throws Exception {
        // given
        Employee entity = DataUtils.getJuanRodriguezTransient();
        Employee savedEntity = employeeRepository.save(entity);
        UUID id = savedEntity.getId();
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezInvalidPatchDto();
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @DisplayName("Test delete by id functionality")
    void givenId_whenDeleteById_thenNoContentResponse() throws Exception {
        // given
        Employee transientEntity = DataUtils.getJuanRodriguezTransient();
        Employee savedEntity = employeeRepository.save(transientEntity);
        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/employees/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON));
        Employee deletedEntity = employeeRepository.findById(savedEntity.getId()).orElse(null);
        // then
        result.andExpect(status().isNoContent());
        assertThat(deletedEntity).isNull();
    }

    @Test
    @DisplayName("Test delete by filter functionality")
    void givenFilter_whenDeleteByFilter_thenNoContentResponse() throws Exception {
        // given
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        Employee luisHernandezTransient = DataUtils.getLuisHernandezTransient();
        Employee mariaAlonsoTransient = DataUtils.getMariaAlonsoTransient();
        Employee ivanAlonsoTransient = DataUtils.getIvanAlonsoTransient();
        Employee gabrielMarquezTransient = DataUtils.getGabrielMarquezTransient();

        employeeRepository.saveAll(List.of(
                juanRodriguezTransient,
                luisHernandezTransient,
                mariaAlonsoTransient,
                ivanAlonsoTransient,
                gabrielMarquezTransient));
        Map<String, List<String>> map = new HashMap<>();
        map.put("lastName", List.of("Alonso"));         // ~ searchEmployeeFilter.setLastName("Alonso")
        MultiValueMap<String, String> multiValueMap = new MultiValueMapAdapter<>(map);
        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(multiValueMap));
        SearchEmployeeFilter findAllFilter = SearchEmployeeFilter.builder().build();
        Specification<Employee> spec = SpecBuilder.buildSpec(findAllFilter);
        Page<Employee> byFilter = employeeRepository.findAll(spec, PageRequest.of(0, 10));
        // then
        result.andExpect(status().isNoContent());
        assertThat(byFilter.getTotalElements()).isEqualTo(3);
        assertThat(byFilter).hasSize(3);
    }
}