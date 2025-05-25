<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Trip</title>
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
                        <input type="text" name="origin" value="<%= request.getParameter("origin") %>">
                    </td>
                    <td>
                        <input type="text" name="destination" value="<%= request.getParameter("destination") %>">
                    </td>
                    <td>
                        <input type="datetime-local" name="departure" value="<%= request.getParameter("departure") %>">
                    </td>
                    <td>
                        <select name="travelType">
                            <option value="City">City</option>
                            <option value="InterCity">InterCity</option>
                        </select>
                    </td>
                    <td>
                        <input type="number" name="availableSeats" value="<%= request.getParameter("availableSeats") %>">
                    </td>
                    <td>
                        <input type="hidden" name="tripId" value="<%= request.getParameter("tripId")%>">
                        <input type="hidden" name="action" value="edit">
                        <input type="submit" value="Confirm">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>