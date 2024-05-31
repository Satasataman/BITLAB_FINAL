package kz.bitlab.academy.internetmarket.items.service;

import kz.bitlab.academy.internetmarket.items.dto.ItemEdit;
import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    String create(MultipartFile file, ItemEdit input);

    String update(Long id, MultipartFile file, ItemEdit input);

    List<ItemEntity> findAll();

    ItemEntity findById(Long id);

    void delete(Long id);

}
