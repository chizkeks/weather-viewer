package ru.petprojects.chizkeks.weather_viewer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petprojects.chizkeks.weather_viewer.model.Session;
import ru.petprojects.chizkeks.weather_viewer.model.User;
import ru.petprojects.chizkeks.weather_viewer.model.dto.UserDto;
import ru.petprojects.chizkeks.weather_viewer.service.SessionService;
import ru.petprojects.chizkeks.weather_viewer.service.UserService;
import ru.petprojects.chizkeks.weather_viewer.utils.HashUtils;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, HttpServletResponse response, Model model) {
        model.addAttribute("activePanel", "login");

        User userFromDB = userService.findUserByLogin(user.getLogin());

        if(userFromDB != null && userFromDB.getPassword().equals(HashUtils.encodePBKDF2(user.getPassword(), user.getLogin().getBytes()))) {
            Session userSession = sessionService.create(userFromDB);
            response.addCookie(new Cookie("sessionId", userSession.getId()));
            return "home";
        }
        bindingResult.rejectValue("login", "authError", "Неверный логин или пароль");

        //Clear "new_user" in case it already exists
        model.addAttribute("new_user", new UserDto());
        return "authentication";
    }

    @GetMapping("/authentication")
    public String autheticateUser(Model model) {
        //Every time create new "user" and "new_user" (to clear all errors and data store in it)
        model.addAttribute("user", new UserDto());
        model.addAttribute("new_user", new UserDto());

        model.addAttribute("activePanel", "login");
        return "authentication";
    }

    @GetMapping("/registration")
    public String registration_get(@Valid @ModelAttribute("user") UserDto user,
                                   Model model) {
        //Every time create new "new_user" (to clear all errors and data store in it)
        model.addAttribute("new_user", new UserDto());

        model.addAttribute("activePanel", "registration");
        return "authentication";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("new_user") UserDto user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpServletResponse response,
                               Model model) {
        model.addAttribute("activePanel", "registration");

        //Every time create new "user" (to clear all errors and data store in it)
        model.addAttribute("user", new UserDto());

        //Return to the authentication page if there are errors
        if(!bindingResult.hasErrors()) {
            //else try to create a new user and a new session for this user in db
            // and redirect him to the home page
            user.setPassword(HashUtils.encodePBKDF2(user.getPassword(), user.getLogin().getBytes()));

            User createdUser = userService.createUser(user);
            Session userSession = sessionService.create(createdUser);
            response.addCookie(new Cookie("sessionId", userSession.getId()));
            //redirectAttributes.addFlashAttribute("user", user);
            model.addAttribute("activePanel", "login");
            return "authentication";
        } else {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                System.out.println(error.getField() + "  " + error.getDefaultMessage());
            }
        }

        model.addAttribute("new_user", user);

        return "authentication";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
