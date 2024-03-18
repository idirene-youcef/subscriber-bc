package fr.cp.subscriber.repositories;

import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.entities.Subscriber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {
    @Query("SELECT s FROM Subscriber s WHERE s.fName = :fName AND s.lName = :lName AND s.mail = :mail")
    Optional<Subscriber> findByFNameAndLNameAndMail(@Param("fName") String fName, @Param("lName") String lName, @Param("mail") String mail);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Subscriber s WHERE s.fName = :fName AND s.lName = :lName AND s.mail = :mail")
    boolean existsByFNameAndLNameAndMail(@Param("fName") String fName, @Param("lName") String lName, @Param("mail") String mail);

    default List<Subscriber> search(String fName, String lName, String mail, String phone, Boolean isActive) {
        return findAll(applySearchCriteria(fName, lName, mail, phone, isActive));
    }

    default Specification<Subscriber> applySearchCriteria(String fName, String lName, String mail, String phone, Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fName != null) {
                predicates.add(criteriaBuilder.equal(root.get("fName"), fName));
            }
            if (lName != null) {
                predicates.add(criteriaBuilder.equal(root.get("lName"), lName));
            }
            if (mail != null) {
                predicates.add(criteriaBuilder.equal(root.get("mail"), mail));
            }
            if (phone != null) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }
            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
