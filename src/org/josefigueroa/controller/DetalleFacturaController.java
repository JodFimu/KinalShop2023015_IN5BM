package org.josefigueroa.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.DetalleFactura;
import org.josefigueroa.bean.Factura;
import org.josefigueroa.bean.Productos;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;

public class DetalleFacturaController implements Initializable {
    
    private Main escenarioPrincipal;
    
    private ObservableList<DetalleFactura> listarDetalleFactura;
    private ObservableList<Productos> listarProductos;
    private ObservableList<Factura> listarFactura;


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
    private TableView tblDetFact;

    @FXML
    private TableColumn colCod;

    @FXML
    private TableColumn colPrecUnit;

    @FXML
    private TableColumn colCant;

    @FXML
    private TableColumn colNumFact;

    @FXML
    private TableColumn colCodProd;

    @FXML
    private TextField txtCod;

    @FXML
    private TextField txtPrecUnit;

    @FXML
    private TextField txtCant;

    @FXML
    private ComboBox cbxNumFact;

    @FXML
    private ComboBox cbxProd;
    
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
        cbxNumFact.setItems(getFactura());
        cbxProd.setItems(getProductos());
        cbxNumFact.setDisable(true);
        cbxProd.setDisable(true);
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblDetFact.setItems(getDetalleFactura());
        colCod.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecUnit.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCant.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumFact.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
  }
    
     public void desactivarControles() {
        txtCod.setEditable(false);
        txtPrecUnit.setEditable(false);
        txtCant.setEditable(false);
        cbxNumFact.setDisable(true);
        cbxProd.setDisable(true);
    }

    public void activarControles() {
        txtCod.setEditable(true);
        txtCant.setEditable(true);
        cbxNumFact.setDisable(false);
        cbxProd.setDisable(false);
    }

    public void limpiarControles() {
        txtCod.clear();
        txtPrecUnit.clear();
        txtCant.clear();
        cbxNumFact.setValue(null);
        cbxProd.setValue(null);
    }
    
    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                                                        resultado.getDouble("precioUnitario"),
                                                        resultado.getInt("cantidad"),
                                                        resultado.getInt("numeroFactura"), 
                                                        resultado.getString("codigoProducto")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarDetalleFactura = FXCollections.observableList(lista);
    }
    
    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                                                        resultado.getString("descripcionProducto"),
                                                        resultado.getDouble("precioUnitario"),
                                                        resultado.getDouble("precioDocena"),
                                                        resultado.getDouble("precioMayor"),
                                                        resultado.getBlob("imagenProducto"),
                                                        resultado.getInt("existencia"), 
                                                        resultado.getInt("tipoProducto"),
                                                        resultado.getInt("proveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarProductos = FXCollections.observableList(lista);
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
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void agregarDetalleFactura() {
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
                guardarDetalleFactura();
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
    
    public void guardarDetalleFactura(){
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoProducto(((Productos) cbxProd.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroFactura(((Factura) cbxNumFact.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCantidad(Integer.parseInt(txtCant.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleFactura(?,?,?)}");
            procedimiento.setInt(1, registro.getCantidad());
            procedimiento.setInt(2, registro.getNumeroFactura());
            procedimiento.setString(3, registro.getCodigoProducto());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        String codProd = ((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getCodigoProducto();
        
        txtCod.setText(((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtPrecUnit.setText(String.valueOf(((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCant.setText(String.valueOf(((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getCantidad()));
        cbxNumFact.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cbxProd.getSelectionModel().select(buscaProducto(codProd));
    }
    
    public Productos buscaProducto(String codProd) {
        Productos result = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductos(?)}");
            procedimiento.setString(1, codProd);

            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                result = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getBlob("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("tipoProducto"),
                        registro.getInt("proveedor"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public Factura buscarFactura(int codFact){
        Factura result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarFactura(?)}");
           procedimiento.setInt(1, codFact);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result= new Factura(registro.getInt("numeroFactura"),
               registro.getString("estado"),
               registro.getDouble("totalFactura"),
               registro.getString("fechaFactura"),
               registro.getInt("codigoCliente"),
               registro.getInt("codigoEmpleado"));

           }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void eliminarDetalleFactura() {
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
                if (tblDetFact.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Detalle Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetalleFactura) tblDetFact.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                            boolean execute = procedimiento.execute();
                            listarProductos.remove(tblDetFact.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblDetFact.getSelectionModel().getSelectedItem() != null) {
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
        DetalleFactura registro = (DetalleFactura)tblDetFact.getSelectionModel().getSelectedItem();
        
        registro.setCodigoProducto(((Productos) cbxProd.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroFactura(((Factura) cbxNumFact.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCantidad(Integer.parseInt(txtCant.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleFactura(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            
            procedimiento.execute();
            
            listarDetalleFactura.add(registro);
        }catch(Exception e){
            e.printStackTrace();
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
        }else if(event.getSource()==btnEmpleados){
            escenarioPrincipal.EmpleadosView();
        }else if(event.getSource()==btnFactura){
            escenarioPrincipal.FacturaView();
        }else if(event.getSource()==btnDetalleFactura){
            escenarioPrincipal.DetalleFacturaView();
        }
    }
}
