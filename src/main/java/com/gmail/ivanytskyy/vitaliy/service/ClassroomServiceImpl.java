package com.gmail.ivanytskyy.vitaliy.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
/*
 * Task #3/2015/12/14 (web project #3)
 * ClassroomServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("classroomService")
public class ClassroomServiceImpl implements ClassroomService {
	@Autowired
	private ClassroomDao classroomDao;
	private static final Logger log = Logger.getLogger(ClassroomServiceImpl.class);
	public void setClassroomDao(ClassroomDao classroomDao){
		this.classroomDao = classroomDao;
	}
	@Override
	public Classroom create(String classroomName){		
		log.info("Creating classroom with classroomName = " + classroomName);
		Classroom classroom = classroomDao.create(classroomName);
		log.trace("Classroom was created");
		return classroom;
	}
	@Override
	public Classroom findById(long classroomId){
		log.info("Getting classroom by classroomId = " + classroomId);		
		Classroom classroom = classroomDao.findById(classroomId);
		log.trace("Classroom was gotten");
		return classroom;
	}
	@Override
	public List<Classroom> findByName(String classroomName){
		log.info("Getting classrooms by classroomName = " + classroomName);
		List<Classroom> classrooms = classroomDao.findByName(classroomName);
		log.trace("Classrooms were gotten");
		return classrooms;
	}
	@Override
	public List<Classroom> findAll(){
		log.info("Getting all classrooms");
		List<Classroom> classrooms = classroomDao.findAll();
		log.trace("Classrooms were gotten");
		return classrooms;
	}	
	@Override
	public boolean isExists(long classroomId) {
		log.info("Checking if exists the classroom with classroomId = " + classroomId);
		boolean result = classroomDao.isExists(classroomId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long classroomId, String newClassroomName){
		log.info("Updating classroom  with classroomId = " + classroomId 
				+ " by new classroomName = " + newClassroomName);
		classroomDao.update(classroomId, newClassroomName);
		log.trace("Classroom was updated");
	}	
	@Override
	public void deleteById(long classroomId){
		log.info("Remove classroom by classroomId = " + classroomId);
		classroomDao.deleteById(classroomId);
		log.trace("Classroom with classroomId = " + classroomId + " was removed");
	}
}