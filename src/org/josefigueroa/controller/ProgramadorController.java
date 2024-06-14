package org.josefigueroa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.josefigueroa.system.Main;


public class ProgramadorController implements Initializable{
    private Main  escenarioPrincipal;
    @FXML private Button btnRegresar;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador; 
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnCompras;
    @FXML MenuItem btnCargoEmpleado;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnDetCompra;
    @FXML MenuItem btnEmpleados;
    @FXML MenuItem btnFactura;
    @FXML MenuItem btnDetalleFactura;
    
    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnMin;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void handleButtonAction (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
        
        if (event.getSource() == btnMin) {
            Stage stage = (Stage) btnMin.getScene().getWindow();
            minimizeStage(stage);
        } else if (event.getSource() == btnCerrar) {
            System.exit(0);
        }

        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.ProgramadorView();
        } else if (event.getSource() == btnTipoProducto) {
            escenarioPrincipal.TipoProductoView();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.ComprasView();
        } else if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.CargoEmpleadoView();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.ProveedoresView();
        } else if (event.getSource() == btnProductos) {
            escenarioPrincipal.ProductosView();
        } else if (event.getSource() == btnDetCompra) {
            escenarioPrincipal.DetalleCompraView();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.EmpleadosView();
        } else if (event.getSource() == btnFactura) {
            escenarioPrincipal.FacturaView();
        } else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.DetalleFacturaView();
        }
    }
    
    private void minimizeStage(Stage stage) {
        stage.setIconified(true);
    }
}
