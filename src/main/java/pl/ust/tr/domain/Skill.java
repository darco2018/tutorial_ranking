package pl.ust.tr.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Skill implements Serializable {

    @Id
    private String code;

    @Column
    private String name;

    @OneToMany(mappedBy="skill", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Tutorial> tutorials = new ArrayList<>();

    public Skill(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Skill() {
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill that = (Skill) o;
        return code.equals(that.code) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
