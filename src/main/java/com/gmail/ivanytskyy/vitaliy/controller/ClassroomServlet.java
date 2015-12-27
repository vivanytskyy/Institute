package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import com.gmail.ivanytskyy.vitaliy.domain.Classroom;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomService;
import com.gmail.ivanytskyy.vitaliy.service.ClassroomServiceImpl;
/*
 * Task #3/2015/12/15 (web project #2)
 * ClassroomServlet
 * @version 1.01 2015.12.15
 * @author Vitaliy Ivanytskyy
 */
public class ClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ClassroomServlet.class);
	private ClassroomService classroomService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the ClassroomService object");
		this.classroomService = appContext.getBean("classroomService", ClassroomServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String classroomIdStr = request.getParameter("classroomId");		
		String classroomName = request.getParameter("classroomName");
		log.trace("Create new ClassroomService object");
		Classroom classroom = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& classroomName != null 
				&& !classroomName.equals("")
				&& !classroomName.trim().equals("")){
			try {
				log.trace("Try create classroom with name=" + classroomName);				
				classroom = classroomService.create(classroomName.trim());
				log.trace("Classroom with name=" + classroomName + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create classroom", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)) {
			long classroomId = Long.valueOf(classroomIdStr);
			try {
				log.trace("Try find classroom with classroomId = " + classroomId);
				request.setAttribute("resultClassroom", classroomService.findById(classroomId));
				log.trace("Classroom with classroomId=" + classroomId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find classroom", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& classroomName != null 
				&& !classroomName.equals("")
				&& !classroomName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)) {
			long classroomId = Long.valueOf(classroomIdStr);
			try {
				log.trace("Try update classroom with classroomId = " + classroomId + " by new classroomName = " + classroomName);
				classroomService.update(classroomId, classroomName.trim());
				log.trace("Classroom with classroomId = " + classroomId + " was updated by new classroomName = " + classroomName);
			} catch (DataAccessException e) {
				log.error("Cannot update classroom", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(classroomIdStr)) {
			long classroomId = Long.valueOf(classroomIdStr);	
			try {
				log.trace("Try delete classroom with classroomId=" + classroomId);
				classroomService.deleteById(classroomId);
				log.trace("Classroom with classroomId=" + classroomId + " was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete classroom", e);
			}
		}		
		request.setAttribute("classroom", classroom);
		try {
			log.trace("Try get all classrooms for putting to request");
			request.setAttribute("allClassrooms", classroomService.findAll());
			log.trace("Classrooms were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all classrooms", e);
		}
		request.getRequestDispatcher("/views/administrator/adminClassroom.jsp").forward(request, response);
	}
}