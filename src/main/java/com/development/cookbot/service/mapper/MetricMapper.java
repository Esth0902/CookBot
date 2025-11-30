package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.metic.MetricDto;
import com.development.cookbot.entity.MetricEntity;
import org.springframework.stereotype.Service;

@Service
public class MetricMapper {
    public MetricDto mapToMetricDto(MetricEntity metricEntity) {

        return MetricDto.builder()
                .id(metricEntity.getId())
                .creationDate(metricEntity.getCreationDate())
                .executionTime(metricEntity.getExecutionTime())
                .inputToken(metricEntity.getInputToken())
                .outputToken(metricEntity.getOutputToken())
                .totalToken(metricEntity.getTotalToken())
                .build();
    }
}
