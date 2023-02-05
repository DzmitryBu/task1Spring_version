package by.it_academy.jd2.MJD29522.dao.orm;

import by.it_academy.jd2.MJD29522.dao.api.IVoteDao;
import by.it_academy.jd2.MJD29522.dao.orm.api.IManager;
import by.it_academy.jd2.MJD29522.dao.orm.entity.GenreEntity;
import by.it_academy.jd2.MJD29522.dao.orm.entity.SingerEntity;
import by.it_academy.jd2.MJD29522.dao.orm.entity.VoteEntity;
import by.it_academy.jd2.MJD29522.dto.Vote;
import by.it_academy.jd2.MJD29522.dto.VoteDTO;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class VoteDaoHibernate implements IVoteDao {

    private final IManager manager;

    public VoteDaoHibernate(IManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public List<VoteEntity> getVoteList() {
        EntityManager entityManager = null;
        List<VoteEntity> voteEntityList;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
           voteEntityList = entityManager.createQuery
                    ("FROM VoteEntity ORDER BY id", VoteEntity.class).getResultList();

            entityManager.getTransaction().commit();
            return voteEntityList;
        } catch (Exception e) {
            if(entityManager != null){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    @Override
    public boolean save(VoteDTO vote) {
        List <GenreEntity> genres = voteToGenreEntityList(vote);
        SingerEntity singer = voteToSingerEntity(vote);
        EntityManager entityManager = null;
        VoteEntity voteEntity = new VoteEntity(vote.getMessage(),
                vote.getEmail(),
                java.util.Date
                        .from(vote.getLocalDate().atZone(ZoneId.systemDefault())
                                .toInstant()),
                singer,
                genres);
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(voteEntity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if(entityManager != null){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    private List <GenreEntity> voteToGenreEntityList (VoteDTO voteDTO){
        List <GenreEntity> genresEntityList = new ArrayList<>();
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            for (long genre : voteDTO.getGenresID()) {
                genresEntityList.add(entityManager.find(GenreEntity.class,genre));
            }
            return genresEntityList;
        } catch (Exception e) {
            if(entityManager != null){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    private SingerEntity voteToSingerEntity (VoteDTO voteDTO){
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            return entityManager.find(SingerEntity.class,voteDTO.getSingerID());
        } catch (Exception e) {
            if(entityManager != null){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }
}
