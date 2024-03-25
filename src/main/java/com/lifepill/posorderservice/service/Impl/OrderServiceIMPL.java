package com.lifepill.posorderservice.service.Impl;

import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.OrderDetails;
import com.lifepill.posorderservice.repository.OrderDetailsRepo;
import com.lifepill.posorderservice.repository.OrderRepo;
import com.lifepill.posorderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    private CashierRepo cashierRepo;
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;
//    @Autowired
//    private ItemRepo itemRepo;

    @Override
    @Transactional
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        Order order = new Order(
          cashierRepo.getById(requestOrderSaveDTO.getCashiers()),
          requestOrderSaveDTO.getOrderDate(),
          requestOrderSaveDTO.getTotal()
        );
        orderRepo.save(order);
        if (orderRepo.existsById(order.getOrderId())){

            List<OrderDetails> orderDetails = modelMapper.
                    map(requestOrderSaveDTO.getOrderDetails(),new TypeToken<List<OrderDetails>>(){
                    }.getType());

            for (int i=0;i<orderDetails.size();i++){
                orderDetails.get(i).setOrders(order);
                orderDetails.get(i).setItems(itemRepo.getById(requestOrderSaveDTO.getOrderDetails().get(i).getItems()));
            }

            if (orderDetails.size()>0){
                orderDetailsRepo.saveAll(orderDetails);
            }
            return "saved";
        }
        return null;
    }
}
