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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Account account = new Account();
            account.setUsername("관리자");
            account.setAge(10);
            account.changeTeam(team);

            em.persist(account);

            em.flush();
            em.clear();

//            String query = "select " +
//                    "        case when a.age <= 10 then '학생요금'" +
//                    "             when a.age >= 60 then '경로요금'" +
//                    "             else '일반요금' " +
//                    "        end " +
//                    "from Account a " ; // CASE

//            String query = "select coalesce(a.username, '이름 없는 회원') from Account as a"; // 하나씩 조회해서 null아니면 반환
            String query = "select NULLIF(a.username, '관리자') from Account as a"; // 두 값이 같으면 null 반환 다르면 첫번째 값 반환

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
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
