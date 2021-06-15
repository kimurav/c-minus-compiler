package absyn;

public class DecList extends Absyn {
    public Dec head;
    public DecList tail;

    public DecList(Dec h, DecList t) {
        this.head = h;
        this.tail = t;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}