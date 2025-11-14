package su.aleksokol3.employeeservice.controller;

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

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<PageDto<ReadEmployeeDto>> findBy(
            @Valid SearchEmployeeFilter filter,
            @NotNull @PageableDefault(page = 0, size = 10) @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(employeeService.findBy(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody @Valid CreateEmployeeDto dto) {
        UUID uuid = employeeService.create(dto);
        String location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return ResponseEntity.created(URI.create(location + "/" + uuid))
                .body(uuid);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> update(@PathVariable("id") UUID id, @RequestBody @Valid PatchEmployeeDto dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBy(@Valid DeleteEmployeeFilter filter) {
        employeeService.deleteBy(filter);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
