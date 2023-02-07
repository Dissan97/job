package org.disagn.decorator.decorations;

import org.disagn.decorator.VisualTextComponent;

public class BorderDecorator extends Decorator {
    public BorderDecorator(VisualTextComponent component) {
        super(component);
    }

    @Override
    public String decorate(){
        return "["+ super.decorate() + "]";
    }
}
