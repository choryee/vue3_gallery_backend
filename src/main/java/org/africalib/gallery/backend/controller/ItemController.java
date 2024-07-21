package org.africalib.gallery.backend.controller;

import org.africalib.gallery.backend.entity.Item;
import org.africalib.gallery.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems(){ // 첫화면에서 data뿌리기
        List<Item> items = itemRepository.findAll();

//        List<String> items=new ArrayList<>();
//        items.add("alpha");
//        items.add("beta");
//        items.add("gamma");
        return items;
    }
}
