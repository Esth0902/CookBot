package com.development.cookbot.service.ai.observer;

import com.development.cookbot.entity.MetricEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.metric.MetricRepository;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Component
public class AiMetricLoggerObserver implements CallAdvisor {

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();

        Instant start = Instant.now();

        ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);

        long executionTime = Duration.between(start, Instant.now()).toMillis();

        Usage usage = response.chatResponse().getMetadata().getUsage();

        if (usage != null) {

            MetricEntity metricEntity = MetricEntity
                    .builder()
                    .outputToken(usage.getCompletionTokens().longValue())
                    .inputToken(usage.getPromptTokens().longValue())
                    .executionTime(executionTime)
                    .user(userDetails)
                    .totalToken(usage.getTotalTokens().longValue())
                    .creationDate(Timestamp.from(Instant.now()))
                    .build();

            metricRepository.save(metricEntity);
        }

        return response;
    }

    @Override
    public String getName() {
        return "AiMetricLoggerObserver";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
