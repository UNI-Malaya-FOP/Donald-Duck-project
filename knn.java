import java.util.Arrays;


public class knn {
    public static void main(String[] args) {
        double [] doubles = {5.2,9.8,7.6};
        double e = 1;
        double f = 3;
    }
    public static double information(double[] [] array, double x1 , double y1 ) { // y1 & X1 are the values of the object we need to classify
        
        double sum = Arrays.stream(array [0]).summaryStatistics().getSum();
        double avg = Arrays.stream(array [0]).summaryStatistics().getAverage();
        double count = Arrays.stream(array [0]).summaryStatistics().getCount();
        double dist;
        int k = (int) Math.sqrt(count);
        for (int i = 0; i < array.length; i++) {
            double x2 = array[i][0]; // a value from array
            double y2 = array[0][i]; // a corosponding value 
        dist = Math.sqrt( Math.pow(x1-x2,2) + Math.pow(y1-y2,2) );

        // add a method to append the dist to each item
        // sort by rank
        // choose "K" number of elements from the top
        /* **regression**
            -- output the mean of the top k elements

            **Classification**
            -- output the mode of the top k elements
        */
        }

        return 0;
    }




}
