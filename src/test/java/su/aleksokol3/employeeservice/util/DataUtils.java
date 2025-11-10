package su.aleksokol3.employeeservice.util;

import org.openapitools.jackson.nullable.JsonNullable;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DataUtils {

    public static Employee getJuanRodriguezTransient() {
        return Employee.builder()
                .firstName("Juan")
                .lastName("Rodriguez")
                .age(40)
                .salary(BigDecimal.valueOf(222.22))
                .hiringDate(LocalDate.of(2020, 10, 15))
                .build();
    }
    public static Employee getJuanRodriguezPersisted() {
        return Employee.builder()
                .id(UUID.fromString("10e5eb40-472e-40d2-9591-036287d20258"))
                .firstName("Juan")
                .lastName("Rodriguez")
                .age(40)
                .salary(BigDecimal.valueOf(222.22))
                .hiringDate(LocalDate.of(2020, 10, 15))
                .build();
    }
    public static CreateEmployeeDto getJuanRodriguezCreateDto() {
        return CreateEmployeeDto.builder()
                .firstName("Juan")
                .lastName("Rodriguez")
                .age(40)
                .salary(BigDecimal.valueOf(222.22))
                .hiringDate(LocalDate.of(2020, 10, 15))
                .build();
    }
    public static CreateEmployeeDto getJuanRodriguezInvalidCreateDto() {
        return CreateEmployeeDto.builder()
                .firstName("Juan")
                .patronymic("V")
                .lastName("Rodriguez")
                .age(-40)
                .salary(BigDecimal.valueOf(222.22))
                .hiringDate(LocalDate.of(2020, 10, 15))
                .build();
    }
    public static PatchEmployeeDto getJuanRodriguezPatchDto() {
        return PatchEmployeeDto.builder()
//                .firstName(JsonNullable.of("Juanito"))
                .patronymic(JsonNullable.undefined())
                .lastName(JsonNullable.undefined())
                .age(JsonNullable.undefined())
                .salary(JsonNullable.of(BigDecimal.valueOf(777.77)))
                .hiringDate(JsonNullable.undefined())
                .build();
    }
    public static ReadEmployeeDto getJuanRodriguezReadDto() {
        return ReadEmployeeDto.builder()
                .id(UUID.fromString("10e5eb40-472e-30d2-9591-036287d20258"))
                .firstName("Juan")
                .patronymic("V")
                .lastName("Rodriguez")
                .age(40)
                .salary(BigDecimal.valueOf(222.22))
                .hiringDate(LocalDate.of(2020, 10, 15))
                .build();
    }

    public static Employee getLuisHernandezTransient() {
        return Employee.builder()
                .firstName("Luis")
                .lastName("Hernandez")
                .age(38)
                .salary(BigDecimal.valueOf(333.33))
                .hiringDate(LocalDate.of(2021, 5, 5))
                .build();
    }
    public static Employee getMariaAlonsoTransient() {
        return Employee.builder()
                .firstName("Maria")
                .lastName("Alonso")
                .age(36)
                .salary(BigDecimal.valueOf(426.00))
                .hiringDate(LocalDate.of(2022, 4, 8))
                .build();
    }
    public static Employee getMariaAlonsoPersisted() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Maria")
                .lastName("Alonso")
                .age(36)
                .salary(BigDecimal.valueOf(426.00))
                .hiringDate(LocalDate.of(2022, 4, 8))
                .build();
    }
    public static Employee getIvanAlonsoTransient() {
        return Employee.builder()
                .firstName("Ivan")
                .lastName("Alonso")
                .age(48)
                .salary(BigDecimal.valueOf(504.13))
                .hiringDate(LocalDate.of(2017, 2, 20))
                .build();
    }
    public static Employee getIvanAlonsoPersisted() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Ivan")
                .lastName("Alonso")
                .age(48)
                .salary(BigDecimal.valueOf(504.13))
                .hiringDate(LocalDate.of(2017, 2, 20))
                .build();
    }

}
