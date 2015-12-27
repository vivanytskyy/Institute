package com.gmail.ivanytskyy.vitaliy.controller;
import java.io.IOException;
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
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
import com.gmail.ivanytskyy.vitaliy.service.GroupService;
import com.gmail.ivanytskyy.vitaliy.service.GroupServiceImpl;
import com.gmail.ivanytskyy.vitaliy.service.StudentService;
import com.gmail.ivanytskyy.vitaliy.service.StudentServiceImpl;
/*
 * Task #3/2015/12/15 (web project #3)
 * GroupServlet
 * @version 1.01 2015.12.15
 * @author Vitaliy Ivanytskyy
 */
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GroupServlet.class);
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
		String groupName = request.getParameter("groupName");
		request.setAttribute("alarmMessage", "");
		Group group = null;
		if(action != null
				&& action.equalsIgnoreCase("Add")
				&& groupName != null
				&& !groupName.equals("")
				&& !groupName.trim().equals("")){
			try {
				log.trace("Try create group with name=" + groupName);
				group = groupService.create(groupName.trim());
				log.trace("Group with name=" + groupName + " was created");
			} catch (DataAccessException e) {
				log.error("Cannot create group", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Select") 
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)) {
			long groupId = Long.valueOf(groupIdStr);
			try {
				log.trace("Try find group with groupId = " + groupId);
				request.setAttribute("resultGroup", groupService.findById(groupId));
				log.trace("Group with groupId=" + groupId + " was found");
			} catch (DataAccessException e) {
				log.error("Cannot find group", e);
			}
		}else if (action != null 
				&& action.equalsIgnoreCase("Edit")
				&& groupName != null 
				&& !groupName.equals("")
				&& !groupName.trim().equals("")
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)) {
			long groupId = Long.valueOf(groupIdStr);
			try {
				log.trace("Try update group with groupId = " + groupId + " by new groupName = " + groupName);
				groupService.update(groupId, groupName.trim());
				log.trace("Group with groupId = " + groupId + " was updated by new groupName = " + groupName);
			} catch (DataAccessException e) {
				log.error("Cannot update group", e);
			}
		}else if (action != null
				&& action.equalsIgnoreCase("Delete")
				&& InputDataValidator.isPositiveLongNumber(groupIdStr)) {
			long groupId = Long.valueOf(groupIdStr);
			List<Student> students = new LinkedList<Student>();
			try {
				log.trace("Try get information about students of group with groupId=" + groupId);
				students = studentService.findByGroupId(groupId);
				log.trace("Information about students of group with groupId=" + groupId + " was gotten");
			} catch (DataAccessException e) {
				log.error("Cannot get information about students by groupId", e);
			}
			if(students.isEmpty()){
				try {
					log.trace("Try delete group with groupId=" + groupId);
					groupService.deleteById(groupId);
					log.trace("Group with groupId=" + groupId + " was deleted");
				} catch (DataAccessException e) {
					log.error("Cannot delete group", e);
				}
			}else{
				request.setAttribute("alarmMessage", "Can not delete group with the students");
			}
		}
		request.setAttribute("group", group);
		try {
			log.trace("Try get all groups for putting to request");
			request.setAttribute("allGroups", groupService.findAll());
			log.trace("Groups were gotten");
		} catch (DataAccessException e) {
			log.error("Cannot get all groups", e);
		}
		request.getRequestDispatcher("/views/administrator/adminGroup.jsp").forward(request, response);
	}
}