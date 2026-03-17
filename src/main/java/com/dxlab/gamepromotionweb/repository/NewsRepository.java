package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
