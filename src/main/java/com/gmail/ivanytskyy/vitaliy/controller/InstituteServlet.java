package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
import com.gmail.ivanytskyy.vitaliy.domain.Institute;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomService;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.GroupService;
import com.gmail.ivanytskyy.vitaliy.service.GroupServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.LecturerService;
import com.gmail.ivanytskyy.vitaliy.service.LecturerServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * InstituteServlet
 * @version 1.01 2015.12.15
 * @author Vitaliy Ivanytskyy
 */
public class InstituteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(InstituteServlet.class);
	private Institute institute;
	private GroupService groupService;
	private LecturerService lecturerService;
	private ClassroomService classroomService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the Institute object");
		institute = appContext.getBean("institute", Institute.class);
		log.trace("Getting the GroupService object");
		groupService = appContext.getBean("groupService", GroupServiceImpl.class);
		log.trace("Getting the LecturerService object");
		lecturerService = appContext.getBean("lecturerService", LecturerServiceImpl.class);
		log.trace("Getting the ClassroomService object");
		classroomService = appContext.getBean("classroomService", ClassroomServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String url = "/views/index.jsp";
		String variant = request.getParameter("variant");
		String groupName = request.getParameter("group");
		String classroomName = request.getParameter("classroom");
		String lecturerName = request.getParameter("lecturer");
		String day = request.getParameter("day");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		String[] result = {"Shedule does not exist!"};		
		Calendar date = new GregorianCalendar();
		if(InputDataValidator.isDay(day) 
				&& InputDataValidator.isMonth(month)
				&& InputDataValidator.isYear(year)){
			date.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
		}else{
			response.sendError(400, "Input error! Try again, please!");
			return;
		}		
		if(variant != null && !variant.equals("")){
			if(variant.equals("byGroup")
					&& groupName != null
					&& !groupName.trim().equals("")) {
				log.trace("Create new Group object");
				List<Group> groups = new LinkedList<Group>();
				try {
					log.trace("Try get groups with groupName=" + groupName);
					groups = groupService.findByName(groupName.trim());
					log.trace("Groups with groupName=" + groupName + " were gotten");
				} catch (DataAccessException e) {
					log.error("Cannot get groups by groupName", e);
				}
				if(groups.isEmpty()){
					log.trace("Groups with groupName=" + groupName + " don't exist");
				}else{
					log.trace("Try get schedule for groups with groupName=" + groupName);
					for (Group group : groups) {
						String resultAsStr = institute.obtainSchedule(group, date);
						if(resultAsStr != null && !resultAsStr.isEmpty()){
							log.trace("Schedule for group with groupName=" + group.getGroupName() + " was gotten");
							result = resultAsStr.split("\n");
							log.trace("Schedule for group with groupName=" + group.getGroupName() + " was set in request");
						}
					}
				}
				url = "/views/users/resultScheduleList.jsp";
			}else if(variant.equals("byClassroom") 
					&& classroomName != null
					&& !classroomName.trim().equals("")){
				log.trace("Create new classroomDao object");
				List<Classroom> classrooms = new LinkedList<Classroom>();
				try {
					log.trace("Try get classrooms with classroomName=" + classroomName);
					classrooms = classroomService.findByName(classroomName.trim());
					log.trace("Classrooms with classroomName=" + classroomName + " were gotten");
				} catch (DataAccessException e) {
					log.error("Cannot get classrooms by classroomName", e);
				}
				if(classrooms.isEmpty()){
					log.trace("Classrooms with classroomName=" + classroomName + " don't exist");
				}else{
					log.trace("Try get schedule for classrooms with classroomName=" + classroomName);
					for (Classroom classroom : classrooms) {
						String resultAsStr = institute.obtainSchedule(classroom, date);
						if(resultAsStr != null && !resultAsStr.isEmpty()){
							log.trace("Schedule for classroom with classroomName=" + classroom.getClassroomName() + " was gotten");
							result = resultAsStr.split("\n");
							log.trace("Schedule for classroom with classroomName=" + classroom.getClassroomName() + " was set in request");
						}						
					}
				}
				url = "/views/users/resultScheduleList.jsp";
			}else if(variant.equals("byLecturer")
					&& lecturerName != null
					&& !lecturerName.trim().equals("")) {
				List<Lecturer> lecturers = new LinkedList<Lecturer>();				
				try {
					log.trace("Try get lecturers with lecturerName=" + lecturerName);
					lecturers = lecturerService.findByName(lecturerName.trim());
					log.trace("Lecturers with lecturerName=" + lecturerName + " were gotten");
				} catch (DataAccessException e) {
					log.error("Cannot get lecturers by lecturerName", e);
				}
				if(lecturers.isEmpty()){
					log.trace("Lecturers with lecturerName=" + lecturerName + " don't exist");
				}else{
					log.trace("Try get schedule for lecturers with lecturerName=" + lecturerName);
					for (Lecturer lecturer : lecturers) {
						String resultAsStr = institute.obtainSchedule(lecturer, date);
						if(resultAsStr != null && !resultAsStr.isEmpty()){
							log.trace("Schedule for lecturer with lecturerName=" + lecturer.getLecturerName() + " was gotten");
							result = resultAsStr.split("\n");
							log.trace("Schedule for lecturer with lecturerName=" + lecturer.getLecturerName() + " was set in request");
						}
					}
				}
				url = "/views/users/resultScheduleList.jsp";
			}else{
				response.sendError(400, "Input error! Try again, please!");
				return;
			}
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher(url).forward(request, response);
	}
}