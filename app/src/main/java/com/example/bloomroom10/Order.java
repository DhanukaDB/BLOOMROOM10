package com.example.bloomroom10;

public class Order {
    private String orderFlowerName;
    private String orderFlowerOffer;
    private String customerName;
    private int contactNumber;
    private String customerAddress;
    private String orderStatus;

    // Constructors, getters, setters as needed...

    public Order(String OrderFlowerName, String orderFlowerOffer, String customerName,  int contactNumber, String customerAddress, String orderStatus) {
        this.orderFlowerName = orderFlowerName;
        this.orderFlowerOffer = orderFlowerOffer;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.customerAddress = customerAddress;
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
