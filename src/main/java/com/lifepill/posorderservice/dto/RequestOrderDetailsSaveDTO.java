package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSaveDTO {

    private String itemName;
    private Double qty;
    private Double totalAmount;
    private int items;
}
