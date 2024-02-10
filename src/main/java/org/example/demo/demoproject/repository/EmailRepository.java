package org.example.demo.demoproject.repository;

import org.example.demo.demoproject.entity.EmailDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Repository
public interface EmailRepository extends JpaRepository<EmailDataEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM public.email_data " +
            "WHERE email = :email", nativeQuery = true)
    void deleteEmailByEmail(@Param("email") String email);

    Optional<EmailDataEntity> findByEmail(@Param("email") String email);

}
