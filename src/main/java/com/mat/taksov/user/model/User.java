package com.mat.taksov.user.model;

import com.fasterxml.jackson.annotation.*;
import com.mat.taksov.authentication.model.enums.Role;
import com.mat.taksov.workout.model.ProgramDay;
import com.mat.taksov.workout.model.WorkoutSession;
import com.mat.taksov.workout.model.WorkoutSplit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.id.IdentityGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import javax.persistence.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", unique = true)
    @NotBlank
    private String username;

    @Column(name = "email", unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password")
//    @Length(min = 6, max = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Set<Task> tasks = new HashSet<>();
////
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Set<WorkoutSession> workoutSessions = new HashSet<>();
//
//    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private ProgramDay programDay;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @Override
//    public String toString(){
//        return "User{" +
//                "id=" + id +
//                ", email='" + email.replaceFirst("@.*", "@***") +
//                ", passwordHash='" + password.substring(0, 10) +
//                ", role=" + role +
//                '}';
//    }

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id")
//    private Role role;
}
