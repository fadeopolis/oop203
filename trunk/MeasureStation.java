import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MeasureStation {
	
	protected int id;
	protected Filter filter;

	protected double threshold;
	
	protected Map<String, Function> conversionFunctions;
	protected Map<String, Double> lastEntrys;
	protected Map<String, HashSet<MeasureUnit>> data;	
	
	private Saver saver;
	private File file;
	
	public MeasureStation(int id, double threshold ) throws IOException, ClassNotFoundException{
		this.file = new File("MeasureStation_" + id + ".txt");
		this.saver = new Saver(file);
		
		// datei exisitiert, lade das ganze einfach
		if ( file.exists() && file.length() > 5) {
			load();
		// erzeuge neue measureStation
		} else {
			this.id=id;
			this.threshold = threshold;
			
			lastEntrys = new HashMap<String, Double>();
			filter = new Filter();	
			
			data = new HashMap<String, HashSet<MeasureUnit>>();
			conversionFunctions = new HashMap<String, Function>();
			
			data.put("Temperature", new HashSet<MeasureUnit>());
			conversionFunctions.put("Celsius", new Function("x"));
			conversionFunctions.put("Fahrenheit", new Function("x 32 - 1,8 /"));
			conversionFunctions.put("Kelvin", new Function("x 273,7 +"));
			
			data.put("Humidity", new HashSet<MeasureUnit>());
			conversionFunctions.put("Percent", new Function("x"));
			conversionFunctions.put("Absolute", new Function("x 100 /"));
			
			save();
		}
	}
	
	/**
	 * Return the ID of this measure station
	 * @return
	 */
	public int getID(){
		return id;	
	}

	public double getThreshold() {
		return this.threshold;
	}
	
	/**
	 * Adds a Measure Unit to this MeasureStation
	 * @param dataType The type of the measure unit to add
	 * @param unit the unit in which the value was measured
	 * @param value the measured value
	 * @param time
	 * @return
	 */
	public String add(String dataType, String unit, double value, Calendar time){
		Set<MeasureUnit> set = data.get(dataType);
		Function funct = conversionFunctions.get(unit);
		Double last = lastEntrys.get(dataType);
		if ( last == null ) last = 0d;
		
		if ( set != null && funct != null) {
			MeasureUnit mu = new MeasureUnitImplementation(funct.calculate(value), time);
			set.add(mu);
			
			String s = filter.control(set, threshold, value, last);
			lastEntrys.put(dataType, value);
			return s;
		} else return "Datentyp nicht gefunden.";
	}
	
	/**
	 * Removes a data unit from this measure station
	 * @param dataType The type of the unit to remove
	 * @param time the time of the unit to remove
	 * @return
	 */
	public String remove(String dataType, Calendar time){
		Set<MeasureUnit> set = data.get(dataType);
		if ( set != null ) {
			return filter.getRemoved(set, time);
		}
		return "No such data type";
	}
	
	/**
	 * Get all values of type that where measured in the specified time window
	 * @param dataType
	 * @param from
	 * @param to
	 * @return
	 */
	public Set<MeasureUnit> getTimeWindowFrom(String dataType,Calendar from, Calendar to){
		Set<MeasureUnit> set = data.get(dataType);

		if( set == null ) return null;
		
		return filter.getFiltered(set, from, to);
	}
	
	/**
	 * Get the average of all values of type that where measured in the specified time window
	 * @param dataType
	 * @param from
	 * @param to
	 * @return
	 */
	public double getMeanFrom(String dataType, Calendar from, Calendar to){
		Set<MeasureUnit> set = data.get(dataType);
		
		if ( set != null ) return filter.getMedian(set, from, to);
		else return 0;
	}
	
	public void save() throws IOException {
		saver.save(id, filter, threshold, conversionFunctions, lastEntrys, data );
	}
	
	@SuppressWarnings("unchecked")
	public void load() throws IOException, ClassNotFoundException {
		Object[] o = saver.load();
		
		this.id = (Integer) o[0];
		this.filter = (Filter) o[1];
		this.threshold = (Double) o[2];
		this.conversionFunctions = (Map<String, Function>) o[3];
		this.lastEntrys = (Map<String, Double>) o[4];
		this.data = (Map<String, HashSet<MeasureUnit>>) o[5];
	}
}
