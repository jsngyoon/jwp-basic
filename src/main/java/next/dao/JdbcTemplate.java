package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import core.jdbc.ConnectionManager;

public class JdbcTemplate {
    public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameters(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
    
    public void executeUpdate(String sql, Object... para) throws SQLException {
    	executeUpdate(sql, createPreparedStatementSetter(para));
    }

	private PreparedStatementSetter createPreparedStatementSetter(Object... para) {
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < para.length; i++) {
					pstmt.setObject(i+1, para[i]);
				}				
			}    		
    	};
		return pss;
	}

    public <T> T executeQuery(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws SQLException {
    	Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();  
            return rm.rowMapper(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public <T> T executeQuery(String sql, RowMapper<T> rm, Object... para) throws SQLException {
    	return executeQuery(sql, rm, createPreparedStatementSetter(para));
    }
}
