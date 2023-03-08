package shop.mtcoding.bank.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // 스프링이
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tb")
@Entity
public class User { // extends 시간설정 (상속)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false,length = 20)
    private String username;
    @Column(unique = false,length = 60)
    private String password;
    @Column(unique = false,length = 20)
    private String email;
    @Column(unique = false,length = 20)
    private String fullname;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; // ADMIN,CUSTOMER
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
