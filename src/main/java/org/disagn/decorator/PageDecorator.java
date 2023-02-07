package org.disagn.decorator;

public class PageDecorator extends VisualTextComponent{

    private final String page;

    public PageDecorator(String page) {
        this.page = page;
    }

    @Override
    public String decorate() {
        return this.page;
    }

}
