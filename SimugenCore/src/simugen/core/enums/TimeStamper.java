package simugen.core.enums;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Lorelei
 * @deprecated
 */
public enum TimeStamper {
	AMPM, SHORT_DATE_AMPM, LONG_DATE_AMPM, CUSTOM;

	private static final SimpleDateFormat FORMAT_AMPM = new SimpleDateFormat("hh:mm:ss aa");

	private static final SimpleDateFormat FORMAT_SHORT_DATE_AMPM = new SimpleDateFormat("MM/dd/yy hh:mm:ss aa");

	private static final SimpleDateFormat FORMAT_LONG_DATE_AMPM = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss aa");

	private static SimpleDateFormat formatCustom = null;

	private long epoch = 0L;

	public void setEpoch(long epoch) {
		this.epoch = epoch;
	}

	public void setCustom(SimpleDateFormat format) {
		if (!this.equals(CUSTOM)) {
			throw new IllegalAccessError();
		}

		formatCustom = format;
	}

	public String getTimeStamp(long time) {
		final Date date = new Date(time + epoch);

		switch (this) {
		case AMPM:
			return FORMAT_AMPM.format(date);
		case CUSTOM:
			if (formatCustom == null) {
				throw new IllegalStateException();
			}
			return formatCustom.format(date);
		case LONG_DATE_AMPM:
			return FORMAT_LONG_DATE_AMPM.format(date);
		case SHORT_DATE_AMPM:
			return FORMAT_SHORT_DATE_AMPM.format(date);
		default:
			return null;
		}
	}
}
