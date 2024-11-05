package com.example1.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data


public class RegiDto {

    private Long id;

    @Size( min =2 ,message = "should be more than 2 characters")
    private String name;

     @Email(message="invalid  email format")
    private String email;

     @Size( min =10 , max =10, message = "should be 10 digits")
    private String mobile;

    private String message;

}