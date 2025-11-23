package com.development.cookbot.controller.ai;

import com.development.cookbot.dto.embedding.EmbeddingDto;
import com.development.cookbot.service.ai.embedding.WordComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/demo")
public class AIWordComparisonController {

    @Autowired
    private WordComparisonService wordComparisonService;


    @PostMapping("/compare")
    public List<String> compareWords(@RequestBody EmbeddingDto embeddingDto) {
        List<String> listA = embeddingDto.getListA();
        List<String> listB = embeddingDto.getListB();
        return wordComparisonService.findWordsUniqueToFirstList(listA, listB);
    }
}