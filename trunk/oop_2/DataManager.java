import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

	/**
	 * all the measureStations maintained by this manager
	 */
	protected HashMap<Integer, MeasureStation> msList;
	
	/**
	 * all users registered with this manager
	 */
	protected HashMap<String, UserData> userList;

	/**
	 * list of all measureStations id's
	 */
	protected List<Integer> msIDs;
	protected List<Double> thresholds;
	
	private Saver saver;
	private File file;
	
	public DataManager(File file) throws IOException, ClassNotFoundException{
		this.file = file;
		this.saver = new Saver(this.file);
		
		// datei exisitiert, lade das ganze einfach
		if ( file.exists() && file.length() > 5 ) {
			load();
		// erzeuge neuen DM
		} else {
			msList = new HashMap<Integer, MeasureStation>();
			userList = new HashMap<String, UserData>();
			
			userList.put("admin", new UserData("aaa", UserType.Administrator));
		}	
	}
	
	public void save() throws IOException {
		this.msIDs = new ArrayList<Integer>(msList.keySet());
		this.thresholds = new ArrayList<Double>();
		
		for ( MeasureStation ms : msList.values() ) thresholds.add(ms.getThreshold());
		
		saver.save(	userList, msIDs, thresholds );
		
		// speichere alle messstationen
		for ( MeasureStation ms : msList.values() ) ms.save();

	}
	
	@SuppressWarnings("unchecked")
	public void load() throws IOException, ClassNotFoundException {
		Object[] o = saver.load();
		
		this.userList = (HashMap<String, UserData>) o[0];
		this.msIDs = (List<Integer>) o[1];
		this.thresholds = (List<Double>) o[2];
		
		for ( int i = 0; i < msIDs.size(); i++ ) {
			MeasureStation ms = new MeasureStation(msIDs.get(i), thresholds.get(i));
			msList.put(msIDs.get(0), ms);
		}
	}
	
	public User login(String user, String pw){
		
	    UserData data = userList.get(user);
		
	    if(data == null){
	    	//Exc
	    }
		if(!data.getPw().equals(pw)){
			//Exc
		}
		
		if(data.getTyp().equals("User")){
			return new User(msList);
		}else{ return new Admin(msList, userList);}
		
	
	}
}