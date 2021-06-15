package absyn;


public class VarDecList extends Absyn {
    public VarDec head;
    public VarDecList tail;

    public VarDecList( VarDec h, VarDecList l) {
        this.head = h;
        this.tail = l;
    }
     public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}