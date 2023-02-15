package by.it_academy.jd2.MJD29522.dto;

import by.it_academy.jd2.MJD29522.entity.SingerEntity;
import java.util.Objects;

public class SingerDTOWithID {
    private String name;
    private long id;

    private long version;

    public SingerDTOWithID(SingerEntity singer) {
        this.name = singer.getName();
        this.id = singer.getId();
        this.version = singer.getVersion();
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

    public void setSinger(String singer) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingerDTOWithID that = (SingerDTOWithID) o;
        return id == that.id && version == that.version && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, version);
    }

    @Override
    public String toString() {
        return "SingerDTOWithID{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", version=" + version +
                '}';
    }
}

