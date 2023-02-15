package by.it_academy.jd2.MJD29522.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app.votes")
public class VoteEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "about")
    private String about;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinTable(
            name="app.vote_singer",
            joinColumns=
            @JoinColumn(name="vote_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="singer_ID", referencedColumnName="ID")
    )
    private SingerEntity singer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="app.vote_genre",
            joinColumns=
            @JoinColumn(name="vote_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="genre_ID", referencedColumnName="ID")
    )
    private List<GenreEntity> genres;

    public VoteEntity() {
    }

    public VoteEntity(SingerEntity singer, List<GenreEntity> genres, String about, String email, Date date) {
        this.about = about;
        this.email = email;
        this.date = date;
        this.singer = singer;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public String getAbout() {
        return about;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDate() {
        return this.date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public SingerEntity getSinger() {
        return singer;
    }

    public List<GenreEntity> getGenre() {
        return genres;
    }

    @Override
    public String toString() {
        return "VoteEntity{" +
                "id=" + id +
                ", about='" + about + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", singer=" + singer +
                ", genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteEntity that = (VoteEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(about, that.about) && Objects.equals(email, that.email) && Objects.equals(date, that.date) && Objects.equals(singer, that.singer) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, about, email, date, singer, genres);
    }
}
