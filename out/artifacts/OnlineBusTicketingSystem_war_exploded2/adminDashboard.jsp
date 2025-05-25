<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<form method="get" action="AdminRulesManagementServlet">
    <input type="submit" value="Change Fares and Discounts">
</form>
<form method="get" action="AdminTripsManagementServlet">
    <input type="submit" value="Manage and View Trips">
</form>
<form method="get" action="HistoryServlet">
    <input type="submit" value="Reports">
    <input type="hidden" name="action" value="report">
</form>
<form method="get" action="AdminSessionServlet">
    <input type="submit" value="Logout">
</form>

</body>
</html>
