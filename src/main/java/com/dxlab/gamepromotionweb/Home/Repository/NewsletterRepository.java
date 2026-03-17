package com.dxlab.gamepromotionweb.Home.Repository;

import com.dxlab.gamepromotionweb.Home.Entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
}
