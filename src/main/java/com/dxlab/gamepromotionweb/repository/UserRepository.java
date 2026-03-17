package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
