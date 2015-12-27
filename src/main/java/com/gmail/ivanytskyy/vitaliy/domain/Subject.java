package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/11 (pet web project #3)
 * Subject class
 * @version 1.01 2015.12.11
 * @author Vitaliy Ivanytskyy
 */
public class Subject {
	private long subjectId;
	private String subjectName;
	public long getSubjectId() {
		return subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}