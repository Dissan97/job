package org.disagn.decorator.decorations;

import org.disagn.decorator.VisualTextComponent;
import org.disagn.models.users.User;

import java.util.Locale;

public class AccountDecorator extends Decorator{

    private final User user;
    public AccountDecorator(VisualTextComponent component, User user) {
        super(component);
        this.user = user;
    }

    @Override
    public String decorate(){
        String ret = "";
        if (this.user != null){
           ret = ": {"+ this.user.getUsername() + "@" + this.user.getUserType().name().toLowerCase(Locale.ROOT) + "}";
        }
        return super.decorate() + ret;
    }
}
