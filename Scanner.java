import java.io.InputStreamReader;
import java_cup.runtime.Symbol;

public class Scanner {
    private Lexer scanner = null;

    public Scanner(Lexer lexer) {
        this.scanner = lexer;
    }
    public Symbol getNextToken() throws java.io.IOException {
        return this.scanner.next_token();
    }

    public static void main(String args[]) {
        try {
            Scanner sc = new Scanner(new Lexer(new InputStreamReader(System.in)));
            Symbol tok = null;
            while( (tok = sc.getNextToken()) != null) {
                System.out.println(tok);
            }
        }
        catch(Exception e) {
            System.out.println("Unexpected error");
            e.printStackTrace();
        }
    }
}