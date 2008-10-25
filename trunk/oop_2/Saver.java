import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Saver {

	private final File file;
	private FileOutputStream fout;
	private FileInputStream fin;
	private ObjectOutputStream oout;
	private ObjectInputStream oin;
	
	public Saver( File file ) throws IOException {
		this.file = file;
		
		fout = new FileOutputStream(file);
		fin = new FileInputStream(file);
		
		oout = new ObjectOutputStream(fout);
		oin = new ObjectInputStream(fin);
	}
	
	public void save( Object... o ) throws IOException {
		fout = new FileOutputStream(file);
		oout = new ObjectOutputStream(fout);
		
		oout.writeObject(o);
		oout.close();
	}
	
	public Object[] load() throws IOException, ClassNotFoundException {
		fin = new FileInputStream(file);
		oin = new ObjectInputStream(fin);
		
		Object[] out;
		Object o = oin.readObject();

		if ( o.getClass().isArray() ) out = (Object[]) o;
		else {
			out = new Object[1];
			out[0] = o;
		}
		
		oin.close();
		return out;
	}
	
	@Override 
	public void finalize() {
		try {
			oout.close();
			oin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
