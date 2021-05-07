package org.srcom.views.main;

import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route("/admin")
@Secured("ROLE_Admin") //
class AdminView extends VerticalLayout {
    @Autowired
    public AdminView() {
        Label label = new Label("Looks like you are admin!");
        add("admins");
        //add(label);
    }

}
