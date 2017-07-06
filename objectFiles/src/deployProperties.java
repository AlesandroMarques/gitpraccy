/**
 * Created by ALESANDROMarques on 2017-06-12.
 */
import java.io.*;
import java.util.Properties;
import java.io.FileInputStream;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;
public class deployProperties implements Serializable{

    private static final long serialVersionUID = 1L;
    final String location = "deployObjects.txt";
    String dbtype ;
    String dbname ;
    String dbpassword;
    String dbSchema;

public deployProperties(String type, String name , String pw, String schema ){
 dbtype = type;
 dbname=name;
 dbpassword=pw;
 dbSchema=schema;





}// in mdmiaService uses a null object to call the dispDeployProperties method
public deployProperties(){
        dbtype = null;
        dbname=null;
        dbpassword=null;
        dbSchema=null;

    }
    // displays current values of these adjustable settings in the file
    public static void dispDeployProperties(String loc) {

        try {
           // FileInputStream in = new FileInputStream("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            FileInputStream in = new FileInputStream(loc+"\\deploy.properties");
            Properties props = new Properties();
            props.load(in);
            System.out.println("current deploy.properties database settings");
            System.out.println("deploy.db.type= " + props.getProperty("deploy.db.type"));
            System.out.println("deploy.db.name= "+props.getProperty("deploy.db.name"));
            System.out.println("deploy.db.user= " +props.getProperty("deploy.db.user"));
            System.out.println("deploy.db.password= " +props.getProperty("deploy.db.password"));
            System.out.println("deploy.db.schema= " +props.getProperty("deploy.db.schema"));
            System.out.println("deploy.db.schema.pw= "+props.getProperty("deploy.db.schema.pw"));
            System.out.println("");
            in.close();


        } catch (IOException ex) {
            ex.printStackTrace();


        }


    }
// if no changes are made to properies this is called and ueses currrent settings to create a object in order to check dbname.properties
    //
    public deployProperties returnDeployProperties(String loc) {
        deployProperties dp =null;
        try {
            // FileInputStream in = new FileInputStream("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            FileInputStream in = new FileInputStream(loc+"\\deploy.properties");
            Properties props = new Properties();
            props.load(in);
             dp = new deployProperties(props.getProperty("deploy.db.type"),props.getProperty("deploy.db.name"),props.getProperty("deploy.db.password"),props.getProperty("deploy.db.user"));




            in.close();


        } catch (IOException ex) {
            ex.printStackTrace();


        }
        return dp;

    }




    //changes properties file values to those of this object
    public void changeDeployProperties(String loc) {
        try {
            //File f = new File("deploy.properties");

            // PropertiesConfiguration config = new PropertiesConfiguration("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            PropertiesConfiguration config = new PropertiesConfiguration(loc+"\\deploy.properties");
            //  PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
            // layout.load(new InputStreamReader(new FileInputStream(f)));

            config.setProperty("deploy.db.type", dbtype);
            config.setProperty("deploy.db.name", dbname);
            config.setProperty("deploy.db.user", dbSchema);
            config.setProperty("deploy.db.password", dbpassword);
            config.setProperty("deploy.db.schema", dbSchema);
            config.setProperty("deploy.db.schema.pw", dbpassword);
            config.save();
            // layout.save(new FileWriter("deploy.properties", false));
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }
    //because objects are saved in a text, there is no way to append so this method resaves all old objects and saves
    //the new object created
    public void writeDeployObj(deployProperties[] all){

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

    // returns an array of stored objects with property values , and displays them
    public deployProperties[] readDeployObj(){
        deployProperties all[] = null;
       // deployProperties a = new deployProperties();
        try{
            FileInputStream f = new FileInputStream(new File(location));
            ObjectInputStream o = new ObjectInputStream(f);

            int i=0;
            // calculates how many saved objects there are
            while (true){
                try {
                    deployProperties dbj2 = (deployProperties)o.readObject();

                    i++;

                }catch(EOFException ex){
                    break;
                }
            }
            f.close();
            o.close();

            //saves the saved objects into a array
            f = new FileInputStream(new File(location));
            o = new ObjectInputStream(f);
            System.out.println("number of saved settings "+i);
            all = new deployProperties[i];

            for (int x=0; x<all.length;x++){
                all[x]= (deployProperties) o.readObject();


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
        //returns saved objects to be used to change settings
        return all;

    }
@Override
public String toString(){
    return dbtype + " " + dbname + " " + dbSchema + " " + dbpassword;


}
}
