package absyn;

public class WhileExp extends Exp {
    public Exp test;
    public Exp body;

    public WhileExp(int r, int c, Exp t, Exp b) {
        this.row = r;
        this.col = c;
        this.test = t;
        this.body = b;
    }
     public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}