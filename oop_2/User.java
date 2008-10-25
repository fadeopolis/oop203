import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class User {

	protected HashMap<Integer, MeasureStation> msList;
	
	public User(HashMap<Integer, MeasureStation> ms){
		
		this.msList = ms;
	}
	
	/**
	 * get an HashSet of values from a specific time range
	 * @param id MeasureStation ID
	 * @param Datatyp
	 * @param from Start of range
	 * @param to End of range
	 * @return HashSet with the value range
	 */
	public Set<MeasureUnit> getTimeWindowFrom(int id, String Datatyp,Calendar from, Calendar to){
		
		MeasureStation ms = msList.get(id);
		if(ms == null){
			throw new RuntimeException("No such measure station exists");
		} else {
			 return ms.getTimeWindowFrom(Datatyp, from, to);
		 }
	}
	
	/**
	 * return the Median from an specific date range 
	 * @param id MeasureStattion ID
	 * @param Datatyp
	 * @param from
	 * @param to
	 * @return double Value
	 */
	public double getMeanFrom(int id, String Datatyp, Calendar from, Calendar to){	
	
		double erg = msList.get(id).getMeanFrom(Datatyp, from, to);
		
		if(erg <= 0) throw new RuntimeException("No such Measure Station found");
			
		return erg;
	}	

}
