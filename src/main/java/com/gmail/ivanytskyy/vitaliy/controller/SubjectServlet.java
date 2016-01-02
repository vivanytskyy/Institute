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

import com.gmail.ivanytskyy.vitaliy.domain.Subject;
import com.gmail.ivanytskyy.vitaliy.service.SubjectService;
import com.gmail.ivanytskyy.vitaliy.service.SubjectServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * SubjectServlet
 * @version 1.02 2016.01.02
 * @author Vitaliy Ivanytskyy
 */
@WebServlet("/views/administrator/subjectResult")
public class SubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(SubjectServlet.class);
	private SubjectService subjectService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the SubjectService object");
		subjectService = appContext.getBean("subjectService", SubjectServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String subjectIdStr = request.getParameter("subjectId");		
		String subjectName = request.getParameter("subjectName");
		Subject subject = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& subjectName != null 
				&& !subjectName.equals("")
				&& !subjectName.trim().equals("")){
			try {
				log.trace("Try create subject with name=" + subjectName);
				subject = subjectService.create(subjectName.trim());
				log.trace("Subject with name=" + subjectName + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create subject", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)) {
			long subjectId = Long.valueOf(subjectIdStr);
			try {
				log.trace("Try find subject with subjectId = " + subjectId);
				request.setAttribute("resultSubject", subjectService.findById(subjectId));
				log.trace("Subject with subjectId=" + subjectId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find subject", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& subjectName != null 
				&& !subjectName.equals("")
				&& !subjectName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)) {
			long subjectId = Long.valueOf(subjectIdStr);
			try {
				log.trace("Try update subject with subjectId = " + subjectId + " by new subjectName = " + subjectName);
				subjectService.update(subjectId, subjectName.trim());
				log.trace("Subject with subjectId = " + subjectId + " was updated by new subjectName = " + subjectName);
			} catch (DataAccessException e) {
				log.error("Cannot update subject", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(subjectIdStr)) {
			long subjectId = Long.valueOf(subjectIdStr);	
			try {
				log.trace("Try delete subject with subjectId=" + subjectId);
				subjectService.deleteById(subjectId);
				log.trace("Subject with subjectId=" + subjectId + " was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete subject", e);
			}
		}		
		request.setAttribute("subject", subject);
		try {
			log.trace("Try get all subjects for putting to request");
			request.setAttribute("allSubjects", subjectService.findAll());
			log.trace("Subjects were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all subjects", e);
		}
		request.getRequestDispatcher("/views/administrator/adminSubject.jsp").forward(request, response);		
	}
}