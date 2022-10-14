<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 전체 조회</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>학번</th>
			<th>이름</th>
			<th>학과</th>
			<th>주소</th>
		</tr>
		<c:forEach var="student" items="${stdList}">
			<tr>
				<td>${student.studentNo}</td>
				<td>${student.studentName}</td>
				<td>${student.departmentName}</td>
				<td>${student.studentAddress}</td>
			</tr>
		</c:forEach>
	
	</table>

</body>
</html>