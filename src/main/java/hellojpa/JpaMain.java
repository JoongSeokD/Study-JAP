package hellojpa;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Movie;
import org.hibernate.Hibernate;

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
            account.setUsername("hello");

            em.persist(account);

            em.flush();
            em.clear();

            //
//            Account account1 = em.find(Account.class, account.getId());
            Account emReference = em.getReference(Account.class, account.getId());
            // 프록시 클래스
            // em.find DB를 통해 실제 엔티티 객체 조회
            // em.reference DB조회를 미루는 가짜 엔티티 객체 조회

            // 특징 : 실제 클래스를 상속받아서 만들어짐 실제 클래스와 겉 모양이 같다.
            // 사용하는 입장에서 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
            // 실제 객체의 참조(target)를 보관
            // 프록시 객체는 처음 사용할 때 한 번만 초기화
            // 프록시 객체를 초기화 할때 , 프록시 객체가 실제 엔티티로 바뀌는 것은 아님,
            // 초기화되면 프록시 객체를 통해 실제 엔티티에 접근 가능
            // 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함 (== 비교 실패, 대신 instance of 사용)
            // 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
            // 엳속성 컨텍스트의 도움을 받을수 없는 준영속 상태일때 프록시를 초기화하면 문제 발생
            // (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)
            System.out.println("before emReference = " + emReference.getClass());

            // 강제 초기화 (JPA 표준은 강제 초기화 없음)
            Hibernate.initialize(emReference);
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(emReference) = " + emf.getPersistenceUnitUtil().isLoaded(emReference));
//            em.detach(emReference);
            System.out.println(emReference.getId());
            System.out.println(emReference.getUsername());
            System.out.println(emReference.getUsername());
            System.out.println("after emReference = " + emReference.getClass());

            // 프록시 인스턴스 초기화 여부확인
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(emReference) = " + emf.getPersistenceUnitUtil().isLoaded(emReference));

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
