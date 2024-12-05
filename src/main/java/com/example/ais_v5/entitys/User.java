package com.example.ais_v5.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 48)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 48)
    private String name;

    @Column(nullable = false, length = 48)
    private String lastname;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Set<Subject> taughtSubjects = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    private Set<Group> studentGroups = new HashSet<>();

    // Custom constructor
    public User(String email, String password, String name, String lastname, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.roles = roles;
    }

    // Add a single role
    public void setRole(Role role) {
        this.roles.clear(); // Ensures a single role at a time (if required)
        this.roles.add(role);
    }

    // Convenience methods
    public String getLogin() {
        return name;
    }

    public String getDefaultPassword() {
        return lastname;
    }
}
