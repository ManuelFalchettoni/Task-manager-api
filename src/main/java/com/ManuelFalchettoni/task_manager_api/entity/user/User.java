package com.ManuelFalchettoni.task_manager_api.entity.user;

import com.ManuelFalchettoni.task_manager_api.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    public User(){};
    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public UserRole getRole() {return role;}

    public void setPassword(String password) {this.password = password;}
    public void setRole(UserRole role) {this.role = role;}
}
