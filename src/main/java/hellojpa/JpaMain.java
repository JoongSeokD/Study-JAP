package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Account account = new Account();
            account.setUsername("account1");
            account.setAge(10);
            em.persist(account);

            em.flush();
            em.clear();

            List<Address> resultList = em.createQuery("select a.homeAddress from Account a ", Address.class)
                    .getResultList();

            List<AccountDTO> resultList1 = em.createQuery("select new jpabook.jpashop.domain.AccountDTO( a.username, a.age) from Account a ", AccountDTO.class)
                    .getResultList();

            for (AccountDTO accountDTO : resultList1) {
                System.out.println("accountDTO = " + accountDTO.getUsername());
                System.out.println("accountDTO = " + accountDTO.getAge());
            }


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
