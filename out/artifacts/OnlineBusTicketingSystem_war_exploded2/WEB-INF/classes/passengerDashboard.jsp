<%@ page import="utility.SessionManager" %>
<%@ page import="utility.SessionExpiredException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Passenger Dashboard - BusTicket Pro</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 40px 20px;
        }

        .header {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            padding: 20px 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 30px;
        }

        .logo {
            font-size: 1.8rem;
            font-weight: bold;
            color: #4a5568;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .logo::before {
            content: "🚌";
            font-size: 1.5rem;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .welcome-user {
            color: #4a5568;
            font-weight: 500;
        }

        .logout-btn {
            background: linear-gradient(135deg, #e53e3e, #c53030);
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 15px rgba(229, 62, 62, 0.3);
        }

        .dashboard-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 50px 40px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 800px;
            margin: 0 auto;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .dashboard-title {
            font-size: 2.2rem;
            font-weight: bold;
            color: #4a5568;
            margin-bottom: 15px;
        }

        .dashboard-subtitle {
            color: #718096;
            font-size: 1.1rem;
            margin-bottom: 50px;
            font-weight: 300;
        }

        .action-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
            margin-bottom: 40px;
        }

        .action-card {
            background: rgba(255, 255, 255, 0.8);
            border-radius: 15px;
            padding: 40px 30px;
            text-decoration: none;
            transition: all 0.3s ease;
            border: 2px solid transparent;
            position: relative;
            overflow: hidden;
        }

        .action-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
            transition: left 0.5s;
        }

        .action-card:hover::before {
            left: 100%;
        }

        .action-card:hover {
            transform: translateY(-5px);
            border-color: rgba(255, 255, 255, 0.5);
        }

        .history-card {
            background: linear-gradient(135deg, #9f7aea, #805ad5);
            color: white;
            box-shadow: 0 8px 25px rgba(159, 122, 234, 0.3);
        }

        .history-card:hover {
            box-shadow: 0 15px 35px rgba(159, 122, 234, 0.4);
        }

        .search-card {
            background: linear-gradient(135deg, #4299e1, #3182ce);
            color: white;
            box-shadow: 0 8px 25px rgba(66, 153, 225, 0.3);
        }

        .search-card:hover {
            box-shadow: 0 15px 35px rgba(66, 153, 225, 0.4);
        }

        .card-icon {
            font-size: 3rem;
            margin-bottom: 20px;
            display: block;
        }

        .card-title {
            font-size: 1.4rem;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .card-description {
            font-size: 1rem;
            opacity: 0.9;
            line-height: 1.5;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .dashboard-container {
            animation: fadeInUp 0.8s ease-out;
        }

        .action-card {
            animation: fadeInUp 0.8s ease-out;
        }

        .action-card:nth-child(1) {
            animation-delay: 0.1s;
        }

        .action-card:nth-child(2) {
            animation-delay: 0.2s;
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
<%
    try {
        SessionManager.getLoggedInUserOrThrow(request);
    } catch (SessionExpiredException e) {
        request.setAttribute("errorMessage", "Please login first!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<div class="header">
    <div class="logo">BusTicket Pro</div>
    <div class="user-info">
        <span class="welcome-user">Welcome, <%= session.getAttribute("username") %></span>
        <a href="logout" class="logout-btn">Logout</a>
    </div>
</div>

<div class="dashboard-container">
    <div class="dashboard-title">Passenger Dashboard</div>
    <div class="dashboard-subtitle">Manage your bookings and find new trips</div>

    <div class="action-grid">
        <a href="HistoryServlet" class="action-card history-card">
            <span class="card-icon">📋</span>
            <div class="card-title">Purchase History</div>
            <div class="card-description">View all your past bookings, tickets, and travel history</div>
        </a>

        <a href="SearchTripServlet?operation=populate" class="action-card search-card">
            <span class="card-icon">🔍</span>
            <div class="card-title">Search Trips</div>
            <div class="card-description">Find and book new bus trips to your destination</div>
        </a>
    </div>


</div>
</body>
</html>