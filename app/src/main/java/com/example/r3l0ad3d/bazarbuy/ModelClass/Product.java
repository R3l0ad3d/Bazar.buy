package com.example.r3l0ad3d.bazarbuy.ModelClass;

import java.io.Serializable;

/**
 * Created by r3l0ad3d on 5/26/17.
 */

public class Product implements Serializable {
    private String productId;
    private String userId;
    private String ownerName;
    private String productName;
    private String productCatagory;
    private String productDetails;
    private String productQuentity;
    private String productPrice;
    private String ownerContact;
    private String ownerMobileNo;
    private String ownerEmailNo;
    private String ownerLocation;
    private String createTime;
    private String productImageOne;
    private String productImageTwo;
    private String productImageThree;
    private String isSell;
    private String isBookMarked;

    public Product(String productId, String userId, String productName, String productCatagory,
                   String productDetails, String productQuentity, String productPrice,
                   String ownerContact, String ownerLocation, String createTime,
                   String productImageOne, String productImageTwo, String productImageThree,String isSell) {
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.productCatagory = productCatagory;
        this.productDetails = productDetails;
        this.productQuentity = productQuentity;
        this.productPrice = productPrice;
        this.ownerContact = ownerContact;
        this.ownerLocation = ownerLocation;
        this.createTime = createTime;
        this.productImageOne = productImageOne;
        this.productImageTwo = productImageTwo;
        this.productImageThree = productImageThree;
        this.isSell=isSell;
    }

    public Product() {
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCatagory() {
        return productCatagory;
    }

    public void setProductCatagory(String productCatagory) {
        this.productCatagory = productCatagory;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerLocation() {
        return ownerLocation;
    }

    public void setOwnerLocation(String ownerLocation) {
        this.ownerLocation = ownerLocation;
    }

    public String getProductImageOne() {
        return productImageOne;
    }

    public void setProductImageOne(String productImageOne) {
        this.productImageOne = productImageOne;
    }

    public String getProductImageTwo() {
        return productImageTwo;
    }

    public void setProductImageTwo(String productImageTwo) {
        this.productImageTwo = productImageTwo;
    }

    public String getProductImageThree() {
        return productImageThree;
    }

    public void setProductImageThree(String productImageThree) {
        this.productImageThree = productImageThree;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuentity() {
        return productQuentity;
    }

    public void setProductQuentity(String productQuentity) {
        this.productQuentity = productQuentity;
    }

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell;
    }

    public String getIsBookMarked() {
        return isBookMarked;
    }

    public void setIsBookMarked(String isBookMarked) {
        this.isBookMarked = isBookMarked;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOwnerMobileNo() {
        return ownerMobileNo;
    }

    public void setOwnerMobileNo(String ownerMobileNo) {
        this.ownerMobileNo = ownerMobileNo;
    }

    public String getOwnerEmailNo() {
        return ownerEmailNo;
    }

    public void setOwnerEmailNo(String ownerEmailNo) {
        this.ownerEmailNo = ownerEmailNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
