import java.util.Arrays;

public class Scaling {
    public static void main(String[] args) {


        double[] tester = {
            3.48, 2.30, 3.61, 3.16, 3.56, 2.9, 3.99, 4.87, 3.91, 6.28
        } ;
       double[] k = stdScaling(tester);

        System.out.println(  "std "+stddev(tester));
        System.out.println("mean " +mean(tester));
        System.out.println("tester = " + Arrays.toString(k));
       double[] j = min_max_scaling(tester);
        System.out.println("j = " + Arrays.toString(j));
        
    }
    //------------------------------------------------------------------------------------------------------------------
    public static double stddev(double[] array) {
        double std = 0;
        double avg =mean(array);
        for (double i:array
             ) {

            std += Math.pow (i - avg ,2) / array.length;
        }

        return Math.sqrt(std);
    }

    public static double mean(double[] array) {
        double Mean = 0;
        double sum = 0;
        double count = array.length;
        for (double i:array
             ) {
            sum += i;
        }
        Mean = sum/count;


        return Mean;
    }

    public static double[] stdScaling(double[] array){
        double [] new_array = new double[array.length];
        double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
        double avg = mean(array);
        double standard_deviation = stddev(array);
        for (double p : array) {
                max = Math.max(max, p);
                min = Math.min(min, p);
            }
        for (int i = 0; i < array.length; i++) {

            new_array [i] = (array [i] - avg )/ standard_deviation;
        }
        return new_array;

    }
    public static double [] min_max_scaling (double [] array) {
        double [] new_array = new double[array.length];
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double range;
        for (double p : array) {
            max = Math.max(max, p);
            min = Math.min(min, p);
        }
        range = max - min;
        for (int i = 0; i < array.length; i++) {

            new_array [i] = (array [i] - min )/ range;
        }

        return new_array;
    }



}