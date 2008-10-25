class Value implements Statement {

	private static final long serialVersionUID = -6285165103157643755L;

	private enum Type {
		usual, pi, e;
	}

	private final double value;
	
	private final Type type;
	
	public Value ( double value ) { 
		this.value = value;
		
		if ( value == StrictMath.PI ) type = Type.pi;
		else if ( value == StrictMath.E ) type = Type.e;
		else type = Type.usual;
	}

	@Override
	public double evaluate() {
		return value;
	}

	@Override
	public String toString() {
		switch ( type ) {
		case pi : 
			return "pi";
		case e :
			return "e";
		default :
			return Double.toString(value);
		}
	}
}