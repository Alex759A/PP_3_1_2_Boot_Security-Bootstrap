package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.*;



@Service
public class UserDetailsServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userNameLogin = userRepository.findByUserNameAndFetchRoles(username);
        if (userNameLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userNameLogin;
    }

    @Override
    public User findOne(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

//    @Transactional
//    @Override
//    public boolean saveUser(User user)  {
//        System.out.println("SAVE----------------");
//        if (user.getId() == 0) {
//            Set<Role> roles = new HashSet<>();
//            for (Role x : user.getRoles()) {
//                System.out.println(x + "  Это X-------------");
//                String name = x.getName();
//                System.out.println(name + "  Это X-------------");
//
//                roles.add(roleService.addRole(roleRepository.findByName(name)));
//            }
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
////            user.setRoles(roles);
//            userRepository.save(user);
//            return true;
//        } else {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            userRepository.save(user);
//        }
//        return true;
//    }
    @Transactional
    @Override
    public boolean saveUser(User user)  {
        System.out.println("SAVE----------------");
//        System.out.println("Это роли save---------" + user.getRoles());
//        System.out.println("Это роли save class---------" + user.getRoles().getClass());
        Set<Role> roles = new HashSet<>();
        roles.add((Role) user.getRoles());
        System.out.println("Это роли save---------" + roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);

        return true;
    }



    @Transactional
    @Override
    public void update(Long id, User updateUser) {
        User updateNewUser = userRepository.findById(id).get();

        Set<Role> roles = updateNewUser.getRoles();

        for (Role x : updateUser.getRoles()) {
            System.out.println(x +  "Это X --------");
            long z = x.getId();

            roles.add(roleService.getById(z));
            System.out.println("Это роли ------------    " + roles);
        }
        updateNewUser.setRoles(roles);
        updateNewUser.setFirstName(updateUser.getFirstName());
        updateNewUser.setLastName(updateUser.getLastName());
        updateNewUser.setAge(updateUser.getAge());
        updateNewUser.setEmail(updateUser.getEmail());

        userRepository.save(updateNewUser);
    }

    @Transactional
    @Override
    public boolean delete(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}




