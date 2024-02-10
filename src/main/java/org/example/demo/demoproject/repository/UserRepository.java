package org.example.demo.demoproject.repository;

import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.model.request.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select distinct * " +
            "from public.user u" +
            "         left join public.email_data ed on u.id = ed.user_id " +
            "         left join public.phone_data pd on u.id = pd.user_id " +
            "where ed.email = :param " +
            "   or pd.phone = :param " +
            "limit 1", nativeQuery = true)
    Optional<UserEntity> findUserByPhoneOrEmail(@Param("param") String param);

    @Query(value = "select distinct on(u.id) * " +
            "from public.user u" +
            "         left join public.account ac on u.id = ac.user_id" +
            "         left join public.email_data ed on u.id = ed.user_id" +
            "         left join public.phone_data pd on u.id = pd.user_id " +
            "where (:#{#userRequest.name} is null or u.name ilike CONCAT(cast(:#{#userRequest.name} as text), '%'))" +
            "  and (:#{#userRequest.email} is null or ed.email = cast(:#{#userRequest.email} as text))" +
            "  and (:#{#userRequest.phone} is null or pd.phone = cast(:#{#userRequest.phone} as text))" +
            "  and (to_date(cast(:#{#userRequest.dateOfBirth} as text), 'YYYY-MM-DD') is null" +
            " or u.date_of_birth > to_date(cast(:#{#userRequest.dateOfBirth} as text), 'YYYY-MM-DD') ) " +
            " limit cast(:#{#userRequest.size} as bigint) " +
            " offset cast(:#{#userRequest.offset} as bigint)", nativeQuery = true)
    List<UserEntity> findUsers(@Param("userRequest") UserRequest userRequest);

    @Query(value = "with data as (select distinct on(u.id) * " +
            "from public.user u" +
            "         left join public.account ac on u.id = ac.user_id" +
            "         left join public.email_data ed on u.id = ed.user_id" +
            "         left join public.phone_data pd on u.id = pd.user_id " +
            "where (:#{#userRequest.name} is null or u.name ilike CONCAT(cast(:#{#userRequest.name} as text), '%'))" +
            "  and (:#{#userRequest.email} is null or ed.email = cast(:#{#userRequest.email} as text))" +
            "  and (:#{#userRequest.phone} is null or pd.phone = cast(:#{#userRequest.phone} as text))" +
            "  and (to_date(cast(:#{#userRequest.dateOfBirth} as text), 'YYYY-MM-DD') is null" +
            " or u.date_of_birth > to_date(cast(:#{#userRequest.dateOfBirth} as text), 'YYYY-MM-DD') )) " +
            "select count(*) as pages from data ",
            nativeQuery = true)
    Integer getPages(@Param("userRequest") UserRequest userRequest);

}
