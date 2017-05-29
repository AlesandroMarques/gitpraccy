
import java.util.Scanner;
public class dosomething {

    public static void main(String[] args) {

    people [] parray = new people[2];
    parray = fillpeopleArray(parray);
    displayPeople(parray);
        raiseBylocation(parray);
        displayPeople(parray);

    }
    public static people [] fillpeopleArray(people [] parray ){
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < parray.length ; i++) {
            System.out.println("enter name");
            String name = scan.nextLine();
             name = scan.nextLine();
            System.out.println("enter location");
            String loc = scan.nextLine();
            System.out.println("enter salary");
            int sal = scan.nextInt();
            parray[i]= new people(name,loc,sal);
        }
        return parray;


    }
    public static void displayPeople(people [] parray){
        for (int i = 0; i <parray.length ; i++) {
            parray[i].display();
        }


    }

    public static void raiseBylocation(people [] parray){
        for (int i = 0; i <parray.length ; i++) {
            if (parray[i].location.equalsIgnoreCase("warden")){
                int r = (int)(parray[i].salary * .05);

                parray[i].raise(r);

            }

        }


        //return parray;
    }

}
