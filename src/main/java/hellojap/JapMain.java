package hellojap;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JapMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Account acoount = new Account();
            acoount.setUsername("account1");
            acoount.setTeam(team);
            em.persist(acoount);

            em.flush();
            em.clear();

            Account findAccount = em.find(Account.class, acoount.getId());

            Team team1 = findAccount.getTeam();
            System.out.println(team1);


            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();

    }
}
