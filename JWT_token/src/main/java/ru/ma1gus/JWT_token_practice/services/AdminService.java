package ru.ma1gus.JWT_token_practice.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doSmth() {
        System.out.println("Всем стоять, всем лежать.");
    }
}
