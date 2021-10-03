package org.srcom.ui.views;

import backend.BankAccount;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import backend.entidades.Expediente;
import lombok.extern.apachecommons.CommonsLog;
import org.json.JSONArray;
import org.json.JSONObject;
import org.srcom.rest.EjecutaPac;
import org.srcom.ui.components.Badge;
import org.srcom.ui.components.FlexBoxLayout;
import org.srcom.ui.components.ListItem;
import org.srcom.ui.layout.size.Horizontal;
import org.srcom.ui.layout.size.Right;
import org.srcom.ui.layout.size.Top;
import org.srcom.ui.layout.size.Vertical;
import org.srcom.ui.util.*;
import org.srcom.ui.util.css.Overflow;
import org.srcom.ui.util.css.PointerEvents;
import org.srcom.ui.util.css.TextOverflow;
import org.srcom.ui.util.css.lumo.BadgeColor;
import org.srcom.ui.util.css.lumo.BadgeShape;
import org.srcom.ui.util.css.lumo.BadgeSize;
import org.srcom.ui.views.main.MainView;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@PageTitle("Loomer")
@Route(value = "expedientes", layout = MainView.class)
public class Expedientes extends ViewFrame {

	public static final int MOBILE_BREAKPOINT = 480;

	private TextField filterText = new TextField();

	List<Expediente> lf = new ArrayList<Expediente>();

	TextField txNombre = new TextField();



	private Grid<Expediente> grid = new Grid<>();


	private Registration resizeListener;

	public Expedientes() {
		setViewContent(createContent());
	}

