package absyn;

public abstract class Absyn {
    public int row, col;
    
    abstract public void accept( AbsynVisitor visitor, int level );
}