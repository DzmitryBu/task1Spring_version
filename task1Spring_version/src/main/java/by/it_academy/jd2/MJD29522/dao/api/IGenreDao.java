package by.it_academy.jd2.MJD29522.dao.api;

import by.it_academy.jd2.MJD29522.dto.GenreDTO;
import by.it_academy.jd2.MJD29522.entity.GenreEntity;

import java.util.List;

public interface IGenreDao{

    List<GenreEntity> get();

    boolean add(GenreDTO newGenre);

    void update(GenreEntity genreEntity);

    boolean delete(long id, long version);

    GenreEntity exist(long id);

    String getName(long id);

    GenreEntity getCard(long id);
}
