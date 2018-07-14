package br.com.casadocodigo.loja.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class Price {

    @Column(scale = 2)
    @Min(1)
    @NotNull
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private BookType bookType;

}
