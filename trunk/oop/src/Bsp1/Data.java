package Bsp1;

import java.util.Date;

public class Data implements Comparable<Data> {
	protected final Time time;
	protected final double value;
	
	public Data( double value, int year, int month, int day, int hour, int minute, int second ) {
		this( value, new Time( year, month, day, hour, minute, second ) );
	}
	
	public Data( double value, Time time ) {
		this.value = value;
		this.time = time;
	}
	
	public Time getTime() {
		return time;
	}

	public double getValue() {
		return value;
	}

	@Override
	public int compareTo(Data d) {
		return time.compareTo(d.getTime());
//		return (int) Math.signum( d.getValue() - getValue() );
/////////////////////////
//
//		return time.getcompare();
	}

	public String toString() {
		return "(Value : " + value + " , Time : " + time.toString() + ")\n";
	}
}
