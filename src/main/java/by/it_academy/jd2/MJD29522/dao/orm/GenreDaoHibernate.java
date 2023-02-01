package by.it_academy.jd2.MJD29522.dao.orm;

import by.it_academy.jd2.MJD29522.dao.api.IGenreDao;
import by.it_academy.jd2.MJD29522.dao.orm.api.IManager;
import by.it_academy.jd2.MJD29522.dao.orm.entity.GenreEntity;
import by.it_academy.jd2.MJD29522.dto.GenreDTO;
import by.it_academy.jd2.MJD29522.dto.GenreID;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoHibernate implements IGenreDao {

    private final IManager manager;

    public GenreDaoHibernate(IManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public synchronized List<GenreID> get() {
        List<GenreID> genres = new ArrayList<>();

        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            List<GenreEntity> genreEntityList = entityManager.createQuery
                    ("from GenreEntity ORDER BY id", GenreEntity.class).getResultList();

            entityManager.getTransaction().commit();
            for (GenreEntity genreEntity : genreEntityList) {
                genres.add(new GenreID(new GenreDTO(genreEntity.getName()), genreEntity.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
        return genres;
    }

    @Override
    public synchronized boolean add(String newGenre) {
        boolean result = false;
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(new GenreEntity(newGenre));
            entityManager.getTransaction().commit();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
        return result;
    }

    @Override
    public synchronized void update(long id, String name) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            GenreEntity genreToUpdate = entityManager.find(GenreEntity.class, id);
            genreToUpdate.setName(name);
            entityManager.merge(genreToUpdate);
            entityManager.getTransaction().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    @Override
    public synchronized boolean delete(long id) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            GenreEntity genreToRemoved = entityManager.find(GenreEntity.class, id);
            entityManager.remove(genreToRemoved);
            entityManager.getTransaction().commit();
            if(genreToRemoved !=null){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
        return false;
    }

    @Override
    public synchronized boolean exist(long id) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            GenreEntity genre = entityManager.find(GenreEntity.class, id);
            entityManager.getTransaction().commit();
            if(genre !=null){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
        return false;
    }
}
