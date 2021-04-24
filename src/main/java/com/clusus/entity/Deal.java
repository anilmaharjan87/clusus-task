package com.clusus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@EqualsAndHashCode
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String dealId;
    @NotBlank(message = "From currency code cannot be blank")
    private String fromCurrencyCode;
    @NotBlank(message = "To currency code cannot be blank")
    private String toCurrencyCode;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealTime;
    @NotNull
    private BigDecimal dealAmount;
}
