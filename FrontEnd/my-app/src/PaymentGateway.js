import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import './PaymentGateway.css';
const PaymentGateway = () => {
    const [loading, setLoading] = useState(false);
    const { amount } = useParams();
    const navigate = useNavigate();
    const handlePayment = async () => {
        try {
            setLoading(true);

            if (!window.Razorpay) {
                alert("Razorpay SDK not loaded. Please refresh the page.");
                return;
            }

            const response = await fetch(`http://localhost:8080/api/auth/createTransaction/${amount}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
            });

            if (!response.ok) {
                throw new Error("Failed to create Razorpay order.");
            }

            const data = await response.json();

            const options = {
                key: "rzp_test_aWJAvvmflxe7Lx",
                amount: data.amount,
                currency: data.currency,
                order_id: data.id,
                name: "Charge-Flex",
                description: "Payment",
                handler: function (response) {
                    alert(`✅ Payment Successful! Payment ID: ${response.razorpay_payment_id}`);
                    navigate("/recharge-plans");
                },
                prefill: {
                    name: "User Name",
                    email: "user@example.com",
                    contact: "9999999999",
                },
                theme: { color: "#3399cc" },
                modal: {
                    ondismiss: function () {
                        alert("Payment was cancelled by the user.");
                    }
                }
            };

            const rzp1 = new window.Razorpay(options);
            rzp1.open();
        } catch (error) {
            console.error("Payment Error:", error);
            alert("⚠️ Payment failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    

return (
    <div className="payment-container">
        <h2 className="payment-title">Complete Your Payment</h2>
        <p className="payment-description">Proceed with secure payment for ₹{amount}</p>
        <button 
            className="payment-button"
            onClick={handlePayment} 
            disabled={loading}
        >
            {loading ? "Processing..." : `Pay ₹${amount}`}
        </button>
    </div>
);

};

export default PaymentGateway;
