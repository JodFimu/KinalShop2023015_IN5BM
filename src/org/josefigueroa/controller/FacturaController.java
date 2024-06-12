package org.josefigueroa.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.Clientes;
import org.josefigueroa.bean.Empleados;
import org.josefigueroa.bean.Factura;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.report.GenerarReportes;
import org.josefigueroa.system.Main;

public class FacturaController implements Initializable {
    private Main escenarioPrincipal;
    
    private ObservableList<Factura> listarFactura;
    private ObservableList<Empleados> listarEmpleados;
    private ObservableList<Clientes> listarClientes;


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
    private TableView tblFactura;

    @FXML
    private TableColumn colNumFact;

    @FXML
    private TableColumn colEstado;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colFecha;

    @FXML
    private TableColumn colCodClient;

    @FXML
    private TableColumn colCodEmpleado;

    @FXML
    private TextField txtNumFact;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtTotal;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private ComboBox cbxCliente;

    @FXML
    private ComboBox cbxEmpleado;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador; 
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnCompras;
    @FXML MenuItem btnCargoEmpleado;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnDetCompra;
    @FXML MenuItem btnEmpleados;
    @FXML MenuItem btnDetalleFactura;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbxCliente.setItems(getClientes());
        cbxEmpleado.setItems(getEmpleados());
        cbxCliente.setDisable(true);
        cbxEmpleado.setDisable(true);
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblFactura.setItems(getFactura());
        colNumFact.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colCodClient.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }
    
     public void desactivarControles() {
        txtNumFact.setEditable(false);
        txtEstado.setEditable(false);
        txtTotal.setEditable(false);
        dpFecha.setEditable(false);
        cbxCliente.setDisable(true);
        cbxEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtNumFact.setEditable(true);
        txtEstado.setEditable(true);
        dpFecha.setEditable(true);
        cbxCliente.setDisable(false);
        cbxEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtNumFact.clear();
        txtEstado.clear();
        txtTotal.clear();
        dpFecha.setValue(null);
        cbxCliente.setValue(null);
        cbxEmpleado.setValue(null);
    }
    
    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                                                        resultado.getString("estado"),
                                                        resultado.getDouble("totalFactura"),
                                                        resultado.getString("fechaFactura"),
                                                        resultado.getInt("codigoCliente"), 
                                                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarFactura = FXCollections.observableList(lista);
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
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void agregarFactura() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtNumFact.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarFactura();
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
    
    public void guardarFactura(){
        Factura registro = new Factura();
        registro.setFechaFactura(dpFecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setCodigoCliente(((Clientes) cbxCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cbxEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setEstado(txtEstado.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarFactura(?,?,?,?)}");
            procedimiento.setString(1, registro.getEstado());
            procedimiento.setString(2, registro.getFechaFactura());
            procedimiento.setInt(3, registro.getCodigoCliente());
            procedimiento.setInt(4, registro.getCodigoEmpleado());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        int codEmpleado = ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
        
        txtNumFact.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstado.setText(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado());
        txtTotal.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        dpFecha.setValue(LocalDate.parse(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cbxCliente.getSelectionModel().select(buscarCliente(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cbxEmpleado.getSelectionModel().select(buscarEmpleado(codEmpleado));
    }
    
    public Clientes buscarCliente(int cod){
        Clientes result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCliente(?)}");
           procedimiento.setInt(1, cod);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public Empleados buscarEmpleado(int cod){
        Empleados result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleados(?)}");
           procedimiento.setInt(1, cod);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new Empleados(registro.getInt("codigoEmpleado"),
                                   registro.getString("nombresEmpleado"),
                                 registro.getString("apellidosEmpleado"),
                                          registro.getDouble("sueldo"),
                                        registro.getString("direccion"), 
                                           registro.getString("turno"),
                                registro.getInt("codigoCargoEmpleado"));
           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void eliminarFactura() {
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar la factura", "Eliminar factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            boolean execute = procedimiento.execute();
                            listarFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una factura para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    txtNumFact.setDisable(true);
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
        Factura registro = (Factura)tblFactura.getSelectionModel().getSelectedItem();
        
        registro.setFechaFactura(dpFecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setCodigoCliente(((Clientes) cbxCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cbxEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotal.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            
            procedimiento.execute();
            
            listarFactura.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        
        
        int IDFact = Integer.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        parametros.put("IDFact", IDFact);
        
        GenerarReportes.mostrarReportes("Factura.jasper", "Factura", parametros);
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
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnInicio.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoOperaciones = operaciones.NULL;
                cargarDatos();
            
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
        }else if(event.getSource()==btnEmpleados){
            escenarioPrincipal.EmpleadosView();
        }else if(event.getSource()==btnDetalleFactura){
            escenarioPrincipal.DetalleFacturaView();
        }
    }
}
