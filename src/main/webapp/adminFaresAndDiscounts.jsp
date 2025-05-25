<%@ page import="java.util.Map" %>
<%@ page import="model.Enums.DiscountCategory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fares and Discounts Management</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .section {
            margin: 30px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Fares and Discounts Management</h1>

    <%
        Map<String, Double> baseFares = (Map<String, Double>) request.getAttribute("baseFares");
        Map<DiscountCategory, Double> discountRates = (Map<DiscountCategory, Double>) request.getAttribute("discountRates");
    %>

    <div class="section">
        <h2>Base Fares</h2>
        <% if (baseFares != null && !baseFares.isEmpty()) { %>
        <table>
            <thead>
            <tr>
                <th>Travel Type - Ticket Type</th>
                <th>Base Fare</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% for (Map.Entry<String, Double> entry : baseFares.entrySet()) {
                    String [] keys = entry.getKey().split("_");
            %>
            <tr>
                <td><%= entry.getKey() %></td>
                <td>$<%= entry.getValue() %></td>
                <td>
                    <form method="get" action="AdminRulesManagementServlet" style="display: inline;">
                        <input type="hidden" name="action" value="changeBaseFare">
                        <input type="hidden" name="travelType" value="<%=keys[0]%>">
                        <input type="hidden" name="ticketType" value="<%=keys[1]%>">
                        <input type="number" name="newBaseFare" step="0.01" placeholder="New fare" required>
                        <button type="submit">Update</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p>No base fares available.</p>
        <% } %>
    </div>

    <div class="section">
        <h2>Discount Rates</h2>
        <% if (discountRates != null && !discountRates.isEmpty()) { %>
        <table>
            <thead>
            <tr>
                <th>Discount Category</th>
                <th>Discount Rate (%)</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% for (Map.Entry<DiscountCategory, Double> entry : discountRates.entrySet()) { %>
            <tr>
                <td><%= entry.getKey().toString() %></td>
                <td><%= entry.getValue() * 100 %>%</td>
                <td>
                    <form method="get" style="display: inline;">
                        <input type="hidden" name="action" value="changeDiscount">
                        <input type="hidden" name="category" value="<%= entry.getKey().toString() %>">
                        <input type="number" name="newDiscount" step="0.01" min="0" max="100" placeholder="New discount %" required>
                        <button type="submit">Update</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p>No discount rates available.</p>
        <% } %>
    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div style="color: red; margin: 20px 0;">
        <strong>Error:</strong> <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <div class="section">
        <a href="adminDashboard.jsp">Back to Admin Dashboard</a>
    </div>
</div>
</body>
</html>