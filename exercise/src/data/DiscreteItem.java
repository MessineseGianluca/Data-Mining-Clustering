package data;
public class DiscreteItem extends Item {
    
	DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }
  
    double distance (Object a) {
        double v = 0;
        if (!((Item) a).getValue().equals(getValue())) {
            v = 1;
        }
        return v;
    }
}

