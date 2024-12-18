<% 
    Integer userId = (Integer) session.getAttribute("UserID");
    String username = (String) session.getAttribute("Username");

    if (userId == null || username == null) {
        response.sendRedirect("Login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main</title>
    <link rel="stylesheet" href="./Main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            // AJAX form submission
            $('#convertForm').on('submit', function (e) {
                e.preventDefault();

                var formData = new FormData(this);

                $.ajax({
                    url: 'PdfController',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (response) {
                        alert('Conversion Completed!');
                        // Hiển thị trạng thái hoặc thông báo
                    },
                    error: function (xhr, status, error) {
                        alert('Error during conversion: ' + error);
                    }
                });
            });
        });
    </script>
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

    <!-- Chức năng chuyển đổi PDF thành DOCX -->
    <div class="Converter" id="Converter">
        <h1>PDF to Docx Converter</h1>
        <div class="Converter_item">
            <form id="convertForm" action="PdfController" method="post" enctype="multipart/form-data">
                <div class="Content">
                    <i class="fa-solid fa-file-pdf"></i>
                    <input type="file" id="pdfFiles" name="pdfFile" accept=".pdf" multiple required>
                    <button type="submit">Convert and Merge</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
