package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/14 (web project #3)
 * ScheduleItem class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
public class ScheduleItem {
	private long scheduleItemId;
	private long groupId;	
	private long lecturerId;
	private long classroomId;
	private long subjectId;
	private long lessonIntervalId;
	private long scheduleId;
	public long getScheduleItemId() {
		return scheduleItemId;
	}
	public void setScheduleItemId(long scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}	
	public long getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(long lecturerId) {
		this.lecturerId = lecturerId;
	}
	public long getClassroomId() {
		return classroomId;
	}
	public void setClassroomId(long classroomId) {
		this.classroomId = classroomId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public long getLessonIntervalId() {
		return lessonIntervalId;
	}
	public void setLessonIntervalId(long lessonIntervalId) {
		this.lessonIntervalId = lessonIntervalId;
	}
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (classroomId ^ (classroomId >>> 32));
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result + (int) (lecturerId ^ (lecturerId >>> 32));
		result = prime * result
				+ (int) (lessonIntervalId ^ (lessonIntervalId >>> 32));
		result = prime * result + (int) (scheduleId ^ (scheduleId >>> 32));
		result = prime * result
				+ (int) (scheduleItemId ^ (scheduleItemId >>> 32));
		result = prime * result + (int) (subjectId ^ (subjectId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleItem other = (ScheduleItem) obj;
		if (classroomId != other.classroomId)
			return false;
		if (groupId != other.groupId)
			return false;
		if (lecturerId != other.lecturerId)
			return false;
		if (lessonIntervalId != other.lessonIntervalId)
			return false;
		if (scheduleId != other.scheduleId)
			return false;
		if (scheduleItemId != other.scheduleItemId)
			return false;
		if (subjectId != other.subjectId)
			return false;
		return true;
	}
}