package com.javaweb.controller.admin;


import com.javaweb.enums.District;
import com.javaweb.enums.TypeCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.FullBuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.service.BuildingService;
import com.javaweb.service.IUserService;
import com.javaweb.utils.GetDataParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller(value = "buildingControllerOfAdmin")
public class BuildingController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/admin/building-list")
    public ModelAndView buildingList(@ModelAttribute BuildingSearchRequest building, HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("admin/building/list");

        // Chuẩn bị dữ liệu tìm kiếm
        Map<String, Object> searchParams = GetDataParam.getParams(building);
        List<String> typeCodes = GetDataParam.getTypeCode(building);

        // Lấy danh sách tòa nhà và ánh xạ sang DTO phản hồi
        List<BuildingSearchResponse> buildingResponses = buildingService.findAll(searchParams, typeCodes).stream()
                .map(buildingDTO -> modelMapper.map(buildingDTO, BuildingSearchResponse.class))
                .collect(Collectors.toList());

        // Thêm các thuộc tính cần thiết vào ModelAndView
        mav.addObject("modelSearch", building);
        mav.addObject("buildingList", buildingResponses);
        mav.addObject("listStaff", userService.getStaffs());
        mav.addObject("districts", District.type());
        mav.addObject("typeCodes", TypeCode.type());

        return mav;
    }

    @GetMapping(value = "/admin/building-edit")
    public ModelAndView buildingAdd(@ModelAttribute FullBuildingDTO fullBuildingDTO, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        mav.addObject("districts", District.type());
        mav.addObject("typeCodes", TypeCode.type());
        mav.addObject("buildingEdit", fullBuildingDTO);
        return mav;
    }

    @GetMapping(value = "/admin/building-edit-{id}")
    public ModelAndView buildingUpadate(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        FullBuildingDTO building = buildingService.findAllById(id);
        mav.addObject("districts", District.type());
        mav.addObject("typeCodes", TypeCode.type());
        mav.addObject("buildingEdit", building);
        return mav;
    }
}
