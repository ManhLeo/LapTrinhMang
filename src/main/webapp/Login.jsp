<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300..800;1,300..800&display=swap" rel="stylesheet">
<link rel="stylesheet" href="./Login.css">
</head>
<body>
	<div class="box">
        <form action="CheckLoginServlet" method = "POST">
            <h1>Login</h1>
            Username
            <input type="text" id="username" name="Username" required>
            Password
            <input type="password" id="password" name="Password" required>
            <div class="button">
                <input type="submit" value="Login">
                <input type="reset" value="Reset">
            </div>
        </form>
        <div class="content">
            <h1>Welcome to login</h1>
            <p>Don't have an account</p>
            <button  onclick="window.location.href='Register.jsp'">Sign Up</button>
        </div>
    </div>
</body>
</html>