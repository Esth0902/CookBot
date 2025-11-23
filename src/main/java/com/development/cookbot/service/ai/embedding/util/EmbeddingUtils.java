package com.development.cookbot.service.ai.embedding.util;

public class EmbeddingUtils {

    public static double cosineSimilarityFloats(float[] v1, float[] v2) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
            normA += v1[i] * v1[i];
            normB += v2[i] * v2[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}