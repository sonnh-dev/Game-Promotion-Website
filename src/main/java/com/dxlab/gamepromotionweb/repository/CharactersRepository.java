package com.dxlab.gamepromotionweb.repository;

import com.dxlab.gamepromotionweb.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharactersRepository extends JpaRepository<Characters, Integer> {
}
