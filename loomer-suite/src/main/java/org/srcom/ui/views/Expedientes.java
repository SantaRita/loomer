package org.srcom.ui.views;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import backend.BankAccount;
import backend.DummyData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import lombok.Getter;
import lombok.Setter;
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

import javax.xml.crypto.Data;

@PageTitle("Loomer")
@Route(value = "expedientes", layout = MainView.class)
public class Expedientes extends ViewFrame {

	public static final int MOBILE_BREAKPOINT = 480;

	@Setter
	@Getter
	class clExpedientes {
		String NOMBRE;
		String APELLIDO;
		int		CDASISTE;
	}

	private Grid<clExpedientes> grid = new Grid<>();


	private Registration resizeListener;

	public Expedientes() {
		setViewContent(createContent());
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createGrid());
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		return content;
	}

	private Grid createGrid() {
		//grid = new Grid<HashMap<String, Object>>();
		//grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
		//grid.addThemeName("mobile");

		// LLENAMOS LA LISTA DEL GRID CON LOS EXPEDIENTES CON SETDATAPROVIDER
		//grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));


		String expedientesLista = new EjecutaPac().EjecutaPacStr("PAC_SHWEB_LISTAS","F_QUERY",
		//		"SELECT CDASISTE, NBBENOM || ' ' || NBBEAPE1 NOMBRE, FHALTA, CDEXPAJE  FROM EX_EXPEDIENTES WHERE ROWNUM < 5");
		"SELECT  CDASISTE, NBBENOM NOMBRE, NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES WHERE ROWNUM < 5");

		System.out.println("lista devuelta: " + expedientesLista);
		try {


			List<clExpedientes> lf = new ArrayList<clExpedientes>();



			JSONObject jsonObject = new JSONObject(expedientesLista);

			System.out.println("objeto" + jsonObject);


			JSONArray lista = jsonObject.getJSONArray("RETURN");

			System.out.println("lista" + lista);
			for (int i = 0 ; i < lista.length(); i++) {

				System.out.println("entramos");
				JSONObject obj = lista.getJSONObject(i);
				System.out.println("obj" + obj);
				clExpedientes cl = new clExpedientes();
				System.out.println("nombre" + obj.getString("NOMBRE"));
				cl.setAPELLIDO(obj.getString("APELLIDO"));
				cl.setNOMBRE(obj.getString("NOMBRE"));
				cl.setCDASISTE(obj.getInt("CDASISTE"));

				lf.add(cl);
			}


			/*grid.addColumn(clExpedientes::getNOMBRE);
			grid.addColumn(clExpedientes::getAPELLIDO);*/
			grid.setItems(lf);

		} catch ( Exception e) {
			System.out.println( e.toString());

		}

		/*TypeToken<List<clExpedientes>> token = new TypeToken<List<clExpedientes>>(){};
		Gson gson = new Gson();
		List<clExpedientes> personList = gson.fromJson( lista, token.getType());*/

		/*System.out.println("Cuantos:" + lista.size());
		System.out.println("Cuantos:" + lista);

		//grid.setItems(lista);

		System.out.println("Columnas:" + grid.getColumns());*/


		//grid.addColumn(HashMap::entrySet);

		// "Desktop" columns
		grid.addColumn(clExpedientes::getCDASISTE )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Expediente")
				.setSortable(true);
		grid.addColumn(clExpedientes::getNOMBRE )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("NOMBRE")
				.setSortable(true);
		grid.addColumn(clExpedientes::getAPELLIDO )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("APELLIDO")
				.setSortable(true);
		/*grid.addColumn(new ComponentRenderer<>(this::createOwnerInfo))
				.setHeader("Owner")
				.setComparator((account1, account2) ->
					account1.getOwner().compareToIgnoreCase(account2.getOwner()))
				.setSortable(true)
				.setWidth("200px");
		grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
				.setComparator(BankAccount::getAccount)
				.setHeader("Bank Account")
				.setWidth("200px");
		grid.addColumn(new ComponentRenderer<>(this::createAvailability))
				.setAutoWidth(true)
				.setComparator(BankAccount::getAvailability)
				.setFlexGrow(0)
				.setHeader("Availability ($)")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
				.setAutoWidth(true)
				.setComparator(BankAccount::getUpdated)
				.setFlexGrow(0)
				.setHeader("Updated");*/

		return grid;
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
		resizeListener.remove();
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
	}
}
