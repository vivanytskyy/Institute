package com.gmail.ivanytskyy.vitaliy.dao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
 * Task #3/2015/12/18 (web project #3)
 * DaoManager class
 * @version 1.01 2015.12.18
 * @author Vitaliy Ivanytskyy
 */
public final class DaoManager {
	private static final Logger log = Logger.getLogger(DaoManager.class);
	private static DaoManager instance = new DaoManager();
	private ClassroomDao classroomDao;
	private GroupDao groupDao;
	private LecturerDao lecturerDao;
	private LessonIntervalDao lessonIntervalDao;
	private ScheduleDao scheduleDao;
	private ScheduleItemDao scheduleItemDao;
	private StudentDao studentDao;
	private SubjectDao subjectDao;	
	private DaoManager(){
		log.info("Creating context object");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		log.info("Obtain ClassroomDao object by context");
		classroomDao = context.getBean("classroomDao", JdbcTemplateClassroomDaoImpl.class);
		log.info("Obtain GroupDao object by context");
		groupDao = context.getBean("groupDao", JdbcTemplateGroupDaoImpl.class);
		log.info("Obtain LecturerDao object by context");
		lecturerDao = context.getBean("lecturerDao", JdbcTemplateLecturerDaoImpl.class);
		log.info("Obtain LessonIntervalDao object by context");
		lessonIntervalDao = context.getBean("lessonIntervalDao", JdbcTemplateLessonIntervalDaoImpl.class);
		log.info("Obtain ScheduleDao object by context");
		scheduleDao = context.getBean("scheduleDao", JdbcTemplateScheduleDaoImpl.class);
		log.info("Obtain ScheduleItemDao object by context");
		scheduleItemDao = context.getBean("scheduleItemDao", JdbcTemplateScheduleItemDaoImpl.class);
		log.info("Obtain StudentDao object by context");
		studentDao = context.getBean("studentDao", JdbcTemplateStudentDaoImpl.class);
		log.info("Obtain SubjectDao object by context");
		subjectDao = context.getBean("subjectDao", JdbcTemplateSubjectDaoImpl.class);
		log.info("Close context");
		((ConfigurableApplicationContext) context).close();
	}
	public static DaoManager getInstance(){
		log.info("Return DaoManager instance");
		return instance;
	}	
	public ClassroomDao getClassroomDao() {
		log.info("Return ClassroomDao object");
		return classroomDao;
	}
	public GroupDao getGroupDao() {
		log.info("Return GroupDao object");
		return groupDao;
	}
	public LecturerDao getLecturerDao() {
		log.info("Return LecturerDao object");
		return lecturerDao;
	}
	public LessonIntervalDao getLessonIntervalDao() {
		log.info("Return LessonIntervalDao object");
		return lessonIntervalDao;
	}
	public ScheduleDao getScheduleDao() {
		log.info("Return ScheduleDao object");
		return scheduleDao;
	}
	public ScheduleItemDao getScheduleItemDao() {
		log.info("Return ScheduleItemDao object");
		return scheduleItemDao;
	}
	public StudentDao getStudentDao() {
		log.info("Return StudentDao object");
		return studentDao;
	}
	public SubjectDao getSubjectDao(){
		log.info("Return SubjectDao object");
		return subjectDao;		
	}
}