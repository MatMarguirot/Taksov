//package com.mat.taksov.domain.profile.entity;
//
//import com.mat.taksov.user.model.Task;
//import com.mat.taksov.user.model.User;
//import com.mat.taksov.authentication.model.enmsd.Role;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//import org.hibernate.annotations.Type;
//import org.hibernate.validator.constraints.Length;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
////import javax.persistence.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "profile")
//public class Profile {
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
//
//    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user")
//    private User user;
//
//    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    private Set<Task> tasks = new HashSet<>();
//}
//
