package com.gmail.ivanytskyy.vitaliy.dao;

import java.util.Calendar;
import java.util.List;

import com.gmail.ivanytskyy.vitaliy.domain.Schedule;

public interface ScheduleDao {

	public abstract Schedule create(Calendar scheduleDate);

	public abstract Schedule findById(long scheduleId);

	public abstract List<Schedule> findByDate(Calendar scheduleDate);

	public abstract List<Schedule> findAll();
	
	public abstract boolean isExists(long scheduleId);

	public abstract void update(long scheduleId, Calendar newScheduleDate);

	public abstract void deleteById(long scheduleId);

	public abstract void deleteAll();

}