package pl.ust.tr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Technology implements Serializable {

    @Id
    private String code;

    @Column
    private String name;

    public Technology(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected Technology() {
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
        Technology that = (Technology) o;
        return code.equals(that.code) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Technology{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
