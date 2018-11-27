package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate() {
    	    @Override
    		public void setParameters(PreparedStatement pstmt) throws SQLException {
    			pstmt.setString(1, user.getUserId());
    			pstmt.setString(2, user.getPassword());
    			pstmt.setString(3, user.getName());
    			pstmt.setString(4, user.getEmail());    			
    		}

			@Override
			public Object rowMapper(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
    	};
    	String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    	template.executeUpdate(sql);        
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
    	JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
	            pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
	            pstmt.setString(5, user.getUserId());				
			}

			@Override
			public Object rowMapper(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}    		
    	};
    	String sql = "UPDATE USERS SET userId=?, password=?, name=?, email=? WHERE userid=?";
    	template.executeUpdate(sql);        
    }

    public List<User> findAll() throws SQLException {
    	JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
			}
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
 
        String sql = "SELECT * FROM USERS";
        return (List<User>)template.executeQuery(sql);
    }

    public User findByUserId(String userId) throws SQLException {
    	JdbcTemplate template = new JdbcTemplate() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);				
			}
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
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User)template.executeQuery(sql);
    }
}
