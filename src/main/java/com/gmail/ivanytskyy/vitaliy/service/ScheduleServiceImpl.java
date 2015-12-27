package com.gmail.ivanytskyy.vitaliy.service;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleDao;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleItemDao;
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
/*
 * Task #3/2015/12/14 (web project #3)
 * ScheduleServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private ScheduleItemDao scheduleItemDao;
	private static final Logger log = Logger.getLogger(ScheduleServiceImpl.class.getName());
	@Override
	public Schedule create(Calendar scheduleDate){
		log.info("Creating schedule for scheduleDate = " + dateToString(scheduleDate));
		Schedule schedule = scheduleDao.create(scheduleDate);
		log.trace("Schedule was created");
		return schedule;
	}
	@Override
	public Schedule findById(long scheduleId){
		log.info("Getting schedule by scheduleId = " + scheduleId);
		Schedule schedule = scheduleDao.findById(scheduleId);
		log.trace("Schedule was gotten");
		return schedule;
	}
	@Override
	public List<Schedule> findByDate(Calendar scheduleDate){
		log.info("Getting schedule by scheduleDate = " + dateToString(scheduleDate));
		List<Schedule> schedules = scheduleDao.findByDate(scheduleDate);
		log.trace("Schedules were gotten");
		return schedules;
	}	
	@Override
	public boolean isExists(long scheduleId) {
		log.info("Checking if exists the schedule with scheduleId = " + scheduleId);
		boolean result = scheduleDao.isExists(scheduleId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public List<Schedule> findAll(){
		log.info("Getting all schedules");
		List<Schedule> schedules = scheduleDao.findAll();
		log.trace("Schedules were gotten");
		return schedules;
	}
	@Override
	public void update(long scheduleId, Calendar newScheduleDate){
		log.info("Updating schedule  with schedule = " + scheduleId 
				+ " by new scheduleDate = " + dateToString(newScheduleDate));
		scheduleDao.update(scheduleId, newScheduleDate);
		log.trace("Schedule was updated");
	}
	@Override
	public void deleteById(long scheduleId){
		log.info("Remove schedule by scheduleId = " + scheduleId);
		log.trace("Try get information about scheduleItems of schedule "
				+ "with scheduleId = " + scheduleId);
		List<ScheduleItem> scheduleItems = scheduleItemDao.findByScheduleId(scheduleId);
		log.trace("Information about scheduleItems of schedule "
				+ "with scheduleId = " + scheduleId + " was gotten");
		if(scheduleItems.isEmpty()){
			log.trace("Remove schedule with scheduleId = " + scheduleId);
			scheduleDao.deleteById(scheduleId);
			log.trace("Schedule with scheduleId = " + scheduleId + " was deleted");
		}else{
			log.trace("Can not delete schedule with the scheduleItems");
		}
	}
	private String dateToString(Calendar date){
		return date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
	}
}