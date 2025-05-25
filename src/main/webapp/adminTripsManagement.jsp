<%@ page import="model.Trip" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manage Trips</title>
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
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="error-message">
    <%= errorMessage %>
</div>
<%
    }
%>
<div class="container">
    <div class="section">
        <a href="adminDashboard.jsp">Back to Admin Dashboard</a>
    </div>
    <div class="section">
        <form method="get" action="AdminTripsManagementServlet">
            <table>
                <thead>
                <tr>
                    <th>Origin</th>
                    <th>Destination</th>
                    <th>Departure Time</th>
                    <th>Travel Type</th>
                    <th>Available Seats</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <input type="text" name="origin" required>
                    </td>
                    <td>
                        <input type="text" name="destination" required>
                    </td>
                    <td>
                        <input type="datetime-local" name="departure" required>
                    </td>
                    <td>
                        <select name="travelType" required>
                            <option value="City">City</option>
                            <option value="InterCity">InterCity</option>
                        </select>
                    </td>
                    <td>
                        <input type="number" name="availableSeats" min="1" required>
                    </td>
                    <td>
                        <input type="hidden" name="action" value="register">
                        <input type="submit" value="Add">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <%
        List<Trip> trips = (List<Trip>) request.getAttribute("trips");
    %>
    <div class="section">
        <%
            if (trips != null && !trips.isEmpty()) {
        %>
        <table>
            <thead>

            <tr>
                <th>Origin</th>
                <th>Destination</th>
                <th>Departure Time</th>
                <th>Travel Type</th>
                <th>Available Seats</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% for (Trip trip : trips) {

            %>
            <tr>
                <td><%= trip.getOrigin() %>
                </td>
                <td><%= trip.getDestination() %>
                </td>
                <td><%= trip.getDepartureTime() %>
                </td>
                <td><%= trip.getTravelType()%>
                </td>
                <td><%= trip.getAvailableSeats() %>
                </td>
                <td>
                    <form method="get" action="AdminTripsManagementServlet" class="inline">
                        <input type="hidden" name="tripId" value="<%= trip.getId() %>">
                        <input type="hidden" name="action" value="remove">
                        <input type="submit" value="Delete">
                    </form>
                    <form method="get" action="adminEditTrip.jsp" class="inline">
                        <input type="hidden" name="origin" value="<%=trip.getOrigin()%>">
                        <input type="hidden" name="destination" value="<%=trip.getDestination()%>">
                        <input type="hidden" name="departure" value="<%=trip.getDepartureTime()%>">
                        <input type="hidden" name="travelType" value="<%=trip.getTravelType()%>">
                        <input type="hidden" name="availableSeats" value="<%=trip.getAvailableSeats()%>">
                        <input type="hidden" name="tripId" value="<%=trip.getId()%>">
                        <input type="submit" value="Edit">
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p>No Any Trips</p>
        <%
            }%>

    </div>
</div>

</body>
</html>
