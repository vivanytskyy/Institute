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

import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
import com.gmail.ivanytskyy.vitaliy.service.LessonIntervalService;
import com.gmail.ivanytskyy.vitaliy.service.LessonIntervalServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * LessonIntervalServlet
 * @version 1.02 2016.01.02
 * @author Vitaliy Ivanytskyy
 */
@WebServlet("/views/administrator/lessonIntervalResult")
public class LessonIntervalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(LessonIntervalServlet.class);
	private LessonIntervalService lessonIntervalService;	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Create new LessonIntervalService object");
		lessonIntervalService = appContext.getBean("lessonIntervalService", LessonIntervalServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String lessonIntervalIdStr = request.getParameter("lessonIntervalId");		
		String lessonStart = request.getParameter("lessonStart");
		String lessonFinish = request.getParameter("lessonFinish");
		LessonInterval lessonInterval = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& lessonStart != null && lessonFinish != null
				&& !lessonStart.equals("") && !lessonFinish.equals("")
				&& !lessonStart.trim().equals("") && !lessonFinish.trim().equals("")){
			try {
				log.trace("Try create lessonInterval with lesson interval = " + lessonStart + "-" + lessonFinish);
				lessonInterval = lessonIntervalService.create(lessonStart.trim(), lessonFinish.trim());
				log.trace("LessonInterval with lesson interval = " + lessonStart + "-" + lessonFinish + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create lessonInterval", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)) {
			long lessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			try {
				log.trace("Try find lessonInterval with lessonIntervalId = " + lessonIntervalId);
				request.setAttribute("resultLessonInterval", lessonIntervalService.findById(lessonIntervalId));
				log.trace("LessonInterval with lessonIntervalId=" + lessonIntervalId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find lessonInterval", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& lessonStart != null && lessonFinish != null
				&& !lessonStart.equals("") && !lessonFinish.equals("")
				&& !lessonStart.trim().equals("") && !lessonFinish.trim().equals("") 
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)) {
			long lessonIntervalId = Long.valueOf(lessonIntervalIdStr);
			try {
				log.trace("Try update lessonInterval with lessonIntervalId = " + lessonIntervalId 
						+ " by new lessonStart = " + lessonStart 
						+ " and new lessonFinish = " + lessonFinish);
				lessonIntervalService.update(lessonIntervalId, lessonStart.trim(), lessonFinish.trim());
				log.trace("LessonInterval with lessonIntervalId = " + lessonIntervalId 
						+ " was updated by new lessonStart = " + lessonStart 
						+ " and new lessonFinish = " + lessonFinish);
			} catch (DataAccessException e) {
				log.error("Cannot update lessonInterval", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(lessonIntervalIdStr)) {
			long lessonIntervalId = Long.valueOf(lessonIntervalIdStr);	
			try {
				log.trace("Try delete lessonInterval with lessonIntervalId=" + lessonIntervalId);
				lessonIntervalService.deleteById(lessonIntervalId);
				log.trace("LessonInterval with lessonIntervalId=" + lessonIntervalId + " was deleted");
			} catch (DataAccessException e) {
				log.error("Cannot delete lessonInterval", e);
			}
		}		
		request.setAttribute("lessonInterval", lessonInterval);
		try {
			log.trace("Try get all lessonIntervals for putting to request");
			request.setAttribute("allLessonIntervals", lessonIntervalService.findAll());
			log.trace("LessonIntervals were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all lessonIntervals", e);
		}
		request.getRequestDispatcher("/views/administrator/adminLessonInterval.jsp").forward(request, response);
	}
}