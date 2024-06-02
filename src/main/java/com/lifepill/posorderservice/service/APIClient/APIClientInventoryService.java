package com.lifepill.posorderservice.service.APIClient;

import com.lifepill.posorderservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE/lifepill/v1")
public interface APIClientInventoryService {

    @GetMapping(path = "/check-item-stock/{itemId}/{requiredQuantity}")
    ResponseEntity<StandardResponse> checkItemExistsAndQuantityAvailable(
            @PathVariable(value = "itemId") long itemId,
            @PathVariable(value = "requiredQuantity") Double requiredQuantity
    );
}
