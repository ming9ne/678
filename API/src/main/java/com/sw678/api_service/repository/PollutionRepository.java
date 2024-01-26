package com.sw678.api_service.repository;

import com.sw678.api_service.model.entity.Pollution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollutionRepository extends JpaRepository<Pollution, String> {
}
