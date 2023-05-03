package com.gildas.gestionstock.auth.entity;

import com.gildas.gestionstock.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table
public class Role extends AbstractEntity {

    private String role;
}
