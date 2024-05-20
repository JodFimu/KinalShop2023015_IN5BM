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
        cbxTipoProd.setDisable(true);
        cbxProv.setDisable(true);
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblProductos.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecUnit.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecDoc.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecMay.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImgProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExist.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
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
        txtImg.setEditable(true);
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
                txtCod.setEditable(true);
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
        registro.setImagenProducto(txtImg.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProductos(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setString(3, registro.getImagenProducto());
            procedimiento.setInt(4, registro.getTipoProducto());
            procedimiento.setInt(5, registro.getProveedor());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        int codProv = ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProveedor();
        
        txtCod.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescr.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecUn.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecDoc.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtMayo.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImg.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto());
        txtExist.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cbxTipoProd.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getTipoProducto()));
        cbxProv.getSelectionModel().select(buscarProveedor(codProv));
    }
    
    public TipoProducto buscarTipoProducto(int codTipoProd){
        TipoProducto result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
           procedimiento.setInt(1, codTipoProd);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result=new TipoProducto(registro.getInt("codigoTipoProducto"),
                                        registro.getString("descripcion"));

           }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public Proveedores buscarProveedor(int codProv){
        Proveedores result=null;
        
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedores(?)}");
           procedimiento.setInt(1, codProv);
           
           ResultSet registro = procedimiento.executeQuery();
           
           while(registro.next()){
               result= new Proveedores(registro.getInt("codigoProveedor"),
               registro.getString("NITProveedor"),
               registro.getString("nombresProveedor"),
               registro.getString("apellidosProveedor"),
               registro.getString("direccionProveedor"),
               registro.getString("razonSocial"),
               registro.getString("contactoPrincipal"),
               registro.getString("paginaWeb"));

           }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void eliminarProductos() {
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProductos(?)}");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            boolean execute = procedimiento.execute();
                            listarProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Producto para eliminar");
                }
                    cargarDatos();
                break;
                }
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
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
        Productos registro = (Productos)tblProductos.getSelectionModel().getSelectedItem();
        registro.setProveedor(((Proveedores) cbxProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setTipoProducto(((TipoProducto) cbxTipoProd.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setDescripcionProducto(txtDescr.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecUn.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecDoc.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtMayo.getText()));
        registro.setImagenProducto(txtImg.getText());
        registro.setExistencia(Integer.parseInt(txtExist.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProductos(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setString(3, registro.getImagenProducto());
            procedimiento.setInt(4, registro.getTipoProducto());
            procedimiento.setInt(5, registro.getProveedor());
            procedimiento.execute();
            
            listarProductos.add(registro);
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
