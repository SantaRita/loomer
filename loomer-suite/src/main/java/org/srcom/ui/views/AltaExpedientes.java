package org.srcom.ui.views;

import backend.BankAccount;
import backend.DummyData;
import backend.Person;
import backend.entidades.Cia;
import backend.entidades.ComboLista;
import backend.entidades.Expediente;
import backend.entidades.Provincia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.json.JSONArray;
import org.json.JSONObject;
import org.srcom.rest.EjecutaPac;
import org.srcom.ui.components.Badge;
import org.srcom.ui.components.FlexBoxLayout;
import org.srcom.ui.components.Initials;
import org.srcom.ui.components.ListItem;
import org.srcom.ui.components.detailsdrawer.DetailsDrawer;
import org.srcom.ui.components.detailsdrawer.DetailsDrawerFooter;
import org.srcom.ui.components.detailsdrawer.DetailsDrawerHeader;
import org.srcom.ui.layout.size.*;
import org.srcom.ui.util.*;
import org.srcom.ui.util.css.*;
import org.srcom.ui.util.css.lumo.BadgeColor;
import org.srcom.ui.util.css.lumo.BadgeShape;
import org.srcom.ui.util.css.lumo.BadgeSize;
import org.srcom.ui.views.main.MainView;
import org.srcom.utiles.Utiles;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CommonsLog
@PageTitle("Loomer")
@Route(value = "altaexpedientes", layout = MainView.class)
public class AltaExpedientes extends SplitViewFrame {

	public static final int MOBILE_BREAKPOINT = 480;

	// nuevos

	DetailsDrawerFooter footer = new DetailsDrawerFooter();

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

	FlexBoxLayout links5;

	// campos antiguos
	String estadoSrvManitas;
	int secuencia;
	String txtAvisos = "";
	Label lblTipoApertura;
	Button buscar;
	Button limpiar;
	String inTipoAlta;
	String inFiltro;
	Label msgAlta;
	HorizontalLayout panelAlta = new HorizontalLayout();
	HorizontalLayout hBotonera = new HorizontalLayout();


	TextField telefono;
	TextField direccion;
	DateTimePicker ocurrencia;
	Checkbox veranuladas;

	ComboBox<Cia> cbCias = new ComboBox("Compañia");
	ComboBox<Provincia> cbProvincias = new ComboBox("Provincia");;
	ComboBox<ComboLista> cbMotivosAlta = new ComboBox();
	Label mensajeEstadoPoliza = new Label();

	TextField nombre = new TextField("Nombre") ;
	TextField apellidos = new TextField("Apellidos");
	TextField poliza = new TextField("Póliza");
	TextField nif = new TextField("NIF");
	Button btBuscar = new Button();
	Button btLimpiar = new Button("Limpiar");
	Button btPropuesta = new Button("Alta por Propuesta");

	Label titulo;

	int validarCampos = 0;


	private DetailsDrawer detailsDrawer;
	private DetailsDrawerHeader detailsDrawerHeader;

