package kz.bitlab.academy.internetmarket.items.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.bitlab.academy.internetmarket.comments.service.CommentService;
import kz.bitlab.academy.internetmarket.items.dto.ItemEdit;
import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import kz.bitlab.academy.internetmarket.items.mapper.ItemMapper;
import kz.bitlab.academy.internetmarket.items.repository.ItemRepository;
import kz.bitlab.academy.internetmarket.items.service.ItemService;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import kz.bitlab.academy.internetmarket.users.repository.CartRepository;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final UserService userService;
    private final CommentService commentService;
    private final CartRepository cartRepository;
    private static final String DEFAULT_IMAGE_PATH = "defaultImage";

    @Value("${targetURL}")
    private String targetURL;

    @Transactional
    @Override
    public String create(MultipartFile file, ItemEdit input) {

        ItemEntity entity = ItemMapper.INSTANCE.toEntity(input);
        String image = uploadImage(file, entity);

        entity.setImage(image);
        repository.save(entity);

        return "redirect:/";
    }

    @Transactional
    @Override
    public String update(Long id, MultipartFile file, ItemEdit input) {

        ItemEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));

        entity.setName(input.getName());
        entity.setDescription(input.getDescription());
        entity.setPrice(input.getPrice());
        entity.setVertices(input.getVertices());
        String image = uploadImage(file, entity);
        entity.setImage(image);

        return "redirect:/details/" + id;
    }

    @Override
    public List<ItemEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ItemEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    private String uploadImage(MultipartFile multipartFile, ItemEntity entity) {
        UserEntity currentUser = userService.getCurrentUser();
        String imageToken = DigestUtils.sha1Hex(currentUser.getUsername() + "_" + entity.getName() + "_" + Math.random());
        String image = imageToken + ".jpg";

        if (multipartFile == null || multipartFile.isEmpty()) {
            return DEFAULT_IMAGE_PATH;
        }

        if (multipartFile.getContentType().equals("image/jpeg") || multipartFile.getContentType().equals("image/png")) {
            try {
                Path directory = Paths.get(targetURL, "images");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                    System.out.println("Created directory: " + directory.toString());
                } else {
                    System.out.println("Directory already exists: " + directory.toString());
                }
                Path path = directory.resolve(image);
                System.out.println("Saving file to: " + path.toString());
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes);
                entity.setImage(imageToken);
                System.out.println("Image uploaded successfully to: " + path.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image", e);
            }
        } else {
            throw new RuntimeException("Invalid image file type");
        }
        return imageToken;
    }

    @Transactional
    @Override
    public void delete(Long id) {

        cartRepository.deleteFromCart(id);
        commentService.deleteByItemId(id);
        repository.deleteById(id);
    }
}
