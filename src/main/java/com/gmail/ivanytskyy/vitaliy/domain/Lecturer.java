package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/11 (web project #3)
 * Lecturer class
 * @version 1.01 2015.12.11
 * @author Vitaliy Ivanytskyy
 */
public class Lecturer {
	private long lecturerId;
	private String lecturerName;
	public long getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(long lecturerId) {
		this.lecturerId = lecturerId;
	}
	public String getLecturerName() {
		return lecturerName;
	}
	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}
}