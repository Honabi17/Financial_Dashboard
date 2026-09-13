package com.luis.financial_backend.modules.person.entity;


import com.luis.financial_backend.common.BaseEntity;
import com.luis.financial_backend.modules.auth.entity.User;
import com.luis.financial_backend.modules.person.enums.PersonPermission;
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

    private String name;

    @Enumerated(EnumType.STRING)
    private PersonPermission permission;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
