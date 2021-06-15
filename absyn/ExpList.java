package absyn;

public class ExpList extends Absyn {
    public Exp head;
    public ExpList tail;

    public ExpList(Exp h, ExpList l){
        this.head = h;
        this.tail = l;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}