package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
    			pstmt.setString(2, user.getPassword());
    			pstmt.setString(3, user.getName());
    			pstmt.setString(4, user.getEmail());  				
			}    		
    	};
    	JdbcTemplate template = new JdbcTemplate();
    	String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    	template.executeUpdate(sql, pss);        
    }

    public void update(User user) throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
	            pstmt.setString(5, user.getUserId());					
			}    		
    	};
    	JdbcTemplate template = new JdbcTemplate();
    	String sql = "UPDATE USERS SET userId=?, password=?, name=?, email=? WHERE userid=?";
    	template.executeUpdate(sql, pss);        
    }

    public List<User> findAll() throws SQLException {
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
			}};
    	RowMapper rm = new RowMapper() {
			@Override
			public Object rowMapper(ResultSet rs) throws SQLException {
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
        return (List<User>)template.executeQuery(sql, rm, pss);
    }

    public User findByUserId(String userId) throws SQLException {
       	PreparedStatementSetter pss = new PreparedStatementSetter() {
    			@Override
    			public void setParameters(PreparedStatement pstmt) throws SQLException {
    				pstmt.setString(1, userId);
    			}};
    	RowMapper rm = new RowMapper() {
			@Override
			public Object rowMapper(ResultSet rs) throws SQLException {
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
        return (User)template.executeQuery(sql, rm, pss);
    }
}
