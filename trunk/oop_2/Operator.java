import java.util.List;

public class Operator implements Statement {

	private static final long serialVersionUID = -931759314841484060L;
	
	private final Statement[] operands;
	private final Operations operation;
	
	public Operator( Operations operation, List<Statement> operands ) {
		this.operation = operation;

		int x = (operation.dimension == Integer.MAX_VALUE) ? operands.size() : operation.dimension;
		
		this.operands = new Statement[x];
		
		for ( int i = 0; i < this.operands.length; i++ ) {
			this.operands[i] = operands.remove(0);
		}
	}
	
	@Override
	public double evaluate() {
		return operation.calculate(operands);
	}
	
	public String getSign() {
		return operation.sign;
	}

	public String preOrder() {
		String s = operation.sign;
		for ( Statement st : operands ) s += st.toString();

		s += " )";
		
		return s;
	}
	
	public String inOrder() {
		if ( operation.dimension == 0 ) {
			return operation.sign;
		} else if ( operation.dimension == 2 ) {
			return "( " + operands[0] + " " + operation.sign + " " + operands[1] + " )";
		}
		String s = operation.sign;
		s += "( ";
		for ( Statement st : operands ) s += (st + ", ");
		s += " )";
		
		return s;
	}
	
	public String toString() {
		return inOrder();
	}
	
}