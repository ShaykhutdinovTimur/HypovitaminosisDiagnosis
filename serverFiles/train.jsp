<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Train</title>
    <link href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
</head>
<body>
<div class="photo">
<h3>Here you can set result of photo to improve algorithm<h3>
</div>
<c:forEach var="photo" items="${photos}">
    <div class="container">
    <div class="photo">
    <img src="/photos/${photo}" alt="tonguePhoto" style="width:304px;height:228px;">
    <div class="photo">
    <form id="train" action="/train" method="POST">
        <input id="result" name="result" type="text" value="0"/><input type="hidden" name="photoName" value=${photo}>
        <input type="submit" value="submit result">
    </form>
    </div>
    <hr>
    </div>
    </div>
</c:forEach>

<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>

