package com.demco.metrology_backend.repository;

import com.demco.metrology_backend.entity.User;
import com.demco.metrology_backend.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailId(@Param("email") String email);

    List<UserWrapper>getAllUser();
    List<String> getAllAdmin();


    @Transactional
    @Modifying
    Integer updateStatus(@Param("status")String status, @Param("updatedAt") Timestamp updatedAt, @Param("id") Long id);

    User findByEmail(String email);
}