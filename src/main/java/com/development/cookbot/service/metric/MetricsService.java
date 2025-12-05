package com.development.cookbot.service.metric;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.dto.metic.MetricDto;
import com.development.cookbot.entity.MetricEntity;
import com.development.cookbot.repository.metric.MetricRepository;
import com.development.cookbot.service.client.AuthenticationService;
import com.development.cookbot.service.mapper.MetricMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MetricMapper metricMapper;

    @Autowired
    private MetricRepository metricRepository;

    public List<MetricDto> getMetricsByRangeDate(String dateStart, String dateEnd) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        Timestamp start = Timestamp.valueOf(dateStart);
        Timestamp end   = Timestamp.valueOf(dateEnd);


        List<MetricEntity> metricEntities = metricRepository.getMetricsByRangeDate(start,end);
        List<MetricDto> metricDtos = new ArrayList<>();

        for(MetricEntity metricEntity: metricEntities) {
            MetricDto metricDto = metricMapper.mapToMetricDto(metricEntity);
            metricDto.setUserId(metricEntity.getUser().getId());
            metricDto.setUsername(metricEntity.getUser().getUsername());
            metricDtos.add(metricDto);
        }

        return metricDtos;
    }

    public List<MetricDto> getMetricsByUserId(Long id) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

            List<MetricEntity> metricEntities = metricRepository.findAllMetricsByUserId(id);
            List<MetricDto> metricDtos = new ArrayList<>();

            for(MetricEntity metricEntity: metricEntities) {
                MetricDto metricDto = metricMapper.mapToMetricDto(metricEntity);
                metricDto.setUserId(userPrincipalDto.getId());
                metricDto.setUsername(userPrincipalDto.getUsername());
                metricDtos.add(metricDto);
            }

        return metricDtos;
    }

}
