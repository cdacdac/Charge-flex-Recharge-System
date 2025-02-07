import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CustomerDashboard.css';

const CustomerDashboard = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const validatePhone = () => {
    // Basic phone number validation
    if (phoneNumber.length === 10) {
      setIsLoading(true);
      // Simulating API call
      setTimeout(() => {
        setIsLoading(false);
        navigate('/recharge-plans');
      }, 1000);
    } else {
      alert('Please enter a valid 10-digit phone number');
    }
  };

  return (
    <main className="main-content">
      <div className="verify-card">
        <h1>Verify Your Mobile Number</h1>
        <div className="input-group">
          <label>Enter your phone number:</label>
          <input
            type="text"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value.replace(/\D/g, ''))}
            placeholder="Enter 10-digit number"
            maxLength="10"
          />
        </div>
        <button 
          onClick={validatePhone} 
          className="validate-button"
          disabled={isLoading}
        >
          {isLoading ? 'Validating...' : 'Validate'}
        </button>
      </div>
    </main>
  );
};

export default CustomerDashboard;
