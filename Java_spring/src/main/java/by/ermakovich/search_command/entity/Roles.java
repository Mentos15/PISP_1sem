package by.ermakovich.search_command.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name="name", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "roles")
    private Set<Users> users;


    public long getId(){return id;}
    public String getName() {return name;}

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}
