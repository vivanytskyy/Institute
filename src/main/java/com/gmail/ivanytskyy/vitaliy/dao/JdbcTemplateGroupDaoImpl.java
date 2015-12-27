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
import com.gmail.ivanytskyy.vitaliy.domain.Group;
/*
 * Task #3/2015/12/14 (web project #3)
 * JdbcTemplateGroupDaoImpl class
 * @version 1.01 2015.12.14
 * @author Vitaliy Ivanytskyy
 */
@Repository("groupDao")
public class JdbcTemplateGroupDaoImpl implements GroupDao {
	private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger(JdbcTemplateGroupDaoImpl.class.getName());
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Group create(final String groupName) {
		log.info("Creating new group with groupName = " + groupName);
		final String insertQuery = "INSERT INTO groups (name) VALUES (?)";
		log.info("Creating keyHolder for storage auto-generated id");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		log.info("jdbcTemplate.update");
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1, groupName);
		            return ps;
		        }
		    },
		    keyHolder);
		log.info("Auto-generated id was retrieved");
		Long newGroupId = keyHolder.getKey().longValue();
		log.info("Return Group object by calling findGroupById method");
		return findById(newGroupId);
	}
	@Override
	public Group findById(long groupId) {
		log.info("Getting group by groupId = " + groupId);
		String query = "SELECT * FROM groups WHERE id = ?";
		log.info("Return Group object by jdbcTemplate.queryForObject");
		return this.jdbcTemplate.queryForObject(
		        query,
		        new Object[]{groupId},
		        new GroupMapper());
	}	
	@Override
	public List<Group> findByName(String groupName) {
		log.info("Getting groups by groupName = " + groupName);
		String sql = "SELECT name, id FROM groups WHERE name = ?";
		log.info("Return List<Group> by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				sql,
				new Object[]{groupName},
		        new GroupMapper());
	}
	@Override
	public List<Group> findAll() {
		log.info("Getting all groups");
		String query = "SELECT * FROM groups";
		log.info("Return List<Group> (all groups) by jdbcTemplate.query");
		return this.jdbcTemplate.query(
				query,
		        new GroupMapper());
	}	
	@Override
	public boolean isExists(long groupId) {
		log.info("Checking if exists the group with groupId = " + groupId);
		String query = "SELECT count(*) FROM groups WHERE id = ?";
		Long amount = this.jdbcTemplate.queryForObject(query, Long.class, groupId);
		boolean result = (amount != null && amount > 0);
		log.info("Return result of exists checking = " + result);
		return result;
	}
	@Override
	public void update(long groupId, String newGroupName) {
		log.info("Updating group with groupId = " + groupId 
				+ " by new groupName = " + newGroupName);
		String query = "UPDATE groups SET name = ? WHERE id = ?";
		log.info("Update group by jdbcTemplate.update");
		this.jdbcTemplate.update(query, newGroupName, groupId);
	}
	@Override
	public void deleteById(long groupId) {
		log.info("Delete group by groupId = " + groupId);
		String query = "DELETE FROM groups WHERE id = ?";
		log.info("Delete group by jdbcTemplate.update");
		this.jdbcTemplate.update(query, groupId);
	}
	@Override
	public void deleteAll() {
		log.info("Delete all groups");
		String query = "DELETE FROM groups";
		log.info("Delete all groups by jdbcTemplate.update");
		this.jdbcTemplate.update(query);
	}
	private static final class GroupMapper implements RowMapper<Group>{		
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group group = new Group();
			group.setGroupName(rs.getString("name"));
			group.setGroupId(rs.getLong("id"));
			return group;
		}		
	}
}