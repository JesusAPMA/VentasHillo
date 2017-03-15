package com.example.lenovo.ventashillo;

/**
 * Created by jesus on 18/02/2017.
 */

public class Publication_Details {
    private  String name;
    private   String phone;
    private   String country;
    private   String id;
    private  String date;
    private   String condition;
    private   String description;
    private   String cost;
    private   String title;
    private   String image;
public Publication_Details(){

}
    //String title,String desciption, String country, String id,int image,String phone, String name, String date,String condition, String cost
    public Publication_Details(String title,String description, String country, String id,String image,String phone, String name, String date,String condition, String cost) {
        this.title = title;
        this.description = description;
        this.country = country;
        this.id = id;
        this.image=image;
        this.phone = phone;
        this.name = name;
        this.date = date;
        this.condition = condition;
        this.cost = cost;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
