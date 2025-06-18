package com.example.demo;

public record StudentRegistrationRequest (

    String firstName,
    String lastName,
    String email,
    Integer age

){}

