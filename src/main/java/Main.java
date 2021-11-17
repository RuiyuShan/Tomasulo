import main.Tomasulo;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomasulo tomasulo = new Tomasulo();
        tomasulo.initialize();
        tomasulo.schedule();
    }
}
