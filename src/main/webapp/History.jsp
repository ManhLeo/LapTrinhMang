<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // Kiểm tra session và lấy thông tin userId và username
    Integer userId = (Integer) session.getAttribute("UserID");
    String username = (String) session.getAttribute("Username");

    // Nếu chưa đăng nhập, chuyển hướng đến trang Login.jsp
    if (userId == null || username == null) {
        response.sendRedirect("Login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>History</title>
    <link rel="stylesheet" href="./Main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
</head>
<body>
	<div class="taskbar">
        <div class="taskbar__item">
            <ol>
                <li><a href="./Main.jsp" style="text-decoration: none; color: black;"><i class="fa-solid fa-file-code"></i> Convert</a></li>
                <li onclick="window.location.href='HistoryController'"><i class="fa-solid fa-layer-group"></i> History</li>
            </ol>
            <div class="account">
                <i class="fa-solid fa-user-tie"></i>
                <%= username %>
                <a href="LogoutController" style="text-decoration: none; color: red; margin-left: 10px;">
			        <i class="fa-solid fa-right-from-bracket"></i> Logout
			    </a>
            </div>
        </div>
    </div>
    <div class="History" id="History" style="display: flex">
    	<h1>History</h1>
	    <table>
	        <tr>
	            <th>STT</th>
	            <th>Tên file</th>
	            <th>Thời gian</th>
	            <th>Trạng thái</th>
	            <th>Tải về</th>
	        </tr>
	        <!-- Lặp qua danh sách fileHistory -->
	        <c:forEach var="history" items="${fileHistory}" varStatus="status">
	            <tr>
	                <td>${status.index + 1}</td> <!-- Hiển thị số thứ tự -->
	                <td>${history.fileName}</td>
	                <td>${history.dateConvert}</td>
	                <td style="color: ${history.status == 'Success' ? 'green' : 'red'};">
	                    ${history.status}
	                </td>
	                <td>
	                    <!-- Kiểm tra trạng thái và hiển thị liên kết tải về nếu thành công -->
	                    <c:choose>
						    <c:when test="${history.status == 'Success'}">
						        <a href="DownloadController?inforId=${history.inforID}">
						            <i class="fa-solid fa-download"></i>
						        </a>
						    </c:when>
						    <c:otherwise>
						        <i class="fa-solid fa-circle-xmark"></i>
						    </c:otherwise>
						</c:choose>

	                </td>
	            </tr>
	        </c:forEach>
	    </table>
    </div>
</body>
</html>
