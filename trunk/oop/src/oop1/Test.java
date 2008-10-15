package oop1;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class Test {

	/**
	 * Creates am DataManager, feeds it with data.
	 * @param args
	 */
	public static void main(String[] args) {
		// a java.util.Scanner for parsing the input		
		Scanner scan = new Scanner(System.in);
		
		boolean verbose = false;
		
		if ( args.length > 0 ) {
//			System.out.println(args[0]);
			verbose = ( args[0].equals("-v") );
		}
		
		DataManager dm = null;
		
		if ( verbose ) System.out.println("Please insert the threshold for the DataManager, ZERO or NaN for default");
		
		if ( scan.hasNextDouble()  ) {
			dm = new DataManager(scan.nextDouble());
		} else if ( scan.hasNextInt()  ) {
			dm = new DataManager(scan.nextInt());
		} else {
			dm = new DataManager();
			scan.next();
		}

		if ( verbose ) System.out.println("DataManager with treshhold " + dm.getThreshold() + " created.");
		
		int[] x = new int[6];
		int i = -1;
		double value = Double.NaN;

		
		if ( verbose ) System.out.println("Enter Data");
		
		for ( int foo = 0; foo < 8; foo++ ) {
			try {
			
				//end if there is no more input
//				if ( !scan.hasNext() ) {
//					break;
//				}
				
				//scan the value
				if ( i == -1 ) {
					if ( verbose ) System.out.println("Enter a value for the next data");
				
					if ( scan.hasNextDouble() ) {
						value = scan.nextDouble();
					} else if ( scan.hasNextInt() ) {
						value = scan.nextInt();
					}
					i++;
			
					if ( verbose ) System.out.println("value := " + value);
					if ( verbose ) System.out.println("Enter the time for the new data ( Y:M:D:H:M:S )");
			
				} else 	if ( i == 6  ) { 
					if ( verbose ) System.out.println("adding data");
				
					String msg = dm.add( new Data(value,x[0],x[1],x[2],x[3],x[4],x[4]) );
					// restart the whole procedure
					i = -1;
					
					if ( verbose ) System.out.println(msg == null ? "" : msg + "\n");
				} else {
					// scan the time	
					x[i] = scan.nextInt();
					i++;
				} 
				
			} catch ( InputMismatchException e) {
				if ( verbose ) {
					System.out.println(e.getMessage());
					i = -1;
				}
				else throw e;
			} 
			catch ( IllegalArgumentException e ) {
				if ( verbose ) {
					System.out.println(e.getMessage());
					i = -1;
				}
				else throw e;
			}
			
 		}
		
		System.out.println("\n There are " + dm.size() + " elements in the DataManager");
		System.out.println(dm.toString());
		

		// remove an entry
		System.out.println("\nRemove an entry");
		Data d = new Data(0, 1,2,3,4,5,6);	
		dm.add( d );
		System.out.println( dm.remove( d ) ? "Value was removed" : "There was no such value" );
		
		System.out.println("\n There are " + dm.size() + " elements in the DataManager");
		System.out.println(dm.toString());
		
		// get all data in a time window and their average value
		GregorianCalendar c1 = new GregorianCalendar(0,0,0,0,0,0);
		GregorianCalendar c2 = new GregorianCalendar();
		System.out.println( dm.getData(c1, c2) );
		System.out.println( "\nAverage " + dm.getAverage(c1, c2) );
		
		// try an invalid time window
		System.out.println("\n try an invalid time window ");
 		System.out.println( dm.getAverage(c2, c1) );
			
		System.out.println("\n There are " + dm.size() + " elements in the DataManager");
		System.out.println(dm.toString());
	}
}
