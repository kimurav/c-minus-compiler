package absyn;

public class IntExp extends Exp {
    public int val;

    public IntExp(int r, int c, String v) {
        this.row = r;
        this.col = c;
        if(v.length() != 0) {
            this.val = Integer.parseInt(v);
        }
        
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
     }

}