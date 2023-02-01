package com.nhaccuaquang.musique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @Column(name = "permission_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Permission name is required")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Collection<Account> accounts;
}
