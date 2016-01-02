package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.gmail.ivanytskyy.vitaliy.domain.Student;
import com.gmail.ivanytskyy.vitaliy.service.GroupService;
import com.gmail.ivanytskyy.vitaliy.service.GroupServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.StudentService;
import com.gmail.ivanytskyy.vitaliy.service.StudentServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * StudentServlet
 * @version 1.02 2016.01.02
 * @author Vitaliy Ivanytskyy
 */
@WebServlet("/views/administrator/studentResult")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(StudentServlet.class);
	private StudentService studentService;
	private GroupService groupService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Create new StudentService object");
		studentService = appContext.getBean("studentService", StudentServiceImpl.class);
		log.trace("Create new GroupService object");
		groupService = appContext.getBean("groupService", GroupServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String groupIdStr = request.getParameter("groupId");
		String studentIdStr = request.getParameter("studentId");		
		String studentName = request.getParameter("studentName");
		request.setAttribute("alarmMessageForAdd", "");
		request.setAttribute("alarmMessageOfStudentForMove", "");
		request.setAttribute("alarmMessageOfGroupForMove", "");
		Student student = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& studentName != null 
				&& !studentName.equals("")
				&& !studentName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)){
			long groupId = Long.valueOf(groupIdStr);
			if(isGroupExist(groupId)){
				try {
					log.trace("Try create student with name=" + studentName);
					student = studentService.create(studentName.trim(), groupId);
					log.trace("Student with name=" + studentName + " was created");
				} catch (DataAccessException e) {
					log.error("Cannot create student", e);
				}
			}else{
				request.setAttribute("alarmMessageForAdd", "Group with this Id does not exist");
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(studentIdStr)) {
			long studentId = Long.valueOf(studentIdStr);
			try {
				log.trace("Try find student with studentId = " + studentId);
				request.setAttribute("resultStudent", studentService.findById(studentId));
				log.trace("Student with studentId=" + studentId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find student", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& studentName != null 
				&& !studentName.equals("")
				&& !studentName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(studentIdStr)) {
			long studentId = Long.valueOf(studentIdStr);
			try {
				log.trace("Try update student with studentId = " + studentId 
						+ " by new studentName = " + studentName);
				studentService.update(studentId, studentName.trim());
				log.trace("Student with studentId = " + studentId 
						+ " was updated by new studentName = " + studentName);
			} catch (DataAccessException e) {
				log.error("Cannot update student", e);
			}		
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(studentIdStr)) {
			long studentId = Long.valueOf(studentIdStr);		
			try {
				log.trace("Try delete student with studentId=" + studentId);
				studentService.deleteById(studentId);
				log.trace("Student with studentId=" + studentId + " was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete student", e);
			}
		}else if(action != null 
				&& action.equalsIgnoreCase("Move")
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)
				&& InputDataValidator.isPositiveLongNumber(studentIdStr)){
			long groupId = Long.valueOf(groupIdStr);
			long studentId = Long.valueOf(studentIdStr);
			if(isGroupExist(groupId) && isStudentExist(studentId)){
				try {
					log.trace("Try move student with studentId=" + studentId + " to group with groupId=" + groupId);
					studentService.moveToAnotherGroup(studentId, groupId);
					log.trace("Student with studentId=" + studentId + " was moved to group with groupId=" + groupId);
					log.trace("Try get student by studentId=" + studentId + " after moving");
					student = studentService.findById(studentId);
					log.trace("Student with studentId=" + studentId + " after moving was gotten");
				} catch (DataAccessException e) {
					log.error("Cannot move student or get student after moving", e);
				}
			}else if(!isGroupExist(groupId)){
				request.setAttribute("alarmMessageOfGroupForMove", "Group with this Id does not exist");
			}else if(!isStudentExist(studentId)){
				request.setAttribute("alarmMessageOfStudentForMove", "Student with this Id does not exist");
			}
		}		
		request.setAttribute("student", student);
		try {
			log.trace("Try get all students for putting to request");
			request.setAttribute("allStudents", studentService.findAll());
			log.trace("Students were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all students", e);
		}		
		request.getRequestDispatcher("/views/administrator/adminStudent.jsp").forward(request, response);		
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
	private boolean isStudentExist(long studentId){
		boolean result = false;
		try {
			log.trace("Try get student by studentId=" + studentId + " for exist checking");
			result = (studentService.findById(studentId) == null) ? false : true;
			log.trace("Result of checking is " + result);
		} catch (DataAccessException e) {
			log.error("Cannot get student", e);
		}		
		return result;
	}
}