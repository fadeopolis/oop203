class Variable implements Statement {
	
	private static final long serialVersionUID = -3921515892900816879L;
	
	private double value;

	public Variable() {
		this.value = Double.NaN;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public double evaluate() {
		return value;
	}

	@Override
	public String toString() {
		return "x";
	}
}