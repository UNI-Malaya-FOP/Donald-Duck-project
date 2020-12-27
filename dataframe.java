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

    public void  colRange(String [] str){
        for (String p:str
             ) {


//            int[] printarray = new int[array[0].length];
            for (int i = 0; i < array[0].length; i++) {
                if (array[0][i].equalsIgnoreCase(p)) {
                    for (String[] strings : array) {
                        System.out.println(strings[i]);
                    }

                }

            }
        }
    }



    public void sort(){

    }

    public void dropDuplicates(){

    }

    public void dropNull(){

    }



}



