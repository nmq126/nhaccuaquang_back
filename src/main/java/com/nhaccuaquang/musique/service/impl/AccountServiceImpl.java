package com.nhaccuaquang.musique.service.impl;

import com.nhaccuaquang.musique.entity.*;
import com.nhaccuaquang.musique.exception.NotFoundException;
import com.nhaccuaquang.musique.repository.AccountDetailRepository;
import com.nhaccuaquang.musique.repository.AccountRepository;
import com.nhaccuaquang.musique.repository.PermissionRepository;
import com.nhaccuaquang.musique.repository.RoleRepository;
import com.nhaccuaquang.musique.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Override
    public ResponseEntity findAll() throws Exception {
        try {
            List<Account> accounts = accountRepository.findAll();
            if (accounts.isEmpty()) {
                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withMessage("Account list is empty")
                        .build(),HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("Okela")
                    .withData("accounts", accounts)
                    .build(),HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity findById(Long id) throws Exception {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.OK.value())
                    .withMessage("okela")
                    .withData("account", account.get())
                    .build(), HttpStatus.OK);
        } else {
            throw new NotFoundException("Account id not found");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity save(Account account) throws Exception {
        Optional<Account> existedAccount = accountRepository.findByUsername(account.getUsername());
        if (existedAccount.isPresent()){
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Duplicate username")
                    .withData("error", "Username already existed in database")
                    .build(), HttpStatus.BAD_REQUEST);
        }else {
            try {
                account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ROLE_USER").get());
                account.setRoles(roles);

                accountRepository.save(account);

//                AccountDetail accountDetail = new AccountDetail();
//                accountDetail.setId(account.getId());
//                accountDetailRepository.save(accountDetail);

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.CREATED.value())
                        .withMessage("Account created successfully")
                        .withData("account", account)
                        .build(), HttpStatus.CREATED);
            }catch (Exception e){
                throw new Exception();
            }
        }

    }

    @Override
    public ResponseEntity addRoleToAccount(String username, String roleName) throws Exception{
        Optional<Account> account = accountRepository.findByUsername(username);
        if (!account.isPresent()) throw new NotFoundException("User not found");
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) throw new NotFoundException("Role not found");
        if (account.get().getRoles().contains(role.get())){
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Role already added to this account")
                    .build(), HttpStatus.BAD_REQUEST);
        }else {
            try {
                account.get().getRoles().add(role.get());
                accountRepository.save(account.get());

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.OK.value())
                        .withMessage("Role added successfully to this account")
                        .build(), HttpStatus.OK);
            }catch (Exception e){
                throw new Exception();
            }

        }

    }

    @Override
    public ResponseEntity removeRoleFromAccount(String username, String roleName) throws Exception {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (!account.isPresent()) throw new NotFoundException("User not found");

        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) throw new NotFoundException("Role not found");

        if (!account.get().getRoles().contains(role.get())){
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Role not yet added to this account")
                    .build(), HttpStatus.BAD_REQUEST);
        }else {
            try {
                account.get().getRoles().remove(role.get());
                accountRepository.save(account.get());

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.OK.value())
                        .withMessage("Role removed successfully from this account")
                        .build(), HttpStatus.OK);
            }catch (Exception e){
                throw new Exception();
            }

        }

    }

    @Override
    public ResponseEntity addPermissionToAccount(String username, String permName) throws Exception {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (!account.isPresent()) throw new NotFoundException("User not found");
        Optional<Permission> permission = permissionRepository.findByName(permName);
        if (!permission.isPresent()) throw new NotFoundException("Role not found");
        if (account.get().getPermissions().contains(permission.get())){
            return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                    .withStatus(HttpStatus.BAD_REQUEST.value())
                    .withMessage("Permission already added to this account")
                    .build(), HttpStatus.BAD_REQUEST);
        }else {
            try {
                account.get().getPermissions().add(permission.get());
                accountRepository.save(account.get());

                return new ResponseEntity(ResponseHandler.ResponseHandlerBuilder.aResponseHandler()
                        .withStatus(HttpStatus.OK.value())
                        .withMessage("Permission added successfully to this account")
                        .build(), HttpStatus.OK);
            }catch (Exception e){
                throw new Exception();
            }

        }
    }


}
