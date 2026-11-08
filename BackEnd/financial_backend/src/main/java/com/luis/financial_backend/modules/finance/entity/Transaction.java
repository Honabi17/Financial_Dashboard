package com.luis.financial_backend.modules.finance.entity;

import com.luis.financial_backend.common.BaseEntity;
import com.luis.financial_backend.modules.finance.enums.Category;
import com.luis.financial_backend.modules.finance.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private String description;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "acount_id",
            nullable = false
    )
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;


    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TransactionShare> shares = new ArrayList<>();
}
