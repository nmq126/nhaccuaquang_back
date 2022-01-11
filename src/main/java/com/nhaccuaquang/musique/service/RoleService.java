package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface RoleService {

    ResponseEntity findAll() throws Exception;
    ResponseEntity findById(Long id);
    ResponseEntity findAllPermissions() throws Exception;
    ResponseEntity save(Role role) throws Exception;
    ResponseEntity update(Long id, Role role) throws Exception;
    ResponseEntity delete(Long id) throws Exception;
    ResponseEntity addPermissionToRole(String roleName, String permName) throws Exception;
    ResponseEntity removePermissionFromRole(String roleName, String permName) throws Exception;

}
