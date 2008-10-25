import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
	
public class Filter implements Serializable {
	
	private static final long serialVersionUID = 643414923216772607L;

	protected HashSet<MeasureUnit> r;
	
	protected double lastEntry=0;
	protected double threshold=0;
	protected Calendar currentTime;
	
	public Filter(){
		r = new HashSet<MeasureUnit>();
		r.clear();
	}
	
	public Set<MeasureUnit> getFiltered(Set<MeasureUnit> humidity, Calendar from, Calendar to){
		if ( from.after(to) ) return null;
		
		Set<MeasureUnit> output = new HashSet<MeasureUnit>();
		
		for ( MeasureUnit mu : humidity ) {
			if ( mu.getTime().after(from) && mu.getTime().before(to) ) {
				output.add(mu);
			}
		}
		return output;
	}
	
	public double getMedian(Set<MeasureUnit> data, Calendar from, Calendar to){		
		if ( from.after(to) ) return 0;
		
		double median = 0;
		
		for ( MeasureUnit d : getFiltered(data,from,to) ) {
			if ( d.getTime().after(from) && d.getTime().before(to) ) {
				median += d.evaluate();
			}
		}
		
		return median / data.size();
	}
	
	public String getRemoved(Set<MeasureUnit> temprature, Calendar Time){
		for (  MeasureUnit d : temprature ) {
			if ( d.getTime().equals(Time)) {			
				temprature.remove(d);
				return "Data has been removed.";
		}
		}     
		return "Data has not been found";		
	}

	public int getSame(Set<MeasureUnit> Data, Calendar Time){
		for (  MeasureUnit d : Data ) {
			if ( d.getTime().equals(Time)) {			
				return 0;
			}
			}
		return 1;
			
	}
	
	public String control(Set<MeasureUnit> data,double threshold, double Value, double lastEntry){
		String message = null;

		this.lastEntry=lastEntry;
		if ( Value> (threshold*2) ) {
			if ( message == null ) {
				message = Value + " is higher than the threshhold ("+ threshold + ") times 2";
			} else {
				message += "\n" + Value + " is higher than the threshhold ("+ threshold + ") times 2";
			}
		}

		// the last two entrys where above threshhold
		//Die letzten beiden Messwerte uebersteigen jeweils den Grenzwert. 
		if ( Value > threshold && lastEntry > threshold ) {
			if ( message == null ) {
				message = "The last two values where higher than threshhold( "+lastEntry+"/"+Value+")";
			} else {
				message += "\nThe last two values where higher than threshhold ( "+lastEntry+"/"+Value+")";
			}
		}
		
		// check if more than halve of the entrys of the last hours where above threshhold
		if ( data.size() > 1 ) {
			currentTime = new GregorianCalendar();
			
			// only look for entrys from the last hour
			currentTime.setTimeInMillis(System.currentTimeMillis());
			currentTime.add(Calendar.HOUR_OF_DAY, 1);
			
			// An iterator to iterate over the dataSet, the newest values are at the "top"
			Iterator<MeasureUnit> i =  data.iterator();
			MeasureUnit foo = null;
			int checkedEntrys = 0;
			int badEntrys = 0;
			
			do {
				foo = i.next();
				++checkedEntrys;
				if ( foo.evaluate() > threshold ) ++badEntrys;
//				System.out.println(foo + " " + badEntrys + " of " + checkedEntrys );
			} while ( i.hasNext() && foo.getTime().before(currentTime) );

			//Mehr als die Haelfte der innerhalb der letzten Stunde gemessenen Werte uebersteigt den Grenzwert,
			//vorausgesetzt es hat in diesem Zeitraum mindestens fuenf Messungen gegeben.
			if ( badEntrys > checkedEntrys/2 && checkedEntrys > 4 ) {
				if ( message == null ) {
					message = (badEntrys + " of " + checkedEntrys + " entrys in the last hour were above the threshhold ( " + threshold + ") ");
				} else {
					message += "\n" + badEntrys + " of " + checkedEntrys + " entrys in the last hour were above the threshhold ( " + threshold + ") ";
				}
			}		
		}	
		
		if ( message == null ) return "";
		else return message;		
	}
}

