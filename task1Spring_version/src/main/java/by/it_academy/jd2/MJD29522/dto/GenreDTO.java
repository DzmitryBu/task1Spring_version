package by.it_academy.jd2.MJD29522.dto;

import java.util.Objects;

public class GenreDTO {
    private String name;

    private long version;

    public GenreDTO() {
    }
    public GenreDTO(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDTO genreDTO = (GenreDTO) o;
        return version == genreDTO.version && Objects.equals(name, genreDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}

