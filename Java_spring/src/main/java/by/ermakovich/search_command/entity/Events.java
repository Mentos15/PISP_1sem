package by.ermakovich.search_command.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank
    @Size(min=5, max=20)
    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @NotBlank
    @Size(min=5, max=500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @NotBlank
    @Size(min=5, max=500)
    @Column(name = "requirement", length = 500, nullable = false)
    private String requirement;

    @JsonIgnore
    @OneToMany(mappedBy = "events")
    private List<Application> aplication;

    @ManyToMany
    @JoinTable(
            name = "city_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id"))
    Set<City> citys;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }
    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }


    public Set<City> getCitys() {
        return citys;
    }

    public void setCitys(Set<City> citys) {
        this.citys = citys;
    }

    public List<Application> getAplication() {
        return aplication;
    }

    public void setAplication(List<Application> aplication) {
        this.aplication = aplication;
    }


}
