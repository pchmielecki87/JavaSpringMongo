package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String listItems(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/add")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "edit";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String showEditItemForm(@PathVariable String id, Model model) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            model.addAttribute("item", item);
            return "edit";
        }
        return "redirect:/items";
    }

    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable String id, @ModelAttribute Item item) {
        item.setId(id);
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable String id) {
        itemRepository.deleteById(id);
        return "redirect:/items";
    }
}