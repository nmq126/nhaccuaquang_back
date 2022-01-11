package com.nhaccuaquang.musique.service;

import com.nhaccuaquang.musique.entity.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    ResponseEntity findAll() throws Exception;
    ResponseEntity save(Permission permission) throws Exception;
}
