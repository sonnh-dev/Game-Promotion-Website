package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.entity.News;
import com.dxlab.gamepromotionweb.repository.NewsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsAPIController {

    private final NewsRepository newsRepository;

    public NewsAPIController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news")
    public List<News> findAll() {
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
