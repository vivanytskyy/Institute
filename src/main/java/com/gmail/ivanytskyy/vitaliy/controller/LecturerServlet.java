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

import com.gmail.ivanytskyy.vitaliy.domain.Lecturer;
import com.gmail.ivanytskyy.vitaliy.service.LecturerService;
import com.gmail.ivanytskyy.vitaliy.service.LecturerServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * LecturerServlet
 * @version 1.02 2016.01.02
 * @author Vitaliy Ivanytskyy
 */
@WebServlet("/views/administrator/lecturerResult")
public class LecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(LecturerServlet.class);
	private LecturerService lecturerService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the LecturerService object");
		this.lecturerService = appContext.getBean("lecturerService", LecturerServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String lecturerIdStr = request.getParameter("lecturerId");		
		String lecturerName = request.getParameter("lecturerName");
		Lecturer lecturer = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& lecturerName != null 
				&& !lecturerName.equals("")
				&& !lecturerName.trim().equals("")){
			try {
				log.trace("Try create lecturer with lecturerName=" + lecturerName);
				lecturer = lecturerService.create(lecturerName.trim());
				log.trace("Lecturer with lecturerName=" + lecturerName + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);
			try {
				log.trace("Try find lecturer with lecturerId = " + lecturerId);
				request.setAttribute("resultLecturer", lecturerService.findById(lecturerId));
				log.trace("Lecturer with lecturerId=" + lecturerId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& lecturerName != null 
				&& !lecturerName.equals("")
				&& !lecturerName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);
			try {
				log.trace("Try update lecturer with lecturerId = " + lecturerId
						+ " by new lecturerName = " + lecturerName);
				lecturerService.update(lecturerId, lecturerName.trim());
				log.trace("Lecturer with lecturerId = " + lecturerId + " was updated by new lecturerName = " + lecturerName);
			} catch (DataAccessException e) {
				log.error("Cannot update lecturer", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(lecturerIdStr)) {
			long lecturerId = Long.valueOf(lecturerIdStr);	
			try {
				log.trace("Try delete lecturer with lecturerId=" + lecturerId);
				lecturerService.deleteById(lecturerId);
				log.trace("Lecturer with lecturerId=" + lecturerId + " was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete lecturer", e);
			}
		}		
		request.setAttribute("lecturer", lecturer);
		try {
			log.trace("Try get all lecturers for putting to request");
			request.setAttribute("allLecturers", lecturerService.findAll());
			log.trace("Lecturers were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all lecturers", e);
		}
		request.getRequestDispatcher("/views/administrator/adminLecturer.jsp").forward(request, response);
	}
}