package by.it_academy.jd2.MJD29522.dto;

import java.util.Objects;

public class SingerDTO {
    private String name;

    private long version;

    public SingerDTO() {
    }
    public SingerDTO(String name) {
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
        SingerDTO singerDTO = (SingerDTO) o;
        return version == singerDTO.version && Objects.equals(name, singerDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return "SingerDTO{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
