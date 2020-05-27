package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "10000");
            Account account = new Account();
            account.setUsername("account1");
            account.setHomeAddress(address);
            em.persist(account);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Account account2 = new Account();
            account2.setUsername("account2");
            account2.setHomeAddress(copyAddress);
            em.persist(account2);

//            account.getHomeAddress().setCity("newCity");

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }


        emf.close();

    }
}
