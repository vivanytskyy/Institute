package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/11 (web project #3)
 * LessonInterval class
 * @version 1.01 2015.12.11
 * @author Vitaliy Ivanytskyy
 */
public class LessonInterval {
	private long lessonIntervalId;
	private String lessonStart;
	private String lessonFinish;
	public long getLessonIntervalId() {
		return lessonIntervalId;
	}
	public void setLessonIntervalId(long lessonIntervalId) {
		this.lessonIntervalId = lessonIntervalId;
	}
	public String getLessonStart() {
		return lessonStart;
	}
	public void setLessonStart(String lessonStart) {
		this.lessonStart = lessonStart;
	}
	public String getLessonFinish() {
		return lessonFinish;
	}
	public void setLessonFinish(String lessonFinish) {
		this.lessonFinish = lessonFinish;
	}
}