<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MyPictures - Error</title>
    <link rel="stylesheet" type="text/css" href="/jsp/error/errorPage.css">
    <link rel="icon" type="image/png" href="/images/icon.png">
    <% String errorText = (String) request.getAttribute("errorMessage");%>
    <%
        if (errorText == null || errorText.isEmpty())
            errorText = "Error occured.";
    %>

</head>
<body>
<div class="wrapper">
    <nav class="header">
        <a href="${pageContext.request.contextPath}/home">MyPictures</a>
    </nav>
    <h1><%=errorText%>
    </h1>
</div>

</body>
</html>
