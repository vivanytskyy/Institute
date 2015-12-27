package com.gmail.ivanytskyy.vitaliy.service;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
/*
 * Task #3/2015/12/14 (web project #3)
 * LecturerServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("lecturerService")
public class LecturerServiceImpl implements LecturerService {
	@Autowired
	private LecturerDao lecturerDao;
	private static final Logger log = Logger.getLogger(LecturerServiceImpl.class);
	@Override
	public Lecturer create(String lecturerName){
		log.info("Creating lecturer with lecturerName = " + lecturerName);
		Lecturer lecturer = lecturerDao.create(lecturerName);
		log.trace("Lecturer was created");
		return lecturer;
	}
	@Override
	public Lecturer findById(long lecturerId){
		log.info("Getting lecturer by lecturerId = " + lecturerId);
		Lecturer lecturer = lecturerDao.findById(lecturerId);
		log.trace("Lecturer was gotten");
		return lecturer;
	}
	@Override
	public List<Lecturer> findByName(String lecturerName){
		log.info("Getting lecturers by lecturerName = " + lecturerName);
		List<Lecturer> lecturers = lecturerDao.findByName(lecturerName);
		log.trace("Lecturers were gotten");
		return lecturers;
	}
	@Override
	public List<Lecturer> findAll(){
		log.info("Getting all lecturers");
		List<Lecturer> lecturers = lecturerDao.findAll();
		log.trace("Lecturers were gotten");
		return lecturers;
	}	
	@Override
	public boolean isExists(long lecturerId) {
		log.info("Checking if exists the lecturer with lecturerId = " + lecturerId);
		boolean result = lecturerDao.isExists(lecturerId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long lecturerId, String newLecturerName) {
		log.info("Updating lecturer  with lecturerId = " + lecturerId 
				+ " by new lecturerName = " + newLecturerName);
		lecturerDao.update(lecturerId, newLecturerName);
		log.trace("Lecturer was updated");
	}
	@Override
	public void deleteById(long lecturerId) {
		log.info("Remove lecturer by lecturerId = " + lecturerId);
		lecturerDao.deleteById(lecturerId);
		log.trace("Lecturer with lecturerId = " + lecturerId + " was removed");
	}
}