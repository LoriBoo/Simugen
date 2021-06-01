package simugen.core.sql;

/**
 * Enumerators for column types for SQL.
 * 
 * @author Lorelei
 *
 */
public enum ColumnType {
	STRING, INT, DOUBLE, TIME, DATE, TIMESTAMP;

	public String getType() {
		switch (this) {
		case STRING:
			return "TEXT";
		case INT:
			return "INTEGER";
		case DOUBLE:
			return "DOUBLE";
		case TIME:
			return "TIME";
		case DATE:
			return "DATE";
		case TIMESTAMP:
			return "TIMESTAMP";
		default:
			return null;
		}
	}
}
