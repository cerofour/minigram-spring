package com.cerofour.MiniGram.health;

import com.cerofour.MiniGram.health.dto.GenericHealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/health")
public class HealthCheckController {
    @GetMapping("")
    public ResponseEntity<GenericHealthResponse> healthCheck() {
        return ResponseEntity.ok(
                new GenericHealthResponse("OK", 200)
        );
    }
}
