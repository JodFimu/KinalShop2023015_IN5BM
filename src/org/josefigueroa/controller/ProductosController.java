package org.josefigueroa.controller;

import static java.lang.Double.parseDouble;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.josefigueroa.bean.Productos;
import org.josefigueroa.bean.Proveedores;
import org.josefigueroa.bean.TipoProducto;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;

public class ProductosController implements Initializable {
    private Main escenarioPrincipal;
    
    private ObservableList<Productos> listarProductos;
    private ObservableList<Proveedores> listarProveedores;
    private ObservableList<TipoProducto> listarTipoProducto;


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
    private TableView tblProductos;

    @FXML
    private TableColumn colCodProd;

    @FXML
    private TableColumn colDescProd;

    @FXML
    private TableColumn colPrecUnit;

    @FXML
    private TableColumn colPrecDoc;

    @FXML
    private TableColumn colPrecMay;

    @FXML
    private TableColumn colImgProd;

    @FXML
    private TableColumn colExist;

    @FXML
    private TableColumn colTipoProd;

    @FXML
    private TableColumn colProv;

    @FXML
    private TextField txtCod;

    @FXML
    private TextField txtDescr;

    @FXML
    private TextField txtPrecUn;

    @FXML
    private TextField txtPrecDoc;

    @FXML
    private TextField txtMayo;

    @FXML
    private TextField txtImg;

    @FXML
    private TextField txtExist;

    @FXML
    private ComboBox cbxTipoProd;

    @FXML
    private ComboBox cbxProv;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbxTipoProd.setItems(getTipoProducto());
        cbxProv.setItems(getProveedores());
        cbxTipoProd.setDisable(false);
        cbxProv.setDisable(false);
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblCliente.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecUnit.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecDoc.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecMay.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImgProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExist.setCellValueFactory(new PropertyValueFactory<Productos, String>("existencia"));
        colTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("tipoProducto"));
        colProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("proveedor"));

    }
    
     public void desactivarControles() {
        txtCod.setEditable(false);
        txtDescr.setEditable(false);
        txtPrecUn.setEditable(false);
        txtPrecDoc.setEditable(false);
        txtMayo.setEditable(false);
        txtImg.setEditable(false);
        txtExist.setEditable(false);
        cbxTipoProd.setDisable(true);
        cbxProv.setDisable(true);
    }

    public void activarControles() {
        txtCod.setEditable(true);
        txtDescr.setEditable(true);
        txtPrecUn.setEditable(true);
        txtPrecDoc.setEditable(true);
        txtMayo.setEditable(true);
        txtImg.setEditable(true);
        txtExist.setEditable(true);
        cbxTipoProd.setDisable(false);
        cbxProv.setDisable(false);
    }

    public void limpiarControles() {
        txtCod.clear();
        txtDescr.clear();
        txtPrecUn.clear();
        txtPrecDoc.clear();
        txtMayo.clear();
        txtImg.clear();
        txtExist.clear();
        cbxTipoProd.setValue(null);
        cbxProv.setValue(null);
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
    
     public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarProveedores = FXCollections.observableList(lista);
    }
     
    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listarTipoProducto = FXCollections.observableList(lista);
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void agregarProductos() {
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
                guardarProductos();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtCod.setEditable(true);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarProductos(){
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCod.getText());
        registro.setProveedor(((Proveedores) cbxProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setTipoProducto(((TipoProducto) cbxTipoProd.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setDescripcionProducto(txtDescr.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecUn.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecDoc.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtMayo.getText()));
        registro.setImagenProducto(txtImg.getText());
        registro.setExistencia(Integer.parseInt(txtExist.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getTipoProducto());
            procedimiento.setInt(9, registro.getProveedor());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        txtCod.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescr.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        
    }
}
