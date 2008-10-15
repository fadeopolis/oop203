package oop1;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A collection for Data objects, every time a object is added 
 * @author fader
 *
 */
public class DataManager {

	/**
	 * Stores the given Data Objects
	 */
	protected TreeSet<Data> data;
	
	/**
	 * The last value inserted into this manager, needed for data consistency checks
	 */
	private Data lastEntry;
	
	/**
	 * The threshhold for the data's values to check against
	 */
	private double threshold;
	
	/**
	 * Needed in the add Method
	 */
	private Calendar currentTime;
	
	/**
	 * creates a new DataManager with an empty DataSet and a threshhold of 50
	 */
	public DataManager() {
		this( 50d );
	}
	
	/**
	 * Creates a new DataManager with an empty DataSet and the specified threshhold
	 * @param threshhold the threshhold for the maintained values to check against
	 */
	public DataManager( double threshhold ) {
		data = new TreeSet<Data>();
		
		currentTime = new GregorianCalendar();
		
		lastEntry = new Data(0, new GregorianCalendar(0,0,0,0,0,0));
		this.threshold = threshhold;
	}

	/**
	 * Eingabe von Messdaten
	 * @param value
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return The error messages that where created during the adding proccess, null if are were none
	 */
	public String add( double value, int year, int month, int day, int hour, int minute, int second ) {
		try {
			return add( new Data( value, year, month, day, hour, minute, second ) );
		} catch ( IllegalArgumentException e ) {
			return e.getMessage();
		}
	}
	
	/**
	 * Eingabe von Messdaten
	 * @param d Data Object to add to this Manager
	 * @return The error messages that where created during the adding proccess, null if are were none
	 */
	public String add( Data d ) {
		String message = null;	
		
		if ( data.contains(d) ) {
//			if ( message == null ) {
//				message = "There already is an entry for the time of " + d;
//			} else {
//				message += "\nThere already is an entry for the time of " + d;
//			}
			message = "There already is an entry for the time of " + d;
			return message;
		}
			
		data.add(d);
	
		// Create error messages
			
		
		// the inserted data's value was twice as high as the threshhold
		//Der gerade erfasste Messwert ist mehr als doppelt so hoch wie der Grenzwert. 
		if ( d.value > (threshold*2) ) {
			if ( message == null ) {
				message = "The value of " + d + " is higher than the threshhold ("+ threshold + ") times 2";
			} else {
				message += "\nThe value of " + d + " is higher than the threshhold ("+ threshold + ") times 2";
			}
		}

		// the last two entrys where above threshhold
		//Die letzten beiden Messwerte übersteigen jeweils den Grenzwert. 
		if ( d.value > threshold && lastEntry.value > threshold ) {
			if ( message == null ) {
				message = "The last two values where higher than threshhold";
			} else {
				message += "\nThe last two values where higher than threshhold";
			}
		}
		
		// check if more than halve of the entrys of the last hours where above threshhold
		if ( data.size() > 1 ) {
						
			// only look for entrys from the last hour
			currentTime.setTimeInMillis(System.currentTimeMillis());
			currentTime.add(Calendar.HOUR_OF_DAY, 1);
			
			// An iterator to iterate over the dataSet, the newest values are at the "top"
			Iterator<Data> i = data.descendingIterator();
			Data foo = null;
			int checkedEntrys = 0;
			int badEntrys = 0;
			
			do {
				foo = i.next();
				++checkedEntrys;
				if ( foo.getValue() > threshold ) ++badEntrys;
//				System.out.println(foo + " " + badEntrys + " of " + checkedEntrys );
			} while ( i.hasNext() && foo.getTime().before(currentTime) );

			//Mehr als die Hälfte der innerhalb der letzten Stunde gemessenen Werte übersteigt den Grenzwert,
			//vorausgesetzt es hat in diesem Zeitraum mindestens fünf Messungen gegeben.
			if ( badEntrys > checkedEntrys/2 && checkedEntrys > 4 ) {
				if ( message == null ) {
					message = (badEntrys + " of " + checkedEntrys + " entrys in the last hour were above the threshhold (" + threshold + ") ");
				} else {
					message += "\n" + badEntrys + " of " + checkedEntrys + " entrys in the last hour were above the threshhold (" + threshold + ") ";
				}
			}		
		}
	
		lastEntry = d;
		
		return message;
	}

	/**
	 * Auflistung aller in einem bestimmten Zeitraum gemessenen Werte (mit Ausgabe von Datum und Uhrzeit jeder Messung). 
	 * @param from start time of the time window
	 * @param to end time of the time window
	 * @return all the elements in the time window defined by from and to, null if there are none
	 */
	public String getData( Calendar from, Calendar to ) {
		if ( from.after(to) ) return "Invalid time window";
		
		StringBuilder s = null;
		
		int i = 0;
		for ( Data d : data ) {
			if ( d.getTime().after(from) && d.getTime().before(to) ) {
				if ( s == null ) {
					s = new StringBuilder( d.toString() );
				} else {
					s.append( "\n" + d );
				}
				i++;
			}
		}
		
		if ( i > 0 ) s.insert(0, i + " entries in the given time period\n");
		
		return s == null ? null : s.toString();
	}
	
	/**
	 * Abfrage des Durchschnitts aller in einem bestimmten Zeitraum gemessenen Werte. 
	 * @param from start time of the time window
	 * @param to end time of the time window
	 * @return the average of the values of all the elements in the time window defined by from and to, zero if there are none
	 */
	public double getAverage( Calendar from, Calendar to ) {
		if ( from.after(to) ) throw new IllegalArgumentException( "Invalid time window" );
		
		double average = 0;
		
		int i = 0;
		for ( Data d : data ) {
			if ( d.getTime().after(from) && d.getTime().before(to) ) {
				average += d.getValue();
				i++;
			}
		}
		
		return average/i;
	}
	
	/**
	 * 
	 * @param time Remove the first Data Object with the given Time
	 * @return true if a Data Object was removed, false else
	 */
	public boolean remove( Calendar time ) {
		for ( Data d : data ) {
			if ( d.getTime().equals(time)) {			
				return data.remove(d);
			}
		}
		return false;
	}
	
	/**
	 * set the threshhold of this DataManager
	 */
	public double getThreshold() {
		return this.threshold;
	}
	
	/**
	 * get the threshhold of this DataManager
	 * @param value the threshhold of this DataManager
	 */
	public void setThreshold( double value ) {
		this.threshold = value;
	}
	
	/**
	 * @see java.lang.Object
	 */
	@Override
	public String toString() {
		return data.toString();
	}

	/**
	 * Deletes all data in this DataManager
	 */
	public void clear() {
		data.clear();
	}
	
	/**
	 * Checks if this DataManager is empty
	 * @return true if this DataManager if empty
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 * return an Iterator that traverses this DataManager
	 * from the newest to the oldest entry
	 * @return an Iterator that traverses this DataManager
	 *  from the newest to the oldest entry
	 */
	public Iterator<Data> iterator() {
		return data.descendingIterator();
	}
	
	/**
	 * 
	 * @param c Remove the given data from this manager
	 * @return true if a Data Object was removed, false otherwise
	 */
	public boolean remove(Object o) {
		return data.remove(o);
	}
	
	/**
	 * 
	 * 
	 */
	public int size() {
		return data.size();
	}
	
}