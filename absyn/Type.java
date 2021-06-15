package absyn;

public class Type extends Absyn {
    public int type;
    public static final int INT = 0;
    public static final int VOID = 1;
    public static final int ERROR = -1;

    public Type(int r, int c, int t) {
        this.row = r;
        this.col = c;
        this.type = t;
    }
    
    public Type(int t) {
        this.type = t;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }

    public String toString() {
        switch(this.type) {
            case INT:
                return "int";
            case VOID:
                return "void";
            case ERROR:
                return "error";
        }
        return "";
    }

    public boolean equals(Type t) {
        if(t.type == this.type) {
            return true;
        } else {
            return false;
        }
    }
}
