package ml.kanfa.engine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @uthor Ibrahim Maïga.
 */
public class Serializer implements Serializable{

    private static final long serialVersionUID = 1L;

    private String filename;
    public String dir;

    public Serializer(String filename){
        this.filename = filename;
        this.dir = "resources/flux/";
    }

    public Serializer(){
        this(null);
    }

    public void serialize(Object data){
        this.serialize(this.filename, data);
    }

    public void serialize(String filename, Object data){

        ObjectOutputStream oos = null;
        File file = new File(this.dir + filename);
        try {
            Files.deleteIfExists(Paths.get(this.dir, filename));
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(data);
            oos.flush();
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        finally {
            if (oos != null) {
                try {oos.close();}
                catch (IOException e) {e.printStackTrace();}
            }
        }
    }

    public Object deserialize(String filename){
        ObjectInputStream ois = null;
        Object o = null;
        try {
            final FileInputStream fileInputStream = new FileInputStream(new File(this.dir + filename));
            if (fileInputStream == null) return o;
            ois = new ObjectInputStream(fileInputStream);
            o = ois.readObject();
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        finally {
            if (ois != null) {
                try {ois.close();}
                catch (IOException e) {e.printStackTrace();}
            }
        }
        return o;
    }
}