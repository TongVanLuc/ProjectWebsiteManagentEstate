package com.javaweb.api.admin;


import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.FullBuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@RequestMapping("/api/building")
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	//thêm hoặc sửa tòa nhà
	@PostMapping
	public void btnAddOrUpdateBuilding(@RequestBody FullBuildingDTO fullBuildingDTO){
		//xuống data để update hoặc thêm mới
		if(fullBuildingDTO.getId() == null){
			buildingService.insertBuilding(fullBuildingDTO);
		}
		else{
			buildingService.updateBuilding(fullBuildingDTO);
		}
	}

	//xóa tòa nhà theo danh sách ids
	@DeleteMapping("/{ids}")
	public void DeleteBuilding(@PathVariable("ids") List<Long> ids){
		buildingService.deleteAllById(ids);
	}

	//giao tòa nhà cho nhân viên quản lý
	@GetMapping("/{id}/staffs")
	public ResponseDTO loadStaffs(@PathVariable Long id){
		ResponseDTO result = buildingService.listStaffs(id);
		return result;
	}

	@PostMapping("/assignment")
	public void updateAssignmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO){
		buildingService.updateAssignmentBuiilding(assignmentBuildingDTO);
	}



}