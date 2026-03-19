package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
