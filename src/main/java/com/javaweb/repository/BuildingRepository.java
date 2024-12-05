package com.javaweb.repository;

import java.util.List;

import com.javaweb.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.repository.custom.BuildingRepositoryCustom;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>, BuildingRepositoryCustom {
    public BuildingEntity findAllById(Long id);
    public void deleteAllById(Long id);
}
