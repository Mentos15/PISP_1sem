package by.ermakovich.search_command.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Users")
public class Users {


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @NotBlank
    @Size(min=5, max=50)
    @Column(name="username", unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank
    @Size(min=3, max=100)
    @Column(name = "password", length = 100, nullable = false)
    private  String password;


    @ManyToOne
    @JoinColumn(name = "idrole")
    private Roles roles;

    @Column(name = "activationcode", length = 500)
    private String activationCode;




    public Users() {}

    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    public long getId(){return id;}

    public void setId(long id){this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
