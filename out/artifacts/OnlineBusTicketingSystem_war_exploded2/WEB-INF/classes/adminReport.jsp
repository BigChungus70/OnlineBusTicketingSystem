<%@ page import="model.Tickets.Ticket" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<form method="get" action="HistoryServlet">
    <input type="submit" value="Refresh">
    <input type="hidden" name="action" value="report">
</form>

<%
    List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
    if (tickets != null) {
        Map<String, Integer> ticketsByDate = new HashMap<>();
        Map<String, Integer> ticketsByType = new HashMap<>();
        Map<String, Integer> ticketsByUserCategory = new HashMap<>();
        Map<String, Double> revenueByDay = new HashMap<>();
        Map<String, Double> revenueByWeek = new HashMap<>();
        Map<String, Double> revenueByMonth = new HashMap<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("yyyy-'W'ww");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Ticket ticket : tickets) {
            String date = ticket.getPurchaseDate().format(dateFormatter);
            String week = ticket.getPurchaseDate().format(weekFormatter);
            String month = ticket.getPurchaseDate().format(monthFormatter);
            String ticketType = ticket.getTicketType().toString();
            String userCategory = ticket.getUser().getCategory().toString();

            ticketsByDate.put(date, ticketsByDate.getOrDefault(date, 0) + 1);
            ticketsByType.put(ticketType, ticketsByType.getOrDefault(ticketType, 0) + 1);
            ticketsByUserCategory.put(userCategory, ticketsByUserCategory.getOrDefault(userCategory, 0) + 1);

            if ("Daily".equals(ticketType)) {
                revenueByDay.put(date, revenueByDay.getOrDefault(date, 0.0) + ticket.getPrice());
            } else if ("Weekly".equals(ticketType)) {
                revenueByWeek.put(week, revenueByWeek.getOrDefault(week, 0.0) + ticket.getPrice());
            } else if ("Monthly".equals(ticketType)) {
                revenueByMonth.put(month, revenueByMonth.getOrDefault(month, 0.0) + ticket.getPrice());
            }
        }
%>

<h2>Tickets Report</h2>
<table>
    <thead>
    <tr><th>Date</th><th>Number of Tickets</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Integer> entry : ticketsByDate.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
    <% } %>
    </tbody>
</table>

<table>
    <thead>
    <tr><th>Ticket Type</th><th>Number of Tickets</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Integer> entry : ticketsByType.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
    <% } %>
    </tbody>
</table>

<table>
    <thead>
    <tr><th>User Category</th><th>Number of Tickets</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Integer> entry : ticketsByUserCategory.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td><%= entry.getValue() %></td></tr>
    <% } %>
    </tbody>
</table>

<h2>Revenue Report</h2>
<table>
    <thead>
    <tr><th>Day</th><th>Daily Ticket Revenue</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Double> entry : revenueByDay.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td>$<%= String.format("%.2f", entry.getValue()) %></td></tr>
    <% } %>
    </tbody>
</table>

<table>
    <thead>
    <tr><th>Week</th><th>Weekly Ticket Revenue</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Double> entry : revenueByWeek.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td>$<%= String.format("%.2f", entry.getValue()) %></td></tr>
    <% } %>
    </tbody>
</table>

<table>
    <thead>
    <tr><th>Month</th><th>Monthly Ticket Revenue</th></tr>
    </thead>
    <tbody>
    <% for (Map.Entry<String, Double> entry : revenueByMonth.entrySet()) { %>
    <tr><td><%= entry.getKey() %></td><td>$<%= String.format("%.2f", entry.getValue()) %></td></tr>
    <% } %>
    </tbody>
</table>

<% } %>
</body>
</html>
