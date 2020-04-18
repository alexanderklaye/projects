/**
 * Program Name: AKL_Pizza_Main.java
 *	Purpose: The main() portion of the Pizza project. It handles the creation of the stage for 
 *						the GUI and calls the import of the FXML elements
 *						
 *	Coder: Alex Laye sec02
 *	Date: Aug 22, 2018
 */

// imports for FXML elements to be ported and main()
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


import javafx.fxml.FXMLLoader;

import java.net.URL;

public class AKL_Pizza_Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
    	// build the root for the FXMLLoader
			Parent root = FXMLLoader.load(getClass().getResource("AKL_Pizza_Controller.fxml"));
			// set title for the GUI
			stage.setTitle("Alex Laye's Pizza Order GUI v2");
			// set the scene, window size
			Scene scene = new Scene(root, 600, 500);
			// prepare the scene 
			stage.setScene(scene);
			//'raise curtain'
			stage.show();
    } // end start()
    
    public static void main(String[] args) {
    	// start the program
      launch(args);
  } // end main
} // end class