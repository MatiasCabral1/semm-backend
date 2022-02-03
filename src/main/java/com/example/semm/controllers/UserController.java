package com.example.semm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.semm.models.CurrentAccount;
import com.example.semm.models.Patent;
import com.example.semm.models.User;
import com.example.semm.security.dto.CurrentAccountDTO;
import com.example.semm.security.dto.Message;
import com.example.semm.security.dto.DataAccountUserDTO;
import com.example.semm.service.impl.ParkingServiceImp;
import com.example.semm.service.impl.UserServiceImp;
import com.example.semm.services.CityService;
import com.example.semm.services.CurrentAccountService;
import com.example.semm.service.impl.PatentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    CurrentAccountService ccServiceImp;

    @Autowired
    PatentServiceImpl patentServiceImp;

    @Autowired
    ParkingServiceImp parkingService;

    @Autowired
    CityService cityService;

    @GetMapping
    public ArrayList<User> getAllUsers() {
        // get list of all users
        return userServiceImp.getAll();
    }

    @GetMapping(path = "/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        // get list of all users
        Optional<User> user = userServiceImp.getById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<Message>(new Message("Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @GetMapping(path = "/getData/{id}")
    public ResponseEntity<DataAccountUserDTO> getDataById(@PathVariable("id") Long id) {
        // get the data of a user by id
        Optional<User> user = this.userServiceImp.getById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            DataAccountUserDTO accountData = new DataAccountUserDTO(user.get().getName(), user.get().getUsername(),
                    user.get().getEmail(), user.get().getCurrentAccount());
            return new ResponseEntity<DataAccountUserDTO>(accountData, HttpStatus.OK);
        }

    }

    @GetMapping(path = "/patents/{id}")
    public ResponseEntity<Set<Patent>> getPatentsById(@PathVariable("id") Long id) {
        // get all the patents of a user by id
        Optional<User> user = this.userServiceImp.getById(id);
        Set<Patent> listPatents = this.patentServiceImp.getByIdUser(user.get().getId());
        return new ResponseEntity<Set<Patent>>(listPatents, HttpStatus.OK);
    }

    @GetMapping(path = "/getCurrentAccount/{id}")
    public ResponseEntity<CurrentAccount> getCurrentAccount(@PathVariable("id") Long id) {
        // get the current account of a user by username(phone)
        Optional<User> user = this.userServiceImp.getById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CurrentAccount>(user.get().getCurrentAccount(), HttpStatus.OK);
    }

    @PostMapping("/chargeBalance")
    public ResponseEntity<?> chargeBalance(@Valid @RequestBody CurrentAccountDTO ca, BindingResult result) {
        // add balance to the user's account in "ca.username"
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        this.userServiceImp.chargeBalance(ca);
        return new ResponseEntity<Message>(new Message("Se ha actualizado el saldo"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteById(@PathVariable("id") Long id) {
        // delete a user by id
        boolean ok = this.userServiceImp.delete(id);
        if (!ok) {
            System.out.println("No es posible eliminar, no se encuentra el usuario con id " + id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Se elimino el usuario con id " + id);
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("Actualizando el usuario " + user.getId());
        Optional<User> currentUser = userServiceImp.getById(user.getId());

        if (currentUser.isEmpty()) {
            System.out.println("User with id " + user.getId() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        } else {
            this.userServiceImp.update(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }

    }
}
