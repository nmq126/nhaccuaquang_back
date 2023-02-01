package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Account;
import com.nhaccuaquang.musique.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query(value = "SELECT COUNT(*) FROM `accounts_roles` WHERE role_id = :roleId", nativeQuery = true)
    int countAccountsHaveRoleById(@Param("roleId") Long id);
}

