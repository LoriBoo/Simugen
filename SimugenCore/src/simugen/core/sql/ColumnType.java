package simugen.core.sql;

public enum ColumnType {
	STRING, INT, DOUBLE;

	public String getType() {
		switch (this) {
		case STRING:
			return "text NOT NULL";
		case INT:
			return "integer";
		case DOUBLE:
			return "real";
		default:
			return null;
		}
	}
}
