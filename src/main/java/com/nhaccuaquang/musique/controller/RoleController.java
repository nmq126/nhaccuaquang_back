package com.nhaccuaquang.musique.controller;

import com.nhaccuaquang.musique.entity.Role;
import com.nhaccuaquang.musique.entity.Song;
import com.nhaccuaquang.musique.service.RoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/roles")
@CrossOrigin("http://localhost:8081/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('view:roles')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllRoles() throws Exception {
        return roleService.findAll();
    }

    @PreAuthorize("hasAuthority('view:roles')")
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity getRoleDetail(@PathVariable(name = "id") Long id) {
        return roleService.findById(id);
    }

    @PreAuthorize("hasAuthority('view:roles')")
    @RequestMapping(method = RequestMethod.GET, value = "/permission")
    public ResponseEntity getAllPermissions() throws Exception {
        return roleService.findAllPermissions();
    }

    @PreAuthorize("hasAuthority('create:roles')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createRole(@RequestBody @Valid Role role) throws Exception {
        return roleService.save(role);
    }

    @PreAuthorize("hasAuthority('update:roles')")
    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public ResponseEntity updateRole(@PathVariable(name = "id") Long id, @RequestBody @Valid Role role) throws Exception {
        return roleService.update(id, role);
    }

    @PreAuthorize("hasAuthority('delete:roles')")
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity deleteRole(@PathVariable(name = "id") Long id) throws Exception {
        return roleService.delete(id);
    }

    @PreAuthorize("hasAuthority('addPermission:roles')")
    @RequestMapping(method = RequestMethod.POST, value ="/permission/add" )
    public ResponseEntity addPermissionToRole(@RequestBody PermissionToRoleForm form) throws Exception {
        return roleService.addPermissionToRole(form.getRoleName(), form.getPermName());
    }

    @PreAuthorize("hasAuthority('removePermission:roles')")
    @RequestMapping(method = RequestMethod.POST, value = "/permission/remove" )
    public ResponseEntity removePermissionFromRole(@RequestBody PermissionToRoleForm form) throws Exception {
        return roleService.removePermissionFromRole(form.getRoleName(), form.getPermName());
    }
}

@Data
class PermissionToRoleForm {
    private String roleName;
    private String permName;
}
