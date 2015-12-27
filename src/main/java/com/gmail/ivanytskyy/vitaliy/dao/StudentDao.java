package com.gmail.ivanytskyy.vitaliy.dao;

import java.util.List;

import com.gmail.ivanytskyy.vitaliy.domain.Student;

public interface StudentDao {

	public abstract Student create(String studentName, long groupId);

	public abstract Student findById(long studentId);

	public abstract List<Student> findByGroupId(long groupId);

	public abstract List<Student> findByName(String studentName);

	public abstract List<Student> findAll();

	public abstract void moveToAnotherGroup(long studentId, long groupId);
	
	public abstract boolean isExists(long studentId);

	public abstract void update(long studentId, String newStudentName);

	public abstract void deleteById(long studentId);

	public abstract void deleteAll();

}