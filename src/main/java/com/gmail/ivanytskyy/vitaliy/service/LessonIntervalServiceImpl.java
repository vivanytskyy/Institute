package com.gmail.ivanytskyy.vitaliy.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.LessonIntervalDao;
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
/*
 * Task #3/2015/12/14 (web project #3)
 * LessonIntervalServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("lessonIntervalService")
public class LessonIntervalServiceImpl implements LessonIntervalService {
	@Autowired
	private LessonIntervalDao lessonIntervalDao;
	private static final Logger log = Logger.getLogger(LessonIntervalServiceImpl.class);
	@Override
	public LessonInterval create(String lessonStart, String lessonFinish){
		log.info("Creating lessonInterval with lessonStart = " + lessonStart 
				+ " and lessonFinish = " + lessonFinish);
		LessonInterval lessonInterval = lessonIntervalDao.create(lessonStart, lessonFinish);
		log.trace("LessonInterval was created");
		return lessonInterval;
	}
	@Override
	public LessonInterval findById(long lessonIntervalId){
		log.info("Getting lessonInterval by lessonIntervalId = " + lessonIntervalId);
		LessonInterval lessonInterval = lessonIntervalDao.findById(lessonIntervalId);
		log.trace("LessonInterval was gotten");
		return lessonInterval;
	}
	@Override
	public List<LessonInterval> findAll(){
		log.info("Getting all lessonIntervals");
		List<LessonInterval> lessonIntervals = lessonIntervalDao.findAll();
		log.trace("LessonIntervals were gotten");
		return lessonIntervals;
	}	
	@Override
	public boolean isExists(long lessonIntervalId) {
		log.info("Checking if exists the lessonInterval with lessonIntervalId = " + lessonIntervalId);
		boolean result = lessonIntervalDao.isExists(lessonIntervalId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(
			long lessonIntervalId,
			String newLessonStart,
			String newLessonFinish){
		log.info("Updating lessonInterval with lessonIntervalId = " + lessonIntervalId);
		lessonIntervalDao.update(lessonIntervalId, newLessonStart, newLessonFinish);
		log.trace("LessonInterval was updated");
	}
	@Override
	public void deleteById(long lessonIntervalId){
		log.info("Remove lessonInterval by lessonIntervalId = " + lessonIntervalId);
		lessonIntervalDao.deleteById(lessonIntervalId);
		log.trace("LessonInterval with lessonIntervalId = " + lessonIntervalId + " was removed");
	}	
}