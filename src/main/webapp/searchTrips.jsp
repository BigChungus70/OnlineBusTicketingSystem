<%@ page import="java.util.*, model.Trip" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    List<Trip> allTrips = (List<Trip>) request.getAttribute("trips");
    List<Trip> searchResults = (List<Trip>) request.getAttribute("searchResults");
    String estimatedFare = (String) request.getAttribute("estimatedFare");
    String estimatedTripId = (String) request.getAttribute("estimatedTripId");


    Set<String> origins = (Set<String>) pageContext.getAttribute("origins");
    Set<String> destinations = (Set<String>) pageContext.getAttribute("destinations");

    if (origins == null) {
        origins = new TreeSet<>();
        pageContext.setAttribute("origins", origins);
    }
    if (destinations == null) {
        destinations = new TreeSet<>();
        pageContext.setAttribute("destinations", destinations);
    }

    if (allTrips != null) {
        for (Trip trip : allTrips) {
            if (trip.getOrigin() != null) origins.add(trip.getOrigin());
            if (trip.getDestination() != null) destinations.add(trip.getDestination());
        }
    }

    String selectedOrigin = request.getParameter("origin");
    String selectedDestination = request.getParameter("destination");
    String selectedTravelType = request.getParameter("travelType");
    String selectedDate = request.getParameter("date");

    if (selectedDate == null) {
        selectedDate = java.time.LocalDate.now().toString();
    }
%>

<html>
<head>
    <title>Bus Trip Search</title>
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

<h2>Bus Trip Search</h2>
<%
    User loggedInUser = (User) request.getAttribute("loggedInUser");
    if (loggedInUser != null) {
%>
<div class="user-welcome">
    Welcome, <%= loggedInUser.getUsername() %>!
    (Category: <%= loggedInUser.getCategory() %>)
</div>
<%
    }
%>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div style="color: red; margin: 10px 0; padding: 10px; border: 1px solid red; background-color: #ffe6e6;">
    Error: <%= errorMessage %>
</div>
<% } %>

<div class="section" inline>
    <form method="get" action="searchTrips">
        <input type="hidden" name="operation" value="populate">
        <input type="submit" value="Refresh Data">
    </form>
    <form action="passengerDashboard.jsp">
        <input type="submit" value="Home">
    </form>
</div>


<div class="section">
    <form method="get" action="searchTrips">
        Origin:
        <select name="origin" required>
            <option value="">-- Select Origin --</option>
            <% for (String origin : origins) { %>
            <option value="<%= origin %>" <%= origin.equals(selectedOrigin) ? "selected" : "" %>>
                <%= origin %>
            </option>
            <% } %>
        </select>

        Destination:
        <select name="destination" required>
            <option value="">-- Select Destination --</option>
            <% for (String destination : destinations) { %>
            <option value="<%= destination %>" <%= destination.equals(selectedDestination) ? "selected" : "" %>>
                <%= destination %>
            </option>
            <% } %>
        </select>

        Travel Type:
        <select name="travelType" required>
            <option value="">-- Select Type --</option>
            <option value="City" <%= "City".equals(selectedTravelType) ? "selected" : "" %>>City</option>
            <option value="InterCity" <%= "InterCity".equals(selectedTravelType) ? "selected" : "" %>>Inter-City
            </option>
        </select>

        Date:
        <input type="date" name="date" value="<%= selectedDate %>" required>

        <input type="hidden" name="operation" value="search">
        <input type="submit" value="Search">
    </form>
</div>


<% if (searchResults != null) { %>
<div class="section">
    <h3>Search Results</h3>

    <% if (!searchResults.isEmpty()) { %>
    <table>
        <tr>
            <th>Origin</th>
            <th>Destination</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Available Seats</th>
            <th>Estimated Fare</th>
            <th>Actions</th>
        </tr>

        <% for (Trip trip : searchResults) {
            String tripId = String.valueOf(trip.getId());
            boolean showFare = tripId.equals(estimatedTripId) && estimatedFare != null;
        %>
        <tr>
            <td><%= trip.getOrigin() %>
            </td>
            <td><%= trip.getDestination() %>
            </td>
            <td><%= trip.getDepartureTime() %>
            </td>
            <td><%= trip.getDepartureTime().plusHours(2) %>
            </td>
            <td><%= trip.getAvailableSeats() %>
            </td>
            <td>
                <% if (showFare) { %>
                $<%= estimatedFare %>
                <% } else { %>
                --
                <% } %>
            </td>
            <td>

                <form method="get" action="purchaseTicket.jsp" class="inline">
                    <input type="hidden" name="tripId" value="<%= trip.getId() %>">
                    <input type="submit" value="Purchase">
                </form>


                <form method="post" action="FareEstimationServlet" class="inline">
                    <input type="hidden" name="tripId" value="<%= trip.getId() %>">
                    <input type="hidden" name="travelType" value="<%= trip.getTravelType() %>">


                    <input type="hidden" name="origin" value="<%= selectedOrigin %>">
                    <input type="hidden" name="destination" value="<%= selectedDestination %>">
                    <input type="hidden" name="searchTravelType" value="<%= selectedTravelType %>">
                    <input type="hidden" name="date" value="<%= selectedDate %>">

                    <select name="ticketType" required>
                        <option value="OneTrip">One Trip</option>
                        <option value="Daily">Daily</option>
                        <option value="Weekly">Weekly</option>
                        <option value="Monthly">Monthly</option>
                    </select>

                    <input type="submit" value="Estimate">
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <% } else { %>
    <p>No trips found.</p>
    <% } %>
</div>
<% } %>

</body>
</html>