import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.Properties;

/**
 * Created by ALESANDROMarques on 2017-06-20.
 */
public class dbProperties implements Serializable{
    private static final long serialVersionUID = 1L;
    final String location = "dbObjects.txt";
    String dbtype ;
    String dbname ;
    String serverName;
    String domain;
    String hostname;
    String url;
    String os;
    String version;


    public dbProperties(String type,String name,String server,String domain,String oS,String vers){
dbtype=type;
dbname=name;
serverName=server;
this.domain=domain;
hostname= server+"."+domain;
url = type+":"+"jdbc"+":"+name;
os=oS;
version=vers;
    }   public dbProperties(){
        dbtype=null;
        dbname=null;
        serverName=null;
        this.domain=null;
        hostname= null;
        url = null;
        os=null;
        version=null;
    }




    public static void dispDbProperties(String loc, deployProperties dp) {

        try {
            // FileInputStream in = new FileInputStream("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            FileInputStream in = new FileInputStream(loc+"\\"+dp.dbname+".properties");
            Properties props = new Properties();
            props.load(in);
            System.out.println("Current "+dp.dbname+".properties settings");
            System.out.println("db.type= " + props.getProperty("db.type"));
            System.out.println("db.name= "+props.getProperty("db.name"));
            System.out.println("db.server.name= " +props.getProperty("db.server.name"));
            System.out.println("db.domain= " +props.getProperty("db.domain"));
            System.out.println("db.url= " +props.getProperty("db.url"));
            System.out.println("db.hostname= "+props.getProperty("db.hostname"));
            System.out.println("db.os= " +props.getProperty("db.os"));
            System.out.println("db.version= " +props.getProperty("db.version"));
            System.out.println("");
            in.close();


        } catch (IOException ex) {
            ex.printStackTrace();


        }


    }

    public void changeDbProperties(String loc) {
        try {
            //File f = new File("deploy.properties");

            // PropertiesConfiguration config = new PropertiesConfiguration("C:\\Users\\ALESANDROMarques\\Desktop\\propertiesFiles\\deploy.properties");
            PropertiesConfiguration config = new PropertiesConfiguration(loc+"\\"+dbname+".properties");
            //  PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
            // layout.load(new InputStreamReader(new FileInputStream(f)));

            config.setProperty("db.type", dbtype);
            config.setProperty("db.name", dbname);
            config.setProperty("db.server.name", serverName);
            config.setProperty("db.domain", domain);
            config.setProperty("db.url", url);
            config.setProperty("db.hostname", hostname);
            config.setProperty("db.os", os);
            config.setProperty("db.version", version);
            config.save();
            // layout.save(new FileWriter("deploy.properties", false));
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

// that have same db name
    public dbProperties[] readDbObj(deployProperties dp){
        dbProperties all[] = null;
        // deployProperties a = new deployProperties();
        try{
            FileInputStream f = new FileInputStream(new File(location));
            ObjectInputStream o = new ObjectInputStream(f);

            int i=0;
            while (true){
                try {
                    dbProperties dbj = (dbProperties)o.readObject();
                    if (dbj.dbname.equalsIgnoreCase(dp.dbname)){

                    i++;}

                }catch(EOFException ex){
                    break;
                }
            }
            f.close();
            o.close();


            f = new FileInputStream(new File(location));
            o = new ObjectInputStream(f);
            System.out.println("number of saved settings with dbname "+ dp.dbname +" : "+i);
            all = new dbProperties[i];

            for (int x=0; x<all.length;x++){
                dbProperties dbj = (dbProperties)o.readObject();
                if (dbj.dbname.equalsIgnoreCase(dp.dbname)){
                    all[x]= dbj;}else{x--;}



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

    public dbProperties[] readAllDbObj(){
        dbProperties all[] = null;
        // deployProperties a = new deployProperties();
        try{
            FileInputStream f = new FileInputStream(new File(location));
            ObjectInputStream o = new ObjectInputStream(f);

            int i=0;
            while (true){
                try {
                    dbProperties dbj = (dbProperties)o.readObject();
                    i++;

                }catch(EOFException ex){
                    break;
                }
            }
            f.close();
            o.close();


            f = new FileInputStream(new File(location));
            o = new ObjectInputStream(f);

            all = new dbProperties[i];

            for (int x=0; x<all.length;x++){
                all[x] = (dbProperties)o.readObject();
            }

            o.close();
            f.close();

        }catch(Exception ex){
            ex.printStackTrace();

        }

        return all;

    }


    public void writeDbObj(){
        dbProperties all[] = readAllDbObj();
        // deployProperties dbj1 = new deployProperties(type, name, pw, schema);
        try{
            FileOutputStream f = new FileOutputStream(new File(location));
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (int i =0; i<all.length;i++){
               o.writeObject(all[i]);}
            o.writeObject(this);
            System.out.println("new dbProperties have stored for future use");
            o.close();
            f.close();


        }catch(Exception ex){
            ex.printStackTrace();

        }



    }


    @Override
    public String toString(){
        return dbtype + " " + dbname + " " + serverName + " " + domain + " " + url + " "+ hostname + " " + os+ " "+version;


    }

}
