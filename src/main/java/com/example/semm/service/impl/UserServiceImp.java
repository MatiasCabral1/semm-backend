package com.example.semm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.example.semm.models.City;
import com.example.semm.models.CurrentAccount;
import com.example.semm.models.Parking;
import com.example.semm.models.User;
import com.example.semm.repositories.UserRepository;
import com.example.semm.models.History;
import com.example.semm.security.dto.CurrentAccountDTO;
import com.example.semm.security.dto.TimePriceDTO;
import com.example.semm.services.CityService;
import com.example.semm.services.CurrentAccountService;
import com.example.semm.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrentAccountService ccServiceImp;

    @Autowired
    ParkingServiceImp estService;

    @Autowired
    HistoryServiceImpl historialService;

    @Autowired
    CityService ciudadService;

    @Override
    public ArrayList<User> getAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User usuario) {
        userRepository.save(usuario);
    }

    public void debitBalance(Long id) {
        // actualizo la cuenta del usuario, y guardo la operacion en el historial.
        System.out.println("EJECUTANDO DEBITO");
        Optional<User> per = this.getById(id);
        Optional<Parking> est = this.estService.getById(per.get().getEstacionamiento().getId());
        ArrayList<City> ciudad = this.ciudadService.getAll();
        TimePriceDTO result = per.get().getCurrentAccount().debit(per, est, ciudad.get(0));
        this.update(per.get());
        this.ccServiceImp.update(per.get().getCurrentAccount());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sdf.format(new Date());
        System.out.println("date para historial formateado: " + fecha);
        historialService.saveHistory(new History(fecha, "Consumo", per.get().getCurrentAccount().getBalance(),
                result.getPrice(), per.get().getCurrentAccount()));
    }

    public void chargeBalance(CurrentAccountDTO cc) {
        Optional<User> per = this.existByUsername(cc.getPhone());
        Optional<CurrentAccount> cuentaCorriente = this.ccServiceImp.getById(cc.getId());
        cuentaCorriente.get().loadBalance(cc.getBalance());
        this.ccServiceImp.update(cuentaCorriente.get());
        per.get().getCurrentAccount().setBalance(cuentaCorriente.get().getBalance());
        this.update(per.get());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sdf.format(new Date());
        System.out.println("date para historial formateado: " + fecha);
        historialService.saveHistory(new History(fecha, "Carga", per.get().getCurrentAccount().getBalance(),
                cc.getBalance(), per.get().getCurrentAccount()));
    }

    public Optional<User> existByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

}
