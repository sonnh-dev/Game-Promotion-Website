package com.dxlab.gamepromotionweb.Home.Repository;

import com.dxlab.gamepromotionweb.Home.Entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
}
