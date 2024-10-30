package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    // @GetMapping("/v1/users")
    // public List<UserDto> getAllUsers(){
    //     return userService.findAllUsers()
    //                       .stream()
    //                       .map(userMapper::toDto)
    //                       .toList();
    // }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userMapper.toDto(userService.getUser(id).orElseThrow());
    }

   

    // @GetMapping("/simple")
    // public List<UserBasicDto> getAllUserBasicDto(){
    //     return userService.findAllUsers()
    //                       .stream()
    //                       .map(userMapper::toBasicDto)
    //                       .toList();
    // }

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple(){
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toSimpleDto)
                          .toList();
    }





    // @PostMapping("/addUser")
    // public ResponseEntity<User> createUser(@RequestBody User user) {
    //     User createdUser = userService.createUser(user);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    // }


    // @PostMapping
    // public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

    //     // Demonstracja how to use @RequestBody
    //     System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

    //     // TODO: saveUser with Service and return User
    //     return userService.createUser(userMapper.toEntity(userDto));
    //     // return null;
    // }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        log.info("User with e-mail: {} passed to the request", userDto.email());

        User newUser = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(newUser);

        // Mapowanie utworzonego użytkownika z powrotem na DTO przed zwróceniem
        UserDto createdUserDto = userMapper.toDto(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }
}

