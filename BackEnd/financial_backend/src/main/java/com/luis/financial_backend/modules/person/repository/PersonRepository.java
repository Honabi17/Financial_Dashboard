package com.luis.financial_backend.modules.person.repository;


import com.luis.financial_backend.modules.person.entity.Person;
import com.luis.financial_backend.modules.person.enums.PersonPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByUserId(Long userId);

    Optional<Person> findFirstByUserIdAndPermission(
            Long userId,
            PersonPermission permission
    );
}
