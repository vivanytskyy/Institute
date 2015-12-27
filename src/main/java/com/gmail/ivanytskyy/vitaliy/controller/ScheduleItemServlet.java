package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomService;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.GroupService;
import com.gmail.ivanytskyy.vitaliy.service.GroupServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.LecturerService;
import com.gmail.ivanytskyy.vitaliy.service.LecturerServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.LessonIntervalService;
import com.gmail.ivanytskyy.vitaliy.service.LessonIntervalServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleItemService;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleItemServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleService;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.SubjectService;
import com.gmail.ivanytskyy.vitaliy.service.SubjectServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
/*
 * Task #3/2015/12/15 (web project #3)
 * ScheduleItemServlet
 * @version 1.01 2015.12.15
 * @author Vitaliy Ivanytskyy
 */
public class ScheduleItemServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ScheduleItemServlet.class.getName());
	private static final long serialVersionUID = 1L;
	private ScheduleItemService scheduleItemService;
	private ScheduleService scheduleService;
	private GroupService groupService;
	private LecturerService lecturerService;
	private SubjectService subjectService;
	private ClassroomService classroomService;
	private LessonIntervalService lessonIntervalService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the ScheduleItemService object");
		scheduleItemService = appContext.getBean("scheduleItemService", ScheduleItemServiceImpl.class);
		log.trace("Getting the ScheduleService object");
		scheduleService = appContext.getBean("scheduleService", ScheduleServiceImpl.class);
		log.trace("Getting the GroupService object");
		groupService = appContext.getBean("groupService", GroupServiceImpl.class);
		log.trace("Getting the LecturerService object");
		lecturerService = appContext.getBean("lecturerService", LecturerServiceImpl.class);
		log.trace("Getting the SubjectService object");
		subjectService = appContext.getBean("subjectService", SubjectServiceImpl.class);
		log.trace("Getting the ClassroomService object");
		classroomService = appContext.getBean("classroomService", ClassroomServiceImpl.class);
		log.trace("Getting the LessonIntervalService object");
		lessonIntervalService = appContext.getBean("lessonIntervalService", LessonIntervalServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String scheduleIdStr = request.getParameter("scheduleId");
		String groupIdStr = request.getParameter("groupId");
		String lecturerIdStr = request.getParameter("lecturerId");
		String subjectIdStr = request.getParameter("subjectId");
		String classroomIdStr = request.getParameter("classroomId");
		String lessonIntervalIdStr = request.getParameter("lessonIntervalId");
		String scheduleItemIdStr = request.getParameter("scheduleItemId");
		request.setAttribute("alarmMessageOfScheduleItemForMove", "");
		request.setAttribute("alarmMessageOfScheduleForMove", "");
		request.setAttribute("alarmMessageForAddScheduleId", "");
		request.setAttribute("alarmMessageForAddGroupId", "");
		request.setAttribute("alarmMessageForAddLecturerId", "");
		request.setAttribute("alarmMessageForAddClassroomId", "");
		request.setAttribute("alarmMessageForAddSubjectId", "");
		request.setAttribute("alarmMessageForAddLessonIntervalId", "");		
		request.setAttribute("alarmMessageForEditScheduleId", "");
		request.setAttribute("alarmMessageForEditGroupId", "");
		request.setAttribute("alarmMessageForEditLecturerId", "");
		request.setAttribute("alarmMessageForEditClassroomId", "");
		request.setAttribute("alarmMessageForEditSubjectId", "");
		request.setAttribute("alarmMessageForEditLessonIntervalId", "");		
		ScheduleItem scheduleItem = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add")
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)){
			long scheduleId = Long.valueOf(scheduleIdStr);
			long groupId = Long.valueOf(groupIdStr);
			long lecturerId = Long.valueOf(lecturerIdStr);
			long subjectId = Long.valueOf(subjectIdStr);
			long classroomId = Long.valueOf(classroomIdStr);
			long lessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			if(isScheduleExist(scheduleId) 
					&& isGroupExist(groupId) 
					&& isLecturerExist(lecturerId)
					&& isClassroomExist(classroomId)
					&& isSubjectExist(subjectId)
					&& isLessonIntervalExist(lessonIntervalId)){
				try {
					log.trace("Try create scheduleItem");
					scheduleItem = scheduleItemService.create(groupId, lecturerId, classroomId, subjectId, lessonIntervalId, scheduleId);
					log.trace("ScheduleItem was created");
				} catch (DataAccessException e) {
					log.error("Cannot create scheduleItem", e);
				}
			}else{
				if(!isScheduleExist(scheduleId)){
					request.setAttribute("alarmMessageForAddScheduleId", "Schedule with this Id does not exist");
				}
				if(!isGroupExist(groupId)){
					request.setAttribute("alarmMessageForAddGroupId", "Group with this Id does not exist");
				}
				if(!isLecturerExist(lecturerId)){
					request.setAttribute("alarmMessageForAddLecturerId", "Lecturer with this Id does not exist");
				}
				if(!isClassroomExist(classroomId)){
					request.setAttribute("alarmMessageForAddClassroomId", "Classroom with this Id does not exist");
				}
				if(!isSubjectExist(subjectId)){
					request.setAttribute("alarmMessageForAddSubjectId", "Subject with this Id does not exist");
				}
				if(!isLessonIntervalExist(lessonIntervalId)){
					request.setAttribute("alarmMessageForAddLessonIntervalId", "Lesson interval with this Id does not exist");
				}
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);
			try {
				log.trace("Try find scheduleItem with scheduleItemId = " + scheduleItemId);
				request.setAttribute("resultScheduleItem", scheduleItemService.findById(scheduleItemId));
				log.trace("ScheduleItem with scheduleItemId=" + scheduleItemId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find scheduleItem", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")				
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);
			long newScheduleId = Long.valueOf(scheduleIdStr);
			long newGroupId = Long.valueOf(groupIdStr);
			long newLecturerId = Long.valueOf(lecturerIdStr);
			long newSubjectId = Long.valueOf(subjectIdStr);
			long newClassroomId = Long.valueOf(classroomIdStr);
			long newLessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			if(isScheduleExist(newScheduleId) 
					&& isGroupExist(newGroupId) 
					&& isLecturerExist(newLecturerId)
					&& isClassroomExist(newClassroomId)
					&& isSubjectExist(newSubjectId)
					&& isLessonIntervalExist(newLessonIntervalId)){
				try {
					log.trace("Try update scheduleItem with scheduleItemId = " + scheduleItemId);
					scheduleItemService.update(scheduleItemId, newGroupId, newLecturerId, newClassroomId, newSubjectId, newLessonIntervalId, newScheduleId);
					log.trace("ScheduleItem with scheduleItemId = " + scheduleItemId + " was updated");
				} catch (DataAccessException e) {
					log.error("Cannot update scheduleItem", e);
				}
			}else{
				if(!isScheduleExist(newScheduleId)){
					request.setAttribute("alarmMessageForEditScheduleId", "Schedule with this Id does not exist");
				}
				if(!isGroupExist(newGroupId)){
					request.setAttribute("alarmMessageForEditGroupId", "Group with this Id does not exist");
				}
				if(!isLecturerExist(newLecturerId)){
					request.setAttribute("alarmMessageForEditLecturerId", "Lecturer with this Id does not exist");
				}
				if(!isClassroomExist(newClassroomId)){
					request.setAttribute("alarmMessageForEditClassroomId", "Classroom with this Id does not exist");
				}
				if(!isSubjectExist(newSubjectId)){
					request.setAttribute("alarmMessageForEditSubjectId", "Subject with this Id does not exist");
				}
				if(!isLessonIntervalExist(newLessonIntervalId)){
					request.setAttribute("alarmMessageForEditLessonIntervalId", "Lesson interval with this Id does not exist");
				}
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)) {
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);		
			try {
				log.trace("Try delete scheduleItem");
				scheduleItemService.deleteById(scheduleItemId);
				log.trace("ScheduleItem was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete scheduleItem", e);
			}
		}else if(action != null 
				&& action.equalsIgnoreCase("Move")
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)
				&& InputDataValidator.isPositiveLongNumber(scheduleItemIdStr)){
			long scheduleId = Long.valueOf(scheduleIdStr);
			long scheduleItemId = Long.valueOf(scheduleItemIdStr);			
			if(isScheduleExist(scheduleId) && isScheduleItemExist(scheduleItemId)){
				try {
					log.trace("Try move scheduleItem to another schedule");
					scheduleItemService.moveToAnotherSchedule(scheduleItemId, scheduleId);
					log.trace("ScheduleItem was moved");
					log.trace("Try get scheduleItem after moving");
					scheduleItem = scheduleItemService.findById(scheduleItemId);
					log.trace("ScheduleItem was gotten");
				} catch (DataAccessException e) {
					log.error("Cannot move scheduleItem", e);
				}
			}else if(!isScheduleItemExist(scheduleItemId)){
				request.setAttribute("alarmMessageOfScheduleItemForMove", "ScheduleItem with this Id does not exist");
			}else if(!isScheduleExist(scheduleId)){
				request.setAttribute("alarmMessageOfScheduleForMove", "Schedule with this Id does not exist");
			}			
		}
		request.setAttribute("scheduleItem", scheduleItem);
		try {
			log.trace("Try get all schedules for putting to request");
			request.setAttribute("allSchedules", scheduleService.findAll());
			log.trace("Schedules were gotten");
		} catch (DataAccessException e1) {
			log.error("Cannot get all schedules", e1);
		}
		try {
			log.trace("Try get all scheduleItems for putting to request");
			request.setAttribute("allScheduleItems", scheduleItemService.findAll());
			log.trace("ScheduleItems were gotten");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/views/administrator/adminScheduleItem.jsp").forward(request, response);
	}	
	private boolean isScheduleExist(long scheduleId){
		boolean result = false;
		try {
			log.trace("Try get schedule by scheduleId=" + scheduleId + " for exist checking");
			result = (scheduleService.findById(scheduleId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get schedule", e);
		}
		return result;
	}
	private boolean isGroupExist(long groupId){		
		boolean result = false;
		try {
			log.trace("Try get group by groupId=" + groupId + " for exist checking");
			result = (groupService.findById(groupId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get group", e);
		}
		return result;
	}
	private boolean isLecturerExist(long lecturerId){
		boolean result = false;
		try {
			log.trace("Try get lecturer by lecturerId=" + lecturerId + " for exist checking");
			result = (lecturerService.findById(lecturerId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get lecturer", e);
		}
		return result;
	}
	private boolean isSubjectExist(long subjectId){
		boolean result = false;
		try {
			log.trace("Try get subject by subjectId=" + subjectId + " for exist checking");
			result = (subjectService.findById(subjectId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get subject", e);
		}
		return result;
	}
	private boolean isClassroomExist(long classroomId){
		boolean result = false;
		try {
			log.trace("Try get classroom by classroomId=" + classroomId + " for exist checking");
			result = (classroomService.findById(classroomId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get classroom", e);
		}
		return result;
	}
	private boolean isLessonIntervalExist(long lessonIntervalId){
		boolean result = false;
		try {
			log.trace("Try get lessonInterval by lessonIntervalId=" + lessonIntervalId + " for exist checking");
			result = (lessonIntervalService.findById(lessonIntervalId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get lessonInterval", e);
		}
		return result;
	}
	private boolean isScheduleItemExist(long scheduleItemId){
		boolean result = false;
		try {
			log.trace("Try get scheduleItem by scheduleItemId=" + scheduleItemId + " for exist checking");
			result = (scheduleItemService.findById(scheduleItemId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get scheduleItem", e);
		}
		return result;
	}
}