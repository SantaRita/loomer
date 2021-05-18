package org.srcom.views.main;

import java.util.Optional;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.srcom.views.helloworld.HelloWorldView;
import org.srcom.views.about.AboutView;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.CssImport;

/**
 * The main view is a top-level placeholder for other views.
 */
@CommonsLog
@PWA(name = "loomer-suite", shortName = "loomer-suite", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
@CssImport("lumo-css-framework/all-classes.css")
@CssImport("./views/main/main-view.css")
public class MainView extends AppLayout {

    private final Tabs menu;

    public MainView() {
        HorizontalLayout header = createHeader();
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, menu));
    }

    private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(header, menu);
        return layout;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setId("header");
        Image logo = new Image("images/logo.png", "loomer-suite logo");
        logo.setId("logo");
        header.add(logo);
        Avatar avatar = new Avatar();
        avatar.setId("avatar");


        avatar.setName(VaadinSession.getCurrent().getAttribute("usunom").toString()
                    + " " + VaadinSession.getCurrent().getAttribute("usuape1").toString()
                    + " " + VaadinSession.getCurrent().getAttribute("usuape1").toString());
        avatar.setAbbreviation(VaadinSession.getCurrent().getAttribute("usunom").toString().substring(1,1)
                    +VaadinSession.getCurrent().getAttribute("usuape1").toString().substring(1,1));


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("Datos usuario sesion:"+ ((UserDetails)principal).getUsername());



        HorizontalLayout hr = new HorizontalLayout();

        Label lbUser = new Label();
        lbUser.setText  (VaadinSession.getCurrent().getAttribute("usunom").toString()
                + " " + VaadinSession.getCurrent().getAttribute("usuape1").toString());

        hr.add(lbUser);
        hr.add(avatar);

        hr.setAlignItems(FlexComponent.Alignment.CENTER);


        hr.getStyle().set("margin-left", "auto");

        header.add(new H1("Loomer" + " " + getTranslation("version")));

        header.add(hr);

        /*hr.addClickListener(new ComponentEventListener<ClickEvent<HorizontalLayout>>() {
            @Override
            public void onComponentEvent(ClickEvent<HorizontalLayout> horizontalLayoutClickEvent) {
                ContextMenu menu
            }
        });*/

        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{createTab("Hello World", HelloWorldView.class), createTab("About", AboutView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }
}
