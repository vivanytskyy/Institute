package com.gmail.ivanytskyy.vitaliy.domain;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.gmail.ivanytskyy.vitaliy.dao.ClassroomDao;
import com.gmail.ivanytskyy.vitaliy.dao.DaoManager;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.LecturerDao;
import com.gmail.ivanytskyy.vitaliy.dao.LessonIntervalDao;
import com.gmail.ivanytskyy.vitaliy.dao.ScheduleItemDao;
import com.gmail.ivanytskyy.vitaliy.dao.SubjectDao;
/*
 * Task #3/2015/12/14 (web project #3)
 * Schedule class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
public class Schedule {
	private long scheduleId;
	private Calendar scheduleDate;
	private String scheduleDateAsStr;
	private ScheduleItemDao scheduleItemDao = daoManager.getScheduleItemDao();
	private GroupDao groupDao = daoManager.getGroupDao();
	private SubjectDao subjectDao = daoManager.getSubjectDao();
	private LecturerDao lecturerDao = daoManager.getLecturerDao();
	private LessonIntervalDao lessonIntervalDao = daoManager.getLessonIntervalDao();
	private ClassroomDao classroomDao = daoManager.getClassroomDao();
	private static DaoManager daoManager = DaoManager.getInstance();
	private static final Logger log = Logger.getLogger(Schedule.class.getName());
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Calendar getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Calendar scheduleDate) {
		this.scheduleDate = scheduleDate;
		this.scheduleDateAsStr = dateToString(scheduleDate);
	}	
	public String getScheduleDateAsStr() {
		return scheduleDateAsStr;
	}	
	public void setScheduleDateAsStr(String scheduleDateAsStr) {
		this.scheduleDateAsStr = scheduleDateAsStr;
	}	
	/**
	 * addScheduleItem method
	 * @param group is Group object
	 * @param lecturer is Lecturer object
	 * @param classroom is Classroom object
	 * @param subject is Subject object
	 * @param lessonInterval is LessonInterval object
	 * @return scheduleItem is ScheduleItem object
	 */
	public ScheduleItem createScheduleItem(Group group,
			Lecturer lecturer,
			Classroom classroom,
			Subject subject,
			LessonInterval lessonInterval){
		log.info("Creating new scheduleItem with"
				+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
				+ group.getGroupId() + "/" 
				+ lecturer.getLecturerId() + "/" 
				+ classroom.getClassroomId() + "/" 
				+ subject.getSubjectId() + "/" 
				+ lessonInterval.getLessonIntervalId() + "/" 
				+ scheduleId);
		log.info("Get ScheduleItemDao object");
		ScheduleItem scheduleItem = null;
		log.trace("Create scheduleItem");
		scheduleItem = scheduleItemDao.create(
				group.getGroupId(),
				lecturer.getLecturerId(),
				classroom.getClassroomId(),
				subject.getSubjectId(),
				lessonInterval.getLessonIntervalId(),
				scheduleId);
		log.trace("scheduleItem was created");
		log.trace("Returning scheduleItem");
		return scheduleItem;
	}
	/**
	 * obtainScheduleItemsAsStringList method
	 * @param group is Group object
	 * @return result as List<String> type variable
	 */
	public List<String> obtainScheduleItemsAsStringList(Group group){
		log.info("Obtaining scheduleItems as string list for group with"
				+ " groupId = " + group.getGroupId());
		List<String> scheduleItemAsStringList  = new LinkedList<String>();
		List<ScheduleItem> scheduleItems = scheduleItemDao.findByScheduleId(scheduleId);
		for (ScheduleItem scheduleItem : scheduleItems) {
			if(scheduleItem.getGroupId() == group.getGroupId()){
				log.trace("Found scheduleItem with id = " + scheduleItem.getScheduleItemId());
				scheduleItemAsStringList.add(dateToString(scheduleDate));
				log.trace("Get name of group");
				scheduleItemAsStringList.add(groupDao.findById(scheduleItem.getGroupId()).getGroupName());
				log.trace("Name of group was added");
				log.trace("Get name of subject");
				scheduleItemAsStringList.add(subjectDao.findById(scheduleItem.getSubjectId()).getSubjectName());
				log.trace("Name of subject was added");
				log.trace("Get name of lecturer");
				scheduleItemAsStringList.add(lecturerDao.findById(scheduleItem.getLecturerId()).getLecturerName());
				log.trace("Name of lecturer was added");
				log.trace("Get name of classroom");
				scheduleItemAsStringList.add(classroomDao.findById(scheduleItem.getClassroomId()).getClassroomName());
				log.trace("Name of classroom was added");
				log.trace("Get start and finish of lesson interval");
				scheduleItemAsStringList.add(
						lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonStart()
						+ "-"
						+ lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonFinish());
				log.trace("Start and finish of lesson interval were added");				
				scheduleItemAsStringList.add("\n");
			}
		}
		log.trace("Returning scheduleItems as string list");
		return scheduleItemAsStringList;
	}
	/**
	 * obtainScheduleItemsAsStringList method
	 * @param classroom is Classroom object
	 * @return result as List<String> type variable
	 */
	public List<String> obtainScheduleItemsAsStringList(Classroom classroom){
		log.info("Obtaining scheduleItems as string list for classroom "
				+ "with classroomId = " + classroom.getClassroomId());
		List<String> scheduleItemAsStringList  = new LinkedList<String>();
		List<ScheduleItem> scheduleItems = scheduleItemDao.findByScheduleId(scheduleId);
		for (ScheduleItem scheduleItem : scheduleItems) {
			if(scheduleItem.getClassroomId() == classroom.getClassroomId()){
				log.trace("Found scheduleItem with id = " + scheduleItem.getScheduleItemId());
				scheduleItemAsStringList.add(dateToString(scheduleDate));
				log.trace("Get name of classroom");
				scheduleItemAsStringList.add(classroomDao.
						findById(scheduleItem.getClassroomId()).getClassroomName());
				log.trace("Name of classroom was added");
				log.trace("Get name of group");
				scheduleItemAsStringList.add(groupDao.
						findById(scheduleItem.getGroupId()).getGroupName());
				log.trace("Name of group was added");
				log.trace("Get name of subject");
				scheduleItemAsStringList.add(subjectDao.
						findById(scheduleItem.getSubjectId()).getSubjectName());
				log.trace("Name of subject was added");
				log.trace("Get name of lecturer");
				scheduleItemAsStringList.add(lecturerDao.
						findById(scheduleItem.getLecturerId()).getLecturerName());
				log.trace("Name of lecturer was added");
				log.trace("Get start and finish of lesson interval");
				scheduleItemAsStringList.add(
						lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonStart()
						+ "-"
						+ lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonFinish());
				log.trace("Start and finish of lesson interval were added");
				scheduleItemAsStringList.add("\n");
			}
		}
		log.trace("Returning scheduleItems as string list");
		return scheduleItemAsStringList;		
	}
	/**
	 * obtainScheduleItemsAsStringList method
	 * @param lecturer is Lecturer object
	 * @return result as List<String> type variable
	 */
	public List<String> obtainScheduleItemsAsStringList(Lecturer lecturer) {
		log.info("Obtaining scheduleItems as string list for lecturer with"
				+ " lecturerId = " + lecturer.getLecturerId());
		List<String> scheduleItemAsStringList  = new LinkedList<String>();
		List<ScheduleItem> scheduleItems = scheduleItemDao.findByScheduleId(scheduleId);
		for (ScheduleItem scheduleItem : scheduleItems) {
			log.trace("Found scheduleItem with id = " + scheduleItem.getScheduleItemId());
			if(scheduleItem.getLecturerId() == lecturer.getLecturerId()){
				scheduleItemAsStringList.add(dateToString(scheduleDate));
				log.trace("Get name of lecturer");
				scheduleItemAsStringList.add(lecturerDao.
						findById(scheduleItem.getLecturerId()).getLecturerName());
				log.trace("Name of lecturer was added");
				log.trace("Get name of group");
				scheduleItemAsStringList.add(groupDao.
						findById(scheduleItem.getGroupId()).getGroupName());
				log.trace("Name of group was added");
				log.trace("Get name of subject");
				scheduleItemAsStringList.add(subjectDao.
						findById(scheduleItem.getSubjectId()).getSubjectName());
				log.trace("Name of subject was added");
				log.trace("Get name of classroom");
				scheduleItemAsStringList.add(classroomDao.
						findById(scheduleItem.getClassroomId()).getClassroomName());
				log.trace("Name of classroom was added");
				log.trace("Get start and finish of lesson interval");
				scheduleItemAsStringList.add(
						lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonStart()
						+ "-"
						+ lessonIntervalDao.findById(scheduleItem.getLessonIntervalId()).getLessonFinish());
				log.trace("Start and finish of lesson interval were added");
				scheduleItemAsStringList.add("\n");
			}
		}
		log.trace("Returning scheduleItems as string list");
		return scheduleItemAsStringList;		
	}
	private String dateToString(Calendar date){
		return date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR);
	}
}