package com.example.study2.basic1.user.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.study2.basic1.user.entity.User;

@Repository
public class JdbcTemplateUserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users ORDER BY id DESC";
		return jdbcTemplate.query(sql, (resultSet, rowNum) -> new User(
			resultSet.getLong("id"),
			resultSet.getString("email"),
			resultSet.getString("password"),
			resultSet.getString("nickname")));
	}

	public User getUser(Integer userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{userId} , new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				return user;
			}
		});
	}

	public int createUser(User user) {
		String sql = "INSERT INTO users (email, password, nickname) VALUES(?, ?, ?)";
		return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(),  user.getNickname());
	}

	public User createUser1(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("users")
			// .usingColumns("name", "age")
			.withoutTableColumnMetaDataAccess()
			.usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", user.getEmail());
		parameters.put("password", user.getPassword());
		parameters.put("nickname", user.getNickname());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

		// return key.intValue();
		return new User(key.longValue(), user.getEmail(), user.getPassword(), user.getNickname());
	}
}
