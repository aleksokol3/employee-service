package su.aleksokol3.employeeservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.service.EmployeeService;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest     // загружает только указанные контроллеры, вместо всего контекста
@RequiredArgsConstructor
class EmployeeControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @MockitoBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUpObjectMapper() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JsonNullableModule());
    }

    @Test
    @DisplayName("Test find by id functionality")
    void givenId_whenFindById_thenSuccessResponse() throws Exception {
        // given
        UUID id = UUID.fromString("10e5eb40-472e-30d2-9591-036287d20258");
        ReadEmployeeDto dto = DataUtils.getJuanRodriguezReadDto();
        Mockito.when(employeeService.findById(any(UUID.class)))
                .thenReturn(dto);
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(dto.id().toString())))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(dto.firstName())))
//                .andExpect(jsonPath("$.patronymic", CoreMatchers.is(dto.patronymic())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(dto.lastName())));
//                .andExpect(jsonPath("$.salary", CoreMatchers.is(dto.salary())))
//                .andExpect(jsonPath("$.hiringDate", CoreMatchers.is(dto.hiringDate())));
    }

    @Test
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() throws Exception {
        // given
        UUID id = UUID.fromString("11e5eb40-472e-30d2-9591-036287d20258");
        Mockito.when(employeeService.findById(any(UUID.class)))
                .thenThrow(new NotFoundException("Пользователь с id %s не найден".formatted(id), "Employee", id.toString()));
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.path", CoreMatchers.is("http://localhost/api/v1/employees/" + id)))
                .andExpect(jsonPath("$.error", CoreMatchers.is("Пользователь с id %s не найден".formatted(id))));
    }

//    @Test             // NEED TO REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    @DisplayName("Test find by filter functionality")
//    void givenFilter_whenFindByFilter_thenEmployeesAreReturned() throws Exception {
//        // given
//        EmployeeFilter filter = EmployeeFilter.builder().lastName("Rodriguez").build();
//        List<ReadEmployeeDto> dtos = List.of(DataUtils.getJuanRodriguezReadDto());
//        Mockito.when(employeeService.findBy(any(EmployeeFilter.class), any(Pageable.class)))
//                .thenReturn(dtos);
//        // when
//        ResultActions result = mockMvc.perform(
//                get("/api/v1/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(filter)));
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].firstName", CoreMatchers.is("Juan")))
//                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Rodriguez")));
//        verify(employeeService, times(1)).findBy(any(EmployeeFilter.class), any(Pageable.class));
//    }

    @Test
    @DisplayName("Test create functionality")
    void givenCreateEmployeeDto_whenCreateEmployee_thenSuccessResponse() throws Exception {
        // given
        CreateEmployeeDto dto = DataUtils.getJuanRodriguezCreateDto();
        Employee entity = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeService.create(any(CreateEmployeeDto.class)))
                .thenReturn(entity.getId());
        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$", CoreMatchers.is(entity.getId().toString())));
    }

    @Test
    @DisplayName("Test create with invalid dto functionality")
    void givenInvalidCreateEmployeeDto_whenCreateEmployee_thenErrorResponse() throws Exception {
        // given
        CreateEmployeeDto dto = DataUtils.getJuanRodriguezInvalidCreateDto();
        Mockito.when(employeeService.create(any(CreateEmployeeDto.class)))
                .thenReturn(null);
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
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezPatchDto();
        Employee persistedEntity = DataUtils.getJuanRodriguezPersisted();
        Mockito.doNothing().when(employeeService).update(any(UUID.class), any(PatchEmployeeDto.class));
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/" + persistedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        // then
        result.andExpect(status().isOk());
        verify(employeeService, times(1)).update(any(UUID.class), any(PatchEmployeeDto.class));
    }

    @Test
    @DisplayName("Test delete by id functionality")
    void givenId_whenDeleteById_thenNoContentResponse() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        Mockito.doNothing().when(employeeService).deleteById(any(UUID.class));
        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Test delete by filter functionality")
    void givenFilter_whenDeleteByFilter_thenNoContentResponse() throws Exception {
        // given
        DeleteEmployeeFilter filter = DeleteEmployeeFilter.builder().lastName("Johnson").build();
        Mockito.doNothing().when(employeeService).deleteBy(any(DeleteEmployeeFilter.class));
        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)));
        // then
        result.andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteBy(any(DeleteEmployeeFilter.class));
    }
}