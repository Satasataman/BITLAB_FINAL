package kz.bitlab.academy.internetmarket.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Long> findCartItemIdsByUserId(Long userId) {

        String sql = "SELECT item_id FROM cart WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }

    public void deleteFromCart(Long itemId) {

        String sql = "DELETE FROM cart WHERE item_id = ?";
        jdbcTemplate.update(sql, itemId);
    }

}
