package by.it_academy.jd2.MJD29522.dao.api;

import by.it_academy.jd2.MJD29522.dao.orm.entity.SingerEntity;

import java.util.List;

public interface ISingerDao {

    List<SingerEntity> get();

    boolean add(String newSinger);

    void update(SingerEntity singerEntity);

    boolean delete(long id);

    boolean exist(long id);

    String getName(long id);
}
