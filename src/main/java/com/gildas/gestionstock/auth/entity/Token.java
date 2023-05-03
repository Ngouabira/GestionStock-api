package  com.gildas.gestionstock.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true)
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp timeStamp;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private boolean isExpired;

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public boolean isExpired() {
        return getExpireAt().isBefore(LocalDateTime.now());
    }

}
