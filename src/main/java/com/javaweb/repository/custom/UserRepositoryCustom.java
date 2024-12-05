package com.javaweb.repository.custom;

import com.javaweb.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {
	List<UserEntity> findByRole(String roleCode);
	List<UserEntity> getAllUsers(Pageable pageable);
	int countTotalItem();
}
