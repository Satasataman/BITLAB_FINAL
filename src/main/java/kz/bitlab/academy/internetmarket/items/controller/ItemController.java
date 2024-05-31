package kz.bitlab.academy.internetmarket.items.controller;

import kz.bitlab.academy.internetmarket.comments.service.CommentService;
import kz.bitlab.academy.internetmarket.items.dto.ItemEdit;
import kz.bitlab.academy.internetmarket.items.service.ItemService;
import kz.bitlab.academy.internetmarket.users.service.CartService;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CommentService commentService;
    private final UserService userService;
    private final CartService cartService;

    @Value("${loadURL}")
    private String loadURL;

    @GetMapping("/")
    public String indexPage(Model model) {

        model.addAttribute("items", itemService.findAll());

        if (userService.getCurrentUser() != null) {
            model.addAttribute("user", userService.getCurrentUser());
            model.addAttribute("cart", cartService.findItemIdsInCart(userService.getCurrentUser().getId()));
        }

        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/items/create")
    public String createItem(@RequestParam(name = "imageToken") MultipartFile file,
                             ItemEdit input) {

        return itemService.create(file, input);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/details/update")
    public String updateItem(@RequestParam(name = "itemId") Long itemId,
                             @RequestParam(name = "imageToken") MultipartFile file,
                             ItemEdit input) {

        return itemService.update(itemId, file, input);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/items/delete")
    public String deleteItem(@RequestParam(name = "itemId") Long itemId) {

        itemService.delete(itemId);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String detailsPage(@PathVariable Long id, Model model) {

        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("comments", commentService.findAllByItemId(id));

        if (userService.getCurrentUser() != null) {
            model.addAttribute("user", userService.getCurrentUser());
            model.addAttribute("cart", cartService.findItemIdsInCart(userService.getCurrentUser().getId()));
        }

        return "details";
    }

    @GetMapping(value = "/getImage/{imageToken}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable(name = "imageToken", required = false) String gameImageToken) throws IOException {
        String pictureURL = loadURL + "images/defaultImage.jpg";
        if (gameImageToken != null) {
            pictureURL = loadURL + "images/" + gameImageToken + ".jpg";
        }
        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            pictureURL = loadURL + "images/defaultImage.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            inputStream = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(inputStream);
    }

}
