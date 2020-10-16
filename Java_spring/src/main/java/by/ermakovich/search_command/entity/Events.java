package by.ermakovich.search_command.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 40, nullable = false)
    private String title;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "requirement", length = 500, nullable = false)
    private String requirement;

    @OneToMany
    @JoinColumn(name = "aplication_id", referencedColumnName = "id", nullable = false)
    private List<Application> aplication;

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

}
