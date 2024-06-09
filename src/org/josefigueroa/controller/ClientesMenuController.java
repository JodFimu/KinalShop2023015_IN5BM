package org.josefigueroa.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.Clientes;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.report.GenerarReportes;
import org.josefigueroa.system.Main;


public class ClientesMenuController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Clientes> listarClientes;

    private enum operaciones {
        AGREGAR, EDITAR, ACTUALIZAR, ELIMINAR, CANCELAR, NULL
    }

    private operaciones tipoOperaciones = operaciones.NULL;

    @FXML
    private TableView tblCliente;
    @FXML
    private TableColumn colClienteCod;
    @FXML
    private TableColumn colNomClient;
    @FXML
    private TableColumn colApelClient;
    @FXML
    private TableColumn colNitCliente;
    @FXML
    private TableColumn colTelClient;
    @FXML
    private TableColumn colDirCliente;
    @FXML
    private TableColumn colCorrCliente;
    @FXML
    private TextField txtNITCliente;
    @FXML
    private TextField txtTelCliente;
    @FXML
    private TextField txtCorreoCliente;
    @FXML
    private TextField txtDirCliente;
    @FXML
    private TextField txtNomClient;
    @FXML
    private TextField txtApeClient;
    @FXML
    private TextField txtCodClient;
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnAgregarCliente;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private ImageView imgEditar;   
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        desactivarControles();
        tblCliente.setItems(getClientes());
        colClienteCod.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNomClient.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApelClient.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosCliente"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
        colTelClient.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDirCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colCorrCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void desactivarControles() {
        txtCodClient.setEditable(false);
        txtNomClient.setEditable(false);
        txtApeClient.setEditable(false);
        txtNITCliente.setEditable(false);
        txtDirCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
        txtTelCliente.setEditable(false);
    }

    public void activarControles() {
        txtCodClient.setEditable(true);
        txtNomClient.setEditable(true);
        txtApeClient.setEditable(true);
        txtNITCliente.setEditable(true);
        txtDirCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
        txtTelCliente.setEditable(true);
    }

    public void limpiarControles() {
        txtCodClient.clear();
        txtNomClient.clear();
        txtApeClient.clear();
        txtNITCliente.clear();
        txtDirCliente.clear();
        txtCorreoCliente.clear();
        txtTelCliente.clear();
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarClientes = FXCollections.observableList(lista);
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
        } else if (event.getSource() == btnAgregarCliente) {
            activarControles();
        }
        
        if(event.getSource() == btnProgramador){
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
        }else if(event.getSource()==btnEmpleados){
            escenarioPrincipal.EmpleadosView();
        }else if(event.getSource()==btnFactura){
            escenarioPrincipal.FacturaView();
        }else if(event.getSource()==btnDetalleFactura){
            escenarioPrincipal.DetalleFacturaView();
        }
    }

    public void agregarClientes() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregarCliente.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtCodClient.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarClientes();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtCodClient.setEditable(true);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }

    public void guardarClientes() {
        Clientes registro = new Clientes();
        registro.setNombreCliente(txtNomClient.getText());
        registro.setApellidosCliente(txtApeClient.getText());
        registro.setNITCliente(txtNITCliente.getText());
        registro.setTelefonoCliente(txtTelCliente.getText());
        registro.setDireccionCliente(txtDirCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreCliente());
            procedimiento.setString(2, registro.getApellidosCliente());
            procedimiento.setString(3, registro.getNITCliente());
             procedimiento.setString(4, registro.getDireccionCliente());           
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getCorreoCliente());

            procedimiento.execute();
            listarClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarTupla() {
        txtCodClient.setText(String.valueOf(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNomClient.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApeClient.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtNITCliente.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getNITCliente());
        txtTelCliente.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtDirCliente.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtCorreoCliente.setText(((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getCorreoCliente());
        
    }

    public void eliminarClientes() {
        switch (tipoOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png")); 
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                btnInicio.setDisable(false);
                tipoOperaciones = operaciones.NULL;
                break;
            default:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listarClientes.remove(tblCliente.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un cliente para eliminar");
                }

                break;
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblCliente.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregarCliente.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    activarControles();
                    txtCodClient.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un cliente para editar");
                    break;
                }
            case ACTUALIZAR:
                actualizar();
                imgReporte.setImage(new Image("/org/josefigueroa/images/reporte.png"));
                imgEditar.setImage(new Image("/org/josefigueroa/images/editar.png"));                
                btnReporte.setText("Reporte");
                btnEditar.setText("Editar");
                btnAgregarCliente.setDisable(false);
                btnEliminar.setDisable(false);
                btnInicio.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NULL;
                cargarDatos();
        }
    }public void imprimirReporte(){
        Map parametros= new HashMap();
        
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("ReporteClientes.jasper", "Reporte de clientes", parametros);
        
    }

    
    
    public void reporte() {
        switch (tipoOperaciones) {
            case NULL:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                imgEditar.setImage(new Image("/org/josefigueroa/images/editar.png"));
                imgReporte.setImage(new Image("/org/josefigueroa/images/reporte.png"));
                btnReporte.setText("Reporte");
                btnEditar.setText("Editar");
                btnAgregarCliente.setDisable(false);
                btnEliminar.setDisable(false);
                btnInicio.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NULL;
                cargarDatos();
                break;
            
        }
    }
    
    
    
    public void actualizar(){
        Clientes registro = (Clientes)tblCliente.getSelectionModel().getSelectedItem();
        registro.setNombreCliente(txtNomClient.getText());
        registro.setApellidosCliente(txtApeClient.getText());
        registro.setNITCliente(txtNITCliente.getText());
        registro.setTelefonoCliente(txtTelCliente.getText());
        registro.setDireccionCliente(txtDirCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCliente(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getNITCliente());
            procedimiento.setString(5, registro.getDireccionCliente());           
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());

            procedimiento.execute();
             listarClientes.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    

}
