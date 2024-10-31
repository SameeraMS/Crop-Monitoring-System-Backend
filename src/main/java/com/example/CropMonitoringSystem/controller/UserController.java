package com.example.CropMonitoringSystem.controller;

import com.example.CropMonitoringSystem.dto.impl.UserDto;
import com.example.CropMonitoringSystem.entity.Role;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${administrator.code}")
    private String adminCode;
    @Value("${manager.code}")
    private String managerCode;
    @Value("${scientist.code}")
    private String scientistCode;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@RequestBody UserDto userDto) {

        if (userDto.getRole().equals(Role.ADMINISTRATIVE)) {
            if (!userDto.getRoleCode().equals(adminCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        if (userDto.getRole().equals(Role.MANAGER)) {
            if (!userDto.getRoleCode().equals(managerCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        if (userDto.getRole().equals(Role.SCIENTIST)) {
            if (!userDto.getRoleCode().equals(scientistCode)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        try {
            userService.saveUser(userDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("userId") String userId,@RequestBody UserDto userDto) {
        try {
            userDto.setEmail(userId);
            userService.updateUser(userId, userDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable("userId") String userId) {
            return userService.getSelectedUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
