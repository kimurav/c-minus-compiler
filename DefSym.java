import absyn.*;
import java.util.ArrayList;
public class DefSym {
    String name;
    int size;
    public Type type;
    public int level;
    public ArrayList<Type> parameters;

    public DefSym(String name, Type type, int level) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.parameters = new ArrayList<Type>();
    }

    public DefSym(String name, Type type, int size, int level) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.level = level;
    }

    public String toString() {
        if(this.size == 0) {
            return name + ": " + type.toString();
        } else {
            return name+ ": " + type.toString() +"["+this.size+"]";
        }
        
    }
}