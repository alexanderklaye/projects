/**
 * Program Name: AKL_Pizza_Controller.java
 *	Purpose: 	This is the event controller for AKL_Pizza_Main.java -- project 2 
 *						It contains all of the functionality of the programs 
 *						(ie, the methods that the main GUI uses)
 *	Coder: Alex Laye sec02
 *	Date: Aug 23, 2018
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AKL_Pizza_Controller
{

	// globals
	@FXML Label lblTitle, lblName, lblPhone, lblAddress, lblSize, lblCrust, lblTopping;
	@FXML TextField fldName, fldPhone, fldAddress;
	@FXML RadioButton rbSmall, rbMedium, rbLarge, rbELarge, rbThin, rbRegular, rbThick;
	@FXML CheckBox cbPepperoni, cbSausage, cbGreenPepper, cbOlives, cbMushrooms, cbTomatoes, cbAnchovies, cbOnions;
	@FXML Button btnOkay, btnCancel;
	@FXML ToggleGroup grpSize, grpCrust;
	@FXML String strToppings, strSize, strCrust;
	
	/**
	* Method Name:	initialize()
	* Purpose:			This method is designed for the variables 
	* 							for the gui set up with the FXML file 
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	@FXML private void initialize()
	{
		strToppings = "Your selected toppings are: NONE"; 
		btnOkay.setOnAction(e->btnOkayClick());
		btnCancel.setOnAction(e->Platform.exit());
	}// end initialize
	
	/**
	* Method Name:	btnOkayClick()
	* Purpose:			when the 'okay' button is pressed (lambda listener), 
	* 							initiates checks that information is filled out 
	* 							and prepares customer return statement
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	@FXML public void btnOkayClick()
	{
		if(fldName.getText().length() > 0 && fldPhone.getText().length() > 0 && fldAddress.getText().length() > 0)
		{
			sizeCheck(); // call to check and set the size
			crustCheck(); // call to check and set the crust
			toppingCheck(); // call to check and set the toppings
			// give the customer their information
			String strInfo =  "Customer,\n\t"
								+	fldName.getText() + "\n\t" 
								+ fldPhone.getText() + "\n\t" 
								+ fldAddress.getText() + "\n\t"
								+ strSize +"\n\t"
								+ strCrust + "\n"
								+ strToppings;
			// calls an alert box with all of the information for the customer
				Alert orderInfo = new Alert(Alert.AlertType.INFORMATION, strInfo);
				orderInfo.showAndWait();
		}// endif
		else
		{
			// if there are empty fields, it should alert the customer
			Alert emptyText = new Alert(Alert.AlertType.WARNING, "All fields must be filled.");
			emptyText.showAndWait();
		}//end else
	}// end btnOkayClick
	
	/**
	* Method Name:	toppingCheck()
	* Purpose:			checks if any toppings are selected, modifies the global string	
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	@FXML public void toppingCheck()
	{
		// checks if ANY of the toppings are selected and modifies the global string
		if(cbPepperoni.isSelected() || cbSausage.isSelected() || cbGreenPepper.isSelected() || cbOlives.isSelected() ||
				cbMushrooms.isSelected() || cbTomatoes.isSelected() || cbAnchovies.isSelected() || cbOnions.isSelected())
		{
			strToppings = "Your selected toppings are: ";
			
			// adds selected topping to the string 
			if(cbPepperoni.isSelected())
				strToppings += "Pepperoni, ";
			if(cbSausage.isSelected())
				strToppings += "Sausage, ";
			if(cbGreenPepper.isSelected())
				strToppings += "Green Pepper, ";
			if(cbOlives.isSelected())
				strToppings += "Olives, ";
			if(cbMushrooms.isSelected())
				strToppings += "Mushrooms ";
			if(cbTomatoes.isSelected())
				strToppings += "Tomatoes ";
			if(cbAnchovies.isSelected())
				strToppings += "Anchovies, ";
			if(cbOnions.isSelected())
				strToppings += "Onions, ";
		}//endif
	}// end toppingCheck
	
	/**
	* Method Name:	crustCheck()
	* Purpose:			checks the type of crust selected, modifies the global string
	* 							If, for some reason, there is no selection, it defaults to regular.
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	@FXML	 public void crustCheck()
	{
		// tries to check the selected crust (if it becomes deselected, it defaults to regular)
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
	}// end crustCheck
	
	/**
	* Method Name:	sizeCheck()
	* Purpose:			checks the pizza size selected, sets the global string	
	* 							If for some reason there is no selection, it defaults to medium
	* Accepts:	    nothing, is void
	* Returns:	    nothing, is void
	*/
	public void sizeCheck()
	{
		// tries to check the selected size (if it becomes deselected, it defaults to medium)
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
	}// end sizeCheck
	

}//end class