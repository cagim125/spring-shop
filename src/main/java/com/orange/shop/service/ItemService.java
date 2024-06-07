package com.orange.shop.service;


import com.orange.shop.entity.Item;
import com.orange.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    public Optional<Item> findById(Long id) {return itemRepository.findById(id);}


    public void update(Long id, String title, Integer price){
        Item item = new Item(id, title, price);
        itemRepository.save(item);
    }

    public void saveItem(String title, Integer price) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
}
