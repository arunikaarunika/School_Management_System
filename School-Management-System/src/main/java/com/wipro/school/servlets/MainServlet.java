package com.wipro.school.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipro.school.bean.SchoolBean;
import com.wipro.school.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operation = request.getParameter("operation");

		if (operation == null) {
			response.sendRedirect("error.html");
			return;
		}

		if (operation.equals("newRecord")) {

			String result = addRecord(request);
			if (result != null && !result.equals("FAIL") && !result.equals("INVALID INPUT")
					&& !result.equals("INVALID STUDENT NAME") && !result.equals("INVALID DATE")
					&& !result.equals("ALREADY EXISTS")) {

				response.sendRedirect("success.html");
			} else {
				response.sendRedirect("error.html");
			}
		}

		else if (operation.equals("viewRecord")) {

			try {
				String studentName = request.getParameter("studentName");
				String dateStr = request.getParameter("admissionDate");

				Date admissionDate = Date.valueOf(dateStr);

				Administrator admin = new Administrator();
				SchoolBean bean = admin.viewRecord(studentName, admissionDate);

				if (bean != null) {
					request.setAttribute("student", bean);
					request.getRequestDispatcher("displayStudent.jsp").forward(request, response);
				} else {
					response.sendRedirect("error.html");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("error.html");
			}
		}
	}

	private String addRecord(HttpServletRequest request) {

		try {

			String studentName = request.getParameter("studentName");
			String className = request.getParameter("className");
			String section = request.getParameter("section");
			String dateStr = request.getParameter("admissionDate");
			String ageStr = request.getParameter("age");
			String remarks = request.getParameter("remarks");
			if (studentName == null || studentName.trim().isEmpty() || className == null || className.trim().isEmpty()
					|| section == null || section.trim().isEmpty() || dateStr == null || dateStr.trim().isEmpty()
					|| ageStr == null || ageStr.trim().isEmpty()) {

				return "INVALID INPUT";
			}

			Date admissionDate = Date.valueOf(dateStr);
			int age = Integer.parseInt(ageStr);

			SchoolBean bean = new SchoolBean();
			bean.setStudentName(studentName);
			bean.setClassName(className);
			bean.setSection(section);
			bean.setAdmissionDate(admissionDate);
			bean.setAge(age);
			bean.setRemarks(remarks);

			Administrator admin = new Administrator();
			return admin.addRecord(bean);

		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
}
