package com.onlinestore.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "user_id", columnDefinition = "INT", nullable = false, updatable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserSummary.class)
    private int id;
    @Column(name = "uuid", columnDefinition = "UUID")
    @JsonView(Views.UserSummary.class)
    private UUID uuid;
    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    @JsonView(Views.UserSummary.class)
    private String fullName;
    @Column(name = "phone_number", columnDefinition = "VARCHAR(255)")
    @JsonView(Views.UserSummary.class)
    private String phoneNumber;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    @JsonView(Views.UserSummary.class)
    private String email;
    @OneToMany (mappedBy="personUser", fetch=FetchType.LAZY)
    @JsonView(Views.UserDetails.class)
    private List<Order> orders;

    @Override
    public String toString() {
        return "Пользователь: " + fullName + ", идентификационный номер: " + uuid;
    }
}
