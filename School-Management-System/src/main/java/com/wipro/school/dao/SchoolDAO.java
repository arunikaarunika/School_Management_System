package com.wipro.school.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import com.wipro.school.bean.SchoolBean;
import com.wipro.school.util.DBUtil;

public class SchoolDAO {
	public String createRecord(SchoolBean schoolBean) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBUtil.getDBConnection();
			String recordId = generateRecordID(schoolBean.getStudentName(), schoolBean.getAdmissionDate());

			schoolBean.setRecordId(recordId);

			String query = "INSERT INTO SCHOOL_TB "
					+ "(RECORDID, STUDENTNAME, CLASS, SECTION, ADMISSION_DATE, AGE, REMARKS) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(query);

			ps.setString(1, schoolBean.getRecordId());
			ps.setString(2, schoolBean.getStudentName());
			ps.setString(3, schoolBean.getClassName());
			ps.setString(4, schoolBean.getSection());
			ps.setDate(5, schoolBean.getAdmissionDate());
			ps.setInt(6, schoolBean.getAge());
			ps.setString(7, schoolBean.getRemarks());

			int result = ps.executeUpdate();

			if (result > 0)
				return schoolBean.getRecordId();
			else
				return "FAIL";

		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}

	public SchoolBean fetchRecord(String studentName, Date admissionDate) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getDBConnection();

			String query = "SELECT * FROM SCHOOL_TB " + "WHERE STUDENTNAME=? AND ADMISSION_DATE=?";

			ps = con.prepareStatement(query);
			ps.setString(1, studentName);
			ps.setDate(2, admissionDate);

			rs = ps.executeQuery();

			if (rs.next()) {

				SchoolBean bean = new SchoolBean();

				bean.setRecordId(rs.getString("RECORDID"));
				bean.setStudentName(rs.getString("STUDENTNAME"));
				bean.setClassName(rs.getString("CLASS"));
				bean.setSection(rs.getString("SECTION"));
				bean.setAdmissionDate(rs.getDate("ADMISSION_DATE"));
				bean.setAge(rs.getInt("AGE"));
				bean.setRemarks(rs.getString("REMARKS"));

				return bean;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String generateRecordID(String studentName, Date admissionDate) {

		String recordId = "";

		try {
			Connection con = DBUtil.getDBConnection();

			// 1️⃣ Get sequence value from Oracle
			PreparedStatement ps = con.prepareStatement("SELECT SCHOOL_SEQ.NEXTVAL FROM DUAL");

			ResultSet rs = ps.executeQuery();

			int seqValue = 0;

			if (rs.next()) {
				seqValue = rs.getInt(1);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String datePart = format.format(admissionDate);
			String namePart = studentName.substring(0, 2).toUpperCase();
			String sequence = String.format("%02d", seqValue);
			recordId = datePart + namePart + sequence;

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return recordId;
	}

	public boolean recordExists(String studentName, Date admissionDate) {

		SchoolBean bean = fetchRecord(studentName, admissionDate);

		return bean != null;
	}
}
