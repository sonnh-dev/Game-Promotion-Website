package com.dxlab.gamepromotionweb.Repository;

import com.dxlab.gamepromotionweb.Model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
}
