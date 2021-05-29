package simugen.core.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqlUtils {
	public static String getFormattedTimeStamp(long millis) {
		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(millis);

		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		return format.format(cal.getTime());
	}

	public static Date getTimeStamp(long millis) {
		return new Timestamp(millis);
	}

	public static String getString(ResultSetMetaData rsmd, ResultSet set, int col) {
		try {
			switch (rsmd.getColumnTypeName(col)) {
			case "DATE":
				return set.getDate(col).toString();
			case "TIME":
				return set.getTime(col).toString();
			case "TIMESTAMP":
				return set.getTimestamp(col).toString();
			default:
				return set.getString(col);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Time getTime(Long millis) {

		return new Time(millis);
	}

	public static Date getDate(Long millis) {
		return new Date(millis);
	}
}
