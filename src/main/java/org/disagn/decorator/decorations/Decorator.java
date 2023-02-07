package org.disagn.decorator.decorations;

import org.disagn.decorator.VisualTextComponent;

public class Decorator extends VisualTextComponent {

    protected VisualTextComponent component;

    public Decorator(VisualTextComponent component) {
        this.component = component;
    }

    @Override
    public String decorate() {
        return this.component.decorate();
    }
}
