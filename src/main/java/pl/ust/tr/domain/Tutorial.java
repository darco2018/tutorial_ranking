package pl.ust.tr.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Tutorial implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String title;

    @Column
    private String link;

    @Column(length = 2000)
    private String description;

    @Column
    private int price;

    @Column
    private String duration;

    @Column
    private String keywords;

    @ManyToOne
    private Technology technology;

    @Column
    private Type type;

    @Column
    private Level level;


    public Tutorial(String title, String link, String description, int price, String duration, String keywords, Technology technology, Type type, Level level) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.keywords = keywords;
        this.technology = technology;
        this.type = type;
        this.level = level;
    }

    protected Tutorial(){

    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getDuration() {
        return duration;
    }

    public String getKeywords() {
        return keywords;
    }

    public Technology getTechnology() {
        return technology;
    }

    public Type getType() {
        return type;
    }

    public Level getLevel() {
        return level;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public void setType(Type type) {
        this.type = type;
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
                Objects.equals(technology, tutorial.technology) &&
                type == tutorial.type &&
                level == tutorial.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, description, price, duration, keywords, technology, type, level);
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
                ", technology=" + technology +
                ", type=" + type +
                ", level=" + level +
                '}';
    }
}
