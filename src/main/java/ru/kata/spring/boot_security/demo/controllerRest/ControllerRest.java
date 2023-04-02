package ru.kata.spring.boot_security.demo.controllerRest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController   /// @Controller +  @ResponseBody
@RequestMapping("/api")
public class ControllerRest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
//    @Autowired
//    private final ModelMapper modelMapper;
    @Autowired
    public ControllerRest(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
//        this.modelMapper = modelMapper;
    }

    // возврат всех юзеров
    @GetMapping("/allUsers")
    public List<UserDto> findAll() {
        System.out.println("----------GET LIST-----method");

//      userDetailsService.findAll().stream().map(this::convertUserDto).collect(Collectors.toList());
        return userDetailsService.findAll().stream().map(this::convertUserDto).collect(Collectors.toList());

    }


    // возврат одного юзера
    @GetMapping("/allUsers/{id}")
    public UserDto findOne(@PathVariable("id") Long id) {
        System.out.println("Это рес гет запрос по ID");
        User user = userDetailsService.findOne(id);
        if (user.getId() == null) {
           throw new NoSuchUserException("There is no User with ID = " + id +
                   " in DataBase");
        }
        return convertUserDto(user);
//        return user;
    }
    // создание юзера
    @PostMapping("/allUsers")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        System.out.println("Это метод POST 1");
        if (bindingResult.hasErrors()) {
            StringBuilder errorMesage = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                errorMesage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NoSuchUserException (errorMesage.toString());
        }
//        UserDto user1 = convertUserDto(user);
//        User user2 = convertUser(user1);
//        System.out.println("Это роли POST  " + user.getRoles());
        System.out.println("Это RoleID POST  "  );
//        System.out.println("Это emIL POST  " + user2.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
//        System.out.println("Это  iD  --- " + user.getId());
 //       System.out.println(" Это DTO role --- " + user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
        userDetailsService.saveUser(user);
//        User user1 = (User) userDetailsService.loadUserByUsername(name);
//        System.out.println("Это метод POST");
//        System.out.println(user1.getRoles());
        // Отправляем HTTP ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);

    }

    // редактирование полей юзера
    @PutMapping("/allUsers")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        System.out.println("----------PUT-----method");
//        System.out.println(userDto.getRoles());
//        System.out.println(" Это DTO role --- " + userDto.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
        userDetailsService.update(userDto.getId(), convertUser(userDto));
        return userDto;
    }

    // удаление юзера по id
    @DeleteMapping("/allUsers/{id}")
    public String deleteUser(@PathVariable("id")  Long id) {
        System.out.println("----------DELETE-----method");
        User user = userDetailsService.findOne(id);
        if (user.getId() == null) {
            throw new NoSuchUserException("There is no User with ID = " + id +
                    " in DataBase");
        }
         userDetailsService.delete(id);
         return "User with id = " +id+ " was deleted";
    }


    // конвертер UserDto-->User (вернет user, bean(modelMapper) в мэйне)
    private User convertUser(UserDto userDto) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(userDto, User.class);
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setRoles(userDto.getRoles());
//        user.setRoles(Collections.singleton(new Role(3L, "ROLE_USER")));
        return user;
    }

    // конвертер User --> UserDto (вернет user, bean(modelMapper) в мэйне)
    private UserDto convertUserDto(User user) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(user, UserDto.class);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setRoles(user.getRoles());
//        userDto.setRoles(Collections.singleton(new Role(3L, "ROLE_USER")));
        return userDto;
    }


}
