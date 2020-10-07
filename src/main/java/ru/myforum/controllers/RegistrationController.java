package ru.myforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.myforum.entities.Role;
import ru.myforum.entities.User;
import ru.myforum.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@Controller

public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registrationPage(){
        return "registration";
    }
    //Пост обработка регистрации
    @PostMapping("/registration")
    public String postRegistration(User user, Map<String, Object> model){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            model.put("message", "Пользователь с таким логином уже зарегистрирован");
            return "/registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

}
