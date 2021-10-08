package org.srcom.ui.views;

import backend.BankAccount;
import backend.DummyData;
import backend.entidades.Cia;
import backend.entidades.ComboLista;
import backend.entidades.Expediente;
import backend.entidades.Provincia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import javafx.scene.control.CheckBox;
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
import org.srcom.ui.components.navigation.bar.AppBar;
import org.srcom.ui.layout.size.*;
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
import java.util.Comparator;
import java.util.List;

@CommonsLog

@Route(value = "altapropuesta", layout = MainView.class)
@PageTitle("Alta por propuesta")
public class AltaPropuesta extends SplitViewFrame {

	public ComboBox  tfContrato = new ComboBox();
	public ComboBox  tfSwGuardaCart = new ComboBox();
	public ComboBox  tfCompania = new ComboBox();
	public TextField tfNomtitular = new TextField();
	public TextField tfApe1 = new TextField();
	public TextField tfApe2 = new TextField();
	public TextField tfNif = new TextField();
	//public CheckBox ckNoValidarNif = new CheckBox("No Validar NIF/NIE  ");
	public DateTimePicker tfVigencia= new DateTimePicker();
	public TextField tfPoliza =new TextField();
	public TextField tfDireccion = new TextField();
	public ComboBox tfCp = new ComboBox();
	public ComboBox  tfPoblacion = new ComboBox();
	public ComboBox  tfProvincia =new ComboBox();
	public ComboBox tfPais = new ComboBox();
	public TextField tfTelefono = new TextField();
//	public CheckBox  tfVip = new CheckBox();
	public TextField tfVipDescripcion = new TextField();
	public TextField tfOficina = new TextField();
	public TextField tfCodigoActivo = new TextField();

	public static final int MOBILE_BREAKPOINT = 480;


	DetailsDrawerFooter footer = new DetailsDrawerFooter();


	private DetailsDrawer detailsDrawer;
	private DetailsDrawerHeader detailsDrawerHeader;



	public AltaPropuesta() {

		setViewContent(createContent());
		//setViewDetails(createDetailsDrawer());

		MainView.get().getAppBar().setTitle("Alta de Expedientes Por Propuesta");


	}

	private Component createContent() {


		/*FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.L,
				LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S );
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1,
						FormLayout.ResponsiveStep.LabelsPosition.ASIDE),
				new FormLayout.ResponsiveStep("21em", 2,
						FormLayout.ResponsiveStep.LabelsPosition.ASIDE));



		form.addFormItem(tfContrato, "Contrato:");
		form.addFormItem(tfContrato, "TipoSubcontrato:");
		form.addFormItem(tfContrato, "Subcontrato:");

		tfContrato.focus();



		form.addFormItem(tfCompania, "Compañia:");
		form.addFormItem(tfNomtitular, "Nombre Titular:");
		form.addFormItem(tfApe1, "Primer Apellido:");
		form.addFormItem(tfApe2, "Segundo Apellido:");
		form.addFormItem(tfNif, "Nif:");
		//form.addFormItem(ckNoValidarNif, "No Validar NIF:");

		form.addFormItem(tfVigencia, "Vigencia:");

		form.addFormItem(tfPoliza, "Póliza:");

		form.addFormItem(tfTelefono, "Telefono:");

		form.addFormItem(tfDireccion, "Dirección:");

		form.addFormItem(tfPais, "País:");

		form.addFormItem(tfProvincia, "Provincia:");

		form.addFormItem(tfPoblacion, "Población:");

		form.addFormItem(tfCp, "Código Postal:");

		//form.addFormItem(tfVip, "Vip:");

		form.addFormItem(tfVipDescripcion, "Descripción VIP:");

		form.addFormItem(tfOficina, "Oficina:");

		form.addFormItem(tfCodigoActivo, "Código Activo:");
*/

		FormLayout layoutWithFormItems = new FormLayout();

		TextField firstName = new TextField();
		firstName.setPlaceholder("John");

		TextField lastName = new TextField();
		lastName.setPlaceholder("Doe");

		TextField phone = new TextField();
		TextField email = new TextField();
		DatePicker birthDate = new DatePicker();
		Checkbox doNotCall = new Checkbox("Do not call");

		layoutWithFormItems.addFormItem(firstName, "First name");
		layoutWithFormItems.addFormItem(lastName, "Last name");

		layoutWithFormItems.addFormItem(birthDate, "Birthdate");
		layoutWithFormItems.addFormItem(email, "E-mail");
		FormLayout.FormItem phoneItem = layoutWithFormItems.addFormItem(phone, "Phone");
		phoneItem.add(doNotCall);


		return layoutWithFormItems;
	}




}
