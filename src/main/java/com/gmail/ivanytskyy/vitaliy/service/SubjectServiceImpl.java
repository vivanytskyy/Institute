package com.gmail.ivanytskyy.vitaliy.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.SubjectDao;
import com.gmail.ivanytskyy.vitaliy.domain.Subject;
/*
 * Task #3/2015/12/14 (web project #3)
 * SubjectServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectDao subjectDao;
	private static final Logger log = Logger.getLogger(SubjectServiceImpl.class);
	@Override
	public Subject create(String subjectName){
		log.info("Creating subject with subjectName = " + subjectName);
		Subject subject = subjectDao.create(subjectName);
		log.trace("Subject with subjectName = " + subjectName + " was created");
		return subject;
	}
	@Override
	public Subject findById(long subjectId){
		log.info("Getting subject by subjectId = " + subjectId);
		Subject subject = subjectDao.findById(subjectId);
		log.trace("Subject was gotten");
		return subject;
	}
	@Override
	public List<Subject> findByName(String subjectName){
		log.info("Getting subjects by subjectName = " + subjectName);
		List<Subject> subjects = subjectDao.findByName(subjectName);
		log.trace("Subjects were gotten");
		return subjects;
	}
	@Override
	public List<Subject> findAll(){
		log.info("Getting all subjects");
		List<Subject> subjects = subjectDao.findAll();
		log.trace("Subjects were gotten");
		return subjects;
	}	
	@Override
	public boolean isExists(long subjectId) {
		log.info("Checking if exists the subject with subjectId = " + subjectId);
		boolean result = subjectDao.isExists(subjectId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long subjectId, String newSubjectName){
		log.info("Updating subject  with subjectId = " + subjectId 
				+ " by new subjectName = " + newSubjectName);
		subjectDao.update(subjectId, newSubjectName);
		log.trace("Subject was updated");
	}
	@Override
	public void deleteById(long subjectId){
		log.info("Remove subject by subjectId = " + subjectId);
		subjectDao.deleteById(subjectId);
		log.trace("Subject with subjectId = " + subjectId + " was removed");
	}
}