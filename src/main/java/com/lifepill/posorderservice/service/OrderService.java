package com.lifepill.posorderservice.service;


import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;

public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);
}
