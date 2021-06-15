package absyn;

public class IndexVar extends Var {
    public String name;
    public Exp index;

    public IndexVar(int r, int c, String n, Exp e) {
        this.row = r;
        this.col = c;
        this.name = n;
        this.index = e;
    } 
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
