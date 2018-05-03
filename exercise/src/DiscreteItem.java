public class DiscreteItem extends Item {
    
	DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }
  
    double distance (Object a) {
        double v = 0;
        if(getValue() != ((Item) a).getValue()) {
            v = 1;
        }
        return v;
    }
}

