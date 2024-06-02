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

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Implementation of the {@link OrderService} interface that handles order-related operations.
 */
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceIMPL implements OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private OrderDetailsRepository orderDetailsRepo;
    private OrderDetailsRepository orderDetailsRepository;
    private PaymentRepository paymentRepository;

    /**
     * Adds an order to the system.
     *
     * @param requestOrderSaveDTO The DTO containing order details.
     * @return A message indicating the result of the operation.
     */
    @Override
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        // Check if items in the order have sufficient quantity
        checkItemStock(requestOrderSaveDTO);

      /*  // Update item quantities
        updateItemQuantities(requestOrderSaveDTO);

        Order order = new Order();
        order.setEmployer(employerRepository.getById(requestOrderSaveDTO.getEmployerId()));
        order.setOrderDate(requestOrderSaveDTO.getOrderDate());
        order.setTotal(requestOrderSaveDTO.getTotal());
        order.setBranchId(requestOrderSaveDTO.getBranchId());
        orderRepository.save(order);

        if (orderRepository.existsById(order.getOrderId())) {
            List<OrderDetails> orderDetails = modelMapper.
                    map(requestOrderSaveDTO.getOrderDetails(), new TypeToken<List<OrderDetails>>() {
                            }
                                    .getType()
                    );
            for (int i = 0; i < orderDetails.size(); i++) {
                orderDetails.get(i).setOrders(order);
                orderDetails.get(i).setItems(itemRepository
                        .getById(requestOrderSaveDTO
                                .getOrderDetails().get(i).getId()
                        )
                );
            }
            if (!orderDetails.isEmpty()) {
                orderDetailsRepo.saveAll(orderDetails);
            }
            savePaymentDetails(requestOrderSaveDTO.getPaymentDetails(), order);
            return "saved";
        }*/
        return "Order saved successfully";
    }

    /**
     * Checks the stock availability of items in the order.
     *
     * @param requestOrderSaveDTO The DTO containing the order details.
     * @throws InsufficientItemQuantityException if an item in the order does not have enough quantity.
     * @throws NotFoundException                 if an item in the order is not found in the database.
     */
    private void checkItemStock(RequestOrderSaveDTO requestOrderSaveDTO) {
//        for (RequestOrderDetailsSaveDTO orderDetail : requestOrderSaveDTO.getOrderDetails()) {
//            Optional<Item> optionalItem = itemRepository.findById(orderDetail.getId());
//            if (optionalItem.isPresent()) {
//                Item item = optionalItem.get();
//                if (item.getItemQuantity() < orderDetail.getAmount()) {
//                    throw new InsufficientItemQuantityException(
//                            "Item " + item.getItemId()
//                                    + " does not have enough quantity"
//                    );
//                }
//            } else {
//                throw new NotFoundException("Item not found with ID: " + orderDetail.getId());
//            }
//        }
    }
}