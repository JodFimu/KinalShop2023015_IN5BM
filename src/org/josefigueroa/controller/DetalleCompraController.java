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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.Compras;
import org.josefigueroa.bean.DetalleCompra;
import org.josefigueroa.bean.Productos;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;


public class DetalleCompraController implements Initializable {
    private Main escenarioPrincipal;
    
    private ObservableList<Productos> listarProductos;
    private ObservableList<Compras> listarCompras;
    private ObservableList<DetalleCompra> listarDetalleCompra;


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
    private TableView<DetalleCompra> tblDetCompra;

    @FXML
    private TableColumn colCodDEtCompra;

    @FXML
    private TableColumn colCostUnit;

    @FXML
    private TableColumn colCant;

    @FXML
    private TableColumn colCodProd;

    @FXML
    private TableColumn colNumDoc;

    @FXML
    private TextField txtCod;

    @FXML
    private TextField txtCostUnit;

    @FXML
    private TextField txtCant;

    @FXML
    private ComboBox cbxProducto;

    @FXML
    private ComboBox cbxNumDoc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbxProducto.setItems(getProductos());
        cbxNumDoc.setItems(getCompras());
    }    
    
    public void cargarDatos() {
        
        tblDetCompra.setItems(getDetalleCompra());
        colCodDEtCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoDetalleCompra"));
        colCostUnit.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCant.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumDoc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
        desactivarControles();
   }
    
     public void desactivarControles() {
        txtCod.setEditable(false);
        txtCostUnit.setEditable(false);
        txtCant.setEditable(false);
        cbxProducto.setDisable(true);
        cbxNumDoc.setDisable(true);
    }

    public void activarControles() {
        txtCod.setEditable(true);
        txtCostUnit.setEditable(true);
        txtCant.setEditable(true);
        cbxProducto.setDisable(false);
        cbxNumDoc.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCod.clear();
        txtCostUnit.clear();
        txtCant.clear();
        cbxProducto.setValue(null);
        cbxNumDoc.setValue(null);
    }
    
    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                                                        resultado.getDouble("costoUnitario"),
                                                        resultado.getInt("cantidad"),
                                                        resultado.getString("codigoProducto"),
                                                        resultado.getInt("numeroDocumento")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarDetalleCompra= FXCollections.observableList(lista);
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
                                                        resultado.getString("imagenProducto"),
                                                        resultado.getInt("existencia"), 
                                                        resultado.getInt("tipoProducto"),
                                                        resultado.getInt("proveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarProductos = FXCollections.observableList(lista);
    }
    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarCompras = FXCollections.observableList(lista);
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void agregarDetalleCompra() {
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
                guardarDetalleCompra();
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
    
    
    public void guardarDetalleCompra(){
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoProducto(((Productos) cbxProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cbxNumDoc.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        registro.setCostoUnitario(Double.parseDouble(txtCostUnit.getText()));
        registro.setCantidad(Integer.parseInt(txtCant.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleCompra(?,?,?,?)}");
            procedimiento.setDouble(1, registro.getCostoUnitario());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setString(3, registro.getCodigoProducto());
            procedimiento.setInt(4, registro.getNumeroDocumento());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        DetalleCompra selectedItem = (DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem();
        
        txtCostUnit.setText(String.valueOf(selectedItem.getCostoUnitario()));
        cbxProducto.getSelectionModel().select(buscaProducto(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cbxNumDoc.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtCod.setText(String.valueOf(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCant.setText(String.valueOf(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCantidad()));
        
    }

    public Compras buscarCompra(int codCompra) {
        Compras result = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCompras(?)}");
            procedimiento.setInt(1, codCompra);

            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                result = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("tipoProducto"),
                        registro.getInt("proveedor"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public void eliminarDetalleCompra() {
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
                if (tblDetCompra.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar detalle de compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            boolean execute = procedimiento.execute();
                            listarDetalleCompra.remove(tblDetCompra.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tupla para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblDetCompra.getSelectionModel().getSelectedItem() != null) {
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
        //String codProd = ((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCodigoProducto();
        
        DetalleCompra registro = (DetalleCompra)tblDetCompra.getSelectionModel().getSelectedItem();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCod.getText()));
        registro.setCodigoProducto(((Productos) cbxProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cbxNumDoc.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        registro.setCostoUnitario(Double.parseDouble(txtCostUnit.getText()));
        registro.setCantidad(Integer.parseInt(txtCant.getText()));
        
        /*txtCod.setText(String.valueOf(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostUnit.setText(String.valueOf(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCant.setText(String.valueOf(String.valueOf(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getCantidad())));
        cbxNumDoc.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        cbxProducto.getSelectionModel().select(buscaProducto(codProd));*/
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            
            listarDetalleCompra.add(registro);
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
    }
}
