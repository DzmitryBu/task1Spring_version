package by.it_academy.jd2.MJD29522.dto;

public class StatisticDTOArtist {
    private final long  id;
    private final String name;
    private long count;

    public StatisticDTOArtist(long id, String name) {
        this.id = id;
        this.name = name;
        count = 0;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

    public void addCount() {
        this.count++;
    }

    @Override
    public String toString() {
        return "StatisticDTOArtist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
