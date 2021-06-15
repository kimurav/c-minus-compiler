package absyn;

public class SimpleDec extends VarDec {
    public Type type;
    public String name;

    public SimpleDec(int r, int c, Type t, String n) {
        this.row = r;
        this.col = c;
        this.type = t;
        this.name = n;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}