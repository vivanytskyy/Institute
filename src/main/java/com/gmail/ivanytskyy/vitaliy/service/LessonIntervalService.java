package com.gmail.ivanytskyy.vitaliy.service;

import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;

public interface LessonIntervalService {

	public abstract LessonInterval create(String lessonStart, String lessonFinish);

	public abstract LessonInterval findById(long lessonIntervalId);

	public abstract List<LessonInterval> findAll();
	
	public abstract boolean isExists(long lessonIntervalId);

	public abstract void update(long lessonIntervalId, String newLessonStart,
			String newLessonFinish);

	public abstract void deleteById(long lessonIntervalId);

}