package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.entities.Role;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    private Long id;
    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String username;


    private String password;


    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String firstName;




    @NotEmpty(message = "Name should note be empty!")
    @Size(min = 2, max = 30, message = "Name should be betwen 2 and 30 characters")
    private String lastName;


    private Long age;


    @NotEmpty(message = "Email should note be empty!")
    @Email(message = "Email should be valid!")
    private String email;


    private Set<Role> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
