package src.edu.dataframe;

public interface DataFrame {

    static DataFrame create() {
        return new NewDataFrame();
    }
}
