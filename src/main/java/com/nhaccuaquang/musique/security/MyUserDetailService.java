package com.nhaccuaquang.musique.security;

import com.nhaccuaquang.musique.entity.Account;
import com.nhaccuaquang.musique.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (!account.isPresent()) throw new UsernameNotFoundException("User not found");
        Account foundAccount = account.get();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        foundAccount.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            });
        });
        foundAccount.getPermissions().forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        });
        org.springframework.security.core.userdetails.User userSpring
                = new org.springframework.security.core.userdetails.User
                (foundAccount.getUsername(), foundAccount.getPassword(), authorities);
        return userSpring;
    }
}
