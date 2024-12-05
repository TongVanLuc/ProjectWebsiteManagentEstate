package com.javaweb.repository.custom;

import com.javaweb.entity.RentAreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentAreaRepositoryCustom {
    List<RentAreaEntity> findByBuildingid(Long buildingId);
}
