package com.Charge_Flex.services.auth;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Charge_Flex.dto.RecordDto;
import com.Charge_Flex.dto.UserDto;
import com.Charge_Flex.entity.Records;
import com.Charge_Flex.entity.Users;
import com.Charge_Flex.enums.UserRole;
import com.Charge_Flex.repository.RecordRepository;
import com.Charge_Flex.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServicesImpl implements AuthSevices{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RecordRepository recordRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		Users adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			Users newAdminAccount = new Users();
			newAdminAccount.setName("Admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setPassword("admin");
			newAdminAccount.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminAccount);
			System.out.println("Admin account created successfully");
		}
	}
	@Override
	public UserDto createCustomer(UserDto signupRequest) {
		Users user = new Users();
		user.setUserRole(UserRole.CUSTOMER);
		
		BeanUtils.copyProperties(signupRequest, user);
		userRepository.save(user);
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	@Override
	public RecordDto createRecord(RecordDto saveRequest) {
		Records record = new Records();
		BeanUtils.copyProperties(saveRequest, record);
		recordRepository.save(record);
		return null;
	}

	@Override
	public boolean hasCustomerWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

}
