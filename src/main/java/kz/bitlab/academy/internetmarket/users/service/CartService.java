package kz.bitlab.academy.internetmarket.users.service;

import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface CartService {

    void addToCart(Long userId, Long itemId);

    List<Long> findItemIdsInCart(Long userId);

    List<ItemEntity> findAll();

    BigDecimal getCartPrice();

}
