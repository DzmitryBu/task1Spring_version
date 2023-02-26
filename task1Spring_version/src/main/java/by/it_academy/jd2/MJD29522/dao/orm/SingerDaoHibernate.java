package by.it_academy.jd2.MJD29522.dao.orm;

import by.it_academy.jd2.MJD29522.dao.api.ISingerDao;
import by.it_academy.jd2.MJD29522.dao.orm.api.IManager;
import by.it_academy.jd2.MJD29522.dto.SingerDTO;
import by.it_academy.jd2.MJD29522.entity.SingerEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class SingerDaoHibernate implements ISingerDao {

    private final IManager manager;

    public SingerDaoHibernate(IManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    public List<SingerEntity> get() {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            List<SingerEntity>singerEntityList = entityManager.createQuery
                    ("from SingerEntity ORDER BY id", SingerEntity.class).getResultList();

            entityManager.getTransaction().commit();

            return singerEntityList;
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    @Override
    public boolean add(SingerDTO newSinger) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(new SingerEntity(newSinger.getName()));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
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
    public void update(SingerEntity singerEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            SingerEntity singerToUpdate = entityManager.find(SingerEntity.class, singerEntity.getId());
            if(singerToUpdate != null){
                entityManager.merge(singerEntity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
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
    public boolean delete(long id, long version) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            SingerEntity singerToRemoved = entityManager.find(SingerEntity.class, id);
            if(singerToRemoved != null){
                entityManager.remove(singerToRemoved);
            }
            entityManager.getTransaction().commit();
            return singerToRemoved != null;
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
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
    public SingerEntity exist(long id) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            SingerEntity singer = entityManager.find(SingerEntity.class, id);
            entityManager.getTransaction().commit();
           return singer;
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }
        finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    @Override
    public String getName(long id) {
        EntityManager entityManager = null;
        try {
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            SingerEntity singer = entityManager.find(SingerEntity.class, id);
            entityManager.getTransaction().commit();
            if(singer == null){
                throw new IllegalArgumentException("Исполнителя с id " + id + " не существует");
            }
            return singer.getName();
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка в базе данных", e);
        }
        finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    @Override
    public SingerEntity getCard(long id){
        EntityManager entityManager = null;
        try{
            entityManager = manager.getEntityManager();
            entityManager.getTransaction().begin();
            SingerEntity singerEntity = entityManager.find(SingerEntity.class, id);
            entityManager.getTransaction().commit();
            if(singerEntity != null){
                return singerEntity;
            }else throw new IllegalArgumentException("Артиста с id " + id + " не найдено");
        } catch (Exception e) {
            if(entityManager != null && entityManager.getTransaction().isActive()){
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
