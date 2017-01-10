<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>&lt;c:forEach&gt; Demo</title>
</head>
<body>

<c:forEach var="photo" items="${photos}">
    <img src="/photos/${photo}" alt="tonguePhoto" style="width:304px;height:228px;">
        <form id="train" action="/train" method="POST">
            <input id="result" name="result" type="text" value="0"/><input type="hidden" name="photoName" value=${photo}>
            <input type="submit" value="submit result">
        </form>
        <hr>
</c:forEach>

</body>
</html>

