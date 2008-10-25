import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// erzeuge datei zum speichern und lesen
		System.out.println("erzeuge datei zum speichern und lesen");
		File f = new File("testDataManager");
		
		// erzeuge datenManager
		System.out.println("erzeuge datenManager");
		DataManager dm = new DataManager(f);
		
		// teste erzeugen und einloggen von usern
		System.out.println("teste erzeugen und einloggen von usern");
		User op = dm.login("admin", "aaa");
		
		String erg = ((Admin) op).addUser("bbb", "aaaa",UserType.User);
		
		erg += ((Admin) op).addUser("admin2", "aaaa",UserType.Administrator);
		
		User op2 = dm.login("bbb", "aaaa");
		erg += ((Admin) op).getUserTyp("bbb");		
		  
		erg += ((Admin) op).removeUser("aadmin");
		erg += ((Admin) op).removeUser("bbb");
		  
		System.out.println(erg);
		
		// teste erzeugen und benutzen von messstationen
		System.out.println("teste erzeugen und benutzen von messstationen");
		
		System.out.println(((Admin)op).addMeasureStation());
			
		// fuelle werte ein
		System.out.println(((Admin)op).addValueToMeasureStation(2,"Temperature","Celsius", 56, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Humidity","Percent", 110, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Temperature", "Fahrenheit", 30, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Humidity", "Absolute", 80, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Temperature","Celsius", 70, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Humidity","Percent", 47, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Temperature", "Fahrenheit", 70, new GregorianCalendar() ));
		System.out.println(((Admin)op).addValueToMeasureStation(1,"Humidity", "Absolute", 83, new GregorianCalendar() ));

		// entferne werte
		System.out.println("entferne werte");
		System.out.println(((Admin)op).removeValueFromMeasureStation(1,"Temperature", new GregorianCalendar()));
		
		// lese und pruefe werte
		System.out.println("lese und pruefe werte");
		GregorianCalendar c1 = new GregorianCalendar(-1000,0,0,0,0,0);
		GregorianCalendar c2 = new GregorianCalendar(10000,0,0,0,0,0);

		try {
		System.out.print( "\nTry to get all values from MeasureStation 1 within a time window : ");
		System.out.println( ((Admin)op).getTimeWindowFrom(1,"Temperature", c1, c2) );
		} catch ( Exception e ) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.print( "\nTry to get average from time window : ");
			System.out.println( ((Admin)op).getMeanFrom(1,"Humidity", c1, c2));
		} catch ( Exception e ) {
			System.out.println(e.getMessage());
		}
		
		// teste speichern
		System.out.println("\nteste speichern");
		
		dm.save();
		
		dm.load();
	}
}
