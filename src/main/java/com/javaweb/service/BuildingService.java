package com.javaweb.service;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.FullBuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.ResponseDTO;

import java.util.List;
import java.util.Map;


public interface BuildingService {
	List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode);

    FullBuildingDTO findAllById(Long id);
	void deleteAllById(List<Long> buildingId);
	ResponseDTO listStaffs(Long buildingId);
	void insertBuilding(FullBuildingDTO fullBuildingDTO);
	void updateBuilding(FullBuildingDTO fullBuildingDTO);
	void updateAssignmentBuiilding(AssignmentBuildingDTO assignmentBuildingDTO);
}