	private Component createContent() {

		;

		HorizontalLayout hl = new HorizontalLayout();
		VerticalLayout vl = new VerticalLayout();
		FlexBoxLayout content = new FlexBoxLayout();
		Button btBuscar = new Button();
		btBuscar.setText(getTranslation("buscar"));

		btBuscar.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
				// PULSAMOS EL BOTÓN DE BUSCAR
				String consulta = "SELECT  EE.FHALTA, TO_CHAR(EA.FHOCURRE,'DD/MM/YYYY') FHOCURRE, EE.CDEXPAJE, EE.CDASISTE, EE.NBBENOM NOMBRE, EE.NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES ee, EX_ASISTENCIAS EA " +
						"WHERE ee.cdasiste = ea.cdasiste AND ROWNUM < 20";
				if ( txNombre!=null )
					consulta += " AND UPPER(EE.NBBENOM) LIKE '%"+ txNombre.getValue().toUpperCase() + "%' ";
				createGrid(consulta);
			}
		});

		filterText.setClearButtonVisible(true);
		filterText.setPlaceholder(getTranslation("filtrar"));
		filterText.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
			@Override
			public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
				if (filterText.getValue() == null || filterText.getValue().isEmpty()) {
					grid.setItems(lf);
				} else {
					System.out.println("entramos" +  filterText.getValue());
					grid.setItems(lf.stream().filter( c -> c.getNOMBRE().startsWith(filterText.getValue())));

				}
			}
		});

		vl.add(filterText);


		//createGrid("SELECT  EE.FHALTA, TO_CHAR(EA.FHOCURRE,'DD/MM/YYYY') FHOCURRE, EE.CDEXPAJE, EE.CDASISTE, EE.NBBENOM NOMBRE, EE.NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES ee, EX_ASISTENCIAS EA " +
		//		"WHERE ee.cdasiste = ea.cdasiste AND ROWNUM < 5");
		createGrid(null);
		vl.add(grid);

		txNombre.setPlaceholder(getTranslation("nombre"));

		TextField txNIF = new TextField();
		txNIF.setPlaceholder(getTranslation("nif"));

		hl.add(txNombre, txNIF, btBuscar);

		setViewHeader(hl);
		hl.setMargin(true);
		content.add(vl);

		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		content.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		return content;
	}

	private void createGrid(String consulta) {
		//grid = new Grid<HashMap<String, Object>>();
		//grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
		//grid.addThemeName("mobile");

		// LLENAMOS LA LISTA DEL GRID CON LOS EXPEDIENTES CON SETDATAPROVIDER
		//grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));

		grid.setSizeFull();
		grid.removeAllColumns();

		//consulta = "SELECT  EE.FHALTA, TO_CHAR(EA.FHOCURRE,'DD/MM/YYYY') FHOCURRE, EE.CDEXPAJE, EE.CDASISTE, EE.NBBENOM NOMBRE, EE.NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES ee, EX_ASISTENCIAS EA " +
		//		"WHERE ee.cdasiste = ea.cdasiste AND ROWNUM < 5";


		String expedientesLista = null;
		
		if ( consulta!=null ) {
			expedientesLista = new EjecutaPac().EjecutaPacStr("PAC_SHWEB_LISTAS", "F_QUERY", consulta);
			// hide the splash screen after the main view is loaded
			//UI.getCurrent().getPage().executeJs("document.querySelector('#splash-screen').classList.add('loaded')");
		}
		/*String expedientesLista = new EjecutaPac().EjecutaPacStr("PAC_SHWEB_LISTAS","F_QUERY",
				//		"SELECT CDASISTE, NBBENOM || ' ' || NBBEAPE1 NOMBRE, FHALTA, CDEXPAJE  FROM EX_EXPEDIENTES WHERE ROWNUM < 5");
				"SELECT  EE.FHALTA, TO_CHAR(EA.FHOCURRE,'DD/MM/YYYY') FHOCURRE, EE.CDEXPAJE, EE.CDASISTE, EE.NBBENOM NOMBRE, EE.NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES ee, EX_ASISTENCIAS EA " +
						"WHERE ee.cdasiste = ea.cdasiste AND ROWNUM < 5");*/

		try {

			lf.clear();

			JSONObject jsonObject = new JSONObject(expedientesLista);

			JSONArray lista = jsonObject.getJSONArray("RETURN");

			for (int i = 0 ; i < lista.length(); i++) {

				System.out.println("entramos");
				JSONObject obj = lista.getJSONObject(i);
				System.out.println("obj" + obj);
				Expediente cl = new Expediente();
				System.out.println("nombre" + obj.getString("NOMBRE"));
				cl.setAPELLIDO(obj.getString("APELLIDO"));
				cl.setNOMBRE(obj.getString("NOMBRE"));
				cl.setCDASISTE(obj.getInt("CDASISTE"));
				cl.setFHALTA(obj.getString("FHALTA"));
				 if(!obj.isNull("CDEXPAJE")) {
					 cl.setCDEXPAJE(obj.getString("CDEXPAJE"));
				 }

				lf.add(cl);
			}


			grid.setItems(lf);

		} catch ( Exception e) {
			log.error( e.toString() );

		}

		grid.addColumn(Expediente::getCDASISTE )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Expediente")
				.setSortable(true);
		grid.addColumn(Expediente::getNOMBRE )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("NOMBRE")
				.setSortable(true);
		grid.addColumn(Expediente::getAPELLIDO )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("APELLIDO")
				.setSortable(true);
		grid.addColumn(Expediente::getCDEXPAJE )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("CDEXPAJE")
				.setSortable(true);
		grid.addColumn(Expediente::getFHALTA )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("FHALTA")
				.setSortable(true);


		//return grid;
	}

	private BankAccountMobileTemplate getMobileTemplate(BankAccount bankAccount) {
		return new BankAccountMobileTemplate(bankAccount);
	}

	private Component createOwnerInfo(BankAccount bankAccount) {
		ListItem item = new ListItem(bankAccount.getOwner());
		item.setPadding(Vertical.XS);
		item.setPrefix(new Image(bankAccount.getLogoPath(), "Company logo"));
		item.setSpacing(Right.M);
		return item;
	}

	private Component createBankInfo(BankAccount bankAccount) {
		ListItem item = new ListItem(bankAccount.getAccount(), bankAccount.getBank());
		item.setPadding(Vertical.XS);
		return item;
	}

	private Label createAvailability(BankAccount bankAccount) {
		Double availability = bankAccount.getAvailability();
		Label amountLabel = UIUtils.createAmountLabel(availability);
		if (availability > 0) {
			UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
		} else {
			UIUtils.setTextColor(TextColor.ERROR, amountLabel);
		}
		return amountLabel;
	}

	private void viewDetails(BankAccount bankAccount) {
		UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
	}

	/*@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		getUI().ifPresent(ui -> {
			Page page = ui.getPage();
			resizeListener = page.addBrowserWindowResizeListener(event -> updateVisibleColumns(event.getWidth()));
			page.retrieveExtendedClientDetails(details -> updateVisibleColumns(details.getBodyClientWidth()));
		});
	}*/

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		/*resizeListener.remove();*/
		super.onDetach(detachEvent);
	}

	/*private void updateVisibleColumns(int width) {
		boolean mobile = width < MOBILE_BREAKPOINT;
		List<Grid.Column<BankAccount>> columns = grid.getColumns();

		// "Mobile" column
		columns.get(0).setVisible(mobile);

		// "Desktop" columns
		for (int i = 1; i < columns.size(); i++) {
			columns.get(i).setVisible(!mobile);
		}
	}*/

	/**
	 * A layout for displaying BankAccount info in a mobile friendly format.
	 */
	private class BankAccountMobileTemplate extends FlexBoxLayout {

		private BankAccount bankAccount;

		public BankAccountMobileTemplate(BankAccount bankAccount) {
			this.bankAccount = bankAccount;

			UIUtils.setLineHeight(LineHeight.M, this);
			UIUtils.setPointerEvents(PointerEvents.NONE, this);

			setPadding(Vertical.S);
			setSpacing(Right.L);

			Image logo = getLogo();
			FlexBoxLayout owner = getOwner();
			Label account = getAccount();
			FlexBoxLayout availability = getAvailability();

			FlexBoxLayout column = new FlexBoxLayout(owner, account, availability);
			column.setFlexDirection(FlexDirection.COLUMN);
			column.setOverflow(Overflow.HIDDEN);

			add(logo, column);
			setFlexGrow(1, column);
		}

		private Image getLogo() {
			Image logo = new Image(bankAccount.getLogoPath(), "Company logo");
			setFlexShrink("0", logo);
			logo.setHeight(LumoStyles.IconSize.M);
			logo.setWidth(LumoStyles.IconSize.M);
			return logo;
		}

		private FlexBoxLayout getOwner() {
			Label owner = UIUtils.createLabel(FontSize.M, TextColor.BODY, bankAccount.getOwner());
			UIUtils.setOverflow(Overflow.HIDDEN, owner);
			UIUtils.setTextOverflow(TextOverflow.ELLIPSIS, owner);

			Badge id = new Badge(String.valueOf(bankAccount.getId()), BadgeColor.NORMAL, BadgeSize.S, BadgeShape.PILL);

			FlexBoxLayout wrapper = new FlexBoxLayout(owner, id);
			wrapper.setAlignItems(Alignment.CENTER);
			wrapper.setFlexGrow(1, owner);
			wrapper.setFlexShrink("0", id);
			wrapper.setSpacing(Right.M);
			return wrapper;
		}

		private Label getAccount() {
			Label account = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, bankAccount.getAccount());
			account.addClassNames(LumoStyles.Margin.Bottom.S);
			UIUtils.setOverflow(Overflow.HIDDEN, account);
			UIUtils.setTextOverflow(TextOverflow.ELLIPSIS, account);
			return account;
		}

		private FlexBoxLayout getAvailability() {
			Label availability = createAvailability(bankAccount);
			availability.setText("$" + availability.getText());
			Label updated = UIUtils.createLabel(FontSize.XS, TextColor.TERTIARY, UIUtils.formatDate(bankAccount.getUpdated()));

			FlexBoxLayout wrapper = new FlexBoxLayout(availability, updated);
			wrapper.setAlignItems(Alignment.BASELINE);
			wrapper.setFlexGrow(1, availability);
			return wrapper;
		}

		private void configureFilter() {
			filterText.setPlaceholder("Filter by name...");
			filterText.setClearButtonVisible(true);
			filterText.setValueChangeMode(ValueChangeMode.LAZY);
			filterText.addValueChangeListener(e -> updateList());
		}

		private void updateList() {
			//grid.setDragFilter(Expediente -> Getter);
		}
	}
}
