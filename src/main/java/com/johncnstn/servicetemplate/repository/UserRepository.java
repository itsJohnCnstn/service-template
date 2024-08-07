package com.johncnstn.servicetemplate.repository;

import com.johncnstn.servicetemplate.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findOneByIdAndDeletedAtIsNull(UUID id);

    @Query(value = "SELECT u.* FROM user_ AS u WHERE u.deleted_at IS NULL", nativeQuery = true)
    Page<UserEntity> findAllNotDeleted(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE user_ SET deleted_at = now() WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param("id") UUID id);
}
