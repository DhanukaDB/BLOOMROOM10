package com.example.bloomroom10;

public class Order {
    private String id;
    private String orderFlowerName;
    private String orderFlowerOffer;
    private String customerName;
    private int contactNumber;
    private String customerAddress;
    private String orderStatus;

    public Order() {
    }
    // Constructors, getters, setters as needed...

    public Order(String orderFlowerName, String orderFlowerOffer, String customerName,  int contactNumber, String customerAddress, String orderStatus) {
        this.orderFlowerName = orderFlowerName;
        this.orderFlowerOffer = orderFlowerOffer;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.customerAddress = customerAddress;
        this.orderStatus = orderStatus;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderFlowerName(String orderFlowerName) {
        this.orderFlowerName = orderFlowerName;
    }

    public void setOrderFlowerOffer(String orderFlowerOffer) {
        this.orderFlowerOffer = orderFlowerOffer;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderFlowerName() {
        return orderFlowerName;
    }

    public String getOrderFlowerOffer() {
        return orderFlowerOffer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
