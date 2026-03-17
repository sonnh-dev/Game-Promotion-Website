package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
}
