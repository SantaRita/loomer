package org.srcom.ui.views;

import backend.BankAccount;
import backend.entidades.Cia;
import backend.entidades.Expediente;
import backend.entidades.Provincia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
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
import org.srcom.ui.layout.size.Uniform;
import org.srcom.ui.layout.size.Vertical;
import org.srcom.ui.util.*;
import org.srcom.ui.util.css.Overflow;
import org.srcom.ui.util.css.PointerEvents;
import org.srcom.ui.util.css.Shadow;
import org.srcom.ui.util.css.TextOverflow;
import org.srcom.ui.util.css.lumo.BadgeColor;
import org.srcom.ui.util.css.lumo.BadgeShape;
import org.srcom.ui.util.css.lumo.BadgeSize;
import org.srcom.ui.views.main.MainView;
import org.srcom.utiles.Utiles;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@CommonsLog
@PageTitle("Loomer")
@Route(value = "altaexpedientes", layout = MainView.class)
public class AltaExpedientes extends ViewFrame {

	public static final int MOBILE_BREAKPOINT = 480;

	// nuevos

	@Setter
	@Getter
	public class BuscarExpediente {
		String nombre;
		String poliza;

	}

	Binder binder = new Binder<>();
	private TextField filterText = new TextField();
	List<Expediente> lf = new ArrayList<Expediente>();

	private Grid<Expediente> grid = new Grid<>();
	private Grid<Expediente> gridExp = new Grid<>();
	private Registration resizeListener;

	// campos antiguos
	String estadoSrvManitas;
	int secuencia;
	String txtAvisos = "";
	Label lblTipoApertura;
	Button buscar;
	Button limpiar;
	String inTipoAlta;
	String inFiltro;
	Button btCartera;
	ComboBox cbMotivoAlta;
	Button btPropuesta;
	Label msgAlta;
	HorizontalLayout panelAlta = new HorizontalLayout();
	HorizontalLayout hBotonera = new HorizontalLayout();


	TextField telefono;
	TextField direccion;
	DateTimePicker ocurrencia;
	Checkbox veranuladas;

	ComboBox<Cia> cbCias = new ComboBox("Compañia");
	ComboBox<Provincia> cbProvincias = new ComboBox("Provincia");;
	TextField nombre = new TextField("Nombre") ;
	TextField apellidos = new TextField("Apellidos");
	TextField poliza = new TextField("Póliza");
	TextField nif = new TextField("NIF");
	Button btBuscar = new Button();
	Button btLimpiar = new Button("Limpiar");

	Label titulo;

	int validarCampos = 0;


	public AltaExpedientes() {
		setViewContent(createContent());
	}

