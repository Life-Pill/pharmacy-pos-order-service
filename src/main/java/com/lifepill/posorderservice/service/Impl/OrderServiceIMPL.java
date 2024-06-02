package com.lifepill.posorderservice.service.Impl;

import com.lifepill.posorderservice.dto.OrderResponseDTO;
import com.lifepill.posorderservice.dto.RequestOrderDetailsSaveDTO;
import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.dto.RequestPaymentDetailsDTO;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.OrderDetails;
import com.lifepill.posorderservice.entity.PaymentDetails;
import com.lifepill.posorderservice.exception.NotFoundException;
import com.lifepill.posorderservice.repository.OrderDetailsRepository;
import com.lifepill.posorderservice.repository.OrderRepository;
import com.lifepill.posorderservice.repository.PaymentRepository;
import com.lifepill.posorderservice.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implementation of the {@link OrderService} interface that handles order-related operations.
 */
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceIMPL implements OrderService {


}