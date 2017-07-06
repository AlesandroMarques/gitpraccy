/**
 * Created by ALESANDROMarques on 2017-06-12.
 */

import java.util.Scanner;

public class objectFiles {

    public static void main(String args[]) {
        //calls service you would like to run then goes through all steps tp make sure location in ehich you are calling mdmia from
        //has all nessasary properties files and that they are completed
       Scanner scan = new Scanner(System.in);
        System.out.println("Configuring MDMIA");
        System.out.println("enter location of property files");
        String loc = scan.nextLine();
        System.out.println(loc);

       MDMIAService serv = new MDMIAService(loc);
       serv.editDeployProperties();



    }





}