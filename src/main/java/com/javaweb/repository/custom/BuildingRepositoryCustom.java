package com.javaweb.repository.custom;

import java.awt.print.Pageable;
import java.util.List;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;


@Repository
public interface BuildingRepositoryCustom {
	List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
