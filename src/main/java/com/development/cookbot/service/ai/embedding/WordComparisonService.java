package com.development.cookbot.service.ai.embedding;

import com.development.cookbot.service.ai.embedding.util.EmbeddingUtils;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordComparisonService {

    @Autowired
    private EmbeddingModel embeddingModel;

    public List<String> findWordsUniqueToFirstList(List<String> listA, List<String> listB) {
        double threshold = 0.80;

        // Embeddings de B
        Map<String, float[]> embeddingsB = listB.stream()
                .collect(Collectors.toMap(
                        word -> word,
                        word -> embeddingModel.embed(word)  // version embed simple
                ));

        List<String> unique = new ArrayList<>();

        for (String wordA : listA) {
            float[] embA = embeddingModel.embed(wordA);

            boolean hasSimilar = embeddingsB.values().stream().anyMatch(embB -> {
                return EmbeddingUtils.cosineSimilarityFloats(embA, embB) >= threshold;
            });

            if (!hasSimilar) {
                unique.add(wordA);
            }
        }

        return unique;
    }
}