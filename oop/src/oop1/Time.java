package oop1;

import java.io.Serializable;
import java.util.Arrays;

public class Time implements Serializable, Cloneable, Comparable<Time> {

	public enum Month {
		Januar(31), Februar(28) {
			@Override
			public int getNumberOfDays( boolean leapYear ) {
				return leapYear ? numberOfDays+1 : numberOfDays;		
			}
		},
		March(31), April(30), May(31), Juni(30), July(31),
		August(31), September(30), Oktober(31), November(30), December(31);

		protected final int numberOfDays;
		
		private Month( int numberOfDays ) {
			this.numberOfDays = numberOfDays;
		}
	
		public int getNumberOfDays( boolean leapYear ) {
			return numberOfDays;		
		}
		
		public static Month forOridinal( int ordinal ) {
			if ( ordinal < 1 || ordinal > 12 ) {
				throw new IllegalArgumentException("Invalid Month " + ordinal );				
			} else return Month.values()[ordinal-1];
		}
	}
	
	protected final int year;
	protected final Month month;
	protected final int day;
	protected final int hour;
	protected final int minute;
	protected final int second;
	
	protected final boolean leapYear;

	public Time( Time time ) throws Exception {
		this( time.getYear(), time.getMonth(), time.getDay(), time.getHour(), time.getMinute(), time.getSecond() );
	}
	
	public Time( int year, int month, int day, int hour, int minute, int second ) throws IllegalArgumentException {
		this( year, Month.forOridinal(month), day, hour, minute, second );
	}
	
    public Time( int year, Month month, int day, int hour, int minute, int second ) throws IllegalArgumentException {
    	this.year = year;
    	
    	if ( year % 4 == 0 && !(year % 400 == 0) ) {
    		leapYear = true;
    	} else leapYear = false;
    	
    	this.month = month;
    	
    	if ( day < 1 || day > month.numberOfDays ) {
    		throw new IllegalArgumentException("Illegal Day " + day + " in month " + month);
    	} else this.day = day;
    	
    	if ( hour < 0 || hour > 23 ) {
    		throw new IllegalArgumentException("Illegal Hour " + hour );
    	} else this.hour = hour;
    	
    	if ( minute < 0 || minute > 59 ) {
    		throw new IllegalArgumentException("Illegal Minute " + minute);
    	} else this.minute = minute;
    	
    	if ( second < 0 || second > 59 ) {
    		throw new IllegalArgumentException("Illegal Second " + second );
    	} else this.second = second;   	
   	}
 
/*
//    public Time( int year, int month, int day, long time ) throws IllegalArgumentException {
//    	this( year, Month.forOridinal(month), day, time );
//    }
//    
//    public Time( int year, Month month, int day, long time ) throws IllegalArgumentException {
//    	this.year = year;
//    	this.month = month;
//    	if ( day <= month.numberOfDays ) {
//    		this.day = day;
//    	} else {
//    		throw new IllegalArgumentException("There only are " + month.numberOfDays + " days in " + month.toString() );
//    	}
//    	
//    	this.hour = (int) (time/3600000);
//    	time = time - hour*3600000;
//    	this.minute = (int) (time/60000);
//    	time = time - minute*60000;
//    	this.second = (int) time/1000;
//    }
*/
    
    // GETTERS
    
    /**
     * 
     * @return The year of this Time Object
     */
    public int getYear() {
        return year;
    }
  
    /**
     * 
     * @return The month of this Time Object
     */
    public int getMonth() {
        return month.ordinal()+1;
    }
    
    /**
     * 
     * @return The day of this Time Object
     */
    public int getDay() {
        return day;
    }

    /**
     * 
     * @return The hour of this Time Object
     */
    public int getHour() {
    	return hour;
    }

    /**
     * 
     * @return The minute of this Time Object
     */
    public int getMinute() {
    	return minute;
    }

    /**
     * 
     * @return The second of this Time Object
     */
    public int getSecond() {
        return second;
        
    }

//    /**
//     * 
//     * @return
//     */
//    public long getTime() {
//    	long l = ((long) hour)*3600;
//    	l += ((long) minute)*60;
//    	l += second;
//    	return l;
//    }

