package com.nhnacademy.infrastructure.adapter;

import com.nhnacademy.infrastructure.dto.GatewayCountUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway-service")
public interface GatewayServiceAdapter {

    @PutMapping("/gateways/update-sensor-count")
    ResponseEntity<Void> updateGatewaySensorCount(
            @RequestBody GatewayCountUpdateRequest request
    );
}
