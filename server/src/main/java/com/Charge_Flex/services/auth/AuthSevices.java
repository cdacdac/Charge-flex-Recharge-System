package com.Charge_Flex.services.auth;

import com.Charge_Flex.dto.RecordDto;
import com.Charge_Flex.dto.UserDto;

public interface AuthSevices {
	UserDto createCustomer(UserDto signupRequest);
	RecordDto createRecord(RecordDto saveRequest);
	boolean hasCustomerWithEmail(String email);
}
