package ru.petprojects.chizkeks.weather_viewer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.petprojects.chizkeks.weather_viewer.model.Session;
import ru.petprojects.chizkeks.weather_viewer.model.dto.UserDto;
import ru.petprojects.chizkeks.weather_viewer.service.SessionService;
import ru.petprojects.chizkeks.weather_viewer.service.UserService;
import ru.petprojects.chizkeks.weather_viewer.utils.HashUtils;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto user, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        UserDto userFromDB = userService.findUserByLogin(user.getLogin());
        if(userFromDB.getPassword().equals(HashUtils.encodePBKDF2(user.getPassword()))) {
            Session userSession = sessionService.create(userFromDB);
            response.addCookie(new Cookie("sessionId", userSession.getId()));
            return "redirect:/home";
        }

        return "redirect:/unauthorized";
    }

    @GetMapping("/authentication")
    public String autheticateUser(Model model, @ModelAttribute("user") UserDto userDto) {
        model.addAttribute("user", userDto);
        return "authentication";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute UserDto user, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        user.setPassword(HashUtils.encodePBKDF2(user.getPassword()));

        Session userSession = sessionService.create(user);
        response.addCookie(new Cookie("sessionId", userSession.getId()));
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
