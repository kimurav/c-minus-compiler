package absyn;

public class ReturnExp extends Exp {
 
    public Exp exp;

    public ReturnExp(int r, int c, Exp e) {
        this.row = r;
        this.col = c;
        this.exp = e;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}