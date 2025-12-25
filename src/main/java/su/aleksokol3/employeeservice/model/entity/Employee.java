package su.aleksokol3.employeeservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Employee entity, representing a table 'employee' in the DB
 */
@Entity
@Table(name = "employee", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends AuditableEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = true)
    private String patronymic;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private LocalDate hiringDate;
}
