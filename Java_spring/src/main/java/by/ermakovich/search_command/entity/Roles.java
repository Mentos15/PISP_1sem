package by.ermakovich.search_command.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name="name", nullable = false, length = 20)
    private String name;


    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getUsername() {return name;}
    public void setUsername(String name) {
        this.name = name;
    }

}
