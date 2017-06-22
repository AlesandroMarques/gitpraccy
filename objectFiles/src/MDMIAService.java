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

    public void editDeployProperties(){
        Scanner scan = new Scanner(System.in);
        deployProperties a = new deployProperties();
        a.dispDeployProperties(location);
        // dispDeployProperties();
        System.out.println("would you like to change properties ? 1= yes, rest =no");
        int change = scan.nextInt();
        if(change==1) {
            deployProperties all[] = a.readDeployObj();

            System.out.println("to change properties to an saved setting enter setting number , enter any other number and will be prompted for new value");
            change = scan.nextInt();
            if (change >=0 && change <all.length){
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
                deployProperties deployProps = new deployProperties(type,name,schema,pw);
                deployProps.changeDeployProperties(location);
                //writeManyObj(all,deployProps);
                deployProps.writeDeployObj(all);
                all = a.readDeployObj();
                change=all.length-1;
            }
            //dispDeployProperties();
            a.dispDeployProperties(location);
            editBVTProperties();
            editDbProperties(all[change]);
        }else{
            deployProperties deployProps = a.returnDeployProperties(location);
            editBVTProperties();
            editDbProperties(deployProps);


        }
        // else create current setting into a object and check if dbname.properties exits


    }

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

    public void editDbProperties(deployProperties dp) {
        Scanner scan = new Scanner(System.in);
        dbProperties a = new dbProperties();
        File f = new File("DB.properties");
        int edit =0;

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
            System.out.println(dp.dbname + ".properties  exists in location " + location);
            a.dispDbProperties(location,dp);
            System.out.println("would you like to edit these properties: 1=yes rest=no");
            edit = scan.nextInt();
        }if (edit ==1){
            dbProperties []all =  a.readDbObj(dp);
            System.out.println("to change properties to an saved setting enter setting number , enter any other number and will be prompted for new value");
            int change = scan.nextInt();
            if (all!=null &&change >=0 && change <all.length){
                all[change].changeDbProperties(location);

            }else{
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
                dbProps.changeDbProperties(location);

                dbProps.writeDbObj();


            }



            a.dispDbProperties(location,dp);

        }










        // edit dbname.properties file
        buildProperties();

    }

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
