package com.demco.metrology_backend.wrapper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserWrapper {

    private  Long id ;

    private String firstName;

    private String lastName;

    private String telephone;

    private String email;

    private String status;

    public UserWrapper(Long id, String firstName, String lastName, String telephone, String email, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
    }


}