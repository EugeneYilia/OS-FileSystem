import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class TestOutputColorFont {
    public static void main(String[] args) {
        //System.out.println(ansi().eraseScreen().render("@|red Hello|@ @|green World|@"));
//        System.out.println(ansi().eraseScreen().fg(RED).a("Hello").fg(GREEN).a(" World").reset());
        int i = 1 % 7;
        System.out.print(ansi().eraseScreen().fg(RED).a(i).fg(YELLOW).a("World").reset());
        System.out.print(ansi().eraseScreen().fg(RED).a(i).fg(GREEN).a("World"));
    }
}
