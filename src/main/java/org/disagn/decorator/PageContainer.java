package org.disagn.decorator;

import org.disagn.decorator.decorations.AccountDecorator;
import org.disagn.decorator.decorations.BorderDecorator;
import org.disagn.models.users.User;


public class PageContainer {

    private VisualTextComponent component;
    private final User user;
    public PageContainer(String page, User user) {
        this.user = user;
        this.decorate(page);
    }

    public PageContainer(String page){
        this(page, null);
    }

    public void decorate(String page) {
        PageDecorator pageDecorator = new PageDecorator(page);
        this.setComponent(pageDecorator);
        AccountDecorator accountDecorator = new AccountDecorator(pageDecorator, this.user);
        BorderDecorator borderDecorator = new BorderDecorator(accountDecorator);
        this.setComponent(borderDecorator);
    }

    public void setComponent(VisualTextComponent component) {
        this.component = component;
    }

    public String display(){
        return this.component.decorate();
    }



}
