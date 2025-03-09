package com.okejkadev.clinic_system.controller;

import com.okejkadev.clinic_system.dto.request.LoginRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.User;
import com.okejkadev.clinic_system.service.impl.UserService;
import com.okejkadev.clinic_system.service.interf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService IUserService;
    @Autowired
    private UserService userService;


    @PostMapping("/add-admin")
    public ResponseEntity<Response> addAdmin(@RequestBody User admin) {
        Response response = userService.addUser(admin);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/add-clinician")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addClinician(@RequestBody User user) {
        Response response = userService.addUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
