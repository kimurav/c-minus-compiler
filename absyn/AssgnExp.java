package absyn;


public class AssgnExp extends Exp {
    public Var left;
    public Exp right;

    public AssgnExp(int r, int c, Var lhs, Exp rhs) {
        this.row = r;
        this.col = c;
        this.left = lhs;
        this.right = rhs;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}