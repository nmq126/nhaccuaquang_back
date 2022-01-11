package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Account;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity findAll() throws Exception;
    ResponseEntity findById(Long id) throws Exception;
    ResponseEntity save(Account account) throws Exception;
    ResponseEntity addRoleToAccount(String username, String roleName) throws Exception;
    ResponseEntity removeRoleFromAccount(String username, String roleName) throws Exception;
    ResponseEntity addPermissionToAccount(String username, String permName) throws Exception;

}