	private Component createContent() {

		// validaciones

		Binder<BuscarExpediente> binder = new Binder<>(BuscarExpediente.class);


		binder.forField(nombre).asRequired("Campo nombre obligatorio");

		Label polizaStatus = new Label();
		polizaStatus.getStyle().set("color", "Red");
		binder.forField(poliza)
				// Validator defined based on a lambda
				// and an error message
				.withValidator(
						e -> poliza.getValue().length() >= 3,
						"Póliza must contain at least three characters")
				.bind(BuscarExpediente::getPoliza, BuscarExpediente::setPoliza);


		// Carga de datos
		String data = null;
		Gson gson = new Gson();

		data = (String) UI.getCurrent().getSession().getAttribute("companias");

		Type type = new TypeToken<List<Cia>>(){}.getType();
		List<Cia> lCias = gson.fromJson(data, type);
		cbCias.setItemLabelGenerator(Cia::getDSCLIENTE);
		cbCias.setItems(lCias);



		data = (String) UI.getCurrent().getSession().getAttribute("provincias");
		List<Provincia> lProvincias = gson.fromJson(data,new TypeToken<List<Provincia>>(){}.getType());
		cbProvincias.setItemLabelGenerator(Provincia::getNBZONA);
		cbProvincias.setItems(lProvincias);

		btBuscar.setText(getTranslation("buscar"));

		btBuscar.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
				// PULSAMOS EL BOTÓN DE BUSCAR



				binder.validate();
				if ( binder.validate().isOk()) {
					/*String consulta = "SELECT  EE.FHALTA, TO_CHAR(EA.FHOCURRE,'DD/MM/YYYY') FHOCURRE, EE.CDEXPAJE, EE.CDASISTE, EE.NBBENOM NOMBRE, EE.NBBEAPE1 APELLIDO  FROM EX_EXPEDIENTES ee, EX_ASISTENCIAS EA " +
							"WHERE ee.cdasiste = ea.cdasiste AND ROWNUM < 20";
					if (nombre != null)
						consulta += " AND UPPER(EE.NBBENOM) LIKE '%" + nombre.getValue().toUpperCase() + "%' ";*/

					createGrid();
				}
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
					grid.setItems(lf.stream().filter( c -> c.getNOMBRE().startsWith(filterText.getValue())));

				}
			}
		});

		// medidas em. el campo standard son de 12em
		cbProvincias.getStyle().set("width", "18em");
		cbCias.getStyle().set("width", "18em");



		FlexBoxLayout links = new FlexBoxLayout(nombre, apellidos, poliza, nif, cbCias, cbProvincias);


		links.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		links.setSpacing(Right.S);

		FlexBoxLayout links2 = new FlexBoxLayout(btBuscar, btLimpiar);
		btBuscar.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		btLimpiar.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_CONTRAST);
		// IMPORTANTE ¡¡¡¡¡¡ JUSTIFICAR A LA DERECHA.
		links2.setJustifyContentMode(FlexComponent.JustifyContentMode.END);



		links2.setFlexWrap(FlexLayout.FlexWrap.WRAP);

		links2.setSpacing(Right.S);

		FlexBoxLayout links3 = new FlexBoxLayout(grid);
		links3.setShadow(Shadow.XS);
		links3.setFlexWrap(FlexLayout.FlexWrap.WRAP);

		links3.setSizeUndefined();
		links3.setSpacing(Right.S);

		FlexBoxLayout links4 = new FlexBoxLayout(gridExp);
		links4.setShadow(Shadow.XS);
		links4.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		links4.setSizeUndefined();
		links4.setSpacing(Right.S);


		FlexBoxLayout content = new FlexBoxLayout(links, links2, links3, links4);
		content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO );
		content.setPadding(Uniform.RESPONSIVE_L);
		//content.setHeightFull();

		poliza.setValue("02HU11092367");

		return content;
	}

	private String consultarExpedientes() {

		String inFiltros;

		/* FILTROS
		 a. Codigo de compañia
		 b. Codigo de poblacion
		 c. Codigo de provincia
		 d. No. de poliza
		 e. Telefono
		 f. Direccion
		 g. DNI/CIF
		 h. Apellido(s)
		 i. Nombre(s) */

		inFiltros = "";

		if (cbCias.getValue()!=null && !cbCias.getValue().equals("") ) inFiltros = cbCias.getValue().toString();
		inFiltros += "|";
		/*if ( (cBpoblaciones.getValue()!=null) && (!poblaciones.getValue().equals("")) )
		{
			inFiltros = inFiltros + poblaciones.getItemCaption(poblaciones.getValue());
		}*/
		inFiltros += "|";
		inFiltros += "|";
		if (poliza.getValue()!=null && !poliza.getValue().equals("")  ) {
			inFiltros = inFiltros + poliza.getValue().toString() ;
		}



		inFiltros += "|";
		inFiltros += "|";
		inFiltros += "|";
		if (nif.getValue()!=null && !nif.getValue().equals("") ) inFiltros = inFiltros + nif.getValue().toString();
		inFiltros += "|";
		if (apellidos.getValue()!=null && !apellidos.getValue().equals("") ) inFiltros = inFiltros + apellidos.getValue().toString();
		inFiltros += "|";
		if (nombre.getValue()!=null && !nombre.getValue().equals("") ) inFiltros = inFiltros + nombre.getValue().toString();
		inFiltros += "|";



		inFiltros += "|";
		return new EjecutaPac().EjecutaPacStr("PAC_SHWEB_EXPEDIENTES","F_GET_CONTRATOS", inFiltros, 0, UI.getCurrent().getSession().getAttribute("usuario"));

	}

	private void createGrid() {



		//grid.setSizeFull();
		grid.setSizeUndefined();
		grid.removeAllColumns();

		gridExp.setSizeUndefined();
		gridExp.removeAllColumns();

		String expedientesLista =consultarExpedientes();

		lf.clear();
		JSONObject jsonObject = new JSONObject(expedientesLista);
		JSONArray lista = jsonObject.getJSONArray("RETURN");
		try {
		for (int i = 0 ; i < lista.length(); i++) {
			JSONObject obj = lista.getJSONObject(i);
			Expediente cl = new Expediente();
			cl.setNROPOL(Utiles.Vacio(obj, "NROPOL"));
			cl.setAPE1(Utiles.Vacio(obj, "APE1"));
			cl.setNOMB(Utiles.Vacio(obj, "NOMB"));
			cl.setDESCTTO(Utiles.Vacio(obj, "DESCTTO"));
			cl.setFEBAJA(Utiles.Vacio(obj, "FEBAJA"));
			cl.setCODCTTO_1(Utiles.Vacio(obj, "CODCTTO_1"));
			cl.setCODCTTO_2(Utiles.VacioInt(obj, "CODCTTO_2"));
			lf.add(cl);
		}
			grid.setItems(lf);
		} catch ( Exception e) {
			log.error( e.toString() );
		}

		grid.addColumn(Expediente::getCODCTTO_1 )
				.setHeader("CODCTTO_1")
				.setVisible(false);
		grid.addColumn(Expediente::getCODCTTO_2 )
				.setHeader("CODCTTO_2")
				.setVisible(false);
		grid.addColumn(Expediente::getNROPOL )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Póliza")
				.setSortable(true);
		grid.addColumn(Expediente::getNOMB )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Nombre")
				.setSortable(true);
		grid.addColumn(Expediente::getDESCTTO )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Contrato")
				.setSortable(true);
		grid.addColumn(Expediente::getFEBAJA )
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("F.Baja")
				.setSortable(true);



		grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Expediente>>() {
			@Override
			public void onComponentEvent(ItemClickEvent<Expediente> expedienteItemClickEvent) {

				gridExp.removeAllColumns();
				System.out.println("HAcemos click "+ expedienteItemClickEvent.getItem().getNROPOL());
				String contratos =  new EjecutaPac().EjecutaPacStr("PAC_SHWEB_EXPEDIENTES","F_GET_EXPEDIENTES",
						expedienteItemClickEvent.getItem().getCODCTTO_1(),
						expedienteItemClickEvent.getItem().getCODCTTO_2(),
						expedienteItemClickEvent.getItem().getNROPOL());

				lf.clear();
				JSONObject jsonObject = new JSONObject(contratos);
				JSONArray lista = jsonObject.getJSONArray("RETURN");
				try {
					for (int i = 0 ; i < lista.length(); i++) {
						JSONObject obj = lista.getJSONObject(i);
						Expediente cl = new Expediente();
						cl.setNUMEXP(Utiles.VacioInt(obj, "NUMEXP"));
						lf.add(cl);
					}
					gridExp.setItems(lf);
				} catch ( Exception e) {
					log.error( e.toString() );
				}


				gridExp.addColumn(Expediente::getNUMEXP )
						.setAutoWidth(true)
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Expediente")
						.setSortable(true);



			}
		});
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




	/*public void enter() {

		System.out.println( "Entra a la pantalla de busqueda" );
		data = (Generico0) UI.getCurrent().getSession().getAttribute("companias");
		for (Generico0.MapObject l: data.getMapObject()){
			cias.addItem(l.getMap().get("RGCLIEN"));
			cias.setItemCaption(l.getMap().get("RGCLIEN"),(String)l.getMap().get("DSCLIENTE"));
		}
		cias.setFilteringMode(FilteringMode.CONTAINS);

		Generico0 pob = null;
		//pob = service.ejecutaPAC("PAC_SHWEB_LISTAS", "F_GET_LSTPOBLACIONES", true, "-1");
		pob = (Generico0) UI.getCurrent().getSession().getAttribute("poblaciones");
		for (Generico0.MapObject l: pob.getMapObject()){
			poblaciones.addItem(l.getMap().get("CDZONA"));
			poblaciones.setItemCaption(l.getMap().get("CDZONA"),(String)l.getMap().get("NBZONA"));
		}
		poblaciones.setFilteringMode(FilteringMode.CONTAINS);

		System.out.println( "provincias" );
		Generico0 prv = null;
		prv = (Generico0) UI.getCurrent().getSession().getAttribute("provincias");
		for (Generico0.MapObject l: prv.getMapObject()){
			provincias.addItem(l.getMap().get("CDZONA"));
			provincias.setItemCaption(l.getMap().get("CDZONA"),(String)l.getMap().get("NBZONA"));
		}

		provincias.setFilteringMode(FilteringMode.CONTAINS);

		table.setPageLength(0);
		tableexp.setPageLength(0);
		tableexp.setVisible(false);

		PropertysetItem item = new PropertysetItem();
		item.addItemProperty("nif", new ObjectProperty<String>(""));
		item.addItemProperty("poliza", new ObjectProperty<String>(""));
		item.addItemProperty("telefono", new ObjectProperty<String>(""));
		item.addItemProperty("direccion", new ObjectProperty<String>(""));
		item.addItemProperty("apellidos", new ObjectProperty<String>(""));
		item.addItemProperty("cias", new ObjectProperty<String>(""));
		item.addItemProperty("poblaciones", new ObjectProperty<String>(""));
		item.addItemProperty("provincias", new ObjectProperty<String>(""));

		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);


		System.out.println( "Antes de asignar poliza" );
		try {
			//System.out.println("Maquina:"+Inet4Address.getLocalHost().getHostName().toString());
			if (Inet4Address.getLocalHost().getHostName().toString().equals("PORTATIL")
					&& UI.getCurrent().getSession().getAttribute("entorno").toString().equals("TEST") ) {

				System.out.println( "Asignar poliza" );
				poliza.setValue("02HU11092367");
				//poliza.setValue("04HGA0010180");
				//nif.setValue("X9231233E");
				//nif.setValue("21482144Y");
				poliza.setValue("00005072742694-0002"); // LIBERTY
				cias.setValue("SURF");
				//poliza.setValue("VH 8043776"); // RACC SEGUROS
				//poliza.setValue("103186"); // SANTANDER MANITAS
				//poliza.setValue("09017148000000000001"); // ASEFA
				//poliza.setValue("22046000158"); // AGRUPACIO
				//poliza.setValue("140271408"); // GAS NATURAL
				//poliza.setValue("62180009435124-01006"); // BBVA
				//poliza.setValue("A15372430SR"); // B2C
				//poliza.setValue("10046532"); // ATLANTIS


			}
		} catch (UnknownHostException e) {

			e.printStackTrace();

		}


	}*/


}
