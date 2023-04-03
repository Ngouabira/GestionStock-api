package com.gildas.gestionstock.mapper;

import com.gildas.gestionstock.dto.UserDTO;
import com.gildas.gestionstock.entity.User;
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
