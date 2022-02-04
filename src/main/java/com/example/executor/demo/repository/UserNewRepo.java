package com.example.executor.demo.repository;

import com.example.executor.demo.entity.UserNew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserNewRepo extends JpaRepository<UserNew, UUID> {
}