    /**
     * @see     java.lang.Object
     */
    public boolean equals(Object obj) {
    	if ( !( obj instanceof Time ) ) return false;
    	Time t = (Time) obj;
    	if ( ( getYear() != t.getYear() ) && ( getMonth() != t.getMonth() ) && 
    		 ( getDay() != t.getDay() ) && ( getHour() != t.getHour() ) &&
    		 ( getMinute() != t.getMinute() ) && ( getSecond() != t.getSecond() ) ) {
    		return false;
    	}
        return true;
    }

    /**
     * @see java.lang.Comparable
     */
    public int compareTo(Time anotherTime) {
    	
    	int foo = anotherTime.getYear() - this.getYear();
    	if ( foo != 0 ) return (int) Math.signum(foo);
    
    	foo = anotherTime.getMonth() - this.getMonth();
    	if ( foo != 0 ) return (int) Math.signum(foo);

    	foo = anotherTime.getDay() - this.getDay();
    	if ( foo != 0 ) return (int) Math.signum(foo);
    	
    	foo = anotherTime.getHour() - this.getHour();
    	if ( foo != 0 ) return (int) Math.signum(foo);
    	
    	foo = anotherTime.getMinute() - this.getMinute();
    	if ( foo != 0 ) return (int) Math.signum(foo);
    	
    	foo = anotherTime.getSecond() - this.getSecond();
    	if ( foo != 0 ) return (int) Math.signum(foo);
    	
    	return 0;
    }
    
    /**
     * Returns a hash code value for this object. The result is the
     * exclusive OR of the two halves of the primitive <tt>long</tt>
     * value returned by the {@link Date#getTime}
     * method. That is, the hash code is the value of the expression:
     * <blockquote><pre>
     * (int)(this.getTime()^(this.getTime() >>> 32))</pre></blockquote>
     *
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        long ht = this.getYear();
        ht += this.getMonth();
        ht += this.getDay();
        ht += this.getHour();
        ht += this.getMinute();
        ht += this.getSecond();
        
        return (int) ht ^ (int) (ht >> 32);
    }
    
    /**
     * @see java.lang.Object
     */
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append( getHour() < 10 ? "0"+getHour() : getHour() );
    	s.append(":");
    	s.append( getMinute() < 10 ? "0"+getMinute() : getMinute() );
    	s.append(":");
    	s.append( getSecond() < 10 ? "0"+getSecond() : getSecond() );
    	s.append(" ");
    	s.append( getDay() < 10 ? "0"+getDay() : getDay() );
    	s.append(".");
    	s.append( getMonth() < 10 ? "0"+getMonth() : getMonth() );
    	s.append(".");
    	
    	if ( getYear() < 10 ) {
    		s.append( "000"+getYear() );
    	} else if ( getYear() < 100 ) {
    		s.append( "00"+getYear() );
    	} else if ( getYear() < 1000 ) {
    		s.append( "0"+getYear() );
    	} else {
    		s.append( getYear() );
    	}
    	
    	return s.toString();
    }
    
    public static void main( String... args ) {
//    	Time t1 = new Time( -5000000, 1, 31, 0, 0, 0 );
//    	Time t2 = new Time( 0, 1, 2, 0, 0, 1 );
//    	System.out.println(t1 + "\n" + t2);
//    	int i = t1.compareTo(t2);
//    	System.out.println(i+"\n");
    	
//    	1.1.1970
    	long l = System.currentTimeMillis();
    	long[] f = new long[6];
    	l /= 1000;
    	
    	System.out.println( (long) l );
    	f[5] = (long) (l % 60);
//    	System.out.println( l-f[5] + " ,minus " + f[5] + " seconds");
    	l /= 60;
    	f[4] = (long) (l % 60);
//    	System.out.println( l-f[4] + " ,minus " + f[4] + " minutes");
    	l /= 60;
    	f[3] = (long) (l % 24);
//    	System.out.println( l-f[3] + " ,minus " + f[3] + " hours");
    	l /= 24;
    	f[2] = (long) ((l % 30));
//    	System.out.println( l-f[2] + " ,minus " + f[2] + " days");
    	l /= 30;
    	f[1] = (long) ((l % 12));
//    	System.out.println( l-f[1] + " ,minus " + f[1] + " months");
    	l /= 12;
    	f[0] = (long) (l + 1970);
//    	System.out.println( l-f[0] + " ,minus " + f[0] + " years");
    	
    	System.out.println(Arrays.toString(f));
    }
}