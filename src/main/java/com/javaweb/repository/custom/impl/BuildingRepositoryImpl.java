package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	//kiểm tra xem cặp key:value có phải join với bảng khác hay không
	public static StringBuilder joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			sql.append(" inner join assignmentbuilding ass on b.id = ass.buildingid ");
		}

		return null;
	}

	//	hàm để query với chính trong bảo đó
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {

		//java reflecion
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fileName = item.getName();
				if (!fileName.equals("staffId") && !fileName.equals("typeCode") &&
						!fileName.startsWith("area") && !fileName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if (value != null) {
						if (item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" and b." + fileName + " = " + value + " ");
						} else if (item.getType().getName().equals("java.lang.String")) {
							where.append(" and b." + fileName + " like '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	//hàm để query khi phải join với bảng khác
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {

		//kiểm tra với staffID
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append(" and ass.staffid = " + staffId + " ");
		}


		//kiểm tra với diện tích
		Long areaTo = buildingSearchBuilder.getAreaTo();
		Long areaFrom = buildingSearchBuilder.getAreaFrom();
		if (areaFrom != null || areaTo != null) {
			where.append(" and exists (select * from rentarea rta where b.id = rta.buildingid ");
			if (areaFrom != null) {
				where.append(" and rta.value >=" + areaFrom);
			}
			if (areaTo != null) {
				where.append(" and rta.value <=" + areaTo);
			}
			where.append(") ");
		}

		//kiểm tra với giá thuê
		Long rentAreaTo = buildingSearchBuilder.getRentPriceTo();
		Long rentAreaFrom = buildingSearchBuilder.getRentPriceFrom();
		if (rentAreaFrom != null || rentAreaTo != null) {
			if (rentAreaFrom != null) {
				where.append(" and b.rentprice >=" + rentAreaFrom);
			}
			if (rentAreaTo != null) {
				where.append(" and b.rentprice <=" + rentAreaTo);
			}
		}

		//dùng java 8
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" and( ");
			String sql = typeCode.stream().map(it -> "b.type like '%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}


	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {

		//tạo câu lệnh lấy ra các giá trị yêu cầu
		StringBuilder sql = new StringBuilder("select b.* from building b ");

		//kết hợp với bảng khác khi có yêu cầu cần kết nối bảng
		joinTable(buildingSearchBuilder, sql);

		//tạo lệnh where
		StringBuilder where = new StringBuilder(" where 1=1 ");

		//viết câu lệnh khi không phải join với bảng khác
		queryNormal(buildingSearchBuilder, where);

		//viết câu lệnh khi phải join và lấy dữ liệu ở bảng khác
		querySpecial(buildingSearchBuilder, where);

		//nối câu where sau khi check xong vào câu lệnh SQL
		where.append(" group by b.id;");
		sql.append(where);

		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);

		return query.getResultList();
	}

}