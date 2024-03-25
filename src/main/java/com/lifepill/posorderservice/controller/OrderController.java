package com.lifepill.posorderservice.controller;

import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.service.OrderService;
import com.lifepill.posorderservice.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lifepill/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO) {
       String id =  orderService.addOrder(requestOrderSaveDTO);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201, id +"Item Savd Successfully",id),
                HttpStatus.CREATED);
    }
}
