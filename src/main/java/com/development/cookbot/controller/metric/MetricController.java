package com.development.cookbot.controller.metric;

import com.development.cookbot.dto.metic.MetricDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.metric.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metric")
public class MetricController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getMetricsByUserId(@PathVariable Long id) throws IOException {

        List<MetricDto> metricDto = metricsService.getMetricsByUserId(id);
        Response<List<MetricDto>> response = Response.<List<MetricDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(metricDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Object> getMetricsByRangeDate(String dateStart,String dateEnd) throws IOException {

        List<MetricDto> metricDto = metricsService.getMetricsByRangeDate(dateStart, dateEnd);
        Response<List<MetricDto>> response = Response.<List<MetricDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(metricDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
