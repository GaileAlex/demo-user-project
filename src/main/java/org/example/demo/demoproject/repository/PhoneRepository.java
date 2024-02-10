package org.example.demo.demoproject.repository;

import org.example.demo.demoproject.entity.PhoneDataEntity;
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
public interface PhoneRepository extends JpaRepository<PhoneDataEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM public.phone_data " +
            "WHERE phone = :phone", nativeQuery = true)
    void deletePhoneByPhone(@Param("phone") String phone);

    Optional<PhoneDataEntity> findByPhone(@Param("email") String email);

}
