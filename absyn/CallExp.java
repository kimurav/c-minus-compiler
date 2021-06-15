package absyn;

public class CallExp extends Exp {
    
    public String func;
    public ExpList args;

    public CallExp(int r, int c, String f, ExpList a) {
        this.row = r;
        this.col = c;
        this.func = f;
        this.args = a;
    }
    public void accept( AbsynVisitor visitor, int level ) {
     visitor.visit( this, level );
    }
}