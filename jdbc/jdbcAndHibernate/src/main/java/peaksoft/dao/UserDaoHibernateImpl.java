package peaksoft.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import peaksoft.model.User;
import peaksoft.util.Util;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();

    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("drop from User").executeUpdate();
            entityManager.getTransaction().commit();
            System.out.println("successfully drop");
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            User user = new User(name,lastName,age);
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("delete from User u where u.id = :id")
                    .setParameter("id",id);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            System.out.println("successfully removed");
        }catch(HibernateException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.createQuery("select u from User u ",User.class).getResultList();
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("truncate table User ").executeUpdate();
            entityManager.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.getMessage());
        }
    }
}
