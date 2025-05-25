<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Purchasing a Ticket</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .section {
            margin: 20px 0;
        }

        .inline {
            display: inline;
            margin-right: 5px;
        }
    </style>
</head>
<body>

<div class="section">
    <form method="get" action="searchTrips" style="display: inline;">
        <input type="hidden" name="operation" value="populate">
        <input type="submit" value="Back">
    </form>
    <form action="passengerDashboard.jsp" style="display: inline;">
        <input type="submit" value="Home">
    </form>
</div>

<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<div>
    <%= message %>
</div>
<%
    }
%>

<form method="post" action="purchase">
    <select name="ticketType">
        <option value="OneTrip">One Trip</option>
        <option value="Daily">Daily Pass</option>
        <option value="Weekly">Weekly Pass</option>
        <option value="Monthly">Monthly Pass</option>
    </select>
    <input type="hidden" name="tripId" value="<%=request.getParameter("tripId")%>">
    <input type="submit" value="Confirm">
</form>

</body>
</html>