package com.okejkadev.clinic_system.service.impl;

import com.okejkadev.clinic_system.dto.request.LoginRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.dto.UserDTO;
import com.okejkadev.clinic_system.entity.User;
import com.okejkadev.clinic_system.exception.CustomException;
import com.okejkadev.clinic_system.repository.UserRepository;
import com.okejkadev.clinic_system.service.interf.IUserService;
import com.okejkadev.clinic_system.utils.JWTUtils;
import com.okejkadev.clinic_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Response addUser(User user) {
        Response response = new Response();
        try {
            if(user.getRole() == null || user.getRole().isEmpty())
                user.setRole("CLINICIAN");

            if(userRepository.existsByEmail(user.getEmail()))
                throw new CustomException(user.getEmail() + "User already exists");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);

        } catch (CustomException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CustomException("User not found"));
            var jwt = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Successfully logged in");
            response.setStatusCode(200);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during login" + e.getMessage());
        }
        return response;
    }



    @Override
    public Response getAllUsers(String name) {
        Response response = new Response();
        try {
            List<User> users;
            if (name != null && !name.isBlank()) {
                users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
            } else {
                users = userRepository.findAll();
            }
            List<UserDTO> userDTOList = Utils.mapUserListToUserDTOList(users);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved all users");
            response.setUserList(userDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieve all users" + e.getMessage());
        }
        return response;
    }


    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomException("User not found "));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during delete user" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomException("User not found "));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved user");
            response.setUser(userDTO);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieve user" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved user");
            response.setUser(userDTO);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during retrieve user" + e.getMessage());
        }
        return response;
    }
}
