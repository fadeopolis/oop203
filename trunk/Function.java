import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A function in postorder with one variable
 * @author fader
 *
 */
public class Function implements Serializable {

	private static final long serialVersionUID = -6682326522746305967L;
	
	private Variable variable;
	private final Statement formula;
	
	private final double primitiveValue;
	
	public Function( String formula ) {
		Scanner scan = new Scanner(formula);

		this.variable = null;
		
		LinkedList<Statement> stack = new LinkedList<Statement>();
		
		// fill the stack
		while ( scan.hasNext() ) {		
			// read a simple value
			if ( scan.hasNextInt() || scan.hasNextDouble() ) {
				stack.add(new Value(scan.nextDouble()));
				
				continue;
			}
		
			// read a variable or special value ( pi,e,...)
			String x = scan.next();
				
			if ( x.equalsIgnoreCase("x") ) {
				if ( variable == null) this.variable = new Variable();
				stack.add(variable);
				continue;
			}
				
			// try to read an operator sign
			int i = Operations.isValidOperationSign(x);
			// a result of -1 tells me that there is no such operation
			if ( i != -1 ) {				
				Operator o = new Operator( Operations.forSign(x), stack );
				stack.push(o);
				
				continue;
			} else throw new RuntimeException("Invalid operation sign " + x);			
		}
		
		if ( stack.size() > 1) throw new RuntimeException("Too many operands");
		
		this.formula = stack.pop();
		
		if ( variable == null ) this.primitiveValue = this.formula.evaluate();
		else this.primitiveValue = Double.NaN;
	}

	public double calculate( double x ) {
		if ( variable == null ) return primitiveValue;
		else {
			variable.setValue(x);
			return formula.evaluate();
		}
	}
	
	public String toString() {
		if ( variable == null ) {
			return "f(x) = " + formula.toString() + " = " + primitiveValue;
		} else 
		return "f(x) = " + formula.toString();
	}
	
	public static void main(String... args) throws Exception {
		String s = new String("x 32 - 1,8 /");
//		String s = "";
//		for ( int i = 0; i < 50000; i++ ) s+= " pi";
//		s += " avg";
		
		Function f = new Function(s);
//		System.out.println(f.calculate(21));
		
		File file = new File("test");
//		System.out.println(file.delete());
		file.delete();
		file.createNewFile();
		
		Saver save = new Saver(file);
		save.save(f);
		Object[] o = save.load();
		
		long l = file.length();
		String foo;
		if (l > 1024) {
			l /= 1024;
			foo = "KB";
		} else foo = "B";
		if ( l > 1024) {
			l /= 1024;
			foo = "MB";
		}
		
		System.out.println(Arrays.toString(o) + "\n" + l + " " + foo);
	}
}	