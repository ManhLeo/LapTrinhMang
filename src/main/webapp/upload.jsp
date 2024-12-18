<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload PDF</title>
</head>
<body>
    <h1>Upload PDF to Convert to DOCX</h1>
    
    <!-- Form để upload file PDF và cung cấp đường dẫn output -->
    <form action="PdfController" method="post" enctype="multipart/form-data">
        <label for="pdfFile">Select PDF File:</label>
        <input type="file" id="pdfFile" name="pdfFile" accept=".pdf" required><br><br>
        
        <label for="outputPath">Output Path:</label>
        <input type="text" id="outputPath" name="outputPath" required><br><br>

        <input type="submit" value="Convert and Merge">
    </form>
</body>
</html>
