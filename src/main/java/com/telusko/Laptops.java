package com.telusko;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Laptops {

    @Id
    private int laptopId;
    private String brand;
    private int price;

    public int getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(int laptopId) {
        this.laptopId = laptopId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "laptopId=" + laptopId +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
