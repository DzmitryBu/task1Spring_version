package by.it_academy.jd2.MJD29522.dao.api;

import by.it_academy.jd2.MJD29522.dto.SingerDTO;
import by.it_academy.jd2.MJD29522.entity.SingerEntity;

import java.util.List;

public interface ISingerDao {

    List<SingerEntity> get();

    boolean add(SingerDTO newSinger);

    void update(SingerEntity singerEntity);

    boolean delete(long id, long version);

    SingerEntity exist(long id);

    String getName(long id);

    SingerEntity getCard(long id);
}
