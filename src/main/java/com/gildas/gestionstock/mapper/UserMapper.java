package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserMapper {

    public static UserDTO toDTO(User user) {
        return null;
    }

    public static User toEntity(UserDTO userDTO) {
        return null;
    }
}
