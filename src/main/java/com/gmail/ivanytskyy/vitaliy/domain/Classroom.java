package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/11 (web project #3)
 * Classroom class
 * @version 1.01 2015.12.11
 * @author Vitaliy Ivanytskyy
 */
public class Classroom {	
	private long classroomId;
	private String classroomName;
	public void setClassroomId(long classroomId) {
		this.classroomId = classroomId;
	}
	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	public long getClassroomId() {
		return classroomId;
	}
	public String getClassroomName() {
		return classroomName;
	}
}