package com.nhaccuaquang.musique.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhaccuaquang.musique.entity.Account;
import com.nhaccuaquang.musique.entity.Permission;
import com.nhaccuaquang.musique.repository.AccountRepository;
import com.nhaccuaquang.musique.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@CrossOrigin("http://localhost:8081/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @PreAuthorize("hasAuthority('view:accounts')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllAccounts() throws Exception{
        return accountService.findAll();
    }

    @PreAuthorize("hasAuthority('view:accounts')")
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity getAccountDetail(@PathVariable(name = "id")Long id) throws Exception{
        return accountService.findById(id);
    }

    @PreAuthorize("hasAuthority('create:accounts')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid Account account) throws Exception {
        return accountService.save(account);
    }

    @PreAuthorize("hasAuthority('addRole:accounts')")
    @RequestMapping(method = RequestMethod.POST, value = "/role/add" )
    public ResponseEntity addRoleToAccount(@RequestBody RoleToUserForm form) throws Exception {
        return accountService.addRoleToAccount(form.getUsername(), form.getRoleName());
    }

    @PreAuthorize("hasAuthority('removeRole:accounts')")
    @RequestMapping(method = RequestMethod.POST, value = "/role/remove" )
    public ResponseEntity removeRoleFromAccount(@RequestBody RoleToUserForm form) throws Exception {
        return accountService.removeRoleFromAccount(form.getUsername(), form.getRoleName());
    }

    @PreAuthorize("hasAuthority('addPermission:accounts')")
    @RequestMapping(method = RequestMethod.POST, value = "/permission/add" )
    public ResponseEntity addPermissionToAccount(@RequestBody PermissionToAccount form) throws Exception {
        return accountService.addPermissionToAccount(form.getUsername(), form.getPermName());
    }

    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Account account = accountRepository.findByUsername(username).get();
                List<String> roles = new ArrayList<>();
                roles.add("ROLE_USER");
                String access_token = JWT.create()
                        .withSubject(account.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*24*3600*100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", roles)
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception exception){
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw new RuntimeException("Refresh token missing");
        }
    }

}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}

@Data
class PermissionToAccount {
    private String username;
    private String permName;
}
