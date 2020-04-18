/**
 * Program Name: PizzaOrder.java
 *	Purpose: 
 *	Coder: Alex Laye sec02
 *	Date: Aug 10, 2018
 */

import javafx.geometry.Insets;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;

public class AKL_Pizza_Controler_JavaFX extends Application
{
	//globals
	Label lblTitle, lblName, lblPhone, lblAddress, lblSize, lblCrust, lblTopping;
	TextField fldName, fldPhone, fldAddress;
	RadioButton rbSmall, rbMedium, rbLarge, rbELarge, rbThin, rbRegular, rbThick;
	CheckBox cbPepperoni, cbSausage, cbGreenPepper, cbOlive, cbMushroom, cbTomato, cbAnchovies, cbOnion;
	Button btnOkay, btnCancel;
	ToggleGroup grpSize, grpCrust;
	String strToppings, strSize, strCrust;
	
	public void start(Stage stage)
	{
		// Initial setting for an initially plain pizza. 
		// This is (possibly) modified further down.
		strToppings = "Your selected toppings are: NONE";
		
		// toggle groups for radio buttons
		grpSize = new ToggleGroup();
		grpCrust = new ToggleGroup();
		
		//labels and label ID
		lblTitle = new Label("Fanshawe Perfect Pizza Online Order System");
		lblTitle.setId("title");

		lblName = new Label("Name");
		lblPhone = new Label("Phone Number:");
		lblAddress = new Label("Address:");
		lblSize = new Label("Size");
		lblCrust = new Label("Crust");
		lblTopping = new Label("Toppings");
		
		//text fields
		fldName = new TextField();
		fldPhone = new TextField();
		fldAddress = new TextField();
		
		//radio buttons
		rbSmall = new RadioButton("Small");
		rbSmall.setToggleGroup(grpSize);
		rbMedium = new RadioButton("Medium");
		rbMedium.setToggleGroup(grpSize);
		rbMedium.setSelected(true); // default selection
		rbLarge = new RadioButton("Large");
		rbLarge.setToggleGroup(grpSize);
		rbELarge = new RadioButton("Extra Large");
		rbELarge.setToggleGroup(grpSize);
		rbThin = new RadioButton("Thin");
		rbThin.setToggleGroup(grpCrust);
		rbRegular = new RadioButton("Regular");
		rbRegular.setToggleGroup(grpCrust);
		rbRegular.setSelected(true); //  default selection
		rbThick = new RadioButton("Thick");
		rbThick.setToggleGroup(grpCrust);

		//checkboxes
		cbPepperoni = new CheckBox("Pepperoni");
		cbSausage = new CheckBox("Sausage");
		cbGreenPepper = new CheckBox("Green Peppers");
		cbOlive = new CheckBox("Olives");
		cbMushroom = new CheckBox("Mushrooms");
		cbTomato = new CheckBox("Tomoatoes");
		cbAnchovies = new CheckBox("Anchovies");
		cbOnion = new CheckBox("Onions");
		
		//buttons
		btnOkay = new Button("Okay");		
		btnOkay.setOnAction(e->btnOkayClick()); // enacts btnOkayClick() detailed further below
		btnCancel  = new Button("Cancel");
		btnCancel.setOnAction(e->Platform.exit()); // exits the program when pressed
		
		// panes
		BorderPane rootPane = new BorderPane(); // the main pane everything will be attached to
		//rootPane.setPadding(new Insets(0,0,0,0));
		
		rootPane.getStyleClass().add("gridpane"); //  css template for everything

		FlowPane titlePane = new FlowPane();
		
		//titlePane.setAlignment(Pos.TOP_CENTER);
		GridPane userPane = new GridPane();
			GridPane fieldPane = new GridPane();
			GridPane optionPane = new GridPane(); // the pane pizza options will be attached to
		HBox hbButtons = new HBox(); // buttons will be attached here

		// containers for radio buttons and check boxes
		VBox boxSize = new VBox();
		VBox boxCrust = new VBox();

		// setting spaces and adding radio buttons to vboxes
		boxCrust.setSpacing(5);
		boxSize.setSpacing(5);
		boxSize.getChildren().addAll(rbSmall, rbMedium, rbLarge, rbELarge);
		boxCrust.getChildren().addAll(rbThin, rbRegular, rbThick);
		//
		optionPane.add(lblTitle, 0, 0);
		optionPane.add(lblName, 0, 1);
		optionPane.add(fldName, 1, 1);
		optionPane.add(lblPhone, 0, 2);
		optionPane.add(fldPhone, 1, 2);
		optionPane.add(lblAddress, 0, 3);
		optionPane.add(fldAddress, 1, 3);
		// all objects for the option pane
		optionPane.add(lblSize,0, 4);
		optionPane.add(lblCrust,1, 4);
		optionPane.add(lblTopping,2, 4);
		// first line of options
		optionPane.add(rbSmall, 0,5);
		optionPane.add(rbThin, 1,5);
		optionPane.add(cbPepperoni, 2,5);
		optionPane.add(cbMushroom, 3,5);
		// second line of options
		optionPane.add(rbMedium, 0,6);
		optionPane.add(rbRegular, 1,6);
		optionPane.add(cbSausage, 2,6);
		optionPane.add(cbTomato, 3,6);
		// third line of options
		optionPane.add(rbLarge, 0,7);
		optionPane.add(rbThick, 1,7);
		optionPane.add(cbGreenPepper, 2,7);
		optionPane.add(cbAnchovies, 3,7);
		// fourth line of options
		optionPane.add(rbELarge, 0,8);
		optionPane.add(cbOlive, 2,8);
		optionPane.add(cbOnion, 3,8);
		
		// add buttons
		optionPane.add(btnOkay, 0, 16);
		optionPane.add(btnCancel, 1, 16);
		
		// panes upon panes
		userPane.add(fieldPane, 0, 1);
		userPane.add(optionPane, 0, 2);
		
		
		hbButtons.getChildren().addAll(btnOkay, btnCancel);
		
		//rootPane.getChildren().add(titlePane);
		rootPane.setTop(lblTitle);
		//titlePane.setAlignment(Pos.CENTER);
		//rootPane.getChildren().add(userPane);
		rootPane.setCenter(userPane);
		//rootPane.getChildren().add(buttonPane);
		rootPane.setBottom(hbButtons);

		//set Scene
		Scene scene = new Scene(rootPane, 600, 400);
		scene.getStylesheets().add("AKL_Pizza_Controller.css"); // add the stylesheet to the controller 
		
		// set stage
		stage.setTitle("Alex Laye's Pizza Order GUI");
		stage.setResizable(false);
		stage.setScene(scene);
		
		// show stage
		stage.show();
	}
	
