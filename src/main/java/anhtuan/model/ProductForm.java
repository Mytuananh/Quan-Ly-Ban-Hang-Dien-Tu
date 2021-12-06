package anhtuan.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
public class ProductForm {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Category category;
    private MultipartFile image;

    public ProductForm() {
    }

    public ProductForm(Long id, String name, Double price, String description, MultipartFile image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public ProductForm(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProductForm(String name, Double price, String description, MultipartFile image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public ProductForm(Long id, String name, Double price, String description, Category category, MultipartFile image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public ProductForm(Long id, String name, Double price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }



    public ProductForm(String name, Double price, String description, Category category, MultipartFile image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }



}
