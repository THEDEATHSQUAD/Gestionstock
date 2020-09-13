package com_categorie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com_connection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import projet.bin.Categorie;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class CategorieController  implements Initializable{
	@FXML
	private TextField searchfield;
	@FXML
	private TextField chargefield;
	@FXML
	private TextField typefield;
	@FXML
	private Button BackBtn;
	@FXML
	private TableView<Categorie> table;
	@FXML
	private TableColumn<Categorie, String> col_cat;
	@FXML
	private Label exit;
	@FXML
    private Button btnadd;
	@FXML
	private TableColumn<Categorie, String> col_desc;
	public ObservableList<Categorie> data= FXCollections.observableArrayList();
	 ObservableList<Categorie> dataList;
	
	 
	  public void handleButtonAction(MouseEvent event) {
	        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        appStage.close();
		}
	  
	  
	  
	@FXML
	public void Modifier(ActionEvent event) {
		// TODO Autogenerated
		Connection con= ConnectionDB.conDB();
    	PreparedStatement st = null;
    	Categorie a = new Categorie();
    	
    	String Nom = typefield.getText() ; 
    	String Desc = chargefield.getText();
    	a.setNom(Nom);
    	a.setDesc(Desc);
    	String rqt ="UPDATE categorie SET Nom_Cat=?, Description = ?  WHERE Nom_Cat=?";
    	try {
			st = con.prepareStatement(rqt);
			
			st.setString(1, a.getNom());
			st.setString(2, a.getDesc());
			st.setString(3,i);
			st.executeUpdate();
			con.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("C'est fait");
			alert.setHeaderText(null);
			alert.setContentText("La cat�gorie est Modifi�e");
			alert.showAndWait();		
    		UpdateTable();

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERREUR");
		alert.setHeaderText(null);
			alert.setContentText("La cat�gorie n'a pas �t� modifi�e");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void Ajouter(ActionEvent event) {
		// TODO Autogenerated
				Connection con= ConnectionDB.conDB();
		    	PreparedStatement st = null;
		    	Categorie a = new Categorie();
		    	
		    	String Nom = typefield.getText() ; 
		    	String Desc = chargefield.getText();
		    	a.setNom(Nom);
		    	a.setDesc(Desc);
		    	String rqt ="Insert into categorie ( Nom_Cat, Description)  values(?,?) ";
		    	try {
					st = con.prepareStatement(rqt);
					
					st.setString(1, a.getNom());
					st.setString(2, a.getDesc());
					
					st.executeUpdate();
					con.close();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("C'est fait");
					alert.setHeaderText(null);
					alert.setContentText("La cat�gorie est ajout�e");
					alert.showAndWait();	
		    		UpdateTable();

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERREUR");
				alert.setHeaderText(null);
					alert.setContentText("la cat�gorie n'a pas �t� ajout�e");
					alert.showAndWait();
				}
		    }
		
		
		
		
	
	@FXML
	public void Retour(ActionEvent event) {
		// TODO Autogenerated
	}
	  //index for selection and i to get id
    String i;
    int index=-1;
    @FXML
    void getSelected (MouseEvent event) {
    	index=table.getSelectionModel().getSelectedIndex();
    	if (index<=-1) {
    	
    		return;
    	}
    	i=col_cat.getCellData(index).toString();
    	typefield.setText(col_cat.getCellData(index).toString());
    	chargefield.setText(col_desc.getCellData(index).toString());
    	
    }
    
    
    public void UpdateTable(){
    	try{
    		//update
    		Connection conn = ConnectionDB.conDB();
		String sql = "SELECT * FROM `categorie`" ;
		PreparedStatement stm  = conn.prepareStatement(sql);
		ResultSet rs = stm.executeQuery();
		table.getItems().removeAll(data);
		while (rs.next())
		{
			data.add(new Categorie(rs.getString(1) , rs.getString(2)  ) );
			
		}
		conn.close();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
    	col_cat.setCellValueFactory(new PropertyValueFactory<Categorie, String>("nom"));
		col_desc.setCellValueFactory(new PropertyValueFactory<Categorie, String>("desc"));
		
		
        table.setItems(data);

    }
    
    
    
    
	@FXML
	public void Supprimer(ActionEvent event) {
		// TODO Autogenerated
		
		
		Connection con= ConnectionDB.conDB();
    	PreparedStatement st = null;
    	String rqt ="delete from categorie where com_cat=?";
    	try {
    		st = con.prepareStatement(rqt);
    		st.setString(1, i);
    		st.executeUpdate();
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("C'est fait");
			alert.setHeaderText(null);
			alert.setContentText("Cat�gorie Supprim�e");
			alert.showAndWait();
    		System.out.println("i="+i);
    		UpdateTable();
    	
    		}
    	catch(Exception e) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERREUR");
			alert.setHeaderText(null);
			alert.setContentText("ERREUR! cette cat�gorie concerne un ou de plusieurs articles");
			alert.showAndWait();
    	}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		UpdateTable();
		search();
		
	}
	private void loadDataDB() {
		data.clear();
		try {
			Connection conn = ConnectionDB.conDB();
			PreparedStatement pst=conn.prepareStatement("Select * from categorie");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				data.add(new Categorie(rs.getString(1),rs.getString(2)));
			}
		}
		catch(SQLException ex){
			Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null,ex);
		}
		table.setItems(data);
	}
	 @FXML
	    void search() {    
		 searchfield.setOnKeyReleased(e->{
				if(searchfield.getText().equals("")) {
					loadDataDB();
				}
				else {
					data.clear();
					String sql= "Select * from categorie where `Nom_Cat` LIKE '%"+searchfield.getText()+"%' " + "UNION Select * from categorie where Description LIKE '%"+searchfield.getText()+"%' ";
					try {
						Connection conn = ConnectionDB.conDB();
						PreparedStatement pst=conn.prepareStatement(sql);
						ResultSet rs=pst.executeQuery();
						while(rs.next()) {
							data.add(new Categorie(rs.getString(1),rs.getString(2)));
						}
						table.setItems(data);
					}catch(SQLException ex) {
						Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null,ex);
					}
				}
						
			}
			);
	    }
	 @FXML
		public void back(MouseEvent event) throws IOException {
			// TODO Autogenerated
			Parent homePage = FXMLLoader.load(getClass().getResource("/vendeur/Vendeur.fxml"));
		    Scene homepageScene = new Scene(homePage);
		    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    appStage.setScene(homepageScene);
		    homepageScene.setFill(Color.TRANSPARENT);
		    appStage.show();
		}
		
	
		 public void vider (MouseEvent event)
		    {
		    	try {
		    		
		    		typefield.clear();
		    		chargefield.clear();
		    	

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
		    	
		    	
		    	
		    }
}
