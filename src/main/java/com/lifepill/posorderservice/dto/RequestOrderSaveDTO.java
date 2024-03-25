package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderSaveDTO {

    private int cashiers;
    private Date orderDate;
    private Double total;
    private List<RequestOrderDetailsSaveDTO> orderDetails;
}
