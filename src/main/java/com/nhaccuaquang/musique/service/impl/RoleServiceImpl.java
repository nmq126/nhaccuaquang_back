package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.PermissionRepository;
import com.nhaccuaquang.musique.repository.RoleRepository;
import com.nhaccuaquang.musique.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Role> roles = roleRepository.findAll();
            if (roles.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Role list is empty")
                        .build(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("roles", roles)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("role", role.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Song id not found");
        }
    }

    @Override
    public ResponseEntity findAllPermissions() throws Exception {
        try {
            List<Permission> permissions = permissionRepository.findAll();
            if (permissions.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Permission list is empty")
                        .build(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("permissions", permissions)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity save(Role role) throws Exception {
        role.getPermissions().forEach(permission -> {
            Optional<Permission> foundPermission = permissionRepository.findById(permission.getPermissionId());
            if (!foundPermission.isPresent()) throw new NotFoundException("Permission not found");
        });
        try {
            roleRepository.save(role);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.CREATED.value())
                    .withMessage("Created successfully")
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity update(Long id, Role role) throws Exception {
        Optional<Role> roleData = roleRepository.findById(id);
        if (!roleData.isPresent()) throw new NotFoundException("Role id not found");
        role.getPermissions().forEach(permission -> {
            Optional<Permission> foundPermission = permissionRepository.findById(permission.getPermissionId());
            if (!foundPermission.isPresent()) throw new NotFoundException("Permission not found");
        });

        Role updatedRole = roleData.get();
        updatedRole.setName(role.getName());
        updatedRole.setPermissions(role.getPermissions());
        try {
            roleRepository.save(updatedRole);
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Updated successfully")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ResponseEntity delete(Long id) throws Exception {
        Optional<Role> roleData = roleRepository.findById(id);
        if (roleData.isPresent()) {

            try {
                for (Account account: roleData.get().getAccounts()) {
                    account.removeRoleFromAccount(roleData.get());
                }
                roleRepository.deleteById(id);
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Deleted successfully")
                        .build(), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Role id not found");
    }


    @Override
    public ResponseEntity addPermissionToRole(String roleName, String permName) throws Exception {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) throw new NotFoundException("Role not found");
        Optional<Permission> permission = permissionRepository.findByName(permName);
        if (!permission.isPresent()) throw new NotFoundException("Permission not found");

        if (role.get().getPermissions().contains(permission.get())) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Permission already added to this role")
                    .build(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                role.get().getPermissions().add(permission.get());
                roleRepository.save(role.get());

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.OK.value())
                        .withMessage("Permission added successfully to this role")
                        .build(), HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }

    @Override
    public ResponseEntity removePermissionFromRole(String roleName, String permName) throws Exception {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) throw new NotFoundException("Role not found");
        Optional<Permission> permission = permissionRepository.findByName(permName);
        if (!permission.isPresent()) throw new NotFoundException("Permission not found");

        if (!role.get().getPermissions().contains(permission.get())) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Permission not yet added to this role")
                    .build(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                role.get().getPermissions().remove(permission.get());
                roleRepository.save(role.get());

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.OK.value())
                        .withMessage("Permission removed successfully to this role")
                        .build(), HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception();
            }

        }
    }
}

