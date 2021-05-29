package simugen.core.sql;

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
