package com.example.study2.user.repository;

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

import com.example.study2.user.entity.User;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users ORDER BY id DESC";
		return jdbcTemplate.query(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				return user;
			}
		});
	}

	public int createUser(User user) {
		String sql = "INSERT INTO users (name, age) VALUES(?, ?)";
		return jdbcTemplate.update(sql, user.getName(), user.getAge());
	}

	public User createUser1(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("users")
			// .usingColumns("name", "age")
			.withoutTableColumnMetaDataAccess()
			.usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", user.getName());
		parameters.put("age", user.getAge());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

		// return key.intValue();
		return new User(key.longValue(), user.getName(), user.getAge());
	}
}
