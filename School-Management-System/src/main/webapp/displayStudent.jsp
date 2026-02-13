<%@ page import="com.wipro.school.bean.SchoolBean"%>

<!DOCTYPE html>
<html>
<head>
<title>Student Record</title>
</head>
<body>

	<h2>Student Details</h2>

	<%
	SchoolBean s = (SchoolBean) request.getAttribute("student");

	if (s != null) {
	%>
	<p>
		<strong>Record ID:</strong>
		<%=s.getRecordId()%></p>
	<p>
		<strong>Student Name:</strong>
		<%=s.getStudentName()%></p>
	<p>
		<strong>Class:</strong>
		<%=s.getClassName()%></p>
	<p>
		<strong>Section:</strong>
		<%=s.getSection()%></p>
	<p>
		<strong>Admission Date:</strong>
		<%=s.getAdmissionDate()%></p>
	<p>
		<strong>Age:</strong>
		<%=s.getAge()%></p>
	<p>
		<strong>Remarks:</strong>
		<%=s.getRemarks()%></p>

	<%
	} else {
	%>

	<h3>No matching records exists! Please try again!</h3>

	<%
	}
	%>

	<br />
	<a href="menu.html">Go Back to Menu</a>

</body>
</html>
