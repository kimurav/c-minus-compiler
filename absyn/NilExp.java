package absyn;

public class NilExp extends Exp {

    public NilExp(int r, int c) {
        this.row = r;
        this.col = c;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}