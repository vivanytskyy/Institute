package com.gmail.ivanytskyy.vitaliy.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.gmail.ivanytskyy.vitaliy.domain.ScheduleItem;
/*
 * Task #3/2015/12/14 (web project #3)
 * JdbcTemplateScheduleItemDaoImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Repository("scheduleItemDao")
public class JdbcTemplateScheduleItemDaoImpl implements ScheduleItemDao{
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateScheduleItemDaoImpl.class.getName());	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public ScheduleItem create(
			final long groupId, final long lecturerId,
			final long classroomId, final long subjectId,
			final long lessonIntervalId, final long scheduleId) {
		log.info("Creating new scheduleItem with"
				+ " groupId/lecturerId/classroomId/subjectId/lessonIntervalId/scheduleId = " 
				+ groupId + "/" 
				+ lecturerId + "/" 
				+ classroomId + "/" 
				+ subjectId + "/" 
				+ lessonIntervalId + "/" 
				+ scheduleId);
		final String insertQuery = "INSERT INTO schedule_items "
				+ "(group_id, lecturer_id, classroom_id, subject_id, "
				+ "lesson_interval_id, schedule_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setLong(1, groupId);
		            ps.setLong(2, lecturerId);
		            ps.setLong(3, classroomId);
		            ps.setLong(4, subjectId);
		            ps.setLong(5, lessonIntervalId);
		            ps.setLong(6, scheduleId);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newScheduleItemId = keyHolder.getKey().longValue();
		log.info("Return ScheduleItem object by calling findScheduleItemById method");
		return findById(newScheduleItemId);
	}
	@Override
	public ScheduleItem findById(long scheduleItemId) {
		log.info("Getting scheduleItem by scheduleItemId = " + scheduleItemId);
		String query = "SELECT * FROM schedule_items WHERE id = ?";
		log.info("Return ScheduleItem object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{scheduleItemId},
		        new ScheduleItemMapper());
	}
	@Override
	public List<ScheduleItem> findByScheduleId(long scheduleId) {
		log.info("Getting scheduleItems by scheduleId = " + scheduleId);
		String query = "SELECT * FROM schedule_items WHERE schedule_id = ?";
		log.info("Return List<ScheduleItem> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{scheduleId},
		        new ScheduleItemMapper());
	}
	@Override
	public List<ScheduleItem> findAll() {
		log.info("Getting all scheduleItems");
		String query = "SELECT * FROM schedule_items";
		log.info("Return List<ScheduleItem> (all scheduleItems) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new ScheduleItemMapper());
	}
	@Override
	public void moveToAnotherSchedule(long scheduleItemId, long scheduleId) {
		log.info("Moving scheduleItem with scheduleItemid = " + scheduleItemId 
				+ " to schedule with scheduleId = " + scheduleId);
		String query = "UPDATE schedule_items SET schedule_id = ? WHERE id = ?";
		log.info("Move student by jdbcTemplate.update");
		this.jdbcTemplate.update(query, scheduleId, scheduleItemId);
	}	
	@Override
	public boolean isExists(long scheduleItemId) {
		log.info("Checking if exists the scheduleItem with scheduleItemId = " + scheduleItemId);
		String query = "SELECT count(*) FROM schedule_items WHERE id = ?";
		Long amount = this.jdbcTemplate.queryForObject(query, Long.class, scheduleItemId);
		boolean result = (amount != null && amount > 0);
		log.info("Return result of exists checking = " + result);
		return result;
	}
	@Override
	public void update(
			long scheduleItemId, long newGroupId,
			long newLecturerId, long newClassroomId,
			long newSubjectId, long newLessonIntervalId,
			long newScheduleId) {
		log.info("Updating scheduleItem with scheduleItemId = " + scheduleItemId 
				+ " by new groupId/lecturerId/classroomId/subjectId/lessonIntervalId/newScheduleId = " 
				+ newGroupId + "/" 
				+ newLecturerId + "/" 
				+ newClassroomId + "/" 
				+ newSubjectId + "/" 
				+ newLessonIntervalId + "/"
				+ newScheduleId);
		String query = "UPDATE schedule_items SET"
				+ " group_id = ?,"
				+ " lecturer_id = ?,"
				+ " classroom_id = ?,"
				+ " subject_id = ?,"
				+ " lesson_interval_id = ?,"
				+ " schedule_id = ?"
				+ " WHERE id = ?";
		log.info("Update scheduleItem by jdbcTemplate.update");
		this.jdbcTemplate.update(
				query,
				newGroupId, newLecturerId, newClassroomId, 
				newSubjectId, newLessonIntervalId, newScheduleId, 
				scheduleItemId);
	}
	@Override
	public void deleteById(long scheduleItemId) {
		log.info("Delete scheduleItem by scheduleItemId = " + scheduleItemId);
		String query = "DELETE FROM schedule_items WHERE id = ?";
		log.info("Delete scheduleItem by jdbcTemplate.update");
		this.jdbcTemplate.update(query, scheduleItemId);

	}
	@Override
	public void deleteAll() {
		log.info("Delete all scheduleItems");
		String query = "DELETE FROM schedule_items";
		log.info("Delete all scheduleItems by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class ScheduleItemMapper implements RowMapper<ScheduleItem>{
		public ScheduleItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleItem scheduleItem = new ScheduleItem();
			scheduleItem.setScheduleItemId(rs.getLong("id"));
			scheduleItem.setGroupId(rs.getLong("group_id"));
			scheduleItem.setLecturerId(rs.getLong("lecturer_id"));
			scheduleItem.setClassroomId(rs.getLong("classroom_id"));
			scheduleItem.setSubjectId(rs.getLong("subject_id"));
			scheduleItem.setLessonIntervalId(rs.getLong("lesson_interval_id"));
			scheduleItem.setScheduleId(rs.getLong("schedule_id"));
			return scheduleItem;
		}		
	}
}