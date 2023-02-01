package com.nhaccuaquang.musique.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "account_permissions",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "permission_id"))
    private Set<Permission> permissions;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    @PrimaryKeyJoinColumn
    private AccountDetail accountDetail;

    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    private Integer status = 1;

    @Column(name = "created_at", updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;

    public void addRoleToAccount(Set<Role> roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        roles.forEach(role -> {
            this.roles.add(role);
        });
    }
    public void removeRoleFromAccount(Role role){
        this.roles.remove(role);
    }
    public void addPermissionToAccount(Set<Permission> permissions) {
        if (this.permissions == null) {
            this.permissions = new HashSet<>();
        }
        permissions.forEach(permission -> {
            this.permissions.add(permission);
        });
    }
}
