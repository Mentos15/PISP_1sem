package by.ermakovich.search_command.entity;

import javax.persistence.*;

@Entity
@Table(name="application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false, length = 20)
    private String name;

    @Column(name="lastname", nullable = false, length = 20)
    private String lastName;

    @Column(name="description", nullable = false, length = 500)
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "aplication_id", nullable = false)
    private Events events;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }

    public Events getEvents() {
        return events;
    }
    public void setEvents(Events events) {
        this.events = events;
    }
}
