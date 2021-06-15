package absyn;

public class DecError extends Dec {

    public DecError(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public void accept( AbsynVisitor visitor, int level ) {
     visitor.visit( this, level );
    }
}