package hellojpa;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);
            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Account account = new Account();
            account.setUsername("관리자");
            account.setAge(10);
            account.changeTeam(team);
            em.persist(account);

            Account account2 = new Account();
            account2.setUsername("관리자2");
            account2.setAge(20);
            account2.changeTeam(team);
            em.persist(account2);

            Account account3 = new Account();
            account3.setUsername("관리자3");
            account3.setAge(30);
            account3.changeTeam(team2);
            em.persist(account3);

            // flush 자동 호출
            int count = em.createQuery("update Account a set a.age = 20")
                    .executeUpdate();

            // 벌크 연산 수행 후 영속성 컨텍스트를 초기화 해야함
            em.clear();

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
