/**
 * Created by ALESANDROMarques on 2017-06-12.
 */

import java.util.Scanner;

public class objectFiles {

    public static void main(String args[]) {
       Scanner scan = new Scanner(System.in);
        System.out.println("Configuring MDMIA");
        System.out.println("enter location of property files");
        String loc = scan.nextLine();
        System.out.println(loc);

       MDMIAService serv = new MDMIAService(loc);
       serv.editDeployProperties();



    }





}