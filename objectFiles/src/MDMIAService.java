import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
/**
 * Created by ALESANDROMarques on 2017-06-20.
 */
public class MDMIAService {

String location;
public MDMIAService(String loc){
location=loc;



}
// method displays deploy.properties current db settings , gives you option to change them to either a previosly saved setting
    // or lets you update with new settings , then saves those settings for future use , then calls editBVTproperties, then
    //calls editDbProperties
    public void editDeployProperties(){
        Scanner scan = new Scanner(System.in);
        deployProperties a = new deployProperties();
        a.dispDeployProperties(location);
        // dispDeployProperties();
        System.out.println("would you like to change properties ? 1= yes, rest =no");
        int change = scan.nextInt();
        if(change==1) {
            //displays all saved settings
            deployProperties all[] = a.readDeployObj();

            System.out.println("to change properties to an saved setting enter setting number , enter any other number and will be prompted for new value");
            change = scan.nextInt();
            if (change >=0 && change <all.length){
                //edits properties to the chosen settings
                all[change].changeDeployProperties(location);

            }else{
                System.out.println("enter db type");
                String type = scan.nextLine();
                type = scan.nextLine();
                System.out.println("enter db name");
                String name = scan.nextLine();
                System.out.println("enter db schema");
                String schema = scan.nextLine();
                System.out.println("enter passsword");
                String pw = scan.nextLine();
                //creates new object
                deployProperties deployProps = new deployProperties(type,name,schema,pw);
                //changes properties to values this object
                deployProps.changeDeployProperties(location);
                //writeManyObj(all,deployProps);
                //saves object
                deployProps.writeDeployObj(all);
                //displays updated properties file settings
                all = a.readDeployObj();
                change=all.length-1;
            }
            //dispDeployProperties();
            a.dispDeployProperties(location);
            editBVTProperties();
            //editDbProperties uses saved deploy.properties settings to properly configure dbname.properties
            editDbProperties(all[change]);
        }else{
            //because settings didnt change need to make a objects with current settings to be passed to dbProperties
            deployProperties deployProps = a.returnDeployProperties(location);
            editBVTProperties();
            //editDbProperties uses saved deploy.properties settings to properly configure dbname.properties
            editDbProperties(deployProps);


        }



    }
     // BVT properties is part of Deploy.properties but since there are fixed values it can be , i created a seperatte class to
    // edit them , this method can change bvt settings to one of the 5 possible settings
    public void editBVTProperties(){
        Scanner scan = new Scanner(System.in);
        BVTproperties bvt = new BVTproperties();
        bvt.dispBVTProperties(location);
        System.out.println("Would you like to change these settings 1:yes  rest:no");
        int editBvt = scan.nextInt();
        if (editBvt==1){
            BVTproperties allBVT [] = bvt.readBVTObj();
            System.out.println("enter setting you would like to change to , if number out of scope settings will not change ");
            int option= scan.nextInt();
            if (option>=0 && option<allBVT.length){
                allBVT[option].changeBVTProperties(location);

            }
            bvt.dispBVTProperties(location);
        }

    }
// for mdmia to run the db you choose in deploy.properties must be cateloged in a properties file dbname.properties
    // based on the setting chosen for deploy.properties checks to see if there is a dbname.properies file in this locations
    // if there is not it creates a new properties file which is then edited or if there is one there gives you the option to edit
    public void editDbProperties(deployProperties dp) {
        Scanner scan = new Scanner(System.in);
        dbProperties a = new dbProperties();
        File f = new File("DB.properties");
        int edit =0;
        //created new properties file from generic one stored in this program
        File destination = new File(location + "\\" + dp.dbname + ".properties");
        if (!destination.exists()){
            edit =1;
            try {
                Files.copy(f.toPath(), destination.toPath());
                System.out.println("new file "+dp.dbname + ".properties created in location "+ location);
            } catch (IOException x) {
                x.printStackTrace();

            }
        }else{
            //if already exits it displays values and gives you option to edit
            System.out.println(dp.dbname + ".properties  exists in location " + location);
            a.dispDbProperties(location,dp);
            System.out.println("would you like to edit these properties: 1=yes rest=no");
            edit = scan.nextInt();
        }if (edit ==1){
            //displays all saved settings with matching dbname
            dbProperties []all =  a.readDbObj(dp);
            System.out.println("to change properties to an saved setting enter setting number , enter any other number and will be prompted for new value");
            int change = scan.nextInt();
            if (all!=null &&change >=0 && change <all.length){
                //changes settings to vales of saved object
                all[change].changeDbProperties(location);

            }else{
                //prompts for valus to edit properties however dbtype and dbname are locked to that saved in deploy.properties
                System.out.println("db type is : "+ dp.dbtype);
                System.out.println("db name is : "+ dp.dbname);
                System.out.println("enter server name (ie mdmubu107)");
                String server = scan.nextLine();
                server = scan.nextLine();
                System.out.println("enter domain name (ie torolab.ibm.com)");
                String domain = scan.nextLine();
                System.out.println("enter server OS (AIX, Linux)");
                String os = scan.nextLine();
                System.out.println("enter version");
                String version = scan.nextLine();
                dbProperties dbProps = new dbProperties(dp.dbtype,dp.dbname,server,domain,os,version);
                //changes settings
                dbProps.changeDbProperties(location);
                //saves object
                dbProps.writeDbObj();


            }



            a.dispDbProperties(location,dp);

        }


        buildProperties();

    }
// checks to see if build.properties , if not there crabs latest file from this program and copies it to there
// (because no values need to be changed), at the end of this all nessary property files and there and filled to
    // run a cycle deployment of mdm
    public void buildProperties(){
        File f = new File("build.properties");
        File destination = new File(location + "\\build.properties");
        if (!destination.exists()){
            try {
                Files.copy(f.toPath(), destination.toPath());
                System.out.println("new file build.properties created in location "+ location);
            } catch (IOException x) {
                x.printStackTrace();

            }
        }else{
            System.out.println("build.properties exits in this location ");
        }

    }

}
