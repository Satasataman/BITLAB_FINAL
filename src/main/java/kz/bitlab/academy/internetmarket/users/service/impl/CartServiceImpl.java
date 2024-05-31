package kz.bitlab.academy.internetmarket.users.service.impl;

import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import kz.bitlab.academy.internetmarket.items.service.ItemService;
import kz.bitlab.academy.internetmarket.users.repository.CartRepository;
import kz.bitlab.academy.internetmarket.users.service.CartService;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CartRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    public void addToCart(Long userId, Long itemId) {
        String selectSql = "SELECT COUNT(*) FROM cart WHERE user_id = ? AND item_id = ?";
        int count = jdbcTemplate.queryForObject(selectSql, Integer.class, userId, itemId);

        if (count == 0) {
            String insertSql = "INSERT INTO cart (user_id, item_id) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, userId, itemId);
        }

    }

    @Override
    public List<Long> findItemIdsInCart(Long userId) {

        return repository.findCartItemIdsByUserId(userId);
    }

    @Override
    public List<ItemEntity> findAll() {

        List<Long> cartItemIds = findItemIdsInCart(userService.getCurrentUser().getId());
        List<ItemEntity> cart = new ArrayList<>();

        for (Long itemId : cartItemIds) {
            itemService.findById(itemId);
            cart.add(itemService.findById(itemId));
        }

        return cart;
    }

    @Override
    public BigDecimal getCartPrice() {

        List<ItemEntity> cart = findAll();
        BigDecimal sum = BigDecimal.ZERO;
        for (ItemEntity item : cart) {
            sum = sum.add(item.getPrice());
        }
        return sum;
    }

}
