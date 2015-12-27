package com.gmail.ivanytskyy.vitaliy.service;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.GroupDao;
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
import com.gmail.ivanytskyy.vitaliy.domain.Group;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
/*
 * Task #3/2015/12/14 (web project #3)
 * GroupServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private StudentDao studentDao;
	private static final Logger log = Logger.getLogger(GroupServiceImpl.class.getName());
	@Override
	public Group create(String groupName){
		log.info("Creating group with groupName = " + groupName);
		Group group = groupDao.create(groupName);
		log.trace("Group with groupName = " + groupName + " was created");
		return group;
	}
	@Override
	public Group findById(long groupId){
		log.info("Getting group by groupId = " + groupId);
		Group group = groupDao.findById(groupId);
		log.trace("Group was gotten");
		return group;
	}
	@Override
	public List<Group> findByName(String groupName){
		log.info("Getting groups by groupName=" + groupName);
		List<Group> groups = groupDao.findByName(groupName);
		log.trace("Groups were gotten");
		return groups;
	}
	@Override
	public List<Group> findAll(){
		log.info("Getting all groups");
		List<Group> groups = groupDao.findAll();
		log.trace("Groups were gotten");
		return groups;
	}	
	@Override
	public boolean isExists(long groupId){
		log.info("Checking if exists the group with groupId = " + groupId);
		boolean result = groupDao.isExists(groupId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long groupId, String newGroupName){
		log.info("Updating group  with groupId = " + groupId + " by new grouptName = " + newGroupName);
		groupDao.update(groupId, newGroupName);
		log.trace("Group was updated");
	}
	@Override
	public void deleteById(long groupId){
		log.info("Remove group by groupId = " + groupId);
		log.trace("Try get information about students of group with groupId=" + groupId);
		List<Student> students = studentDao.findByGroupId(groupId);
		log.trace("Information about students of group with groupId = " + groupId + " was gotten");
		if(students.isEmpty() || students == null){
			log.trace("Remove group with groupId = " + groupId);
			groupDao.deleteById(groupId);
			log.trace("Group with groupId = " + groupId + " was deleted");
		}else{
			log.trace("Can not delete group with the students");
		}
	}
}