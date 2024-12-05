package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.FullBuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.RentareaRepository;
import com.javaweb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BuildingServieImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private BuildingDTOConverter buildingDTOConverter;
	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RentareaRepository rentareaRepository;

	//tìm kiếm tòa nhà
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> Params, List<String> typeCode) {

		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(Params, typeCode);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();

		for (BuildingEntity item: buildingEntities) {

			BuildingDTO building = buildingDTOConverter.toBuildingDTO(item);

			result.add(building);

		}

		return result;
	}

	//tìm kiếm tòa nhà theo ID
	@Override
	public FullBuildingDTO findAllById(Long id) {
		BuildingEntity buildingEntities = buildingRepository.findAllById(id);
		FullBuildingDTO result = buildingDTOConverter.toFullBuildingDTO(buildingEntities);
		return result;
	}

	//xóa tòa nhà
	@Override
	public void deleteAllById(List<Long> buildingIds) {
		for(Long buildingId: buildingIds) {
			BuildingEntity building = buildingRepository.findById(buildingId).get();
			buildingRepository.delete(building);
		}
	}

	//giao tòa nhà
	// đầu tiên lấy ra tòa nhà theo Id, tiếp đến lấy ra tất cả nhân viên có role là staff và trạng thái là "1",
	// lấy ra những nhân viên quản lý tòa nhà theo Id ở trên
	//   <1>  --   duyệt qua tất cả nhân viên sau đó set tên và id cho từng nhân viên
	//   <2>   -  nếu nhân viên đó ở trong danh sách quản lý tòa nhà theo id thì set Checked = checked, nếu không có thì xét là rỗng
	//sau khi set các giá trị cho nhân viên ở <1> và <2> thì lưu vào một danh sách nhân viên
	//sau khi xong thì set thông tin của nhân viên đó vào một DTO và đưa sang font-end để tiếp tục xử lý
	@Override
	public ResponseDTO listStaffs(Long buildingId) {
		BuildingEntity building = buildingRepository.findById(buildingId).get();
		List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");
		List<UserEntity> staffAssignment = building.getUserEntities();
		List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
		ResponseDTO responseDTO = new ResponseDTO();
		for (UserEntity item: staffs) {
			StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
			staffResponseDTO.setFullName(item.getFullName());
			staffResponseDTO.setStaffId(item.getId());
			if (staffAssignment.contains(item)){
				staffResponseDTO.setChecked("checked");
			}
			else{
				staffResponseDTO.setChecked("");
			}
			staffResponseDTOS.add(staffResponseDTO);
		}
		responseDTO.setData(staffResponseDTOS);
		responseDTO.setMessage("success");
		return responseDTO;
	}

	//thêm tòa nhà
	@Override
	public void insertBuilding(FullBuildingDTO fullBuildingDTO) {
		BuildingEntity building = modelMapper.map(fullBuildingDTO, BuildingEntity.class);
		building.setType(String.join(",", fullBuildingDTO.getTypeCode()));
		buildingRepository.save(building);
		String[] values = fullBuildingDTO.getRentArea().split(",");
		for(String value: values) {
			RentAreaEntity rentArea = new RentAreaEntity();
			rentArea.setBuildingid(building);
			rentArea.setValue(Long.valueOf(value.trim()));
			rentareaRepository.save(rentArea);
		}
	}

	//sửa tòa nhà
	@Override
	public void updateBuilding(FullBuildingDTO fullBuildingDTO) {
		BuildingEntity building = buildingRepository.findById(fullBuildingDTO.getId()).get();

		building = modelMapper.map(fullBuildingDTO, BuildingEntity.class);
		building.setType(String.join(",", fullBuildingDTO.getTypeCode()));
		buildingRepository.save(building);

		rentareaRepository.deleteByBuildingid(building);
		String[] values = fullBuildingDTO.getRentArea().split(",");
		for(String value: values) {
			RentAreaEntity rentArea = new RentAreaEntity();
			rentArea.setBuildingid(building);
			rentArea.setValue(Long.valueOf(value.trim()));
			rentareaRepository.save(rentArea);
		}


	}

	@Override
	public void updateAssignmentBuiilding(AssignmentBuildingDTO assignmentBuildingDTO) {
		//lấy về tòa nhà
		BuildingEntity building = buildingRepository.findAllById(assignmentBuildingDTO.getBuildingId());

		//lấy về nhân viên
		List<UserEntity> user = userRepository.findAllById(assignmentBuildingDTO.getStaffs());

		//sét nhân viên quản lý tòa nhà
		building.setUserEntities(user);

		//lưu kết quả
		buildingRepository.save(building);

	}

}
