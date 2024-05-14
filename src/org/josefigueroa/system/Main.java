package org.josefigueroa.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josefigueroa.controller.CargoEmpleadoController;
import org.josefigueroa.controller.ClientesMenuController;
import org.josefigueroa.controller.ComprasController;
import org.josefigueroa.controller.MenuPrincipalController;
import org.josefigueroa.controller.ProductosController;
import org.josefigueroa.controller.ProgramadorController;
import org.josefigueroa.controller.ProveedoresController;
import org.josefigueroa.controller.TipoProductoController;

/**
 *
 * 
 */
public class Main extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal=escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Shop");
        escenarioPrincipal.getIcons().add(new Image("/org/josefigueroa/images/Logo.png"));
        menuPrincipalView();
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
        Initializable result = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/josefigueroa/view/" + fxmlName ) ;
        
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/josefigueroa/view/" + fxmlName ));
        
        escena= new Scene ((AnchorPane)loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        result= (Initializable)  loader.getController();
        
        return result;
    }
    
    public void menuClientesView(){
         try{
            ClientesMenuController menuClientesView= (ClientesMenuController) cambiarEscena("ClientesView.fxml", 1066, 675);
            menuClientesView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void TipoProductoView(){
         try{
            TipoProductoController TipoProductoView= (TipoProductoController) cambiarEscena("TipoProductoView.fxml", 1066, 675);
            TipoProductoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("KinalShopView.fxml", 1020, 580);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void ProgramadorView(){
        try{
            ProgramadorController programadorView = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 600, 400);
            programadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ComprasView(){
        try{
            ComprasController comprasView = (ComprasController) cambiarEscena("ComprasView.fxml", 1066, 675);
            comprasView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void CargoEmpleadoView(){
        try{
            CargoEmpleadoController cargoEmpleadorView = (CargoEmpleadoController) cambiarEscena("CargoEmpleadoView.fxml", 1066, 675);
            cargoEmpleadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    public void ProveedoresView(){
        try{
            ProveedoresController proveedoresView = (ProveedoresController) cambiarEscena("ProveedoresView.fxml", 1066, 675);
            proveedoresView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ProductosView(){
        try{
            ProductosController productoView = (ProductosController) cambiarEscena("ProductosView.fxml", 1066, 675);
            productoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
