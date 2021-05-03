package com.blogspot.rajbtc.G2GTechUser.employee;

public class EmployeeItem {
    String docID,name,fatherName,motherName,contacts,email,nid,preAddress,perAddress,pass,cvLink,type,site;

    public EmployeeItem(String docID,String name, String fatherName, String motherName, String contacts, String email, String nid, String preAddress, String perAddress, String pass, String cvLink, String type,String site) {
        this.docID=docID;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.contacts = contacts;
        this.email = email;
        this.nid = nid;
        this.preAddress = preAddress;
        this.perAddress = perAddress;
        this.pass = pass;
        this.cvLink = cvLink;
        this.type = type;
        this.site=site;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getContacts() {
        return contacts;
    }

    public String getEmail() {
        return email;
    }

    public String getNid() {
        return nid;
    }

    public String getPreAddress() {
        return preAddress;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public String getPass() {
        return pass;
    }

    public String getCvLink() {
        return cvLink;
    }

    public String getType() {
        return type;
    }

    public String getDocID() {
        return docID;
    }

    public String getSite() {
        return site;
    }
}
