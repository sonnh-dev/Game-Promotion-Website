package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.entity.Characters;
import com.dxlab.gamepromotionweb.repository.CharactersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CharactersAPIController {
    CharactersRepository charactersRepository;

    public CharactersAPIController(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }
    @GetMapping("/character")
    public List<Characters> findAll() {
        return charactersRepository.findAll();
    }
}
