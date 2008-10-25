import java.util.Calendar;

public interface MeasureUnit extends Statement {
	
	public Calendar getTime();
	
	public double evaluate();
	
	public String toString();
	
}
