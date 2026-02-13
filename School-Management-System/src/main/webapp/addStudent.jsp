<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Student Record</title>
</head>
<body>

	<h2>Add Student Record</h2>

	<form action="MainServlet" method="post">

		<!-- Hidden Field -->
		<input type="hidden" name="operation" value="newRecord" /> Student
		Name: <input type="text" name="studentName"><br />
		<br /> Class: <input type="text" name="className"><br />
		<br /> Section: <input type="text" name="section"><br />
		<br /> Admission Date: <input type="date" name="admissionDate"><br />
		<br /> Age: <input type="number" name="age"><br />
		<br /> Remarks: <input type="text" name="remarks"><br />
		<br /> <input type="submit" value="Add Student">

	</form>

</body>
</html>
