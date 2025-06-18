package com.example.demo.trainning;

public class Test2 {

    public String test(Test test) {
        // This is a test method
        System.out.println("Test method executed");
        return test.returnNom("");
    }

    public static void main(String[] args) {
        Test t = new Test("faycal");

        Test2 t2 = new Test2();

        System.out.println(t2.test(t));
    }
}
