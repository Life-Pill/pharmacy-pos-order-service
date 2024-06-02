package com.lifepill.posorderservice.controller;

import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.service.OrderService;
import com.lifepill.posorderservice.util.StandardResponse;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class for handling order-related endpoints.
 */
@RestController
@RequestMapping("/lifepill/v1/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    /**
     * Saves an order.
     *
     * @param requestOrderSaveDTO The DTO containing order details.
     * @return A response entity indicating the result of the operation.
     */
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO) {
        String orderId =  orderService.addOrder(requestOrderSaveDTO);

        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        orderId +"Item Saved Successfully",
                        orderId
                ),
                HttpStatus.CREATED);
    }

}
