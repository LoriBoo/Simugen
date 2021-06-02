package simugen.core.interfaces;

import simugen.core.defaults.EngineErrorEvent;

/**
 * Intended to allow heavy models determine what's loggable (displayed in
 * System.out, or consoles in a GUI) and what's not. Implementation needs work,
 * still.<br>
 * <br>
 * Intended Implementation: <br>
 * <li><i><b>DATA</b></i> - Data-based {@link Event}s should be logged.</li>
 * <li><i><b>DEBUG</b></i> - For debugging, anything that is tagged as
 * {@link #DEBUG} would be logged.</li>
 * <li><i><b>ERR</b></i> - Only {@link EngineErrorEvent}s would be displayed, if
 * published.</li>
 * <li><i><b>SUPRESS</b></i> - Nothing would be logged.</li>
 * 
 * @author Lorelei
 *
 */
public enum LoggingStyle
{
	DATA, DEBUG, ERR, SUPRESS
}
