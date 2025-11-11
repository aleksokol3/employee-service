package su.aleksokol3.employeeservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import su.aleksokol3.employeeservice.IntegrationTestBase;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.mapper.EmployeeMapper;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
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
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        ReadEmployeeDto dto = EmployeeMapper.INSTANCE.entityToReadDto(employeeRepository.findById(savedEntity.getId()).get());

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(entity.getId().toString())))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(entity.getFirstName())))
//                .andExpect(jsonPath("$.patronymic", CoreMatchers.is(entity.getPatronymic())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(entity.getLastName())));
//                .andExpect(jsonPath("$.salary", CoreMatchers.is(entity.getSalary())))
//                .andExpect(jsonPath("$.hiringDate", CoreMatchers.is(entity.getHiringDate())));
        assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }
//
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
                .andExpect(jsonPath("$.path", CoreMatchers.is("http://localhost/api/v1/employees/" + incorrectId)))
                .andExpect(jsonPath("$.error", CoreMatchers.is("Пользователь с id %s не найден".formatted(incorrectId))));
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

        SearchEmployeeFilter filter = SearchEmployeeFilter.builder().lastName("Alonso").build();
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)));
        // then
        result.andExpect(status().isOk())
                .andDo(System.out::println)
                .andExpect(jsonPath("$[0].firstName", CoreMatchers.is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Alonso")))
                .andExpect(jsonPath("$.length").value(2));
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
                .andExpect(jsonPath("$.path", CoreMatchers.is("http://localhost/api/v1/employees")))    // where is a PORT???
                .andExpect(jsonPath("$.errors", CoreMatchers.notNullValue()));
//                .andExpect(jsonPath("$.errors", CoreMatchers.hasItem("AGE: must be greater than or equal to 1")))
//                .andExpect(jsonPath("$.errors[0]", CoreMatchers.containsString("AGE")));
    }

    @Test
    @DisplayName("Test update functionality")
    void givenPatchDto_whenUpdate_thenSuccessResponse() throws Exception {
        // given
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezPatchDto();        // salary = 777.77
        Employee transientEntity = DataUtils.getJuanRodriguezTransient();   // salary = 222.22
        Employee savedEntity = employeeRepository.save(transientEntity);
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        Employee updatedEntity = employeeRepository.findById(savedEntity.getId()).orElse(null);
        // then
        result.andExpect(status().isOk());
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedEntity.getSalary()).isEqualByComparingTo(dto.salary().get());
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

        employeeRepository.saveAll(List.of(
                juanRodriguezTransient,
                luisHernandezTransient,
                mariaAlonsoTransient,
                ivanAlonsoTransient));
        SearchEmployeeFilter filter = SearchEmployeeFilter.builder()
                .lastName("Alonso")
                .build();
        SearchEmployeeFilter findAllFilter = SearchEmployeeFilter.builder().build();
                                    System.out.println(objectMapper.writeValueAsString(filter));
        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)));

        List<Employee> byFilter = employeeRepository.findByFilter(findAllFilter, PageRequest.of(0, 10));
        // then
        result.andExpect(status().isNoContent());
        assertThat(byFilter).isNotNull();
        assertThat(byFilter.size()).isEqualTo(2);




//        // given
//        Employee entityTransient1 = DataUtils.getJuanRodriguezTransient();
//        Employee entityTransient2 = DataUtils.getIvanAlonsoTransient();
//        Employee entityTransient3 = DataUtils.getMariaAlonsoTransient();
//        employeeRepository.saveAll(List.of(entityTransient1, entityTransient2, entityTransient3));
//
//        EmployeeFilter filter = EmployeeFilter.builder().lastName("Rodriguez").build();
//        EmployeeFilter findAllFilter = EmployeeFilter.builder().build();
//        // when
//        ResultActions result = mockMvc.perform(
//                delete("/api/v1/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(filter)));
//        List<Employee> employeesByFilter = employeeRepository.findByFilter(filter, PageRequest.of(0, 10));
//        List<Employee> allEmployees = employeeRepository.findByFilter(findAllFilter, PageRequest.of(0, 10));
//        // then
//        result.andExpect(status().isNoContent());
//        assertThat(employeesByFilter).isEmpty();
//        assertThat(allEmployees).hasSize(2);
    }





//    @Test
//    void findById() throws Exception {
//        UUID id = UUID.fromString("00e5eb40-472e-40d2-9591-036287d20258");
//        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/api/v1/employees/" + id.toString()))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
//
//        ReadEmployeeDto dto = employeeService.findById(id);
//        System.out.println(dto);
//        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(dto)); // ?????
//    }
}