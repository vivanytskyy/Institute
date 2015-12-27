package com.gmail.ivanytskyy.vitaliy.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.gmail.ivanytskyy.vitaliy.domain.Schedule;
/*
 * Task #3/2015/12/14 (web project #3)
 * JdbcTemplateScheduleDaoImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Repository("scheduleDao")
public class JdbcTemplateScheduleDaoImpl implements ScheduleDao{
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateScheduleDaoImpl.class.getName());	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Schedule create(final Calendar scheduleDate) {
		log.info("Creating new schedule with scheduleDate = "
				+ scheduleDate.get(Calendar.DAY_OF_MONTH)
				+ "/" + (scheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + scheduleDate.get(Calendar.YEAR));
		final String insertQuery = "INSERT INTO schedules (schedule_date) VALUES (?)";
		final Date dateSql = new Date(scheduleDate.getTimeInMillis());
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setDate(1, dateSql);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newScheduleId = keyHolder.getKey().longValue();
		log.info("Return Schedule object by calling findScheduleById method");
		return findById(newScheduleId);
	}
	@Override
	public Schedule findById(long scheduleId) {
		log.info("Getting schedule by scheduleId = " + scheduleId);
		String query = "SELECT * FROM schedules WHERE id = ?";
		log.info("Return Schedule object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{scheduleId},
		        new ScheduleMapper());
	}
	@Override
	public List<Schedule> findByDate(Calendar scheduleDate) {
		log.info("Getting schedules by scheduleDate = "
				+ scheduleDate.get(Calendar.DAY_OF_MONTH) 
				+ "/" + (scheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + scheduleDate.get(Calendar.YEAR));
		String query = "SELECT schedule_date, id FROM schedules WHERE schedule_date = ?";
		Date dateSql = new Date(scheduleDate.getTimeInMillis());
		log.info("Return List<Schedule> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{dateSql},
		        new ScheduleMapper());
	}
	@Override
	public List<Schedule> findAll() {
		log.info("Getting all schedules");
		String query = "SELECT * FROM schedules";
		log.info("Return List<Schedule> (all schedules) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new ScheduleMapper());
	}	
	@Override
	public boolean isExists(long scheduleId) {
		log.info("Checking if exists the schedule with scheduleId = " + scheduleId);
		String query = "SELECT count(*) FROM schedules WHERE id = ?";
		Long amount = this.jdbcTemplate.queryForObject(query, Long.class, scheduleId);
		boolean result = (amount != null && amount > 0);
		log.info("Return result of exists checking = " + result);
		return result;
	}
	@Override
	public void update(long scheduleId, Calendar newScheduleDate) {
		log.info("Updating schedule with scheduleId = " + scheduleId 
				+ " by new scheduleDate = "
				+ newScheduleDate.get(Calendar.DAY_OF_MONTH) 
				+ "/" + (newScheduleDate.get(Calendar.MONTH) + 1)
				+ "/" + newScheduleDate.get(Calendar.YEAR));
		String query = "UPDATE schedules SET schedule_date = ? WHERE id = ?";
		Date dateSql = new Date(newScheduleDate.getTimeInMillis());
		log.info("Update schedule by jdbcTemplate.update");		
		this.jdbcTemplate.update(query, dateSql, scheduleId);
	}
	@Override
	public void deleteById(long scheduleId) {
		log.info("Delete schedule by scheduleId = " + scheduleId);
		String query = "DELETE FROM schedules WHERE id = ?";
		log.info("Delete schedule by jdbcTemplate.update");
		this.jdbcTemplate.update(query, scheduleId);
	}
	@Override
	public void deleteAll() {
		log.info("Delete all schedules");
		String query = "DELETE FROM schedules";
		log.info("Delete all schedules by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class ScheduleMapper implements RowMapper<Schedule>{
		public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			Calendar calendar = new GregorianCalendar();
			Schedule schedule = new Schedule();
			Date resultDateSql = rs.getDate("schedule_date");
			calendar.setTime(resultDateSql);
			schedule.setScheduleDate(calendar);
			schedule.setScheduleId(rs.getLong("id"));
			return schedule;
		}		
	}
}