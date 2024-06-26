package org.josefigueroa.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.CargoEmpleado;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class CargoEmpleadoController implements Initializable {
    private Main escenarioPrincipal;
    private ObservableList<CargoEmpleado> listarCargoEmpleado;
   
        @FXML
    private TableView tblCargoEmpleado;

    @FXML
    private TableColumn colCodCarg;

    @FXML
    private TableColumn colNomCarg;

    @FXML
    private TableColumn colDescrCarg;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private TextField txtNomCarg;

    @FXML
    private TextField txtCodCarg;

    @FXML
    private TextField txtDescr;

    @FXML
    private Button btnInicio;

    @FXML
    private ImageView imgInicio;
    
    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnMin;
    
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
            
    
    private enum operaciones {
        AGREGAR, EDITAR, ACTUALIZAR, ELIMINAR, CANCELAR, NULL
    }
    
    private operaciones tipoOperaciones = operaciones.NULL;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    
    
    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarCargoEmpleado = FXCollections.observableList(lista);
    }
    
    public void cargarDatos() {
        desactivarControles();
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCodCarg.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNomCarg.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescrCarg.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }
    
    
     public void desactivarControles() {
        txtCodCarg.setEditable(false);
        
        txtNomCarg.setEditable(false);
        txtDescr.setEditable(false);
    }

    public void activarControles() {
        txtCodCarg.setEditable(true);
        txtNomCarg.setEditable(true);
        txtDescr.setEditable(true);
    }

    public void limpiarControles() {
        txtCodCarg.clear();
        txtNomCarg.clear();
        txtDescr.clear();
    }
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnInicio) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            activarControles();
        }
        
        if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }else if(event.getSource() == btnProgramador){
            escenarioPrincipal.ProgramadorView();
        }else if(event.getSource()==btnTipoProducto){
            escenarioPrincipal.TipoProductoView();
        }else if(event.getSource()==btnCompras){
            escenarioPrincipal.ComprasView();
        }else if(event.getSource()==btnProveedores){
            escenarioPrincipal.ProveedoresView();
        }else if(event.getSource()==btnProductos){
            escenarioPrincipal.ProductosView();
        }else if(event.getSource()==btnDetCompra){
            escenarioPrincipal.DetalleCompraView();
        }else if(event.getSource()==btnEmpleados){
            escenarioPrincipal.EmpleadosView();
        }else if(event.getSource()==btnFactura){
            escenarioPrincipal.FacturaView();
        }else if(event.getSource()==btnDetalleFactura){
            escenarioPrincipal.DetalleFacturaView();
        }
        
        if (event.getSource() == btnMin) {
            Stage stage = (Stage) btnMin.getScene().getWindow();
            minimizeStage(stage);
        } else if (event.getSource() == btnCerrar) {
            System.exit(0);
        }
    }
    
    private void minimizeStage(Stage stage) {
        stage.setIconified(true);
    }
    
    public void agregarCargoEmpleado() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtCodCarg.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarCargoEmpleado();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtCodCarg.setEditable(false);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarCargoEmpleado() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setNombreCargo(txtNomCarg.getText());
        registro.setDescripcionCargo(txtDescr.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.setString(2, registro.getDescripcionCargo());

            procedimiento.execute();
            listarCargoEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        txtCodCarg.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNomCarg.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescr.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }
    
    public void eliminarCargoEmpleado() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png")); 
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                btnInicio.setDisable(false);
                tipoOperaciones = operaciones.NULL;
                break;
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar CargoEmpleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listarCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un Cargo de empleado para eliminar");
                }

                break;
        }
    }
    
    public void actualizar(){
        CargoEmpleado registro = (CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem();
        registro.setNombreCargo(txtNomCarg.getText());
        registro.setDescripcionCargo(txtDescr.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());

            procedimiento.execute();
             listarCargoEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    activarControles();
                    txtCodCarg.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un Cargo de empleado para editar");
                    break;
                }
            case ACTUALIZAR:
                actualizar();
                imgReporte.setImage(new Image("/org/josefigueroa/images/reporte.png"));
                imgEditar.setImage(new Image("/org/josefigueroa/images/editar.png"));                
                btnReporte.setText("Reporte");
                btnEditar.setText("Editar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnInicio.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NULL;
                cargarDatos();
        }
    }

    public void reporte() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                imgEditar.setImage(new Image("/org/josefigueroa/images/editar.png"));
                imgReporte.setImage(new Image("/org/josefigueroa/images/reporte.png"));
                btnReporte.setText("Reporte");
                btnEditar.setText("Editar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnInicio.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NULL;
                cargarDatos();
            case NULL:
                break;
        }
    }
}
