package by.it_academy.jd2.MJD29522.dto;

import java.util.List;

public class StatisticDTO {
    private List<StatisticDTOArtist> artists;
    private List<StatisticDTOGenre> genres;
    private List <StatisticDTOMessage> messages;

    public StatisticDTO(List<StatisticDTOArtist> statisticDTOArtist,
                        List<StatisticDTOGenre>  statisticDTOGenre,
                        List <StatisticDTOMessage> statisticDTOMessage) {
        this.artists = statisticDTOArtist;
        this.genres = statisticDTOGenre;
        this.messages = statisticDTOMessage;
    }

    public List<StatisticDTOArtist> getArtist() {
        return artists;
    }

    public List<StatisticDTOGenre> getGenre() {
        return genres;
    }

    public List <StatisticDTOMessage> getMessage() {
        return messages;
    }

    @Override
    public String toString() {
        return "StatisticDTO{" +
                "artists=" + artists +
                ", genres=" + genres +
                ", messages=" + messages +
                '}';
    }
}
