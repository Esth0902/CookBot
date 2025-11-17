package com.development.cookbot.service.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiUserMetrics {

    @Autowired
    private MeterRegistry registry;

    public void recordUserTokens(Long userId, int prompts, int completions) {

        Counter.builder("openai_tokens_prompt_total")
                .tag("user", userId.toString())
                .register(registry)
                .increment(prompts);

        Counter.builder("openai_tokens_completion_total")
                .tag("user", userId.toString())
                .register(registry)
                .increment(completions);

        Counter.builder("openai_tokens_total")
                .tag("user", userId.toString())
                .register(registry)
                .increment(prompts + completions);
    }
}
