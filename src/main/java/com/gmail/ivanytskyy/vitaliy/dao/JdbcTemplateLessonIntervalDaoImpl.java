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
import com.gmail.ivanytskyy.vitaliy.domain.LessonInterval;
/*
 * Task #3/2015/12/14 (web project #3)
 * JdbcTemplateLessonIntervalDaoImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Repository("lessonIntervalDao")
public class JdbcTemplateLessonIntervalDaoImpl implements LessonIntervalDao{
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateLessonIntervalDaoImpl.class.getName());	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public LessonInterval create(final String lessonStart,
			final String lessonFinish) {
		log.info("Creating new lesson interval with start = " + lessonStart + " and finish = " + lessonFinish);
		final String insertQuery = "INSERT INTO lesson_intervals (lesson_start, lesson_finish) VALUES (?, ?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, lessonStart);
		            ps.setString(2, lessonFinish);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newLessonIntervalId = keyHolder.getKey().longValue();
		log.info("Return LessonInterval object by calling findLessonIntervalById method");
		return findById(newLessonIntervalId);
	}
	@Override
	public LessonInterval findById(long lessonIntervalId) {
		log.info("Getting lesson interval by id = " + lessonIntervalId);
		String query = "SELECT * FROM lesson_intervals WHERE id = ?";
		log.info("Return LessonInterval object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{lessonIntervalId},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findByLessonStart(String lessonStart) {
		log.info("Getting lesson intervals by start = " + lessonStart);
		String query = "SELECT lesson_start, lesson_finish, id FROM lesson_intervals WHERE lesson_start = ?";
		log.info("Return List<LessonInterval> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{lessonStart},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findByLessonFinish(
			String lessonFinish) {
		log.info("Getting lesson intervals by finish = " + lessonFinish);
		String query = "SELECT lesson_start, lesson_finish, id FROM lesson_intervals WHERE lesson_finish = ?";
		log.info("Return List<LessonInterval> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
				new Object[]{lessonFinish},
		        new LessonIntervalMapper());
	}
	@Override
	public List<LessonInterval> findAll() {
		log.info("Getting all lesson intervals");
		String query = "SELECT * FROM lesson_intervals";
		log.info("Return List<LessonInterval> (all lessonIntervals) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new LessonIntervalMapper());
	}	
	@Override
	public boolean isExists(long lessonIntervalId) {
		log.info("Checking if exists the lessonInterval with lessonIntervalId = " + lessonIntervalId);
		String query = "SELECT count(*) FROM lesson_intervals WHERE id = ?";
		Long amount = this.jdbcTemplate.queryForObject(query, Long.class, lessonIntervalId);
		boolean result = (amount != null && amount > 0);
		log.info("Return result of exists checking = " + result);
		return result;
	}
	@Override
	public void update(long lessonIntervalId,
			String newLessonStart, String newLessonFinish) {
		log.info("Updating lessonInterval with lessonIntervalId = " + lessonIntervalId);
		String query = "UPDATE lesson_intervals SET lesson_start = ?, lesson_finish = ? WHERE id = ?";
		log.info("Update lessonInterval by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newLessonStart, newLessonFinish, lessonIntervalId);
	}
	@Override
	public void deleteById(long lessonIntervalId) {
		log.info("Delete lessonInterval by lessonIntervalId = " + lessonIntervalId);
		String query = "DELETE FROM lesson_intervals WHERE id = ?";
		log.info("Delete lessonInterval by jdbcTemplate.update");
		this.jdbcTemplate.update(query, lessonIntervalId);
	}
	@Override
	public void deleteAll() {
		log.info("Delete all lessonIntervals");
		String query = "DELETE FROM lesson_intervals";
		log.info("Delete all lessonIntervals by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class LessonIntervalMapper implements RowMapper<LessonInterval>{
		public LessonInterval mapRow(ResultSet rs, int rowNum) throws SQLException {
			LessonInterval lessonInterval = new LessonInterval();
			lessonInterval.setLessonStart(rs.getString("lesson_start"));
			lessonInterval.setLessonFinish(rs.getString("lesson_finish"));
			lessonInterval.setLessonIntervalId(rs.getLong("id"));
			return lessonInterval;
		}		
	}
}