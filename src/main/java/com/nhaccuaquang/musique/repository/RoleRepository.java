package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Account;
import com.nhaccuaquang.musique.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}

