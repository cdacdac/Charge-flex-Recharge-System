import axios from 'axios';

// You can keep the base URL in a separate variable or use environment variables for production
const BASE_URL = "http://localhost:8080";

class AuthService {
  // Register method to call the signup endpoint
  register(signupRequest) {
    
    return axios.post(`${BASE_URL}/api/auth/signup`, signupRequest);
  }

  // Login method to call the login endpoint
  login(loginRequest) {
    return axios.post(`${BASE_URL}/api/auth/login`, loginRequest);
  }

  // Method to save a record
  saveRecord(record) {
    return axios.post(`${BASE_URL}/api/auth/saveRecord`, record);
  }
}

export default new AuthService;