package com.Charge_Flex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Charge_Flex.entity.Users;
import com.Charge_Flex.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	Optional<Users> findFirstByEmail(String email);

	Users findByUserRole(UserRole userRole);
}
