public enum Operations {

	/*
	 * Nullary operators
	 * Nullary operators do not require operands, they just create a double value 
	 */
	
	Pi(0, "pi") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands != null && operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.PI;
		}
	},
	EulerNumber(0, "e") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands != null && operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.E;
		}
	},
	Random(0,"rand") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands != null && operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.random();
		}
	},	
	
	/*
	 * Unary operators
	 * Unary operators require one operand 
	 */
	
	Sine(1,"sin") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.sin( operands[0].evaluate() );
		}
	},
	
	ArcusSine(1,"asin") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.asin( operands[0].evaluate() );
		}
	},
	Cosine(1,"cos") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.cos( operands[0].evaluate() );
		}
	},
	ArcusCosine(1,"acos") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.acos( operands[0].evaluate() );
		}
	},
	Tangens(1, "tan") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.tan( operands[0].evaluate() );
		}
	},
	ArcusTangens(1, "tan") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.atan( operands[0].evaluate() );
		}
	},
	NaturalLogarithm(1, "ln") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.log( operands[0].evaluate() );
		}
	},
	DecimalLogarithm(1, "log") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.log10( operands[0].evaluate() );
		}
	},
	Round(1, "round") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return StrictMath.round( operands[0].evaluate() );
		}
	},
	
	/*
	 * Binary operators
	 * Binary operators require two operands
	 */
	
	Addition(2,"+") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return operands[0].evaluate() + operands[1].evaluate();
		}
	},	
	Substraction(2, "-") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return operands[0].evaluate() - operands[1].evaluate();
		}
	},
	Multiplication(2, "*") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return operands[0].evaluate() * operands[1].evaluate();
		}
	},
	Division(2, "/") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return operands[0].evaluate() / operands[1].evaluate();
		}
	},
	Modulo(2, "%") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			return operands[0].evaluate() % operands[1].evaluate();
		}
	},
	
	/*
	 * Ternary operations
	 * Ternary operations accept three operands
	 */
	
	/**
	 * Take a double input and clamp it between min and max.
	 */
	Clamp(3, "clamp") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			double input = operands[0].evaluate();
			double min = operands[1].evaluate();
			double max = operands[2].evaluate();
			
	        return (input < min) ? min : ( (input > max) ? max : input );		
		}
	},
	
	/*
	 * Funky buisness
	 */
	
	/**
	 * Keplersche fassregel
	 */
	Kepler(4, "kepler") {
		@Override
		public double calculate(Statement[] operands) {
			if ( operands.length != dimension ) throw new RuntimeException("Illegal number of operands");
			
			double intervalLength = operands[0].evaluate(); 
			double y1 = operands[1].evaluate(); 
			double y2 = operands[2].evaluate(); 
			double y3 = operands[3].evaluate(); 

			return (intervalLength/6)*(y1+4*y2+y3);
		}
	},
	
	/*
	 * Unbounded operations
	 * Unbounded operations accept an unlimited number of operands or no operands
	 */
	
	Average(Integer.MAX_VALUE, "avg") {
		@Override
		public double calculate(Statement[] operands) {
			double out = 0;
			for ( Statement s : operands ) {
				out += s.evaluate();
			}
			out /= operands.length;
			return out;
		}
	},
	Sum(Integer.MAX_VALUE, "sum") {
		@Override
		public double calculate(Statement[] operands) {
			double out = 0;
			for ( Statement s : operands ) {
				out += s.evaluate();
			}
			return out;
		}
	}
	;
	
	public final int dimension;
	public final String sign;
	
	private Operations( int dimension, String sign ) {
		this.dimension = dimension;
		this.sign = sign;
	}
	
	public abstract double calculate( Statement[] operands );
	
	public static Operations forSign( String sign, Statement... operands ) {
		for ( Operations o : Operations.values() ) {
			if ( sign.equalsIgnoreCase(o.sign) ) return o;
		}	
		return null;
	}
	
	public static int isValidOperationSign( String sign ) {
		for ( Operations o : Operations.values() ) 
			if ( sign.equalsIgnoreCase(o.sign) ) return o.dimension;
		return -1;
	}
}
