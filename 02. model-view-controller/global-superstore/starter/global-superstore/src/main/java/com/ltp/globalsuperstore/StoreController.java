package com.ltp.globalsuperstore;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class StoreController {
    
    List<Item> items = new ArrayList<>();

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        int index = getItemIndex(id);

        Item item = (index == Constants.NOT_FOUND) ? new Item() : items.get(index);

        model.addAttribute("item", item);
        model.addAttribute("categories", Constants.CATEGORIES);

        return "form";
    }

    @PostMapping("/submitItem")
    public String submitForm(Item item, RedirectAttributes redirectAttributes) {
        String status = Constants.SUCCESS_STATUS;
        int itemIndex = getItemIndex(item.getId());
        boolean itemExists = (itemIndex == Constants.NOT_FOUND) ? false : true; 

        if (itemExists) {
            Date newDate = item.getDate();
            Date oldDate = items.get(itemIndex).getDate();

            if (isWithinDaysLimit(newDate, oldDate)) {
                items.set(itemIndex, item);
            } else {
                status = Constants.FAILED_STATUS;
            }
        } else {
            items.add(item);
        }
        
        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("items", items);
        return "inventory";
    }

    public int getItemIndex(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    boolean isWithinDaysLimit(Date newDate, Date oldDate) {
        long diffInMillis = Math.abs(newDate.getTime() - oldDate.getTime());
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        boolean isWithinValidTime = diffInDays <= Constants.DAYS_LIMIT;
        return  isWithinValidTime;
    }
}
