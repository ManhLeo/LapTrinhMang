<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign up</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300..800;1,300..800&display=swap" rel="stylesheet">
<link rel="stylesheet" href="./Login.css">
</head>
<body>
    <div class="box">
        <form action="RegisterServlet" method="post">
            <h1>Sign Up</h1>
            Username
            <input type="text" id="username" name="username" required>
            Password
            <input type="password" id="password" name="password" required>
            <div class="button">
                <input type="submit">
                <input type="reset" value="Reset">
            </div>
        </form>
        <div class="content">
            <h1>Welcome to Sign Up</h1>
            <button onclick="window.location.href='Login.jsp'">Login</button>
        </div>
    </div>
</body>
</html>
