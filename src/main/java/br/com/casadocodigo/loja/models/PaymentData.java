package br.com.casadocodigo.loja.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PaymentData {

    private BigDecimal value;

}
