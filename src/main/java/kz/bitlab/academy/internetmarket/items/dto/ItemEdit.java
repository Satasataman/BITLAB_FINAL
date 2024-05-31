package kz.bitlab.academy.internetmarket.items.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEdit {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer vertices;

}
