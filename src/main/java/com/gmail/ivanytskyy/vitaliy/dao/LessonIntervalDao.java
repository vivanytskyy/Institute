package com.gmail.ivanytskyy.vitaliy.dao;

import java.util.List;

import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;

public interface LessonIntervalDao {

	public abstract LessonInterval create(String lessonStart, String lessonFinish);

	public abstract LessonInterval findById(long lessonIntervalId);

	public abstract List<LessonInterval> findByLessonStart(String lessonStart);

	public abstract List<LessonInterval> findByLessonFinish(String lessonFinish);

	public abstract List<LessonInterval> findAll();
	
	public abstract boolean isExists(long lessonIntervalId);

	public abstract void update(long lessonIntervalId, String newLessonStart,
			String newLessonFinish);

	public abstract void deleteById(long lessonIntervalId);

	public abstract void deleteAll();

}