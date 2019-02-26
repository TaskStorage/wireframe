package com.taskstorage.wireframe.controller;

import com.taskstorage.wireframe.domain.User;
import com.taskstorage.wireframe.domain.dto.CaptchaResponseDto;
import com.taskstorage.wireframe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    //Для запроса к серверу капчи
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }


    @PostMapping("/registration")

    public String addUser(@RequestParam("password2") String passwordConfirm,
                          //Принимаем ответ пользователя на капчу
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user, BindingResult bindingResult, Model model) {
        //Формируем запрос из строки, секретного ключа и ответа пользователя
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        //Отправляем пост-запрос: сформированный url, пустой лист, т.к. метод требует, и дто
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        //проверяем ответ
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        boolean isPasswordDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);

        if(isPasswordDifferent){
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || isPasswordDifferent || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
