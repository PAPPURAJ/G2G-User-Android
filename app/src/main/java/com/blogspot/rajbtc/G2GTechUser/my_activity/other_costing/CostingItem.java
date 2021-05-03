package com.blogspot.rajbtc.G2GTechUser.my_activity.other_costing;

public class CostingItem {
    private String docID,costName,quantity,payment, type,date, time;

    public CostingItem(String docID,String costName,String quantity,String payment, String type, String time, String date) {
        this.docID=docID;
        this.costName=costName;
        this.quantity=quantity;
        this.payment = payment;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public String getDocID() {
        return docID;
    }

    public String getCostName() {
        return costName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPayment() {
        return payment;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
