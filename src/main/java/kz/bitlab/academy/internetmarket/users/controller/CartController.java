package kz.bitlab.academy.internetmarket.users.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.bitlab.academy.internetmarket.items.dto.ItemCart;
import kz.bitlab.academy.internetmarket.users.repository.CartRepository;
import kz.bitlab.academy.internetmarket.users.service.CartService;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String Cart(Model model) {

        model.addAttribute("cart", cartService.findAll());
        model.addAttribute("cartPrice", cartService.getCartPrice());
        model.addAttribute("user", userService.getCurrentUser());

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(HttpServletRequest request, ItemCart itemId, Model model) {

        cartService.addToCart(userService.getCurrentUser().getId(), itemId.getItemId());

        String referer = request.getHeader("referer");
        if (referer != null) {
            return "redirect:" + referer;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam (value = "id") Long id) {

        cartRepository.deleteFromCart(id);
        return "redirect:/cart";
    }

}

