package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.FullBuildingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingDTOConverter {

	@Autowired
	private ModelMapper modelMapper;

	public BuildingDTO toBuildingDTO(BuildingEntity item) {

		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		building.setAddress(item.getStreet() +", "+ item.getWard() +", "+ District.type().get(item.getDistrict()));
		List<RentAreaEntity> rentAreas = item.getRentArea();
		String valueArea = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));
		building.setValueArea(valueArea);

		return building;
	}

	public FullBuildingDTO toFullBuildingDTO(BuildingEntity item) {
		FullBuildingDTO building = modelMapper.map(item, FullBuildingDTO.class);
		List<RentAreaEntity> rentAreas = item.getRentArea();
		String valueArea = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(valueArea);
		return building;
	}

}
