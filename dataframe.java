public class dataframe {
    String[] [] array;

    public dataframe(String[][] array) {
        this.array = array;
    }

    public String[][] getArray() {
        return array;
    }

    public void setArray (String[][] array) {
        this.array = array;
    }

    public void concatenation(){

    }

    public void  colRange(String str){
        int [] printarray = new int[array [0].length];
        boolean bool = false;
        for (int i = 0; i < array[0].length ; i++) {
                if (array[0][i].equalsIgnoreCase(str)) {
                            printarray[i] = i;
                }
        }
        for (int j : printarray) {
            System.out.println(j);
        }
//        for (int i = 0; i < array[0].length ; i++) {
//            for (int j = 0; j < array.length; j++) {
//                System.out.println(array[i][j]);
//            }
//        }

    }



    public void sort(){

    }

    public void dropDuplicates(){

    }

    public void dropNull(){

    }



}



