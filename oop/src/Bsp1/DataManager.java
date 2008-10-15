package Bsp1;

import java.util.Collection;
import java.util.TreeSet;

public class DataManager {

	protected TreeSet<Data> data;
	protected Data lastEntry = new Data( 1, 1,1,1,1,1,1);
	protected double threshhold;
	protected double lowerBoundry;
	protected double upperBoundry;
	protected int compareTime;
	protected int countValue;
	protected int counttoHigh;
	protected int Median;
	protected String String;
	
	public DataManager() {
		data = new TreeSet<Data>();

		threshhold = 10;
		upperBoundry = 50;
		lowerBoundry = 0;
		countValue=0;
		compareTime=0;
		counttoHigh=0;
		Median=0;
		
		String = "";
	}
	
	public void read( double value, int year, int month, int day, int hour, int minute, int second )throws Exception {
		Data d = new Data( value, year,month,day,hour,minute,second);
		add(d);
		
	}
	
	private void add( Data d ) throws Exception {
		
		if( d.value < lowerBoundry ) throw new Exception("Datavalue too low.");
		
		if( d.value > upperBoundry )throw new Exception("Datavalue too high.");
		
		if ( d.getValue() > (threshhold*2) ) {
				throw new Exception( "The value of " + d + " is 2 times higher than the threshhold ("+ threshhold + ")" );
		}
		
		if ( d.value > threshhold && lastEntry.getValue() > threshhold ) {
				throw new Exception( "The last two values where higher than threshhold" );
		}
			
		if ( data.contains(d) ) throw new Exception( d + " already exists.");
		
		data.add(d);
		lastEntry = d;
	}
	
	
	public void remove( double value, int year, int month, int day, int hour, int minute, int second) throws Exception {
		Data d = new Data( value, year,month,day,hour,minute,second);
		if ( data.contains(d) ){  
			data.remove(d); 
			System.out.println("Data" + d + "removed.");
		}
		else { throw new Exception( d + " does not exist.");}
	
	}
	
	public void periodtoHigh(int year, int month, int day, int hour, int minute, int second){
	Time t = new Time(year, month, day, hour, minute, second);
	
	compareTime = t.getHour()*360+t.getMinute()*60+t.getSecond();
	
	System.out.println(compareTime);
	
	
	//while(){
	//data.iterator();
	
	//if((compareTime >= d.compareTo())&&(d.compareTo >= (compareTime -1800))&&(year==d.getYear())&&(day==d.getDay())&&(month==d.getMonth())){
	//	countValue++;
	//	if(d.getValue() > threshold){
			//counttoHigh++;
	//}
	//}
	//}
	
	//if((countValue==countoHigh)&&(countValue>=5)) throw Exception("All Values in the last half an hour are too high");
	
	
	
	
	}
	
	public  String periodPrint(int year1, int month1, int day1, int hour1, int minute1, int second1,int year2, int month2, int day2, int hour2, int minute2, int second2) {
		Time t1 = new Time(year1, month1, day1, hour1, minute1, second1);
		Time t2 = new Time(year2, month2, day2, hour2, minute2, second2);
		
		//if(t1 >= t2) throw new Exception("Begintime is after Endtime! No use fool ~~");
		
		//while(){
			//data.iterator();
			//if((year==d.getYear())&&(day==d.getDay())&&(month==d.getMonth())){
				//if( (t2>d.compareTo())&&(compareTo > t1) ){ String=String + "(Value : " + value + " , Time : " + time.toString() + ")\n" ;

				//}

			//}
		//}
		String = "";
		Collection<Data> c = data.subSet(new Data(0,t1), false , new Data(0,t2),false);
		for ( Data d : c ) {
			String += d.toString() ;
		}
		return String;
			
	}
	
	public  int periodMedian(int year1, int month1, int day1, int hour1, int minute1, int second1,int year2, int month2, int day2, int hour2, int minute2, int second2) throws Exception {
		Time t1 = new Time(year1, month1, day1, hour1, minute1, second1);
		Time t2 = new Time(year2, month2, day2, hour2, minute2, second2);
		
		//if(t1 >= t2) throw new Exception("Begintime is after Endtime! No use fool ~~");
		
		//while(){
			//data.iterator();
			//if((year==d.getYear())&&(day==d.getDay())&&(month==d.getMonth())){
				//if( (t2>d.compareTo())&&(compareTo > t1) ){ Median=Median+d.getValue(); countValue++;

				//}

			//}
		//}
		return Median/countValue;
		
	}
	
	
	
	public String toString() {
		return data.toString();
	}
	
	//public static void main(String... args) {
		/*DataManager d = new DataManager();
		
		Data d1 = new Data( 9, 2005,2,28,0,0,0);
		Data d2 = new Data( 5, 1995,1,1,0,0,0);
		Data d3 = new Data( 7, 1985,1,1,0,0,0);

		try {
			d.add(d1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		try {
			d.add(d2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		try {
			d.add(d3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		System.out.println("\n" + d);*/
	//}
}