	/**
	* Method Name:	btnOkayClick()
	* Purpose:		when the 'okay' button is pressed (lambda listener), 
	* 						initiates checks that information is filled out 
	* 						and prepares customer return statement
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	public void btnOkayClick()
	{
		if(fldName.getText().length() > 0 && fldPhone.getText().length() > 0 && fldAddress.getText().length() > 0)
		{
			sizeCheck(); // check and set the size
			crustCheck(); // check and set the crust
			toppingCheck(); // check and set the toppings
			// give the customer their information
			String strInfo =  "Customer,\n\t"
								+	fldName.getText() + "\n\t" 
								+ fldPhone.getText() + "\n\t" 
								+ fldAddress.getText() + "\n\t"
								+ strSize +"\n\t"
								+ strCrust + "\n"
								+ strToppings;
				Alert orderInfo = new Alert(Alert.AlertType.INFORMATION, strInfo);
				orderInfo.showAndWait();
		}
		else
		{
			Alert emptyText = new Alert(Alert.AlertType.WARNING, "All fields must be filled.");
			emptyText.showAndWait();
		}
	}
	
	/**
	* Method Name:	toppingCheck()
	* Purpose:		checks if any toppings are selected, sets the global string	
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	public void toppingCheck()
	{
		if(cbPepperoni.isSelected() || cbSausage.isSelected() || cbGreenPepper.isSelected() || cbOlive.isSelected() ||
				cbMushroom.isSelected() || cbTomato.isSelected() || cbAnchovies.isSelected() || cbOnion.isSelected())
			strToppings = "Your selected toppings are: ";
		
		if(cbPepperoni.isSelected())
			strToppings += "Pepperoni, ";
		if(cbSausage.isSelected())
			strToppings += "Sausage, ";
		if(cbGreenPepper.isSelected())
			strToppings += "Green Pepper, ";
		if(cbOlive.isSelected())
			strToppings += "Olives, ";
		if(cbMushroom.isSelected())
			strToppings += "Mushrooms ";
		if(cbTomato.isSelected())
			strToppings += "Tomatoes ";
		if(cbAnchovies.isSelected())
			strToppings += "Anchovies, ";
		if(cbOnion.isSelected())
			strToppings += "Onions, ";
	}
	
	/**
	* Method Name:	crustCheck()
	* Purpose:		checks the type of crust selected, sets the global string
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	public void crustCheck()
	{
		try
		{
			if(grpSize.getSelectedToggle().equals(rbThin))
				strCrust = "Thin Crust";
			else if(grpSize.getSelectedToggle().equals(rbRegular))
				strCrust = "Regular Crust";
			else if(grpSize.getSelectedToggle().equals(rbThick))
				strCrust = "Thick Crust";
			else
				strCrust = "Regular Crust";
		}
		catch(NullPointerException ex)
		{
			strCrust = "Regular Crust";
		}
	}
	/**
	* Method Name:	sizeCheck()
	* Purpose:		checks the pizza size selected, sets the global string	
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	public void sizeCheck()
	{
		try
		{
			if(grpSize.getSelectedToggle().equals(rbSmall))
				strSize = "Small";
			else if(grpSize.getSelectedToggle().equals(rbMedium))
				strSize = "Medium";
			else if(grpSize.getSelectedToggle().equals(rbLarge))
				strSize = "Large";
			else if(grpSize.getSelectedToggle().equals(rbELarge))
				strSize = "Extra Large";
			else
				strSize = "Medium";
		}
		catch(NullPointerException ex)
		{
			strSize = "Medium";
		}
	}
	
	public static void main(String[] args)
	{
		// go time, long form
		Application.launch(args);
	}

}
//end class