package absyn;

public class VarExp extends Exp {
    public Var variable;

    public VarExp(int r, int c, Var v) {
        this.row = r;
        this.col = c;
        this.variable = v;
    }
     public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}