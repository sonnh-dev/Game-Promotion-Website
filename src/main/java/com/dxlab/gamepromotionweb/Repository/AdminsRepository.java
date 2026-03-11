package com.dxlab.gamepromotionweb.Repository;

import com.dxlab.gamepromotionweb.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, Integer> {

}
