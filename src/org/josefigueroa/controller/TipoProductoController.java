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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.TipoProducto;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;

public class TipoProductoController implements Initializable {
    private Main escenarioPrincipal;
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
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private Button btnInicio;

    @FXML
    private TableView tblTipoProducto;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDescripcion;
    
    @FXML
    private TableColumn colCodigo;

    @FXML
    private TableColumn colDescripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        desactivarControles();
        tblTipoProducto.setItems(getTipoProducto());
        colCodigo.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
 
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
    
    public void desactivarControles() {
        txtCodigo.setEditable(false);
        txtDescripcion.setEditable(false);

    }

    public void activarControles() {
        txtCodigo.setEditable(true);
        txtDescripcion.setEditable(true);
;
    }

    public void limpiarControles() {
        txtCodigo.clear();
        txtDescripcion.clear();

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
    
    public void agregarTipoProducto() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                txtCodigo.setEditable(false);
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarTipoProducto();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtCodigo.setEditable(true);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarTipoProducto() {
        TipoProducto registro = new TipoProducto();
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?)}");
            procedimiento.setString(1, registro.getDescripcion());

            procedimiento.execute();
            listarTipoProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarTupla() {
        txtCodigo.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcion.setText(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public void eliminarTipoProducto() {
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Tipo de Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listarTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione a un Tipo de Producto para eliminar");
                }

                break;
        }
    }    

    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    activarControles();
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    activarControles();
                    txtCodigo.setEditable(false);
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
        TipoProducto registro = (TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem();
        registro.setDescripcion(txtDescripcion.getText());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());

            procedimiento.execute();
             listarTipoProducto.add(registro);
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
}
