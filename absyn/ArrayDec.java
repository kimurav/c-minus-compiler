package absyn;

public class ArrayDec extends VarDec {
    public String name;
    public IntExp size;
    public Type type;

    public ArrayDec(int r, int c, Type t, String n, IntExp s) {
        this.row = r;
        this.col = c;
        this.type = t;
        this.name = n;
        this.size = s;
    }

    public void accept( AbsynVisitor visitor, int level ) {
     visitor.visit( this, level );
    }
}