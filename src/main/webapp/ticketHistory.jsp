<%@ page import="model.Tickets.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>History of Purchases</title>
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
        <input type="submit" value="Buy">
    </form>
    <form action="passengerDashboard.jsp" style="display: inline;">
        <input type="submit" value="Home">
    </form>
</div>
<%
    List<Ticket> tickets = (List<Ticket>) request.getAttribute("userTickets");
    if (tickets != null) {
%>
<div class="section">
    <%
        if (!tickets.isEmpty()) { %>
    <h2> Your Purchase History:</h2>
    <table>
        <tr>
            <th>Trip from</th>
            <th>to</th>
            <th>at</th>
            <th>Purchase Date</th>
            <th>Price</th>
            <th>Type</th>
            <th>Actions</th>
        </tr>

        <% for (Ticket ticket : tickets) {

        %>
        <tr>
            <td>
                <%= ticket.getTrip().getOrigin() %>
            </td>
            <td>
                <%= ticket.getTrip().getDestination() %>
            </td>
            <td>
                <%= ticket.getTrip().getDepartureTime()%>
            </td>
            <td>
                <%= ticket.getPurchaseDate()%>
            </td>
            <td>
                <%= ticket.getPrice()%>
            </td>
            <td>
                <%= ticket.getTicketType()%>
            </td>
            <td>
                <form method="get" action="HistoryServlet" class="inline">
                    <input type="hidden" name="ticketId" value="<%= ticket.getId() %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p>you haven't made any purchases yet</p>
    <%
        }
    %>
</div>
<%
    }
%>

</body>
</html>
