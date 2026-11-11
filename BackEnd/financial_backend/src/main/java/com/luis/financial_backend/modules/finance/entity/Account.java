package com.luis.financial_backend.modules.finance.entity;

import com.luis.financial_backend.common.BaseEntity;
import com.luis.financial_backend.modules.auth.entity.User;
import com.luis.financial_backend.modules.finance.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal initialBalance;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}
