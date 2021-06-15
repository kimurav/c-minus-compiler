package absyn;

public class IfExp extends Exp {
    public Exp testBlock;
    public Exp thenBlock;
    public Exp elseBlock;

    public IfExp(int r, int c, Exp test, Exp then, Exp elseb) {
        this.row = r;
        this.col = c;
        this.testBlock = test;
        this.thenBlock = then;
        this.elseBlock = elseb;
    }

    public IfExp(int r, int c, Exp test, Exp then) {
        this.row = r;
        this.col = c;
        this.testBlock = test;
        this.thenBlock = then;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}