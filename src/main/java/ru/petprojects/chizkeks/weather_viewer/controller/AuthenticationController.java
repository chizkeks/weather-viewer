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
    public String login(@ModelAttribute UserDto user, BindingResult bindingResult, HttpServletResponse response, Model model) {
        model.addAttribute("activePanel", "login");

        User userFromDB = userService.findUserByLogin(user.getLogin());

        if(userFromDB != null && userFromDB.getPassword().equals(HashUtils.encodePBKDF2(user.getPassword(), user.getLogin().getBytes()))) {
            Session userSession = sessionService.create(userFromDB);
            response.addCookie(new Cookie("sessionId", userSession.getId()));
            return "redirect:/home";
        }
        bindingResult.rejectValue("login", "authError", "Неверный логин или пароль");
        return "redirect:/authentication";
    }

    @GetMapping("/authentication")
    public String autheticateUser(@ModelAttribute("user") UserDto userDto,
                                  Model model) {
        model.addAttribute("user", userDto);
        model.addAttribute("activePanel", "login");
        return "authentication";
    }

    @GetMapping("/registration")
    public String registration_get(@Valid @ModelAttribute UserDto user,
                                   Model model) {
        model.addAttribute("user", user);
        model.addAttribute("activePanel", "registration");
        return "authentication";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute UserDto user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpServletResponse response,
                               Model model) {
        model.addAttribute("activePanel", "registration");

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
            return "redirect:/authentication";
        } else {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                System.out.println(error.getField() + "  " + error.getDefaultMessage());;
            }
        }
        model.addAttribute("user", user);
        return "authentication";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
