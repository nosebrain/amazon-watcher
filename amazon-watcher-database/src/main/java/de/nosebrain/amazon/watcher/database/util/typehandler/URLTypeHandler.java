package de.nosebrain.amazon.watcher.database.util.typehandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 
 * @author nosebrain
 */
public class URLTypeHandler implements TypeHandler {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		final URL url = (URL) parameter;
		ps.setString(i, url.toString());
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return this.convertToURL(rs.getString(columnName));
	}
	
	private URL convertToURL(final String string) {
		try {
			return new URL(string);
		} catch (MalformedURLException e) {
			// TODO: log
			return null;
		}
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.convertToURL(cs.getString(columnIndex));
	}

}
