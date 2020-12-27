

public class Test {
public static void main(String[] args) {

String [] [] test ={
        {"Name","Department","CurrentCGPA","Expected Graduated","Actual gradual salary"},
        {"Meow","Artificial Intelligence","3.7","10000000","1000"},
        {"Woof","Software Engineer","3","4200","4200"},
        {"LWY","Information System","4.3","1000","1000000"},
                    };
printArray(test);

dataframe array = new dataframe(test);

array.colRange("department");

//int [] [] testing = new int[5][5];
//
//for (int row = 0; row < test.length; row++) {
//        for (int column = 0; column < test [row].length; column++) {
//            test [row][column] = String.valueOf((int) (Math.random() * 100));
//        }
//        System.out.println();

//    }
//    for (int row = 0; row < testing.length; row++) {
//        for (int column = 0; column < testing [row].length; column++) {
//            testing [row][column] = Integer.parseInt( test [row][column]) ;
//        }
//    }

//    printArrayint(testing);
//    System.out.println("after method");
//    for (int i = 0; i < testing.length ; i++) {
//        Arrays.sort(testing[i]);
//    }
//    printArrayint(testing);
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