

public class Test {
public static void main(String[] args) {

String [] [] test ={
        {"Name","Department","CurrentCGPA","Expected Graduated","Actual Graduation Salary"},
        {"Meow","Artificial Intelligence","3.7","10000000","1000"},
        {"Woof","Software Engineer","3","4200","4200"},
        {"LWY","Information System","4.3","1000","1000000"},
                    };
printArray(test);
    System.out.println();

dataframe array = new dataframe(test);

array.colRange(new String []{"department","Actual Graduation Salary"});



}
    //------------------------------------------------------------------------------------------------
    public static void printArray(String [] [] p){
        for (int row = 0; row < p.length; row++) {
            for (int column = 0; column < p [row].length; column++) {
                System.out.print(p [row] [column] + " " );
            }
        System.out.println();

        }
    }
    public static void printArrayint(int [] [] p){
        for (int row = 0; row < p.length; row++) {
            for (int column = 0; column < p [row].length; column++) {
                System.out.print(p [row] [column] + " " );
            }
            System.out.println();

        }
    }




}