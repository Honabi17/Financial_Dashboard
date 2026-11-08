package com.luis.financial_backend.modules.finance.entity;

import com.luis.financial_backend.common.BaseEntity;
import com.luis.financial_backend.modules.person.entity.Person;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "transaction_shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionShare extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "transaction_id",
            nullable = false
    )
    private Transaction transaction;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "person_id",
            nullable = false
    )
    private Person person;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;
}
