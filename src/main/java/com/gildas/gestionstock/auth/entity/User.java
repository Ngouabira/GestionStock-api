package com.gildas.gestionstock.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String nom;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String prenom;

    @NotNull
    private String role;

    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String username;

    @NotNull
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String telephone;

    @NotNull
    @Size(min = 5, max = 11)
    private String genre;

    private String date_naissance;

    @NotNull
    @Size(min = 4)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Lob
    private String photo = "default.png";

    private String resetPasswordToken;

    @NotNull
    @Column(nullable = false)
    private String status ="active";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Token> token = new HashSet<>();

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime date_creation;

    public User(String username, @Size(min = 5) String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public User(@Size(min = 5, max = 40) String nom, @Size(min = 5, max = 40) String prenom, String username,
                @Size(min = 3, max = 40) String telephone, @Size(min = 5, max = 40) String genre,
                @Size(min = 5) String password) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.telephone = telephone;
        this.genre = genre;
        this.password = password;
    }

    public User(String nom, String prenom, String role, String username, String telephone, String genre, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.username = username;
        this.telephone = telephone;
        this.genre = genre;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
