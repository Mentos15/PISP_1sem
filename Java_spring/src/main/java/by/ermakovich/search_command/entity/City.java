package by.ermakovich.search_command.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="City")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="city", nullable = false, length = 50)
    private String city;

    public City(){

    }

    public City(String city) {
        this.city = city;
    }

    public City(long id ,String city) {
        this.id = id;
        this.city = city;
    }


    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
