package com.Group1DevopsCoursework;

public class Main {
    public static void main(String[] args) {
        Database_Connection db = new Database_Connection();  // create instance
        db.connect();
        db.disconnect();

    }
}