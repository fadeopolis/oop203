package oop1;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Encapsulation for a double value with a associated time stored in Calendar object
 * Every field is final, there are no setters
 * @author fader
 *
 */
public class Data implements Comparable<Data> {
	
	protected final Calendar time;
	protected final double value;
	
	/**
	 * Create a new Data object with the given value and the current System Time
	 * the time is stored in a GregorianCalendar 
	 * @param value the value of the new Data object
	 * 
	 * @see java.util.Calendar
 	 * @see java.util.GregorianCalendar
 	 */
	public Data( double value ) {
		this( value, new GregorianCalendar());
	}
	
	/**
	 * Create a new Data object with the given value and time
	 * the time is stored in a GregorianCalendar 
	 * @param value the value of the new Data object
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * 
	 * @see java.util.Calendar
 	 * @see java.util.GregorianCalendar
	 */
	public Data( double value, int year, int month, int day, int hour, int minute, int second ) {
		this( value, new GregorianCalendar( year, month, day, hour, minute, second ) );
	}
	
	/**
	 * Create a new Data object with the given value and time
	 * @param value
	 * @param time
	 * 
	 * @see java.util.Calendar
 	 * @see java.util.GregorianCalendar
	 */
	public Data( double value, Calendar time ) {
		if ( value < 0 || value > 300 || value == Double.NaN) 
			throw new IllegalArgumentException(value + " is not a valid data value, should be 0 < value < 300");
		
		this.value = value;
		this.time = time;
	}
	
	/**
	 * Returns this Data's Calendar object
	 * @return this Data's Calendar object
	 */
	public Calendar getTime() {
		return time;
	}

	/**
	 * Returns this Data's value
	 * @return this Data's value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @see java.lang.Comparable
	 */
	@Override
	public int compareTo(Data d) {
		return time.compareTo(d.getTime());
//		return (int) Math.signum( d.getValue() - getValue() );
	}
	
	/**
	 * @see java.lang.Object
	 */
	public String toString() {
		
		return "(Value : " + value + " , Time : " + 
				time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND) + " " +
				time.get(Calendar.DAY_OF_MONTH) + "." + time.get(Calendar.MONTH) + "." + time.get(Calendar.YEAR )
				+ ")";
	}
}
