package absyn;

public class CompoundExp extends Exp {
    
    public VarDecList decs;
    public ExpList exps;

    public CompoundExp(int r, int c, VarDecList l, ExpList el) {
        this.row = r;
        this.col = c;
        this.decs = l;
        this.exps = el;
    }
    public void accept( AbsynVisitor visitor, int level ) {
      visitor.visit( this, level );
    }
}