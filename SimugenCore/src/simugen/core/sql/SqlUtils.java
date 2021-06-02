package simugen.core.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A set of helpful SQL utilities that I could not find already existing
 * somewhere.
 * 
 * @author Lorelei
 *
 */
public class SqlUtils
{

	/**
	 * This utility might be deprecated.
	 * 
	 * @param millis
	 *            Milliseconds since the java {@link Calendar} Epoch (January
	 *            1st 1970, 0:00:0.00)
	 * @return A {@link String} formatted output of the date, that SQL likes to
	 *         use.
	 */
	public static String getFormattedTimeStamp(long millis)
	{
		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(millis);

		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		return format.format(cal.getTime());
	}

	/**
	 * Returns a readable {@link String} value of the data inserted. Used mainly
	 * to capture DATE, TIME, And TIMESTAMP column types.
	 * 
	 * @param set
	 *            The {@link ResultSet}.
	 * @param col
	 *            The column we care about.
	 * @return Readable {@link String} value of the column.
	 * @throws SQLException
	 */
	public static String getString(ResultSet set, int col) throws SQLException
	{
		ResultSetMetaData rsmd = set.getMetaData();

		try
		{
			switch (rsmd.getColumnTypeName(col))
			{
			case "DATE":
				return set.getDate(col).toString();
			case "TIME":
				return set.getTime(col).toString();
			case "TIMESTAMP":
				return set.getTimestamp(col).toString();
			default:
				return set.getString(col);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
