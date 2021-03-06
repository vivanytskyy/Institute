package com.gmail.ivanytskyy.vitaliy.service;

import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;

public interface ClassroomService {

	public abstract Classroom create(String classroomName);

	public abstract Classroom findById(long classroomId);

	public abstract List<Classroom> findByName(String classroomName);

	public abstract List<Classroom> findAll();
	
	public abstract boolean isExists(long classroomId);

	public abstract void update(long classroomId, String newClassroomName);

	public abstract void deleteById(long classroomId);

}