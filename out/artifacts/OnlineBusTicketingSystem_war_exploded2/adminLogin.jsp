<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin login</title>
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
<form method="post" action="AdminSessionServlet">
  <label for="username">Username:</label>
  <input type="text" name="username" required /><br>

  <label for="password">Password:</label>
  <input type="password" name="password" required /><br>
  <input type="submit" value="Login" />
</form>

</body>
</html>
