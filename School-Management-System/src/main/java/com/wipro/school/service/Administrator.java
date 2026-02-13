package com.wipro.school.service;

import java.sql.Date;

import com.wipro.school.bean.SchoolBean;
import com.wipro.school.dao.SchoolDAO;
import com.wipro.school.util.InvalidInputException;

public class Administrator {

	SchoolDAO dao = new SchoolDAO();

	public String addRecord(SchoolBean schoolBean) {

		try {
			if (schoolBean == null || schoolBean.getStudentName() == null || schoolBean.getAdmissionDate() == null) {

				throw new InvalidInputException();
			}

			String studentName = schoolBean.getStudentName();
			Date admissionDate = schoolBean.getAdmissionDate();
			if (studentName.length() < 2) {
				return "INVALID STUDENT NAME";
			}
			if (admissionDate.after(new Date(System.currentTimeMillis()))) {
				return "INVALID DATE";
			}
			if (dao.recordExists(studentName, admissionDate)) {
				return "ALREADY EXISTS";
			}
			String recordId = dao.generateRecordID(studentName, admissionDate);
			schoolBean.setRecordId(recordId);

			return dao.createRecord(schoolBean);

		} catch (InvalidInputException e) {
			return "INVALID INPUT";
		}
	}

	public SchoolBean viewRecord(String studentName, Date admissionDate) {

		return dao.fetchRecord(studentName, admissionDate);
	}
}
