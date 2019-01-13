package com.taskstorage.wireframe.controller;

import com.taskstorage.wireframe.domain.Role;
import com.taskstorage.wireframe.domain.User;
import com.taskstorage.wireframe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }
    //    Достаём пользователей
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    // Сохраняем изменения
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam(required = false) String active,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        user.setUsername(username);
//        Для строкового поля
//        user.setActive(Boolean.parseBoolean(active));
        if (active !=null && active.equals("on")) {
            user.setActive(true);
        }
        else {
            user.setActive(false);
        }
        //Получаем список ролей и переводим из Енама в Стринги
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);

        return "redirect:/user";
    }
}