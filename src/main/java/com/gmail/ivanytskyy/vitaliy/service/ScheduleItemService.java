package com.gmail.ivanytskyy.vitaliy.service;

import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;

public interface ScheduleItemService {

	public abstract ScheduleItem create(long groupId, long lecturerId,
			long classroomId, long subjectId, long lessonIntervalId,
			long scheduleId);

	public abstract ScheduleItem findById(long scheduleItemId);

	public abstract List<ScheduleItem> findByScheduleId(long scheduleId);

	public abstract List<ScheduleItem> findAll();

	public abstract void moveToAnotherSchedule(long scheduleItemId,
			long anotherScheduleId);
	
	public abstract boolean isExists(long scheduleItemId);

	public abstract void update(long scheduleItemId, long newGroupId,
			long newLecturerId, long newClassroomId, long newSubjectId,
			long newLessonIntervalId, long newScheduleId);

	public abstract void deleteById(long scheduleItemId);

}