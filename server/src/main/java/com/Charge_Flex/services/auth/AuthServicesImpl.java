package com.Charge_Flex.services.auth;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Charge_Flex.dto.RecordDto;
import com.Charge_Flex.dto.UserDto;
import com.Charge_Flex.entity.Records;
import com.Charge_Flex.entity.TransactionDetails;
import com.Charge_Flex.entity.Users;
import com.Charge_Flex.enums.UserRole;
import com.Charge_Flex.repository.RecordRepository;
import com.Charge_Flex.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServicesImpl implements AuthSevices{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RecordRepository recordRepository;
	
	private static final String KEY = "rzp_test_aWJAvvmflxe7Lx";
	private static final String KEY_SECRET = "9URdXKYOlCtatbqQlAhBvRbm";
	private static final String CURRENCY = "INR";
	
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
		BeanUtils.copyProperties(signupRequest, user);
		user.setUserRole(UserRole.CUSTOMER);
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
	
	@Override
	public TransactionDetails createTransaction(Double amount) {
		
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", ( amount * 100 ));
			jsonObject.put("currency",CURRENCY);
			
			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
			
			Order order = razorpayClient.orders.create(jsonObject);
			
			return prepareTransactionDetails(order);
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount = order.get("amount");
		
		TransactionDetails transactionDetails = new TransactionDetails(orderId,currency,amount);
		return transactionDetails;
	}
	

}
