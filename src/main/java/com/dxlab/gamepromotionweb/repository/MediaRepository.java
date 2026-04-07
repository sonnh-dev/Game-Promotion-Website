package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    public List<Media> findAllByEntityType(String entityType);
}
