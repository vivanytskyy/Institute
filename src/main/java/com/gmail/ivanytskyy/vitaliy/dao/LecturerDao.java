package com.gmail.ivanytskyy.vitaliy.dao;

import java.util.List;

import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;

public interface LecturerDao {

	public abstract Lecturer create(String lecturerName);

	public abstract Lecturer findById(long lecturerId);

	public abstract List<Lecturer> findByName(String lecturerName);

	public abstract List<Lecturer> findAll();
	
	public abstract boolean isExists(long lecturerId);

	public abstract void update(long lecturerId, String newLecturerName);

	public abstract void deleteById(long lecturerId);

	public abstract void deleteAll();

}