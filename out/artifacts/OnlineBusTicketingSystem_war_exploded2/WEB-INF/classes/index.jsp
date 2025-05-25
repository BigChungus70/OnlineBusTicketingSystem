<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Bus Ticketing System</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .dashboard-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 60px 40px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 500px;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .logo {
            font-size: 2.5rem;
            font-weight: bold;
            color: #4a5568;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .logo::before {
            content: "🚌";
            font-size: 2rem;
        }

        .subtitle {
            color: #718096;
            font-size: 1.1rem;
            margin-bottom: 40px;
            font-weight: 300;
        }

        .nav-links {
            display: flex;
            flex-direction: column;
            gap: 20px;
            align-items: center;
        }

        .nav-link {
            display: inline-block;
            padding: 16px 32px;
            text-decoration: none;
            border-radius: 12px;
            font-weight: 600;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            width: 250px;
            position: relative;
            overflow: hidden;
        }

        .nav-link::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
            transition: left 0.5s;
        }

        .nav-link:hover::before {
            left: 100%;
        }

        .register-link {
            background: linear-gradient(135deg, #48bb78, #38a169);
            color: white;
            box-shadow: 0 4px 15px rgba(72, 187, 120, 0.3);
        }

        .register-link:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(72, 187, 120, 0.4);
        }

        .login-link {
            background: linear-gradient(135deg, #4299e1, #3182ce);
            color: white;
            box-shadow: 0 4px 15px rgba(66, 153, 225, 0.3);
        }

        .login-link:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(66, 153, 225, 0.4);
        }

        .divider {
            margin: 30px 0;
            position: relative;
            color: #a0aec0;
            font-size: 0.9rem;
        }

        .divider::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(to right, transparent, #e2e8f0, transparent);
        }

        .divider span {
            background: rgba(255, 255, 255, 0.95);
            padding: 0 20px;
            position: relative;
            z-index: 1;
        }

        .welcome-text {
            color: #4a5568;
            font-size: 1.2rem;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .features {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 1px solid rgba(226, 232, 240, 0.5);
        }

        .feature-list {
            display: flex;
            justify-content: space-around;
            gap: 20px;
            margin-top: 20px;
        }

        .feature-item {
            color: #718096;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .feature-item::before {
            content: "✓";
            color: #48bb78;
            font-weight: bold;
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

        .nav-link {
            animation: fadeInUp 0.8s ease-out;
        }

        .nav-link:nth-child(1) { animation-delay: 0.1s; }
        .nav-link:nth-child(2) { animation-delay: 0.2s; }
    </style>
</head>
<body>
<div class="dashboard-container">
    <div class="logo">BusTicket Pro</div>
    <div class="subtitle">Your Journey Starts Here</div>

    <div class="welcome-text">
        Welcome to our online bus ticketing platform. Book your tickets easily and travel comfortably.
    </div>

    <div class="nav-links">
        <a href="register.jsp" class="nav-link register-link">Create New Account</a>

        <div class="divider">
            <span>or</span>
        </div>

        <a href="login.jsp" class="nav-link login-link">Sign In to Your Account</a>
    </div>

    <div class="features">
        <div class="feature-list">
            <div class="feature-item">Easy Booking</div>
            <div class="feature-item">Secure Payment</div>
            <div class="feature-item">24/7 Support</div>
        </div>
    </div>
</div>
</body>
</html>