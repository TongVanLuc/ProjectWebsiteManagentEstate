package com.javaweb.repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.repository.custom.RentAreaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RentareaRepository extends JpaRepository<RentAreaEntity, Long>, RentAreaRepositoryCustom {
    void deleteByBuildingid(BuildingEntity buildingid);
}
