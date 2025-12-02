package su.aleksokol3.employeeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.service.EmployeeService;

import java.net.URI;
import java.util.UUID;

/**
 * EmployeeController is the {@link RestController} class for employee.
 */
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Get a list of employees, using filter and pageable.
     *
     * @param filter Searching filter. Should be valid.
     * @param pageable {@link Pageable}, containing page, size, and sort. Its default params: page = 0, size = 10, there isn't sorting.
     * @return {@link ResponseEntity} of {@link PageDto}, containing a list of employees by filter on the desired page, total amount of employees matching this filter, and timestamp of creating response
     */
    @Operation(
            tags = "Find",
            summary = "Find employees by filter",
            description = "Find employees by filter, page number, size of page; sort employees by field",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns found employees by filter on the specified page of the specified size in the specified order; and total number of founded employees with timestamp of the response"),
                    @ApiResponse(responseCode = "400", description = "Validation failure", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<PageDto<ReadEmployeeDto>> findBy(
            @Valid @ParameterObject SearchEmployeeFilter filter,
            @NotNull @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(employeeService.findBy(filter, pageable));
    }

    /**
     * Get an employee by id.
     *
     * @param id {@link UUID} of an employee to get.
     * @return {@link ResponseEntity} of {@link ReadEmployeeDto}
     */
    @Operation(
            tags = "Find",
            summary = "Find an employee by id",
            parameters = @Parameter(name = "id", example = "01e5eb48-472e-44d2-9591-036287d20258"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns an employee by given id"),
                    @ApiResponse(responseCode = "404", description = "Not found an employee by given id", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    /**
     * Create an employee.
     *
     * @param dto {@link CreateEmployeeDto}. Should be valid.
     * @return {@link ResponseEntity} of {@link UUID} (of the created employee).
     */
    @Operation(
            tags = "Create",
            summary = "Create a new employee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "An employee to create"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns UUID of the created employee"),
                    @ApiResponse(responseCode = "400", description = "Validation failure", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody @Valid CreateEmployeeDto dto) {
        UUID uuid = employeeService.create(dto);
        String location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return ResponseEntity.created(URI.create(location + "/" + uuid))
                .body(uuid);
    }

    /**
     * Update an employee by given id.
     *
     * @param id {@link UUID} of employee to update.
     * @param dto {@link PatchEmployeeDto}. Should be valid.
     * @return {@link ResponseEntity} of {@link ReadEmployeeDto} (updated employee).
     */
    @Operation(
            tags = "Update",
            summary = "Update an employee",
            parameters = @Parameter(name = "id", example = "01e5eb48-472e-44d2-9591-036287d20258"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "An employee to update"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns an updated employee"),
                    @ApiResponse(responseCode = "400", description = "Validation failure", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found an employee to update", content = @Content)
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> update(@PathVariable("id") UUID id, @RequestBody @Valid PatchEmployeeDto dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    /**
     * Delete an employee by filter.
     *
     * @param filter {@link DeleteEmployeeFilter}. Should be valid.
     */
    @Operation(
            tags = "Delete",
            summary = "Delete employees by filter"
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteBy(@Valid @ParameterObject DeleteEmployeeFilter filter) {
        employeeService.deleteBy(filter);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete an employee by given id.
     *
     * @param id {@link UUID} of employee to delete.
     */
    @Operation(
            tags = "Delete",
            summary = "Delete an employee by id",
            parameters = @Parameter(name = "id", example = "01e5eb48-472e-44d2-9591-036287d20258")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