	public AltaExpedientes() {

		setViewContent(createContent());
		setViewDetails(createDetailsDrawer());
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

		// Motivodatos


		data = (String) UI.getCurrent().getSession().getAttribute("motivosalta");
		List<ComboLista> lMotivos = gson.fromJson(data,new TypeToken<List<ComboLista>>(){}.getType());
		cbMotivosAlta.setItemLabelGenerator(ComboLista::getVALORC);
		cbMotivosAlta.setItems(lMotivos);

		// Provincias

		data = (String) UI.getCurrent().getSession().getAttribute("provincias");
		List<Provincia> lProvincias = gson.fromJson(data,new TypeToken<List<Provincia>>(){}.getType());
		cbProvincias.setItemLabelGenerator(Provincia::getNBZONA);
		cbProvincias.setItems(lProvincias);

		btBuscar.setText(getTranslation("buscar"));
		btBuscar.setAutofocus(true);
		btBuscar.setIcon(new Icon("lumo","search"));

		btBuscar.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
				// PULSAMOS EL BOTÓN DE BUSCAR



				binder.validate();
				if ( binder.validate().isOk()) {
					detailsDrawer.hide();
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
		btLimpiar.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		// IMPORTANTE ¡¡¡¡¡¡ JUSTIFICAR A LA DERECHA.
		links2.setMargin(Top.L, Bottom.S);
		links2.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
		links2.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		links2.setSpacing(Right.S);

		FlexBoxLayout links5 = new FlexBoxLayout(btPropuesta);
		btPropuesta.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);

		btPropuesta.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
				UI.getCurrent().navigate(AltaPropuesta.class);

			}
		});

		// IMPORTANTE ¡¡¡¡¡¡ JUSTIFICAR A LA DERECHA.
		links5.setMargin(Top.XS, Bottom.S);
		links5.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
		links5.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		links5.setSpacing(Left.S);

		FlexBoxLayout links3 = new FlexBoxLayout(grid);
		links3.setShadow(Shadow.XS);
		links3.setFlexWrap(FlexLayout.FlexWrap.WRAP);

		links3.setSizeUndefined();
		links3.setSpacing(Right.S);

		gridExp.setVisible(false);
		FlexBoxLayout links4 = new FlexBoxLayout(gridExp);
		links4.setShadow(Shadow.XS);
		links4.setMargin(Left.M, Top.L, Right.S);
		links4.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		links4.setSizeUndefined();
		links4.setSpacing(Right.S);



		FlexBoxLayout content = new FlexBoxLayout(links, links2, links5, links3,links4);
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
		grid.addSelectionListener(event -> event.getFirstSelectedItem()
				.ifPresent(this::showDetails));

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
			cl.setBOTON(Utiles.Vacio(obj, "BOTON"));
			cl.setAPE1(Utiles.Vacio(obj, "APE1"));
			cl.setNOMB(Utiles.Vacio(obj, "NOMB"));
			cl.setDESCTTO(Utiles.Vacio(obj, "DESCTTO"));
			cl.setFEBAJA(Utiles.Vacio(obj, "FEBAJA"));
			cl.setCODCTTO_1(Utiles.Vacio(obj, "CODCTTO_1"));
			cl.setCODCTTO_2(Utiles.VacioInt(obj, "CODCTTO_2"));
			cl.setCOLORFILA(Utiles.Vacio(obj, "COLORFILA"));
			cl.setC_A(Utiles.Vacio(obj, "C_A"));
			cl.setDESTIT(Utiles.Vacio(obj, "DESTIT"));
			cl.setIDDOCUM(Utiles.Vacio(obj, "IDDOCUM"));
			cl.setIDDOCUM(Utiles.Vacio(obj, "IDDOCUM"));
			cl.setDESDIR(Utiles.Vacio(obj, "DESDIR"));
			cl.setCODZONA3(Utiles.Vacio(obj, "CODZONA3"));
			cl.setCODZONA2(Utiles.Vacio(obj, "CODZONA2"));
			cl.setFEALTA(Utiles.Vacio(obj, "FEALTA"));
			cl.setFEFIN(Utiles.Vacio(obj, "FEFIN"));
			cl.setMSJEFILA(Utiles.Vacio(obj, "MSJEFILA"));
			lf.add(cl);
		}
			grid.setItems(lf);
		} catch ( Exception e) {
			log.error( e.toString() );
		}


		// Columnas ocultas
		grid.addColumn(Expediente::getCODCTTO_1 )
				.setHeader("CODCTTO_1")
				.setVisible(false);
		grid.addColumn(Expediente::getBOTON )
				.setHeader("BOTON")
				.setVisible(false);
		grid.addColumn(Expediente::getCOLORFILA )
				.setHeader("COLORFILA")
				.setVisible(false);
		grid.addColumn(Expediente::getCODCTTO_2 )
				.setHeader("CODCTTO_2")
				.setVisible(false);
		grid.addColumn(Expediente::getNOMB )
				.setHeader("Nombre")
				.setVisible(false);
		grid.addColumn(Expediente::getFEBAJA )
				.setHeader("Nombre")
				.setVisible(false);
		// Columnas visibles
		grid.addComponentColumn(item -> {
				/*	Icon icon;

					if(item.getC_A().equals("B")){
						icon = VaadinIcon.CHECK_CIRCLE.create();
						icon.setColor("green");
					} else {
						icon = VaadinIcon.CLOSE_CIRCLE.create();
						icon.setColor("red");
					}
					icon.setSize("1em");
					return icon;*/
					if (item.getC_A()!=null)
						return new Initials(item.getC_A());
					else
						return new Initials("X");
				})
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setSortable(false)
				.setKey("Estado")
				.setHeader("Estado")
				.setResizable(false)
				.setHeader("")
				.setComparator(Comparator.comparing(Expediente::getC_A));
		grid.addColumn(Expediente::getDESCTTO )
				//.setAutoWidth(true)
				.setWidth("18%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setResizable(true)
				.setHeader("Contrato")
				.setSortable(true);
		grid.addColumn(Expediente::getNROPOL )
				//.setAutoWidth(true)
				.setWidth("10%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setResizable(true)
				.setHeader("Póliza")
				.setSortable(true);
		grid.addColumn(Expediente::getDESTIT )
				//.setAutoWidth(true)
				.setWidth("13%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Cliente")
				.setSortable(true)
				.setResizable(true)
				.setVisible(true);
		grid.addColumn(Expediente::getIDDOCUM )
				//.setAutoWidth(true)
				.setWidth("5%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("NIF")
				.setResizable(true)
				.setSortable(true);
		grid.addColumn(Expediente::getDESDIR )
				//.setAutoWidth(true)
				.setWidth("17%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Dirección")
				.setResizable(true)
				.setSortable(true);
		grid.addColumn(Expediente::getCODZONA3 )
				//.setAutoWidth(true)
				.setWidth("5%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("CP")
				.setResizable(true)
				.setSortable(true);
		grid.addColumn(Expediente::getCODZONA2 )
				//.setAutoWidth(true)
				.setWidth("14%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("Población")
				.setResizable(true)
				.setSortable(true);
		grid.addColumn(Expediente::getFEALTA )
				//.setAutoWidth(true)
				.setWidth("6%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setResizable(true)
				.setHeader("F.Alta")
				.setSortable(true);
		grid.addColumn(Expediente::getFEFIN )
				//.setAutoWidth(true)
				.setWidth("6%")
				.setFlexGrow(0)
				.setFrozen(true)
				.setResizable(true)
				.setHeader("F.Fin")
				.setSortable(true);





		grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Expediente>>() {
			@Override
			public void onComponentEvent(ItemClickEvent<Expediente> expedienteItemClickEvent) {

				gridExp.removeAllColumns();
				gridExp.setVisible(true);
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
						cl.setTXSITUA(Utiles.Vacio(obj, "TXSITUA"));
						cl.setFHOCURRE(Utiles.Vacio(obj, "FHOCURRE"));
						cl.setEFHALTA(Utiles.Vacio(obj, "EFALTA"));
						cl.setNBCAUSA(Utiles.Vacio(obj, "NBCAUSA"));
						cl.setTXSERV(Utiles.Vacio(obj, "TXSERV"));
						cl.setTXGREMIO(Utiles.Vacio(obj, "TXGREMIO"));
						cl.setTXPROVEE(Utiles.Vacio(obj, "TXPROVEE"));
						cl.setFHCIERRET(Utiles.Vacio(obj, "FHCIERRET"));
						cl.setNUCOSTE(Utiles.Vacio(obj, "NUCOSTE"));
						lf.add(cl);
					}
					gridExp.setItems(lf);
				} catch ( Exception e) {
					log.error( e.toString() );
				}

				gridExp.addComponentColumn(item -> {


							return new Initials(item.getTXSITUA());
						})
						.setAutoWidth(true)
						.setFlexGrow(0)
						.setFrozen(true)
						.setSortable(false)
						.setResizable(false)
						.setHeader("Sit")
						.setComparator(Comparator.comparing(Expediente::getTXSITUA));
				gridExp.addColumn(new ComponentRenderer<>(this::createExpedienteInfo))
						.setAutoWidth(true)
						.setWidth("10%")
						.setHeader("Expediente");
				/*gridExp.addColumn(Expediente::getNUMEXP )
						.setWidth("7%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Expediente")
						.setSortable(true);
				gridExp.addColumn(Expediente::getFHOCURRE )
						.setWidth("8%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("F.Ocurrencia")
						.setSortable(true);*/
				gridExp.addColumn(Expediente::getFEALTA )
						.setWidth("8%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("F.Alta")
						.setSortable(true);
				gridExp.addColumn(new ComponentRenderer<>(this::createExpedienteCausaInfo))
						.setAutoWidth(true)
						.setWidth("38%")
						.setHeader("Causa");

				/*gridExp.addColumn(Expediente::getNBCAUSA )
						.setWidth("18%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Causa")
						.setSortable(true);
				gridExp.addColumn(Expediente::getTXSERV )
						.setWidth("22%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Servicio")
						.setSortable(true);
				gridExp.addColumn(Expediente::getTXGREMIO )
						.setWidth("8%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Gremio")
						.setSortable(true);*/
				gridExp.addColumn(Expediente::getTXPROVEE )
						.setWidth("20%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Proveedor")
						.setSortable(true);
				gridExp.addColumn(Expediente::getFHCIERRET)
						.setWidth("8%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Fecha CT")
						.setSortable(true);
				gridExp.addColumn(Expediente::getNUCOSTE)
						.setTextAlign(ColumnTextAlign.END)
						.setWidth("6%")
						.setFlexGrow(0)
						.setFrozen(true)
						.setHeader("Coste")
						.setSortable(true);

				// ESTADO DEL CONTRATO -- COLORES


				if ( expedienteItemClickEvent.getItem().getCOLORFILA().equals("GREEN")) {

					//links5.setBackgroundColor("Green");
					mensajeEstadoPoliza.getStyle().set("color","green");
					mensajeEstadoPoliza.setText(expedienteItemClickEvent.getItem().getMSJEFILA());
					footer.save.setVisible(false);
					cbMotivosAlta.setVisible(false);
					if ( expedienteItemClickEvent.getItem().getBOTON().equals("S")) {
						footer.save.setVisible(true);
						cbMotivosAlta.setVisible(true);
					}


				}
				else if ( expedienteItemClickEvent.getItem().getCOLORFILA().equals("RED")) {
					mensajeEstadoPoliza.setText(expedienteItemClickEvent.getItem().getMSJEFILA());
					mensajeEstadoPoliza.getStyle().set("color","red");
					footer.save.setVisible(false);
					cbMotivosAlta.setVisible(false);
					if ( expedienteItemClickEvent.getItem().getBOTON().equals("S")) {
						footer.save.setVisible(true);
						cbMotivosAlta.setVisible(true);
					}
				}

				else if ( expedienteItemClickEvent.getItem().getCOLORFILA().equals("GRAY")) {
					mensajeEstadoPoliza.getStyle().set("color","gray");
					mensajeEstadoPoliza.setText(expedienteItemClickEvent.getItem().getMSJEFILA());
					footer.save.setVisible(false);
					cbMotivosAlta.setVisible(false);
					if ( expedienteItemClickEvent.getItem().getBOTON().equals("S")) {
						footer.save.setVisible(true);
						cbMotivosAlta.setVisible(true);

					}
				}

				else if ( expedienteItemClickEvent.getItem().getCOLORFILA().equals("ORANGE")) {
					mensajeEstadoPoliza.setText(expedienteItemClickEvent.getItem().getMSJEFILA());
					mensajeEstadoPoliza.getStyle().set("color","orange");
					footer.save.setVisible(false);
					cbMotivosAlta.setVisible(false);
					if ( expedienteItemClickEvent.getItem().getBOTON().equals("S")) {
						footer.save.setVisible(true);
					}
				}





			}

			private Component createExpedienteInfo(Expediente expediente) {
				Integer uiExpediente;
				String uiOcurrencia = "";
				uiExpediente = Integer.valueOf(expediente.getNUMEXP());
				uiOcurrencia = expediente.getFHOCURRE().toString();

				ListItem item = new ListItem(uiExpediente.toString(), uiOcurrencia);
				item.setPadding(Vertical.XS);
				item.setSpacing(Right.M);
				return item;
			}

			private Component createExpedienteCausaInfo(Expediente expediente) {
				String uiGremio;
				String uiCausa = "";
				uiGremio = expediente.getTXGREMIO();
				uiCausa = expediente.getNBCAUSA();

				ListItem item = new ListItem(uiGremio, uiCausa);
				item.setPadding(Vertical.XS);
				item.setSpacing(Right.M);
				return item;
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


	private DetailsDrawer createDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerHeader = new DetailsDrawerHeader("");
		detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
		detailsDrawer.setHeader(detailsDrawerHeader);

		// Footer


		footer.save.setText("Alta por cartera");
		footer.save.setIcon(new Icon("lumo","edit"));

		footer.cancel.setText("Cancelar");
		footer.addSaveListener(e -> {
			detailsDrawer.hide();
			UIUtils.showNotification("Changes saved.");
		});


		footer.addCancelListener(e -> detailsDrawer.hide());
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	private void showDetails(Expediente expediente) {
		detailsDrawerHeader.setTitle("Póliza: " + expediente.getNROPOL());
		detailsDrawer.setContent(createDetails(expediente));
		detailsDrawer.show();
	}


	private FormLayout createDetails(Expediente expediente) {
		TextField contrato = new TextField();
		contrato.setValue(expediente.getDESCTTO());
		contrato.setWidthFull();

		TextField cliente = new TextField();
		cliente.setValue(expediente.getDESTIT());
		cliente.setWidthFull();


		TextField direccion = new TextField();
		direccion.setValue(expediente.getDESDIR());
		direccion.setWidthFull();

		TextField cp = new TextField();
		cp.setValue(expediente.getCODZONA3());
		cp.setWidthFull();

		TextField poblacion = new TextField();
		poblacion.setValue(expediente.getCODZONA2());
		poblacion.setWidthFull();

		TextField falta = new TextField();
		if (expediente.getFEALTA() != null ) falta.setValue(expediente.getFEALTA().toString());
		falta.setWidthFull();

		TextField ffin = new TextField();
		if (expediente.getFEFIN() != null ) ffin.setValue(expediente.getFEFIN().toString());
		ffin.setWidthFull();


		ComboBox company = new ComboBox();
		company.setItems(DummyData.getCompanies());
		company.setValue(DummyData.getCompany());
		company.setWidthFull();

		// Form layout
		FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.L,
				LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1,
						FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("21em", 1,
						FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.addFormItem(contrato, "Contrato");

		FormLayout.FormItem clienteItem = form.addFormItem(cliente, "Cliente");


		TextField nif = new TextField();
		nif.setValue(expediente.getIDDOCUM());
		nif.setWidthFull();
		form.addFormItem(nif, "NIF");

		FormLayout.FormItem direccionItem = form.addFormItem(direccion, "Dirección");
		FormLayout.FormItem cpItem = form.addFormItem(cp, "Cp");
		FormLayout.FormItem poblacionItem = form.addFormItem(poblacion, "Poblacion");
		FormLayout.FormItem faltaItem = form.addFormItem(falta, "F.Alta");
		FormLayout.FormItem ffinItem = form.addFormItem(ffin, "F.Fin");
		FormLayout.FormItem mensajeEstadoPolizaItem = form.addFormItem(mensajeEstadoPoliza,"");




		form.addFormItem(cbMotivosAlta,"");

		contrato.setReadOnly(true);
		cliente.setReadOnly(true);
		nif.setReadOnly(true);
		direccion.setReadOnly(true);
		cp.setReadOnly(true);
		poblacion.setReadOnly(true);
		cbMotivosAlta.setRequired(true);
		cbMotivosAlta.setWidthFull();
		cbMotivosAlta.setPlaceholder("Seleccione motivo alta");
		falta.setReadOnly(true);
		ffin.setReadOnly(true);
		/*FormLayout.FormItem emailItem = form.addFormItem(expediente.getDESDIR(), "Dirección");
		FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
		FormLayout.FormItem uploadItem = form.addFormItem(new Upload(),
				"Image");*/
		UIUtils.setColSpan(2, contrato, poliza, nif, direccion, cp, poblacion, falta, ffin);


		return form;
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
