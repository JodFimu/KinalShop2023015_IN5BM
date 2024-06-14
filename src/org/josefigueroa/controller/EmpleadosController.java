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
import javafx.scene.control.ComboBox;
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
import org.josefigueroa.bean.Empleados;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;

/**
 * FXML Controller class
 *
 * @author JoseDavid
 */
public class EmpleadosController implements Initializable {
    private Main escenarioPrincipal;
    
    private ObservableList<Empleados> listarEmpleados;
    private ObservableList<CargoEmpleado> listarCargoEmpleado;


    private enum operaciones {
        AGREGAR, EDITAR, ACTUALIZAR, ELIMINAR, CANCELAR, NULL
    }

    private operaciones tipoOperaciones = operaciones.NULL;

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
    private Button btnInicio;

    @FXML
    private ImageView imgInicio;

    @FXML
    private TableView tblEmpleados;

    @FXML
    private TableColumn colCod;

    @FXML
    private TableColumn colNom;

    @FXML
    private TableColumn colApe;

    @FXML
    private TableColumn colSueldo;

    @FXML
    private TableColumn colDir;

    @FXML
    private TableColumn colTurno;

    @FXML
    private TableColumn colCargo;

    @FXML
    private TextField txtCod;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtApe;

    @FXML
    private TextField txtSueldo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTurno;

    @FXML
    private ComboBox cbxCargo;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador; 
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnCompras;
    @FXML MenuItem btnCargoEmpleado;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnDetCompra;
    @FXML MenuItem btnFactura;
    @FXML MenuItem btnDetalleFactura;
    
    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnMin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbxCargo.setItems(getCargoEmpleado());
        cbxCargo.setDisable(true);
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblEmpleados.setItems(getEmpleados());
        colCod.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNom.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApe.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDir.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
    }
    
    public void desactivarControles() {
        txtCod.setEditable(false);
        txtNom.setEditable(false);
        txtApe.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cbxCargo.setDisable(true);
    }

    public void activarControles() {
        txtCod.setEditable(true);
        txtNom.setEditable(true);
        txtApe.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cbxCargo.setDisable(false);
    }

    public void limpiarControles() {
        txtCod.clear();
        txtNom.clear();
        txtApe.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        cbxCargo.setValue(null);
    }
    
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                                        resultado.getString("nombresEmpleado"),
                                                        resultado.getString("apellidosEmpleado"),
                                                        resultado.getDouble("sueldo"),
                                                        resultado.getString("direccion"), 
                                                        resultado.getString("turno"),
                                                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarEmpleados = FXCollections.observableList(lista);
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
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void agregarEmpleados() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtCod.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarEmpleados();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarEmpleados(){
        Empleados registro = new Empleados();
        registro.setNombresEmpleado(txtNom.getText());
        registro.setApellidosEmpleado(txtApe.getText());
        
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cbxCargo.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

        try{
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleados(?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNombresEmpleado());
                procedimiento.setString(2, registro.getApellidosEmpleado());
                procedimiento.setDouble(3, registro.getSueldo());
                procedimiento.setString(4, registro.getDireccion());
                procedimiento.setString(5, registro.getTurno());
                procedimiento.setInt(6, registro.getCodigoCargoEmpleado());

                procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Sueldo solo puede ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void seleccionarTupla() {
        int codCargo = ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado();
        
        txtCod.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNom.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApe.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurno.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cbxCargo.getSelectionModel().select(buscarCargo(codCargo));
    }
    
    public CargoEmpleado buscarCargo(int codCargo){
        CargoEmpleado result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
           procedimiento.setInt(1, codCargo);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                                        registro.getString("nombreCargo"),
                                    registro.getString("descripcionCargo"));

           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void eliminarEmpleados() {
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            boolean execute = procedimiento.execute();
                            listarEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Empleado para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    txtCod.setDisable(true);
                    activarControles();
                    tipoOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tupla para editar");
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
    
    public void actualizar(){
        Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
        registro.setNombresEmpleado(txtNom.getText());
        registro.setApellidosEmpleado(txtApe.getText());
        
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cbxCargo.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
        
        try{
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmpleados(?,?,?,?,?)}");
                procedimiento.setInt(1, registro.getCodigoEmpleado());
                procedimiento.setString(2, registro.getNombresEmpleado());
                procedimiento.setString(3, registro.getApellidosEmpleado());
                procedimiento.setDouble(4, registro.getSueldo());
                procedimiento.setString(5, registro.getDireccion());
                procedimiento.setString(6, registro.getTurno());
                procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
                procedimiento.execute();

                listarEmpleados.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Sueldo solo puede ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
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
        }else if(event.getSource()==btnCargoEmpleado){
            escenarioPrincipal.CargoEmpleadoView();
        }else if(event.getSource()==btnProveedores){
            escenarioPrincipal.ProveedoresView();
        }else if(event.getSource()==btnProductos){
            escenarioPrincipal.ProductosView();
        }else if(event.getSource()==btnDetCompra){
            escenarioPrincipal.DetalleCompraView();
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
}
