package org.example.demo.demoproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.demoproject.model.request.UserAmountRequest;
import org.example.demo.demoproject.model.request.UserDataForChangeRequest;
import org.example.demo.demoproject.model.request.UserDataRequest;
import org.example.demo.demoproject.model.response.MessageResponse;
import org.example.demo.demoproject.model.response.UserResponse;
import org.example.demo.demoproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/managed-user-data")
    public ResponseEntity<UserResponse> getUsers(@RequestParam(required = false) Map<String, String> params) {
        return new ResponseEntity<>(userService.getUsers(params), HttpStatus.OK);
    }

    @DeleteMapping(path = "managed-user-data")
    public ResponseEntity<MessageResponse> deleteUserData(@Valid @RequestBody UserDataRequest dataRequest) {
        return new ResponseEntity<>(userService.deleteUsersData(dataRequest), HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "managed-user-data")
    public ResponseEntity<MessageResponse> addUserData(@Valid @RequestBody UserDataRequest dataRequest) {
        return new ResponseEntity<>(userService.addUsersData(dataRequest), HttpStatus.OK);
    }

    @PatchMapping(path = "managed-user-data")
    public ResponseEntity<MessageResponse> changeUserData(@Valid @RequestBody UserDataForChangeRequest dataRequest) {
        return new ResponseEntity<>(userService.changeUserData(dataRequest), HttpStatus.OK);
    }

    @PostMapping(path = "managed-user-amount")
    public ResponseEntity<MessageResponse> transferAmount(@RequestBody UserAmountRequest amountRequest) {
        return new ResponseEntity<>(userService.transferAmount(amountRequest), HttpStatus.OK);
    }

}
