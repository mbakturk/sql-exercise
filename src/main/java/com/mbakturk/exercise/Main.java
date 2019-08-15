package com.mbakturk.exercise;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        Table users = new Table("users");
        Table purchases = new Table("purchases");

        try {
            System.out.println("-----Users-----");
            for (String[] value : users.orderByDesc("name")) {
                System.out.println(Arrays.toString(value));
            }

            System.out.println("-----Purchases-----");
            for (String[] value : purchases.orderByDesc("product")) {
                System.out.println(Arrays.toString(value));
            }

            System.out.println("-----Purchases INNER JOIN Users-----");
            for (String[] value : purchases.innerJoin(users, "user_id", "id")
                    .orderByDesc("users.name")) {
                System.out.println(Arrays.toString(value));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
