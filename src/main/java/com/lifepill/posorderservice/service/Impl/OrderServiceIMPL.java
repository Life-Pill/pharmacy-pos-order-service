package com.lifepill.posorderservice.service.Impl;

import com.lifepill.posorderservice.dto.*;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.OrderDetails;
import com.lifepill.posorderservice.entity.PaymentDetails;
import com.lifepill.posorderservice.exception.InsufficientItemQuantityException;
import com.lifepill.posorderservice.exception.NotFoundException;
import com.lifepill.posorderservice.repository.OrderDetailsRepository;
import com.lifepill.posorderservice.repository.OrderRepository;
import com.lifepill.posorderservice.repository.PaymentRepository;
import com.lifepill.posorderservice.service.APIClient.APIClientBranchService;
import com.lifepill.posorderservice.service.APIClient.APIClientEmployeeService;
import com.lifepill.posorderservice.service.APIClient.APIClientInventoryService;
import com.lifepill.posorderservice.service.OrderService;

import com.lifepill.posorderservice.util.StandardResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


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
    private APIClientEmployeeService apiClientEmployeeService;
    private APIClientBranchService apiClientBranchService;
    private APIClientInventoryService apiClientInventoryService;

    /**
     * Adds an order to the system.
     *
     * @param requestOrderSaveDTO The DTO containing order details.
     * @return A message indicating the result of the operation.
     */
    @Override
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        // check if the employer exists
        checkEmployerExists(requestOrderSaveDTO.getEmployerId());

        // check if the branch exists
        checkBranchExists((int) requestOrderSaveDTO.getBranchId());

        // Check if items in the order have sufficient quantity
        checkItemStock(requestOrderSaveDTO);

        // Update item quantities
        updateItemQuantities(requestOrderSaveDTO);

        Order order = new Order();
       // order.setEmployer(employerRepository.getById(requestOrderSaveDTO.getEmployerId()));
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
            for (OrderDetails orderDetail : orderDetails) {
                orderDetail.setOrders(order);
            }
            if (!orderDetails.isEmpty()) {
                orderDetailsRepo.saveAll(orderDetails);
            }
            savePaymentDetails(requestOrderSaveDTO.getPaymentDetails(), order);
            return "saved";
        }

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
        List<RequestOrderDetailsSaveDTO> orderDetails = requestOrderSaveDTO.getOrderDetails();

        for (RequestOrderDetailsSaveDTO orderDetail : orderDetails) {
            System.out.println(  orderDetail.getId() +" Order Details"+ orderDetail.getAmount());
            ResponseEntity<StandardResponse> responseEntityForItem =
                    apiClientInventoryService.checkItemExistsAndQuantityAvailable(
                            orderDetail.getId(), orderDetail.getAmount()
                    );

            boolean itemExists = (boolean) Objects.requireNonNull(responseEntityForItem.getBody()).getData();

            if (!itemExists) {
                throw new NotFoundException("Item not found with ID: " + orderDetail.getId());
            }

            ResponseEntity<StandardResponse> responseEntityForItemStock =
                    apiClientInventoryService.checkItemExistsAndQuantityAvailable(
                            orderDetail.getId(), orderDetail.getAmount()
                    );

            boolean itemStockAvailable =
                    (boolean) Objects.requireNonNull(responseEntityForItemStock.getBody()).getData();

            if (!itemStockAvailable) {
                throw new InsufficientItemQuantityException(
                        "Insufficient quantity for item with ID: " + orderDetail.getId()
                );
            }
        }
    }

    /**
     * Checks if an employer exists by their ID.
     *
     * @param employerId The ID of the employer to check.
     * @throws NotFoundException if the employer is not found.
     */
    private void checkEmployerExists(long employerId) {
        ResponseEntity<StandardResponse> responseEntityForEmployee =
                apiClientEmployeeService.checkEmployerExistsById(employerId);

        boolean employerExists = (boolean) Objects.requireNonNull(responseEntityForEmployee.getBody()).getData();

        if(!employerExists){
            throw new NotFoundException("Employer not found with ID: " + employerId);
        }

    }

    /**
     * Checks if a branch exists by its ID.
     *
     * @param branchId The ID of the branch to check.
     * @throws NotFoundException if the branch is not found.
     */
    private void checkBranchExists(int branchId) {
        ResponseEntity<StandardResponse> responseEntityForBranch =
                apiClientBranchService.checkBranchExistsById(branchId);

        boolean branchExists = (boolean) Objects.requireNonNull(responseEntityForBranch.getBody()).getData();

        if (!branchExists) {
            throw new NotFoundException("Branch not found with ID: " + branchId);
        }

    }

    /**
     * Updates the quantities of items in the database after an order is placed.
     *
     * @param requestOrderSaveDTO The DTO containing the order details.
     * @throws NotFoundException if an item in the order is not found in the database.
     */
    private void updateItemQuantities(RequestOrderSaveDTO requestOrderSaveDTO) {
    List<RequestOrderDetailsSaveDTO> orderDetails = requestOrderSaveDTO.getOrderDetails();
        List<ItemQuantityDTO> itemsQuantityList = new ArrayList<>();

        for (RequestOrderDetailsSaveDTO orderDetail : orderDetails) {
            ItemQuantityDTO itemQuantityDTO = new ItemQuantityDTO();
            itemQuantityDTO.setItemId(orderDetail.getId());
            itemQuantityDTO.setItemQuantity(orderDetail.getAmount());
            itemsQuantityList.add(itemQuantityDTO);
        }
       apiClientInventoryService.updateItemQuantities(itemsQuantityList);

    }

    /**
     * Saves payment details for an order.
     *
     * @param paymentDetailsDTO The DTO containing payment details.
     * @param order             The order for which the payment is made.
     */
    private void savePaymentDetails(RequestPaymentDetailsDTO paymentDetailsDTO, Order order) {

        PaymentDetails paymentDetails = modelMapper.map(paymentDetailsDTO, PaymentDetails.class);
        paymentDetails.setOrders(order);

        paymentRepository.save(paymentDetails);
    }


    @Override
    public List<OrderResponseDTO> getAllOrdersWithDetails() {
       return null;
    }
}