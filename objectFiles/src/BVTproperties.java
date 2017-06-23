import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.Properties;

/**
 * Created by ALESANDROMarques on 2017-06-22.
 */
public class BVTproperties implements Serializable {
    String testType;
    String pBVTMode;
    String pBVTdomain;
    final String location = "BVTObjects.txt";
    private static final long serialVersionUID = 1L;


    public BVTproperties(String type,String pMode,String pDomain) {
        testType=type;
        pBVTMode=pMode;
        pBVTdomain = pDomain;

    }
    public BVTproperties() {
        testType=null;
        pBVTMode=null;
        pBVTdomain = null;

    }
//displays current bvt settings
    public void dispBVTProperties(String loc) {

        try {
            // FileInputStream in = new FileInputStream("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            FileInputStream in = new FileInputStream(loc+"\\deploy.properties");
            Properties props = new Properties();
            props.load(in);
            System.out.println("Current deploy.properties BVT settings");
            System.out.println("deploy.test.type= " + props.getProperty("deploy.test.type"));
            System.out.println("deploy.pBVT.mode= "+props.getProperty("deploy.pBVT.mode"));
            System.out.println("deploy.pBVT.domain= " +props.getProperty("deploy.pBVT.domain"));
            System.out.println("");

            in.close();


        } catch (IOException ex) {
            ex.printStackTrace();


        }


    }
    // changes BVT settings
    public void changeBVTProperties(String loc) {
        try {
            //File f = new File("deploy.properties");

            // PropertiesConfiguration config = new PropertiesConfiguration("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            PropertiesConfiguration config = new PropertiesConfiguration(loc+"\\deploy.properties");
            //  PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
            // layout.load(new InputStreamReader(new FileInputStream(f)));

            config.setProperty("deploy.test.type", testType);
            config.setProperty("deploy.pBVT.mode", pBVTMode);
            config.setProperty("deploy.pBVT.domain",pBVTdomain);
            config.save();
            // layout.save(new FileWriter("deploy.properties", false));
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }
// gets all saved settings and displays them
public BVTproperties[] readBVTObj(){
        BVTproperties all[] = null;
        // deployProperties a = new deployProperties();
        try{
            FileInputStream f = new FileInputStream(new File(location));
            ObjectInputStream o = new ObjectInputStream(f);

            int i=0;
            // calculates how many saved objects there are
            while (true){
                try {
                    BVTproperties dbj2 = (BVTproperties)o.readObject();

                    i++;

                }catch(EOFException ex){
                    break;
                }
            }
            f.close();
            o.close();

            //saved saved objects into array
            f = new FileInputStream(new File(location));
            o = new ObjectInputStream(f);
            System.out.println("All possible BVT settings "+i);
            all = new BVTproperties[i];

            for (int x=0; x<all.length;x++){
                all[x]= (BVTproperties) o.readObject();


            }

            o.close();
            f.close();
            //displays saved objects
            for (int x=0; x<all.length;x++){
                System.out.print(x + ": ");
                System.out.println(all[x].toString());

            }
            System.out.println("");


        }catch(Exception ex){
            ex.printStackTrace();

        }

        return all;

    }
// used to save objects , only used when objects are 1st created because there are only 5 possible bvt settings
    public void writeBVTObj(BVTproperties[] all){

        // deployProperties dbj1 = new deployProperties(type, name, pw, schema);
        try{
            FileOutputStream f = new FileOutputStream(new File(location));
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (int i =0; i<all.length;i++){
                o.writeObject(all[i]);}
            o.writeObject(this);
            o.close();
            f.close();


        }catch(Exception ex){
            ex.printStackTrace();

        }



    }

    @Override
    public String toString(){
        return testType + " " + pBVTMode + " " + pBVTdomain;


    }

}