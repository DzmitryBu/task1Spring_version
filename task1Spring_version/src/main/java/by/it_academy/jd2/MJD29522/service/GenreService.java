package by.it_academy.jd2.MJD29522.service;

import by.it_academy.jd2.MJD29522.dao.api.IGenreDao;
import by.it_academy.jd2.MJD29522.dto.GenreDTO;
import by.it_academy.jd2.MJD29522.dto.GenreDTOWithID;
import by.it_academy.jd2.MJD29522.entity.GenreEntity;
import by.it_academy.jd2.MJD29522.service.api.IGenreService;
import java.util.ArrayList;
import java.util.List;

public class GenreService implements IGenreService {

    private final IGenreDao dao;

    public GenreService(IGenreDao dao) {
        this.dao = dao;
    }

    @Override
    public List<GenreDTOWithID> get(){
        List <GenreEntity> genreEntityList = dao.get();
        List <GenreDTOWithID> genreDTWithId= new ArrayList<>();
        for (GenreEntity genreEntity : genreEntityList) {
            genreDTWithId.add(new GenreDTOWithID(genreEntity));
        }
        return genreDTWithId;
    }

    @Override
    public GenreDTOWithID getCard(long id){
        if(dao.exist(id) == null){
            throw new IllegalArgumentException("Жанра с id " + id + " не нейдено!");
        }
        return new GenreDTOWithID(this.dao.getCard(id));
    }

    @Override
    public boolean add(GenreDTO newGenre) {
        validation(newGenre);
        return dao.add(newGenre);
    }

    @Override
    public void update(long id, long version, GenreDTO genre) {
        validation(genre);
        GenreEntity genreEntityFromDB = dao.exist(id);
        if (genreEntityFromDB != null) {
            if(genreEntityFromDB.getVersion().equals(version)){
                GenreEntity genreEntity = new GenreEntity(id, version, genre.getName());
                dao.update(genreEntity);
            } else throw new IllegalArgumentException("У вас не актуальная версия для обновления жанра");
        }else throw new IllegalArgumentException("Жанра с id " + id + " для обновления не нейдено!");

    }

    @Override
    public boolean delete(long id, long version) {
        GenreEntity genreEntityFromDB = dao.exist(id);
        if(genreEntityFromDB == null){
            throw new IllegalArgumentException("Жанра с id " + id + " для удаления не найдено!");
        }
        if(!genreEntityFromDB.getVersion().equals(version)){
            throw new IllegalArgumentException("У вас не актуальная версия для удаления жанра");
        }
        return dao.delete(id, version);
    }

    @Override
    public boolean exist(long id) {
        return this.dao.exist(id) != null;
    }

    @Override
    public String getName(long id) {
        return this.dao.getName(id);
    }

    @Override
    public boolean validation(GenreDTO genre) {
        List <GenreEntity> genreEntityList = dao.get();
        for (GenreEntity genreEntity : genreEntityList) {
            if(genre.getName().equals(genreEntity.getName())){
                throw  new IllegalArgumentException("Жанр с именем " + genre.getName() + " для добавления уже существует!");
            }
        }
        if(genre.getName().isBlank() || genre.getName() == null){
            throw new IllegalArgumentException("Имя жанра не может быть пустым");
        }
        if(genre.getName().length() > 255){
            throw new IllegalArgumentException("Длина названия жанра не должна превышать 255 символов");
        }
        return true;
    }
}
