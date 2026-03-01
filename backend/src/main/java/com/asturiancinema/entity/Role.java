package com.asturiancinema.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 20)
    private RoleName name;

    public Role() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public RoleName getName() { return name; }
    public void setName(RoleName name) { this.name = name; }

    public enum RoleName {
        USER,
        ADMIN,
        MODERATOR
    }

    public static class RoleBuilder {
        private Integer id;
        private RoleName name;

        public RoleBuilder id(Integer id) { this.id = id; return this; }
        public RoleBuilder name(RoleName name) { this.name = name; return this; }

        public Role build() {
            Role role = new Role();
            role.id = this.id;
            role.name = this.name;
            return role;
        }
    }

    public static RoleBuilder builder() {
        return new RoleBuilder();
    }
}
