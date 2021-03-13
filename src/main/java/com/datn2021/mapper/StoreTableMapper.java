package com.datn2021.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.datn2021.model.StoreTable;

public class StoreTableMapper implements RowMapper<StoreTable> {

	@Override
	public StoreTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		StoreTable newtable = new StoreTable();
		newtable.setId(rs.getLong("id"));
		newtable.setTableName(rs.getString("table_name"));
		return newtable;
	}
	
}
