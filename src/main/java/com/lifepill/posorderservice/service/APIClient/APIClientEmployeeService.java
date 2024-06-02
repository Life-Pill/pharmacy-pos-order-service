package com.lifepill.posorderservice.service.APIClient;

import com.lifepill.posorderservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EMPLOYEE-SERVICE/lifepill/v1")
public interface APIClientEmployeeService {

    @GetMapping("/check-employer-exists-by-id/{employerId}")
    ResponseEntity<StandardResponse> checkEmployerExistsById(
            @PathVariable long employerId
    );
}
