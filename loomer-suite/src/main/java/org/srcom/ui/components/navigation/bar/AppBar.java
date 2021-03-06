package org.srcom.ui.components.navigation.bar;

import ch.carnet.kasparscherrer.LanguageSelect;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.Lumo;
import org.srcom.ui.components.FlexBoxLayout;
import org.srcom.ui.components.navigation.tab.NaviTab;
import org.srcom.ui.components.navigation.tab.NaviTabs;
import org.srcom.ui.util.LumoStyles;
import org.srcom.ui.util.UIUtils;
import org.srcom.ui.views.Administracion;
import org.srcom.ui.views.main.MainView;

import java.util.ArrayList;
import java.util.Locale;

@CssImport("./styles/components/app-bar.css")
public class AppBar extends FlexBoxLayout {

	private String CLASS_NAME = "app-bar";

	private FlexBoxLayout container;

	private LanguageSelect langSelect;


	private Button menuIcon;
	private Button contextIcon;


	private ComboBox cbIdiomas = new ComboBox();
	private DrawerToggle drawerToggle = new DrawerToggle();

	private H4 title;
	private FlexBoxLayout actionItems;
	private Avatar avatar;
	private HorizontalLayout hr;

	private FlexBoxLayout tabContainer;
	private NaviTabs tabs;
	private ArrayList<Registration> tabSelectionListeners;
	private Button addTab;

	private TextField search;
	private Registration searchRegistration;

	public enum NaviMode {
		MENU, CONTEXTUAL
	}

	public AppBar(String title, NaviTab... tabs) {
		setClassName(CLASS_NAME);

		initMenuIcon();
		initContextIcon();
		initTitle(title);
		initSearch();
		initAvatar();
		initActionItems();
		initContainer();
		initTabs(tabs);
	}

	public void setNaviMode(NaviMode mode) {
		if (mode.equals(NaviMode.MENU)) {
			menuIcon.setVisible(true);
			contextIcon.setVisible(false);
		} else {
			menuIcon.setVisible(false);
			contextIcon.setVisible(true);
		}
	}

	private void initMenuIcon() {
		menuIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
		menuIcon.addClassName(CLASS_NAME + "__navi-icon");
		menuIcon.addClickListener(e -> MainView.get().getNaviDrawer().toggle());
		UIUtils.setAriaLabel("Menu", menuIcon);
		UIUtils.setLineHeight("1", menuIcon);
	}

	private void initContextIcon() {
		contextIcon = UIUtils
				.createTertiaryInlineButton(VaadinIcon.ARROW_LEFT);
		contextIcon.addClassNames(CLASS_NAME + "__context-icon");
		contextIcon.setVisible(false);
		UIUtils.setAriaLabel("Back", contextIcon);
		UIUtils.setLineHeight("1", contextIcon);
	}

	private void initTitle(String title) {
		this.title = new H4(title);
		this.title.setClassName(CLASS_NAME + "__title");
	}

	private void initSearch() {
		search = new TextField();
		search.setPlaceholder("Search");
		search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		search.setVisible(false);
	}

	private void initAvatar() {
		avatar = new Avatar();
		avatar.setClassName(CLASS_NAME + "__avatar");


		avatar.setName(VaadinSession.getCurrent().getAttribute("usunom").toString()
				+ " " + VaadinSession.getCurrent().getAttribute("usuape1").toString()
				+ " " + VaadinSession.getCurrent().getAttribute("usuape1").toString());
		avatar.setAbbreviation(VaadinSession.getCurrent().getAttribute("usunom").toString().substring(1,1)
				+VaadinSession.getCurrent().getAttribute("usuape1").toString().substring(1,1));


		hr = new HorizontalLayout();

		Label lbUser = new Label();
		lbUser.setText  (VaadinSession.getCurrent().getAttribute("usunom").toString()
				+ " " + VaadinSession.getCurrent().getAttribute("usuape1").toString());


		hr.add(lbUser);
		hr.add(avatar);
		hr.setAlignItems(FlexComponent.Alignment.CENTER);
		hr.getStyle().set("margin-left", "auto");

		ContextMenu contextMenu = new ContextMenu(hr);
		contextMenu.setOpenOnClick(true);
		contextMenu.addItem(getTranslation("logout" ) + " " + VaadinSession.getCurrent().getAttribute("usunom").toString()
						+ " " + VaadinSession.getCurrent().getAttribute("usuape1").toString()
						+ " " + VaadinSession.getCurrent().getAttribute("usuape1").toString(),
				e -> {
					//UI.getCurrent().getPage().setLocation("/logout")
					//UI.getCurrent().navigate(MainView.class);
					VaadinSession.getCurrent().getSession().invalidate();

				});

		contextMenu.addItem(getTranslation("cambiartema" ),
				e -> {

					ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //

					if (themeList.contains(Lumo.DARK)) { //
						themeList.remove(Lumo.DARK);
						themeList.add(Lumo.LIGHT);
					} else {
						themeList.add(Lumo.DARK);
					}

				});

		boolean useLanguageCookies = true;

		/*langSelect = new LanguageSelect(useLanguageCookies, new Locale("en"));
		contextMenu.addItem(langSelect);*/
		/*contextMenu.addItem( getTranslation(getTranslation("language")),
				e -> Notification.show("Not implemented yet.", 3000,
						Notification.Position.BOTTOM_CENTER));*/
	}

