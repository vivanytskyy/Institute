package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleItemService;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleItemServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleService;
import com.gmail.ivanytskyy.vitaliy.service.ScheduleServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * ScheduleServlet
 * @version 1.02 2016.01.02
 * @author Vitaliy Ivanytskyy
 */
@WebServlet("/views/administrator/scheduleResult")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ScheduleServlet.class);
	private ScheduleService scheduleService;
	private ScheduleItemService scheduleItemService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.trace("Getting the ApplicationContext");
		ApplicationContext appContext = (ApplicationContext) config.getServletContext().getAttribute("appContext");
		log.trace("Getting the ScheduleItemService object");
		scheduleItemService = appContext.getBean("scheduleItemService", ScheduleItemServiceImpl.class);
		log.trace("Getting the ScheduleService object");
		scheduleService = appContext.getBean("scheduleService", ScheduleServiceImpl.class);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String scheduleIdStr = request.getParameter("scheduleId");		
		String dayStr = request.getParameter("day");
		String monthStr = request.getParameter("month");
		String yearStr = request.getParameter("year");
		request.setAttribute("alarmMessage", "");
		Calendar date = new GregorianCalendar();
		Schedule schedule = null;
		if(action != null 
				&& action.equalsIgnoreCase("Add") 
				&& InputDataValidator.isDay(dayStr)
				&& InputDataValidator.isMonth(monthStr)
				&& InputDataValidator.isYear(yearStr)){
			date.set(Integer.valueOf(yearStr), (Integer.valueOf(monthStr) - 1) , Integer.valueOf(dayStr));
			try {
				log.trace("Try create schedule with date (day/month/year)=" + dayStr + "/" + monthStr + "/" + yearStr);
				schedule = scheduleService.create(date);
				log.trace("Schedule with date (day/month/year)=" + dayStr + "/" + monthStr + "/" + yearStr + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create schedule", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)) {
			long scheduleId = Long.valueOf(scheduleIdStr);
			try {
				log.trace("Try find schedule with scheduleId = " + scheduleId);
				request.setAttribute("resultSchedule", scheduleService.findById(scheduleId));
				log.trace("Schedule with scheduleId=" + scheduleId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find schedule", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")				 
				&& InputDataValidator.isDay(dayStr)
				&& InputDataValidator.isMonth(monthStr)
				&& InputDataValidator.isYear(yearStr)
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)) {
			long scheduleId = Long.valueOf(scheduleIdStr);
			date.set(Integer.valueOf(yearStr), (Integer.valueOf(monthStr) - 1) , Integer.valueOf(dayStr));
			try {
				log.trace("Try update schedule with scheduleId = " + scheduleId 
						+ " by new date (day/month/year) = " + dayStr + "/" + monthStr + "/" + yearStr);
				scheduleService.update(scheduleId, date);
				log.trace("Schedule with scheduleId = " + scheduleId 
						+ " was updated by new date (day/month/year) = " + dayStr + "/" + monthStr + "/" + yearStr);
			} catch (DataAccessException e) {
				log.error("Cannot update schedule", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Delete") 
				&& InputDataValidator.isPositiveLongNumber(scheduleIdStr)) {
			long scheduleId = Long.valueOf(scheduleIdStr);
			List<ScheduleItem> scheduleItems = new LinkedList<ScheduleItem>();
			try {
				log.trace("Try get information about scheduleItems by schedule groupId=" + scheduleId);
				scheduleItems = scheduleItemService.findByScheduleId(scheduleId);
				log.trace("Information about scheduleItems by schedule groupId=" + scheduleId + " was gotten");
			} catch (DataAccessException e) {
				log.error("Cannot get information about scheduleItems by scheduleId", e);
			}
			if(scheduleItems.isEmpty()){
				try {
					log.trace("Try delete schedule with scheduleId=" + scheduleId);
					scheduleService.deleteById(scheduleId);
					log.trace("Schedule with scheduleId=" + scheduleId + " was deleted");
				} catch (DataAccessException e) {
					log.error("Cannot delete schedule", e);
				}
			}else{
				request.setAttribute("alarmMessage", "Can not delete schedule with the schedule items");
			}
		}		
		request.setAttribute("schedule", schedule);
		try {
			log.trace("Try get all schedules for putting to request");
			request.setAttribute("allSchedules", scheduleService.findAll());
			log.trace("Schedules were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all schedules", e);
		}
		request.getRequestDispatcher("/views/administrator/adminSchedule.jsp").forward(request, response);
	}
}