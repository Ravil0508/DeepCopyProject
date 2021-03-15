import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Main {

	public static void main(String[] args)throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
		List<String> list = new ArrayList<String>();
		list.add("GGG");
		list.add("HHH");
		Man man = new Man("Misha",22,list);
		Man s = serviceDCopy(man);
		System.out.println(man.hashCode()+" "+s.hashCode());	
	    man.getFavoriteBooks().add("FFF");
	    System.out.println(s.getFavoriteBooks());
	    System.out.println(man.getFavoriteBooks());
	    System.out.println(list);
	}
	
	public static <T> T deepCopy(T o){
		T copyObj = null;
		File file = new File("Test.xml");
		try{
		XMLEncoder e = new XMLEncoder(
		new BufferedOutputStream(new FileOutputStream(file)));
		e.writeObject (o);
		e.close ();}//конец try
		catch(FileNotFoundException e){}
		try{
		XMLDecoder d = new XMLDecoder (
		new BufferedInputStream (
		new FileInputStream (file)));
		copyObj = (T)d.readObject ();
		d.close ();}
		catch(FileNotFoundException e){}
		
		return copyObj;
		}
	
	public static <T> T serviceDCopy(T o) throws IllegalAccessException {
		T copyObj = deepCopy(o);
		Class clazz = copyObj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(int i=0;i<fields.length;i++) {
			
			if(fields[i].getModifiers()!=Modifier.STATIC) {
			
			fields[i].setAccessible(true);
			fields[i].set(copyObj, deepCopy(fields[i].get(o)));}
		}//конец фор
		return copyObj;
	}
}
