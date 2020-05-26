package hellojpa;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Account account = new Account();
            account.setCreatedBy("kim");
            account.setCreatedDate(LocalDateTime.now());
            em.persist(account);

            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함계사라지다");
            movie.setPrice(10000);
            em.persist(movie);

            em.flush();
            em.clear();

            Movie movie1 = em.find(Movie.class, movie.getId());


            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();

    }
}
