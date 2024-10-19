package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/v1/users")
    public List<UserDto> getAllUsers(){
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

   

    @GetMapping("/simple")
    public List<UserBasicDto> getAllUserBasicDto(){
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toBasicDto)
                          .toList();
    }


    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userMapper.toDto(userService.getUser(id).orElseThrow());
    }

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        return null;
    }

}