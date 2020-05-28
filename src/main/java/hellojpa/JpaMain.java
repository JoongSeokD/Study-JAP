package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

            Account account = new Account();
            account.setUsername("관리자");
            account.setAge(10);
            account.changeTeam(team);
            em.persist(account);

            Account account2 = new Account();
            account2.setUsername("관리자2");
            account2.setAge(10);
            account2.changeTeam(team);

            em.persist(account2);

            em.flush();
            em.clear();

//            String query = "select a.username from Account a"; // 상태 필드 경로 탐생의 끝, 탐색 X
//            String query = "select a.team from Account a"; // 단일 값 연관 경로 묵시적 내부 조인 발생, 탐색 O (실무에서 묵지적 내부조인이 발생하게 쿼리를 짜면 안된다. 직관적으로 튜닝하기 어려움)
            String query = "select t.accounts from Team t";  // 컬렉션 값 연관 경로 묵시적 내부 조인 발생, 탐색 X

            Collection resultList = em.createQuery(query, Collection.class)
                    .getResultList();
            for (Object o : resultList) {
                System.out.println("o = " + o);
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
