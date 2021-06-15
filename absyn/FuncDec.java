package absyn;

public class FuncDec extends Dec {

    public Type type;
    public String funcName;
    public VarDecList params;
    public CompoundExp body;
    public int funcAddr;
    
    public FuncDec(int r, int c, Type t,String n, VarDecList p, CompoundExp b) {
        this.row = r;
        this.col = c;
        this.type = t;
        this.funcName = n;
        this.params = p;
        this.body = b;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}