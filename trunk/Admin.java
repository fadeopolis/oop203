import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class Admin extends User {

	protected int id;
	protected double threshold = 50;
	protected HashMap<String, UserData> userData;
	
	public Admin(HashMap<Integer, MeasureStation> ms, HashMap<String, UserData> usrData) {
		super(ms);
		id = 0;
		this.userData = usrData;
	}

	/**
	 * create a new MeasureStation an add it to the HashMap
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public String addMeasureStation() throws IOException, ClassNotFoundException {
		id++;
		MeasureStation tmp = new MeasureStation(id,threshold);
		if(msList.put(id, tmp) == null) return "Measure station " + id + " was successfully added";
		else {
			id--;
			return "Measure Station could not be removed";
		}
	}
	
	/**
	 * delete the MeasureStation from the HashMap
	 * @param id MeasureStation
	 */
	public String removemMeasureStation(int id){
		MeasureStation ms = msList.remove(id);
		if ( ms == null ) return "Measure station not found";

		return "Measure station removed\n";
	}
	
	/**
	 * Add a user from the DataManager
	 * @param user
	 * @param pw
	 * @param typ
	 * @return
	 */
	public String addUser(String user, String pw, UserType typ){
		if(userData.containsKey(user)){
			return "User "+user+" already exists\n";
		}
		
		if(userData.put(user, new UserData(pw, typ))!= null){
			return "User create error";
		}
		return "User "+user+" successfuly added\n";
	}

	/**
	 * Remove a user from the DataManager
	 * @param user
	 * @return
	 */
	public String removeUser(String user){
		
		if(userData.containsKey(user)){
			userData.remove(user);
			return "User "+user+" was successfuly removed\n";
		}
		
		return "User "+user+" does not exist\n";
	}
	
	public String getUserTyp(String user){
		return "Usertyp: " + userData.get(user).getTyp() + "\n";
	}
	
	/**
	 * add a MeasureValue to a Station
	 * @param id MeasureStation ID
	 * @param dataType 
	 * @param unit ValueUnit
	 * @param value Value of measuring
	 * @return String with error
	 */
	public String addValueToMeasureStation( int id, String dataType, String unit, double value, Calendar time ){
		MeasureStation ms = msList.get(id);
		if ( ms == null ) return "Measure station not found";
		else return ms.add(dataType, unit, value, time);
	}
	
	/**
	 * Delete an MeasureValue from the Station
	 * @param id MeasureStation ID
	 * @param dataType 
	 * @param time
	 * @return String with error
	 */
	public String removeValueFromMeasureStation(int id, String dataType, Calendar time){
		MeasureStation ms = msList.get(id);
		if ( ms == null ) return "Measure station not found";
		return ms.remove(dataType, time);
	}	
}
