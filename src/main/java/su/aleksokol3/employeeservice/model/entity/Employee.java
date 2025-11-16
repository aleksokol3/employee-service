package su.aleksokol3.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class Employee extends AuditableEntity implements BaseEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
