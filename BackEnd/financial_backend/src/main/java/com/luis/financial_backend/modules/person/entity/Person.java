package com.luis.financial_backend.modules.person.entity;


import com.luis.financial_backend.common.BaseEntity;
import com.luis.financial_backend.modules.auth.entity.User;
import com.luis.financial_backend.modules.person.enums.PersonPermission;
import com.luis.financial_backend.modules.person.enums.PersonType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonType type;

    @Enumerated(EnumType.STRING)
    private PersonPermission permission;

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
