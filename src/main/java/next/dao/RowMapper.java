package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
	T rowMapper(ResultSet rs) throws SQLException;
}
