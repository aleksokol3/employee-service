package su.aleksokol3.employeeservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.service.EmployeeService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<ReadEmployeeDto>> findBy(@Valid EmployeeFilter filter, @NotNull @PageableDefault Pageable pageable) {
        return ResponseEntity.ok()
                .body(employeeService.findBy(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadEmployeeDto> findById(@PathVariable UUID id) {
        String location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return ResponseEntity.ok()
                .header("location", location)
                .body(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateEmployeeDto dto) {
        UUID uuid = employeeService.create(dto);
        return ResponseEntity.created(URI.create("localhost:8080/api/v1/employees/" + uuid))
                .body(uuid);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid PatchEmployeeDto dto) {
        employeeService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBy(@Valid EmployeeFilter filter) {
        employeeService.deleteBy(filter);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
