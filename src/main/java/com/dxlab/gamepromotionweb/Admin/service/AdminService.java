package com.dxlab.gamepromotionweb.Admin.service;

import com.dxlab.gamepromotionweb.Admin.Entity.Admin;
import com.dxlab.gamepromotionweb.Admin.repository.AdminsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminService {

    private final AdminsRepository adminsRepository;

    public AdminService(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Admin admin = adminsRepository.findAdminsByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found");
        }

        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                )
        );
    }
}