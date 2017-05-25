/**
 * Created by ALESANDROMarques on 2017-05-25.
 */

public class people {
    String name;
    String location;
    int salary;

    public people(String n, String l, int s){
        name =n;
        location=l;
        salary=s;


    }

    public void display(){
        System.out.println(name + "  " + location + "  "+ salary   );
    }
}
