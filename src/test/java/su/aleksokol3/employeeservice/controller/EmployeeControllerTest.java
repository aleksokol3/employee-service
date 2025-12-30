package su.aleksokol3.employeeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import su.aleksokol3.employeeservice.config.JsonConfig;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.service.EmployeeService;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(JsonConfig.class)
@RequiredArgsConstructor
class EmployeeControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @MockitoBean
    private EmployeeService employeeService;
    @MockitoBean(name = "jpaMappingContext")
    private JpaMetamodelMappingContext jpaMappingContext;

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
                get("/api/v1/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.id().toString())))
                .andExpect(jsonPath("$.firstName", is(dto.firstName())))
                .andExpect(jsonPath("$.patronymic", is(dto.patronymic())))
                .andExpect(jsonPath("$.lastName", is(dto.lastName())))
                .andExpect(jsonPath("$.salary").value(comparesEqualTo(dto.salary()), BigDecimal.class))
                .andExpect(jsonPath("$.hiringDate", is(dto.hiringDate().toString())));
    }

    @Test
    @DisplayName("Test find by incorrect id functionality")
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() throws Exception {
        // given
        UUID id = UUID.fromString("11e5eb40-472e-30d2-9591-036287d20258");
        String errorMessage = "not.found.exception";
        Mockito.when(employeeService.findById(any(UUID.class)))
                .thenThrow(new NotFoundException("not.found.exception", "employee", id.toString()));
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .locale(Locale.getDefault()));
        // then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error", is(errorMessage)));
    }

    @Test
    @DisplayName("Test find by filter functionality")
    void givenFilter_whenFindByFilter_thenEmployeesAreReturned() throws Exception {
        // given
        SearchEmployeeFilter filter = SearchEmployeeFilter.builder().lastName("Rodriguez").build();
        List<ReadEmployeeDto> dtos = List.of(DataUtils.getJuanRodriguezReadDto());
        PageDto<ReadEmployeeDto> pageDto = new PageDto<>(dtos, 1, Instant.now());
        Mockito.when(employeeService.findBy(any(SearchEmployeeFilter.class), any(Pageable.class)))
                .thenReturn(pageDto);
        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(filter)));
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].firstName", is("Juan")))
                .andExpect(jsonPath("$.employees[0].lastName", is("Rodriguez")))
                .andExpect(jsonPath("$.total", is(1)));
        verify(employeeService, times(1)).findBy(any(SearchEmployeeFilter.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create functionality")
    void givenCreateEmployeeDto_whenCreateEmployee_thenSuccessResponse() throws Exception {
        // given
        CreateEmployeeDto createDto = DataUtils.getJuanRodriguezCreateDto();
        UUID id = DataUtils.getJuanRodriguezPersisted().getId();
        Mockito.when(employeeService.create(any(CreateEmployeeDto.class)))
                .thenReturn(id);
        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)));
        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string("location", endsWith("/api/v1/employees/" + id)));
    }

    @Test
    @DisplayName("Test create with invalid dto functionality")
    void givenInvalidCreateEmployeeDto_whenCreateEmployee_thenErrorResponse() throws Exception {
        // given
        CreateEmployeeDto invalidCreateDto = DataUtils.getJuanRodriguezInvalidCreateDto();
        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCreateDto)));
        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errors", notNullValue()));
    }

    @Test
    @DisplayName("Test update functionality")
    void givenPatchDto_whenUpdate_thenSuccessResponse() throws Exception {
        // given
        Employee persistedEntity = DataUtils.getJuanRodriguezPersisted();
        PatchEmployeeDto patchDto = DataUtils.getJuanRodriguezPatchDto();
        ReadEmployeeDto updatedReadDto = DataUtils.getJuanRodriguezUpdatedReadDto();

        Mockito.when(employeeService.update(eq(persistedEntity.getId()), any(PatchEmployeeDto.class)))
                .thenReturn(updatedReadDto);
        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/employees/{id}", persistedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)));
        // then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patronymic").value(nullValue()))
                .andExpect(jsonPath("$.salary").value(patchDto.salary().get()));

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
                delete("/api/v1/employees/{id}", id)
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