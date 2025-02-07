import { useState, useEffect } from "react";
import { getAllRecords } from "./adminService";
import "./AdminDashboard.css";

function AdminDashboard() {
  const [records, setRecords] = useState([]);
  const [filteredRecords, setFilteredRecords] = useState([]);
  const [showFilteredTable, setShowFilteredTable] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecords = async () => {
      try {
        const data = await getAllRecords();
        console.log("Fetched Records from API:", data); // Debugging
        const uniqueRecords = removeDuplicates(data);
        const sortedRecords = uniqueRecords.sort((a, b) => {
          const dateA = convertToDate(a.validity);
          const dateB = convertToDate(b.validity);
          return dateA - dateB;
        });
        setRecords(sortedRecords);
        setError(null);
      } catch (error) {
        console.error("Error fetching records:", error);
        setError("Failed to fetch records. Please try again later.");
        setRecords([]);
      }
    };

    fetchRecords();
  }, []);

  // Remove duplicates based on specific properties (userId, name, plan, date, validity)
  const removeDuplicates = (data) => {
    const uniqueRecords = [];
    const seen = new Set();

    data.forEach((record) => {
      const uniqueKey = `${record.userId}-${record.name}-${record.plan}-${record.date}-${record.validity}`;
      if (!seen.has(uniqueKey)) {
        seen.add(uniqueKey);
        uniqueRecords.push(record);
      } else {
        console.log("Duplicate Removed:", record); // Debugging
      }
    });

    return uniqueRecords;
  };

  // Convert validity string to Date object
  const convertToDate = (validity) => {
    if (!validity) return new Date();
    const [day, month, year] = validity.split("/");
    return new Date(year, month - 1, day);
  };

  // Handle filtering based on the user's name clicked
  const filterRecords = (name) => {
    const filtered = records.filter((record) => record.name === name);
    setFilteredRecords(filtered);
    setShowFilteredTable(true);
  };

  if (error) {
    return (
      <div className="content-container">
        <h1 className="table-heading">Error</h1>
        <p style={{ color: "red" }}>{error}</p>
      </div>
    );
  }

  return (
    <div className="content-container">
      <h1 className="table-heading">All Transactions</h1>
      <div className="table-container">
        <table className="styled-table">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Plan</th>
              <th>Date</th>
              <th>Expiry</th>
            </tr>
          </thead>
          <tbody>
            {records.map((record, index) => (
              <tr key={index} onClick={() => filterRecords(record.name)}>
                <td>{record.userId}</td>
                <td className="clickable">{record.name}</td>
                <td>₹ {parseFloat(record.plan)}</td>
                <td>{record.date}</td>
                <td>{record.validity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Show filtered records when a user is clicked */}
      {showFilteredTable && (
        <div>
          <h2 className="table-heading">User's Transaction History</h2>
          <div className="table-container">
            <table className="styled-table">
              <thead>
                <tr>
                  <th>User ID</th>
                  <th>Name</th>
                  <th>Plan</th>
                  <th>Date</th>
                  <th>Expiry</th>
                </tr>
              </thead>
              <tbody>
                {filteredRecords.map((record, index) => (
                  <tr key={index}>
                    <td>{record.userId}</td>
                    <td>{record.name}</td>
                    <td>₹ {parseFloat(record.plan)}</td>
                    <td>{record.date}</td>
                    <td>{record.validity}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <button onClick={() => setShowFilteredTable(false)}>Show All Transactions</button>
        </div>
      )}
    </div>
  );
}

export default AdminDashboard;