	private void initActionItems() {
		actionItems = new FlexBoxLayout();
		actionItems.addClassName(CLASS_NAME + "__action-items");
		actionItems.setVisible(false);
	}

	private void initContainer() {

		container = new FlexBoxLayout(menuIcon,  contextIcon, this.title, search,
				actionItems, hr );
		container.addClassName(CLASS_NAME + "__container");
		container.setAlignItems(Alignment.CENTER);
		container.setFlexGrow(1, search);
		add(container);
	}

	private void initTabs(NaviTab... tabs) {
		addTab = UIUtils.createSmallButton(VaadinIcon.PLUS);
		addTab.addClickListener(e -> this.tabs
				.setSelectedTab(addClosableNaviTab("New Tab", Administracion.class)));
		addTab.setVisible(false);

		this.tabs = tabs.length > 0 ? new NaviTabs(tabs) : new NaviTabs();
		this.tabs.setClassName(CLASS_NAME + "__tabs");
		this.tabs.setVisible(false);
		for (NaviTab tab : tabs) {
			configureTab(tab);
		}

		this.tabSelectionListeners = new ArrayList<>();

		tabContainer = new FlexBoxLayout(this.tabs, addTab);
		tabContainer.addClassName(CLASS_NAME + "__tab-container");
		tabContainer.setAlignItems(Alignment.CENTER);
		add(tabContainer);
	}

	/* === MENU ICON === */

	public Button getMenuIcon() {
		return menuIcon;
	}

	/* === CONTEXT ICON === */

	public Button getContextIcon() {
		return contextIcon;
	}

	public void setContextIcon(Icon icon) {
		contextIcon.setIcon(icon);
	}

	/* === TITLE === */

	public String getTitle() {
		return this.title.getText();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	/* === ACTION ITEMS === */

	public Component addActionItem(Component component) {
		actionItems.add(component);
		updateActionItemsVisibility();
		return component;
	}

	public Button addActionItem(VaadinIcon icon) {
		Button button = UIUtils.createButton(icon, ButtonVariant.LUMO_SMALL,
				ButtonVariant.LUMO_TERTIARY);
		addActionItem(button);
		return button;
	}

	public void removeAllActionItems() {
		actionItems.removeAll();
		updateActionItemsVisibility();
	}

	/* === AVATAR == */

	public Avatar getAvatar() {
		return avatar;
	}

	/* === TABS === */

	public void centerTabs() {
		tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
	}

	private void configureTab(Tab tab) {
		tab.addClassName(CLASS_NAME + "__tab");
		updateTabsVisibility();
	}

	public Tab addTab(String text) {
		Tab tab = tabs.addTab(text);
		configureTab(tab);
		return tab;
	}

	public Tab addTab(String text,
	                  Class<? extends Component> navigationTarget) {
		Tab tab = tabs.addTab(text, navigationTarget);
		configureTab(tab);
		return tab;
	}

	public Tab addClosableNaviTab(String text,
	                              Class<? extends Component> navigationTarget) {
		Tab tab = tabs.addClosableTab(text, navigationTarget);
		configureTab(tab);
		return tab;
	}

	public Tab getSelectedTab() {
		return tabs.getSelectedTab();
	}

	public void setSelectedTab(Tab selectedTab) {
		tabs.setSelectedTab(selectedTab);
	}

	public void updateSelectedTab(String text,
	                              Class<? extends Component> navigationTarget) {
		tabs.updateSelectedTab(text, navigationTarget);
	}

	public void navigateToSelectedTab() {
		tabs.navigateToSelectedTab();
	}

	public void addTabSelectionListener(
			ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
		Registration registration = tabs.addSelectedChangeListener(listener);
		tabSelectionListeners.add(registration);
	}

	public int getTabCount() {
		return tabs.getTabCount();
	}

	public void removeAllTabs() {
		tabSelectionListeners.forEach(registration -> registration.remove());
		tabSelectionListeners.clear();
		tabs.removeAll();
		updateTabsVisibility();
	}

	/* === ADD TAB BUTTON === */

	public void setAddTabVisible(boolean visible) {
		addTab.setVisible(visible);
	}

	/* === SEARCH === */

	public void searchModeOn() {
		menuIcon.setVisible(false);
		title.setVisible(false);
		actionItems.setVisible(false);
		tabContainer.setVisible(false);

		contextIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
		contextIcon.setVisible(true);
		searchRegistration = contextIcon
				.addClickListener(e -> searchModeOff());

		search.setVisible(true);
		search.focus();
	}

	public void addSearchListener(HasValue.ValueChangeListener listener) {
		search.addValueChangeListener(listener);
	}

	public void setSearchPlaceholder(String placeholder) {
		search.setPlaceholder(placeholder);
	}

	private void searchModeOff() {
		menuIcon.setVisible(true);
		title.setVisible(true);
		tabContainer.setVisible(true);

		updateActionItemsVisibility();
		updateTabsVisibility();

		contextIcon.setVisible(false);
		searchRegistration.remove();

		search.clear();
		search.setVisible(false);
	}

	/* === RESET === */

	public void reset() {
		//alberto - el titulo no lo quitamos
		//title.setText("");
		setNaviMode(NaviMode.MENU);
		removeAllActionItems();
		removeAllTabs();
	}

	/* === UPDATE VISIBILITY === */

	private void updateActionItemsVisibility() {
		actionItems.setVisible(actionItems.getComponentCount() > 0);
	}

	private void updateTabsVisibility() {
		tabs.setVisible(tabs.getComponentCount() > 0);
	}
}
