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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.Proveedores;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.report.GenerarReportes;
import org.josefigueroa.system.Main;

public class ProveedoresController implements Initializable {
    private Main escenarioPrincipal;
    private ObservableList<Proveedores> listarProveedores;

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
    private TableView tblProv;

    @FXML
    private TableColumn colProCod;

    @FXML
    private TableColumn colNitProv;

    @FXML
    private TableColumn colNomProv;

    @FXML
    private TableColumn colApeProv;

    @FXML
    private TableColumn colDirProv;

    @FXML
    private TableColumn colRazonProv;

    @FXML
    private TableColumn colContProv;

    @FXML
    private TableColumn  colPagProv;

    @FXML
    private Button btnInicio;

    @FXML
    private ImageView imgInicio;

    @FXML
    private TextField txtCodProv;

    @FXML
    private TextField txtNitProv;

    @FXML
    private TextField txtNomProv;

    @FXML
    private TextField txtApeProv;

    @FXML
    private TextField txtDirProv;

    @FXML
    private TextField txtRazProv;

    @FXML
    private TextField txtContProv;

    @FXML
    private TextField txtPagProv;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    public void cargarDatos() {
        desactivarControles();
        tblProv.setItems(getProveedores());
        colProCod.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNomProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApeProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDirProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPagProv.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void desactivarControles() {
        txtCodProv.setEditable(false);
        txtNitProv.setEditable(false);
        txtNomProv.setEditable(false);
        txtApeProv.setEditable(false);
        txtDirProv.setEditable(false);
        txtRazProv.setEditable(false);
        txtContProv.setEditable(false);
        txtPagProv.setEditable(false);
    }

    public void activarControles() {
        txtCodProv.setEditable(true);
        txtNitProv.setEditable(true);
        txtNomProv.setEditable(true);
        txtApeProv.setEditable(true);
        txtDirProv.setEditable(true);
        txtRazProv.setEditable(true);
        txtContProv.setEditable(true);
        txtPagProv.setEditable(true);
    }

    public void limpiarControles() {
        txtCodProv.clear();
        txtNitProv.clear();
        txtNomProv.clear();
        txtApeProv.clear();
        txtDirProv.clear();
        txtRazProv.clear();
        txtContProv.clear();
        txtPagProv.clear();
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
    }
    
    public void agregarProveedores() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtCodProv.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarProveedores();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtCodProv.setEditable(true);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarProveedores() {
        Proveedores registro = new Proveedores();
        registro.setNITProveedor(txtNitProv.getText());
        registro.setNombresProveedor(txtNomProv.getText());
        registro.setApellidosProveedor(txtApeProv.getText());
        registro.setDireccionProveedor(txtDirProv.getText());
        registro.setRazonSocial(txtRazProv.getText());
        registro.setContactoPrincipal(txtContProv.getText());
        registro.setPaginaWeb(txtPagProv.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProveedores(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getNombresProveedor());
            procedimiento.setString(3, registro.getApellidosProveedor());
             procedimiento.setString(4, registro.getDireccionProveedor());           
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.setString(6, registro.getContactoPrincipal());
            procedimiento.setString(7, registro.getPaginaWeb());

            procedimiento.execute();
            listarProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        txtCodProv.setText(String.valueOf(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getNITProveedor());
        txtNomProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getNombresProveedor());
        txtApeProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getApellidosProveedor());
        txtDirProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPagProv.setText(((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    public void eliminarProveedores() {
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
                if (tblProv.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listarProveedores.remove(tblProv.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un Proveedor para eliminar");
                }

                break;
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblProv.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    activarControles();
                    txtCodProv.setEditable(false);
                    tipoOperaciones = operaciones.ACTUALIZAR;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un Proveedor para editar");
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
        Proveedores registro = (Proveedores)tblProv.getSelectionModel().getSelectedItem();
        registro.setNITProveedor(txtNitProv.getText());
        registro.setNombresProveedor(txtNomProv.getText());
        registro.setApellidosProveedor(txtApeProv.getText());
        registro.setDireccionProveedor(txtDirProv.getText());
        registro.setRazonSocial(txtRazProv.getText());
        registro.setContactoPrincipal(txtContProv.getText());
        registro.setPaginaWeb(txtPagProv.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedores(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());           
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());

            procedimiento.execute();
            listarProveedores.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void imprimirReporte(){
        Map parametros= new HashMap();
        
        parametros.put("codigoProveedor", null);
        GenerarReportes.mostrarReportes("ReporteProveedores.jasper", "Reporte de proveedores", parametros);
        
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
}
