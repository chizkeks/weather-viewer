
package ru.petprojects.chizkeks.weather_viewer.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.petprojects.chizkeks.weather_viewer.model.Session;
import ru.petprojects.chizkeks.weather_viewer.service.SessionService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = {"/home"})
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("sessionId")) {
                    sessionId = cookie.getValue();
                }
            }
        }

        System.out.println(request.getRequestURI());
        if(!sessionId.isEmpty()) {
            Session foundSession = sessionService.getById(sessionId);
            if (foundSession != null) {
                if(foundSession.getExpiresAt().isAfter(LocalDateTime.now())) {
                    System.out.println("Сессия не протухла");
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                };
            }
            httpResponse.sendRedirect("/authentication");
        }
        else {
            httpResponse.sendRedirect("/authentication");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
