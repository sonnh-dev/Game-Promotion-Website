package com.dxlab.gamepromotionweb.Admin.repository;

import com.dxlab.gamepromotionweb.Admin.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, Integer> {
    public Admin findAdminsByUsername(String userName);
    UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException;
}
