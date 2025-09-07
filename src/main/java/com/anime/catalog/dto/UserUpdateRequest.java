package com.anime.catalog.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserUpdateRequest {
    private String username;
    private String email;
    private Set<String> roles; // optional, admin can change roles
}
