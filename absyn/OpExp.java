package absyn;

public class OpExp extends Exp {
    public Exp left;
    public int op;
    public Exp right;

    public final static int PLUS = 0;
    public final static int MINUS = 1;
    public final static int TIMES = 2;
    public final static int DIV = 3;
    public final static int EQ = 4;
    public final static int NEQ = 5;
    public final static int LT = 6;
    public final static int LTE = 7;
    public final static int GT = 8;
    public final static int GTE = 9;


    public OpExp(int r, int c, Exp lhs, int o, Exp rhs) {
        this.row = r;
        this.col = c;
        this.left = lhs;
        this.op = o;
        this.right = rhs;
    }
    public void accept( AbsynVisitor visitor, int level ) {
     visitor.visit( this, level );
    }

    public boolean isArithmatic() {
        switch(this.op) {
            case PLUS:
            case MINUS:
            case TIMES:
            case DIV:
                return true;
            default:
                return false; 
        }
    }

}