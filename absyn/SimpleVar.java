package absyn;

public class SimpleVar extends Var {
    public String name;

    public SimpleVar(int r, int c, String n) {
        this.row = r;
        this.col = c;
        this.name = n;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}