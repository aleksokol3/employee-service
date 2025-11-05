package su.aleksokol3.employeeservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Employee {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private Integer age;
    private BigDecimal salary;
    private LocalDate hiringDate;
}
