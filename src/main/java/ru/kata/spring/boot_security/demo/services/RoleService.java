package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    void delete(long id);
    Role getByName(String name);
    public Role getById(long id);
    Role editBank(Role role);
    List<Role> getAll();



}
