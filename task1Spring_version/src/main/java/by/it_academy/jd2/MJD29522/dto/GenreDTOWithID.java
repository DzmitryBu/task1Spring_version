package by.it_academy.jd2.MJD29522.dto;

import by.it_academy.jd2.MJD29522.entity.GenreEntity;
import java.util.Objects;

public class GenreDTOWithID {
    private String name;
    private long id;

    private long version;

    public GenreDTOWithID(GenreEntity genre) {
        this.name = genre.getName();
        this.id = genre.getId();
        this.version = genre.getVersion();
    }
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setGenre(String genre) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDTOWithID that = (GenreDTOWithID) o;
        return id == that.id && version == that.version && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, version);
    }

    @Override
    public String toString() {
        return "GenreDTOWithID{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", version=" + version +
                '}';
    }
}

