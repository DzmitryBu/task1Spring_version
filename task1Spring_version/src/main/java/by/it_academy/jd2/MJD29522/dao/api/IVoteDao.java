package by.it_academy.jd2.MJD29522.dao.api;

import by.it_academy.jd2.MJD29522.entity.VoteEntity;
import by.it_academy.jd2.MJD29522.dto.VoteDTO;
import java.util.List;

public interface IVoteDao {

    List<VoteEntity> getVoteList();

    void save(VoteDTO vote);
}
