package com.ltp.globalsuperstore;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

public class Item {
    @NotBlank(message = "Please choose a category.")
    private String category;
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    @NonNull
    @PositiveOrZero
    private Double price;
    @NonNull
    @PositiveOrZero
    private Double discount;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date must be in the past.")
    private Date date;
    private String id;


    public Item() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
