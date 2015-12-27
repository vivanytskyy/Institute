package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleDao;
/*
 * Task #3/2015/12/14 (web project #3)
 * Institute class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Component("institute")
public class Institute {
	private String instituteName;
	private List<Schedule> schedules;
	@Autowired
	private ScheduleDao scheduleDao;
	private static final Logger log = Logger.getLogger(Institute.class.getName());
	public Institute(){
		this("Java EE institute");
	}
	public Institute(String name){
		this.instituteName = name;
		schedules = new LinkedList<Schedule>();
	}
	public String getInstituteName() {
		return instituteName;
	}
	/**
	 * obtainSchedule method
	 * @param group is Group object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Group group, Calendar someDate){		
		log.info("Obtaining schedule as string for group with "
				+ "groupId = " + group.getGroupId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		StringBuffer sb = new StringBuffer();		
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAll();
			log.trace("All schedules were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get schedules", e);
		}		
		for (Schedule schedule : schedules) {
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(group);
				} catch (DataAccessException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();		
	}
	/**
	 * obtainSchedule method
	 * @param classroom is Classroom object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Classroom classroom, Calendar someDate){
		log.info("Obtaining schedule as string for classroom with "
				+ "classroomId = " + classroom.getClassroomId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		StringBuffer sb = new StringBuffer();
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAll();
			log.trace("All schedules were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get schedules", e);
		}
		for (Schedule schedule : schedules) {
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(classroom);
				} catch (DataAccessException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();
	}
	/**
	 * obtainSchedule method
	 * @param lecturer is Lecturer object
	 * @param someDate is GregorianCalendar object
	 * @return result as String type variable
	 */
	public String obtainSchedule(Lecturer lecturer, Calendar someDate){
		log.info("Obtaining schedule as string for lecturer with "
				+ "lecturerId = " + lecturer.getLecturerId() 
				+ " and date = " + dateToString(someDate));
		log.info("Get ScheduleDao object");
		StringBuffer sb = new StringBuffer();
		try {
			log.trace("Get all schedules from DB");
			schedules = scheduleDao.findAll();
			log.trace("All schedules were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get schedules");
		}
		for (Schedule schedule : schedules) {			
			if(dateToString(schedule.getScheduleDate()).equals(dateToString(someDate))){
				log.trace("Found schedule with id = " + schedule.getScheduleId());
				List<String> scheduleItemsAsStringList = new LinkedList<String>();
				try {
					scheduleItemsAsStringList = schedule.obtainScheduleItemsAsStringList(lecturer);
				} catch (DataAccessException e) {
					log.error("Cannot get scheduleItems", e);
				}
				for (String item : scheduleItemsAsStringList) {
					sb.append(item);
					if(!item.equals("\n")){
						sb.append("|");
					}
				}
			}
		}
		log.trace("Returning schedule as string");
		return sb.toString();
	}
	private String dateToString(Calendar date){
		return date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
	}	
}