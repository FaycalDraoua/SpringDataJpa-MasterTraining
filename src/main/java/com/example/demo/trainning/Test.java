package com.example.demo.trainning;

public class Test {

    String name;

    public Test(String str) {
        // Constructor
    this.name = str;
    }

    String returnNom(String nom) {
        return nom;
    }


    public static void main(String[] args) {
        Test test = new Test("Amigoscode");

        String nom = test.returnNom("Amigoscode");

        System.out.println(nom.compareTo("salut"));

    }
}
