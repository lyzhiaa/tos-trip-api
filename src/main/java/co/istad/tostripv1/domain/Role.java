package co.istad.tostripv1.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String uuid;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

//    @Override
//    public String getAuthority() {
//        //without ROLE_
//        return " " + name;
//
//        //with ROLE_
////        return "ROLE_" + name; //ROLE_ADMIN
//    }
}