package Bsp1;

import java.util.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		DataManager dm = new DataManager();
		
		try{
			dm.read(1, 2005,2,28,0,0,0);
		} catch ( Exception e){
			System.out.println(e.getMessage());
		}
		
		try{
			dm.read(5, 1995,1,1,0,0,0);
		} catch ( Exception e){
			System.out.println(e.getMessage());
		}
		
		try{
			dm.read(7, 195,1,1,0,0,0);
		} catch ( Exception e){
			System.out.println(e.getMessage());
		}		
		
		System.out.println("\n" + dm);	
		
		try{
			dm.remove(7, 195,1,1,0,0,0);
		}catch ( Exception e){
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n" + dm);
		
		System.out.println(dm.periodPrint(1000,1,1,0,0,0, 1,1,1,0,0,0));
		
		
		
	}

}
