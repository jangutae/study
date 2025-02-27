package com.example.study2.basic2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.study2.basic2.dto.ScheduleResponseDto;
import com.example.study2.basic2.entity.Schedule;

@Repository
public class ScheduleRepository {

	private final JdbcTemplate jdbcTemplate;

	public ScheduleRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public ScheduleResponseDto savedSchedule(Schedule schedule) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("task", schedule.getTask());
		parameters.put("name", schedule.getName());
		parameters.put("password", schedule.getPassword());
		parameters.put("created_at", schedule.getCreatedAt());
		parameters.put("modified_at", schedule.getModifiedAt());

		Number id = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

		return ScheduleResponseDto.toDto(id.longValue(), schedule);

	}

	public List<ScheduleResponseDto> getAllSchedules(String workName, LocalDateTime modifiedAt) {
		StringBuilder query = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (workName != null) {
			query.append(" AND name = ?");
			params.add(workName);
		}

		if (modifiedAt != null) {
			query.append(" AND DATE_FORMAT(modified_at, '%Y-%m-%d') < ?");
			params.add(modifiedAt);
		}

		query.append(" ORDER BY modified_at DESC;");

		return jdbcTemplate.query(query.toString(), params.toArray(), new RowMapper<ScheduleResponseDto>() {
			@Override
			public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new ScheduleResponseDto(
					rs.getLong("id"),
					rs.getString("task"),
					rs.getString("name"),
					rs.getString("password"),
					rs.getObject("created_at", LocalDateTime.class),
					rs.getObject("modified_at", LocalDateTime.class)

				);
			}
		});

	}
}
