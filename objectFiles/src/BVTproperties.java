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

    public BVTproperties[] readBVTObj(){
        BVTproperties all[] = null;
        // deployProperties a = new deployProperties();
        try{
            FileInputStream f = new FileInputStream(new File(location));
            ObjectInputStream o = new ObjectInputStream(f);

            int i=0;
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


            f = new FileInputStream(new File(location));
            o = new ObjectInputStream(f);
            System.out.println("All possible BVT settings "+i);
            all = new BVTproperties[i];

            for (int x=0; x<all.length;x++){
                all[x]= (BVTproperties) o.readObject();


            }

            o.close();
            f.close();

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