package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
     	JdbcTemplate template = new JdbcTemplate();
    	String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    	template.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());        
    }

    public void update(User user) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate();
    	String sql = "UPDATE USERS SET userId=?, password=?, name=?, email=? WHERE userid=?";
    	template.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId() );        
    }

    public List<User> findAll() throws SQLException {
    	RowMapper<List<User>> rm = new RowMapper<List<User>>() {
			@Override
			public List<User> rowMapper(ResultSet rs) throws SQLException {
				ArrayList<User> users = new ArrayList<User>();
				while (rs.next()) {
	                users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                        rs.getString("email")));
	            }
	            return users;
			}    		
    	};
    	JdbcTemplate template = new JdbcTemplate();     
 
        String sql = "SELECT * FROM USERS";
        return template.executeQuery(sql, rm);
    }

    public User findByUserId(String userId) throws SQLException {
    	RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User rowMapper(ResultSet rs) throws SQLException {
	            User user = null;
	            if (rs.next()) {
	                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                        rs.getString("email"));
	            }
	            return user;
			}    		
    	};
    	JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return template.executeQuery(sql, rm, userId);
    }
}
