package com.gmail.ivanytskyy.vitaliy.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleItemDao;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
/*
 * Task #3/2015/12/14 (web project #3)
 * ScheduleItemServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("scheduleItemService")
public class ScheduleItemServiceImpl implements ScheduleItemService {
	@Autowired
	private ScheduleItemDao scheduleItemDao;
	private static final Logger log = Logger.getLogger(ScheduleItemServiceImpl.class);
	@Override
	public ScheduleItem create(
			long groupId,
			long lecturerId,
			long classroomId,
			long subjectId,
			long lessonIntervalId,
			long scheduleId){
		log.info("Creating new scheduleItem with"
				+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
				+ groupId + "/" 
				+ lecturerId + "/" 
				+ classroomId + "/" 
				+ subjectId + "/" 
				+ lessonIntervalId + "/" 
				+ scheduleId);
		ScheduleItem scheduleItem = scheduleItemDao.create(
					groupId,
					lecturerId,
					classroomId,
					subjectId,
					lessonIntervalId,
					scheduleId);
		log.trace("scheduleItem was created");
		return scheduleItem;
	}
	@Override
	public ScheduleItem findById(long scheduleItemId){
		log.info("Getting scheduleItem by scheduleItemId = " + scheduleItemId);
		ScheduleItem scheduleItem = scheduleItemDao.findById(scheduleItemId);
		log.trace("ScheduleItem was gotten");
		return scheduleItem;
	}
	@Override
	public List<ScheduleItem> findByScheduleId(long scheduleId) {
		log.info("Getting scheduleItems by scheduleId = " + scheduleId);
		List<ScheduleItem> scheduleItems = scheduleItemDao.findByScheduleId(scheduleId);
		log.trace("scheduleItems were gotten");
		return scheduleItems;
	}
	@Override
	public List<ScheduleItem> findAll(){
		log.info("Getting all scheduleItems");
		List<ScheduleItem> scheduleItems = scheduleItemDao.findAll();
		log.trace("ScheduleItems were gotten");
		return scheduleItems;
	}
	@Override
	public void moveToAnotherSchedule(long scheduleItemId, long anotherScheduleId){
		log.info("Moving scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " to new schedule with scheduleId = " + anotherScheduleId);
		scheduleItemDao.moveToAnotherSchedule(scheduleItemId, anotherScheduleId);
		log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId 
					+ " was moved to schedule with scheduleId = " + anotherScheduleId);
	}	
	@Override
	public boolean isExists(long scheduleItemId) {
		log.info("Checking if exists the scheduleItem with scheduleItemId = " + scheduleItemId);
		boolean result = scheduleItemDao.isExists(scheduleItemId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long scheduleItemId,
			long newGroupId, long newLecturerId, long newClassroomId,
			long newSubjectId, long newLessonIntervalId, long newScheduleId){
		log.info("Updating scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " by new groupId/lecturerId/classroomId/subjectId/lessonIntervalId = " 
				+ newGroupId + "/" 
				+ newLecturerId + "/" 
				+ newClassroomId + "/" 
				+ newSubjectId + "/" 
				+ newLessonIntervalId + "/" 
				+ newScheduleId);
		scheduleItemDao.update(scheduleItemId, newGroupId, newLecturerId,
				newClassroomId, newSubjectId, newLessonIntervalId,
				newScheduleId);
		log.trace("ScheduleItem was updated");
	}
	@Override
	public void deleteById(long scheduleItemId){
		log.info("Remove scheduleItem by scheduleItemId = " + scheduleItemId);
		scheduleItemDao.deleteById(scheduleItemId);
		log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId + " was removed");
	}
}