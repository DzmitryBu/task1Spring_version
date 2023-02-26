package by.it_academy.jd2.MJD29522.service;

import by.it_academy.jd2.MJD29522.dao.api.ISingerDao;
import by.it_academy.jd2.MJD29522.dto.SingerDTO;
import by.it_academy.jd2.MJD29522.dto.SingerDTOWithID;
import by.it_academy.jd2.MJD29522.entity.SingerEntity;
import by.it_academy.jd2.MJD29522.service.api.ISingerService;
import java.util.ArrayList;
import java.util.List;

public class SingerService implements ISingerService {

    private final ISingerDao dao;

    public SingerService(ISingerDao singerDao) {
        this.dao = singerDao;
    }

    @Override
    public List <SingerDTOWithID> get() {
        List <SingerDTOWithID> singerDTOWithIDList = new ArrayList<>();
        List <SingerEntity> singerEntityList = dao.get();
        for (SingerEntity singerEntity : singerEntityList) {
            singerDTOWithIDList.add(new SingerDTOWithID(singerEntity));
        }
        return singerDTOWithIDList;
    }

    @Override
    public SingerDTOWithID getCard(long id){
        if(dao.exist(id) == null){
            throw new IllegalArgumentException("Артиста с id " + id + " не нейдено!");
        }
        return new SingerDTOWithID(dao.getCard(id));
    }

    @Override
    public boolean add(SingerDTO newSinger) {
        validate(newSinger);
        return dao.add(newSinger);
    }

    @Override
    public void update(long id, long version,  SingerDTO singer) {
        validate(singer);
        SingerEntity singerFromDB = dao.exist(id);
        if (singerFromDB != null) {
            if(singerFromDB.getVersion().equals(version)){
                SingerEntity singerEntity = new SingerEntity(id, version, singer.getName());
                dao.update(singerEntity);
            }else throw new IllegalArgumentException("У вас не актуальная версия для обновления артиста!");
        } else throw new IllegalArgumentException("Артиста с id " + id + " для обновления не нейдено!");
    }

    @Override
    public boolean delete(long id,  long version) {
        SingerEntity singerFromDB = dao.exist(id);
        if(singerFromDB == null){
            throw new IllegalArgumentException("Артиста с id " + id + " для удаления не найдено!");
        }
        if(!singerFromDB.getVersion().equals(version)){
            throw new IllegalArgumentException("У вас не актуальная версия для удаления артиста!");
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
    public boolean validate(SingerDTO singer) {
        List <SingerEntity> singerEntityList = dao.get();
        for (SingerEntity singerEntity : singerEntityList) {
            if(singer.getName().equals(singerEntity.getName())){
                throw  new IllegalArgumentException("Исполнитель с именем " + singer.getName() + " для добавления уже существует!");
            }
        }
        if(singer.getName() == null || singer.getName().isBlank()){
            throw new IllegalArgumentException("Имя исполнителя не может быть пустым");
        }
        if(singer.getName().length() > 255){
            throw new IllegalArgumentException("Длина имени исполнителя не должна превышать 255 символов");
        }
        return true;
    }
}
