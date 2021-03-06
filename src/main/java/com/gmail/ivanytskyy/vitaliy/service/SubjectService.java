package com.gmail.ivanytskyy.vitaliy.service;

import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;

public interface SubjectService {

	public abstract Subject create(String subjectName);

	public abstract Subject findById(long subjectId);

	public abstract List<Subject> findByName(String subjectName);

	public abstract List<Subject> findAll();
	
	public abstract boolean isExists(long subjectId);

	public abstract void update(long subjectId, String newSubjectName);

	public abstract void deleteById(long subjectId);

}