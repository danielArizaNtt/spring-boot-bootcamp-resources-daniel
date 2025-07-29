package com.ltp.globalsuperstore.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.services.StoreService;

@Controller
public class StoreController {

    private StoreService storeService = new StoreService();

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        Item item = storeService.getItemById(id);
        model.addAttribute("item", item);
        return "form";
    }

    @PostMapping("/submitItem")
    public String handleSubmit(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if (!storeService.isValidPrice(item.getPrice(), item.getDiscount())) {
            result.rejectValue("price", "", "Price cannot be less than discount");
        }

        if (result.hasErrors()) return "form";

        String status = storeService.submitItem(item);

        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("items", storeService.getItems());
        return "inventory";
    }

}
