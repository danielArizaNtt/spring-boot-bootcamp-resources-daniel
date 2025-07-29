package com.ltp.globalsuperstore.services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ltp.globalsuperstore.Constants;
import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.repository.StoreRepository;

public class StoreService {
    private StoreRepository storeRepository = new StoreRepository();

    public Item getItemById(String id) {
        int index = getIndexFromId(id);
        Item item = (index != Constants.NOT_FOUND) ? storeRepository.getItem(index) : new Item();
        return item;
    }

    public String submitItem(Item item) {
        int index = getIndexFromId(item.getId());
        String status = Constants.SUCCESS_STATUS;
        if (index == Constants.NOT_FOUND) {
            storeRepository.addItem(item);
        } else if (within5Days(item.getDate(), storeRepository.getItem(index).getDate())) {
            storeRepository.updateItem(index, item);
        } else {
            status = Constants.FAILED_STATUS;
        }

        return status;
    }

    public boolean isValidPrice(Double price, Double discount) {
        return (price <= discount) ? true : false;
    }

    private boolean within5Days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }

    private int getIndexFromId(String id) {
        for (int i = 0; i < storeRepository.getItems().size(); i++) {
            if (storeRepository.getItem(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }

    public List<Item> getItems() {
        return storeRepository.getItems();
    }
}
