package com.eskcti.workercompras.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
public class Order implements Serializable {

    private Long id;
    private String name;
    private Long product;
    private BigDecimal valueOrder;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date purchaseDate;
    private String cpfCustomer;
    private String zipCode;
    private String email;
    private Endereco endereco;

    private Card card;
}
