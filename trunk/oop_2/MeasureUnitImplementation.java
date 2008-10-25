import java.util.Calendar;

public class MeasureUnitImplementation implements MeasureUnit {

	private static final long serialVersionUID = 5080275509263192248L;
	
	private final double value;
	private final Calendar time;
	
	public MeasureUnitImplementation(double value, Calendar time) {
		super();
		this.value = value;
		this.time = time;
	}

	@Override
	public double evaluate() {
		return value;
	}

	@Override
	public Calendar getTime() {
		return time;
	}

	public String toString() {
		return value + " measured at " + time.getTime();
	}
}
