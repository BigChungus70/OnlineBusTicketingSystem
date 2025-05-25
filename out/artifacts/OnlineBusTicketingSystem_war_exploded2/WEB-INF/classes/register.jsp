<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Passenger Registration</title>
</head>
<body>
<h2>Register as Passenger</h2>
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
<form method="post" action="RegistrationServlet">
    <label for="username">Username:</label>
    <input type="text" name="username" required /><br>

    <label for="password">Password:</label>
    <input type="password" name="password" required /><br>
    <select name="category">
        <option value="Student">Student</option>
        <option value="Senior">Senior</option>
        <option value="Regular">Regular</option>
    </select>
    <input type="submit" value="Register" />
</form>
<form action="login.jsp">
    Already registered?
    <input type="submit" value="Login">
</form>
</body>
</html>
