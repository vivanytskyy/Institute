package com.gmail.ivanytskyy.vitaliy.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gmail.ivanytskyy.vitaliy.dao.StudentDao;
import com.gmail.ivanytskyy.vitaliy.domain.Student;
/*
 * Task #3/2015/12/14 (web project #2)
 * StudentServiceImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;
	private static final Logger log = Logger.getLogger(StudentServiceImpl.class);
	@Override
	public Student create(String studentName, long groupId){
		log.info("Creating new student with studentName = " + studentName 
				+ " and groupId = " + groupId);
		Student student = studentDao.create(studentName, groupId);
		log.trace("Student was created");
		return student;
	}
	@Override
	public Student findById(long studentId){
		log.info("Getting student by studentId = " + studentId);
		Student student = studentDao.findById(studentId);
		log.trace("Student was gotten");
		return student;
	}
	@Override
	public List<Student> findByName(String studentName){
		log.info("Getting students by studentName = " + studentName);
		List<Student> students = studentDao.findByName(studentName);
		log.trace("Students were gotten");
		return students;
	}
	@Override
	public List<Student> findByGroupId(long groupId){
		log.info("Getting students of group with groupId = " + groupId);
		List<Student> students = studentDao.findByGroupId(groupId);
		log.trace("Students were gotten");
		return students;
	}
	@Override
	public List<Student> findAll(){
		log.info("Getting all students");
		List<Student> students = studentDao.findAll();
		log.trace("Students were gotten");
		return students;
	}
	@Override
	public void moveToAnotherGroup(long studentId, long anotherGroupId){
		log.info("Moving student with studentId = " + studentId 
				+ " to new group with groupId = " + anotherGroupId);
		studentDao.moveToAnotherGroup(studentId, anotherGroupId);
		log.trace("Student with studentId = " + studentId + " was moved to group "
				+ "with groupId = " + anotherGroupId);
	}	
	@Override
	public boolean isExists(long studentId) {
		log.info("Checking if exists the student with studentId = " + studentId);
		boolean result = studentDao.isExists(studentId);
		log.trace("Existence was checked, result = " + result);
		return result;
	}
	@Override
	public void update(long studentId, String newStudentName){
		log.info("Updating student  with studentId = " + studentId 
				+ " by new studentName = " + newStudentName);
		studentDao.update(studentId, newStudentName);
		log.trace("Student was updated");
	}
	@Override
	public void deleteById(long studentId){
		log.info("Remove student by studentId = " + studentId);
		studentDao.deleteById(studentId);
		log.trace("Student with studentId = " + studentId + " was removed");
	}
}