package org.example.models;

public class OrderScooter {

     private String firstName;
     private String lastName;
     private String address;
     private String metroStation;
     private String phone;
     private int rentTime;
     private String deliveryDate;
     private String comment;
     private String[] color;

    public OrderScooter withFirstName (String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OrderScooter withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OrderScooter withAddress(String address) {
        this.address = address;
        return this;
    }

    public OrderScooter withMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public OrderScooter withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public OrderScooter withRentTime(int rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public OrderScooter withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public OrderScooter withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderScooter withColor(String[] color) {
        this.color = color;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColor(String[] color) {
        this.color = color;
    }
}
