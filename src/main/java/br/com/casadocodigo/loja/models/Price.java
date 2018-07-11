package br.com.casadocodigo.loja.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class Price {

    @Column(scale = 2)
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private BookType bookType;

}
