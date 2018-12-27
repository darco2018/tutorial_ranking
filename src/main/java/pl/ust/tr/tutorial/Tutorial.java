package pl.ust.tr.tutorial;

import pl.ust.tr.domain.Level;
import pl.ust.tr.domain.Medium;
import pl.ust.tr.skill.Skill;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Tutorial")
public class Tutorial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint(2) unsigned")
    private Integer id;

    @Column(length=100, nullable=false)
    private String title;

    @Column(length=255, nullable=false)
    private String link;

    @Column(length = 2000)
    private String description;

    @Column(columnDefinition = "decimal(4, 0) unsigned", precision=4, scale=0, nullable=false)
    private Integer price;

    @Column(length=50)
    private String duration;

    @Column(length=50)
    private String keywords;

    @ManyToOne
    @JoinColumn(name = "skill_code")
    private Skill skill;

    @Column
    private Medium medium;

    @Column(length=20, nullable=false)
    @Enumerated(EnumType.STRING)
    private Level level;

    public Tutorial(String title, String link, String description, int price, String duration, String keywords, Skill skill, Medium medium, Level level) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.keywords = keywords;
        this.skill = skill;
        this.medium = medium;
        this.level = level;
    }

    protected Tutorial() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutorial tutorial = (Tutorial) o;
        return price == tutorial.price &&
                Objects.equals(id, tutorial.id) &&
                Objects.equals(title, tutorial.title) &&
                Objects.equals(link, tutorial.link) &&
                Objects.equals(description, tutorial.description) &&
                Objects.equals(duration, tutorial.duration) &&
                Objects.equals(keywords, tutorial.keywords) &&
                Objects.equals(skill, tutorial.skill) &&
                medium == tutorial.medium &&
                level == tutorial.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, description, price, duration, keywords, skill, medium, level);
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration='" + duration + '\'' +
                ", keywords='" + keywords + '\'' +
                ", skill=" + skill +
                ", medium=" + medium +
                ", level=" + level +
                '}';
    }
}
