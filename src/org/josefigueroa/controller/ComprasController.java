package org.josefigueroa.controller;

import static java.lang.Double.parseDouble;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.josefigueroa.bean.Compras;
import org.josefigueroa.db.Conexion;
import org.josefigueroa.system.Main;


public class ComprasController implements Initializable {
    private Main escenarioPrincipal;
    private ObservableList<Compras> listarCompras;

    private enum operaciones {
        AGREGAR, EDITAR, ACTUALIZAR, ELIMINAR, CANCELAR, NULL
    }

    private operaciones tipoOperaciones = operaciones.NULL;
    
    @FXML
    private TextField txtNumeroDoc;

    @FXML
    private TextField txtDescr;

    @FXML
    private TextField txtTotal;

    @FXML
    private TableColumn colNumDoc;

    @FXML
    private TableColumn colFecha;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colTotal;

    @FXML
    private Button btnInicio;

    @FXML
    private ImageView imgInicio;

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
    private TableView tblCompras;
    
    @FXML
    private DatePicker dpFecha;
    
    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnMin;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador; 
    @FXML MenuItem btnTipoProducto;
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
        tblCompras.setItems(getCompras());
        colNumDoc.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("totalDocumento"));
    }

    public void desactivarControles() {
        txtNumeroDoc.setEditable(false);
        dpFecha.setEditable(false);
        txtDescr.setEditable(false);
        txtTotal.setEditable(false);
    }

    public void activarControles() {
        dpFecha.setEditable(true);
        txtDescr.setEditable(true);
    }

    public void limpiarControles() {
        txtNumeroDoc.clear();
        txtDescr.clear();
        txtTotal.clear();
        dpFecha.setValue(null);
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
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
    
    
    
    public void agregarCompras() {
        switch (tipoOperaciones) {
            case NULL:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnAgregar.setText("Guardar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                btnReporte.setDisable(true);
                btnEditar.setDisable(true);
                btnInicio.setDisable(true);
                tipoOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardarCompras();
                cargarDatos();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/josefigueroa/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josefigueroa/images/eliminar.png"));  
                btnInicio.setDisable(false);
                txtNumeroDoc.setEditable(false);
                btnReporte.setDisable(false);
                btnEditar.setDisable(false);
                
                tipoOperaciones = operaciones.NULL;
                break;
        }
    }
    
    public void guardarCompras() {
        Compras registro = new Compras();
        
        registro.setDescripcion(txtDescr.getText());

        try{
            registro.setFechaDocumento(dpFecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            try {
                
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompras(?, ?)}");
                procedimiento.setString(1, registro.getFechaDocumento());
                procedimiento.setString(2, registro.getDescripcion());

                procedimiento.execute();
                listarCompras.add(registro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Fecha no valida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void seleccionarTupla() {        
        txtNumeroDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        dpFecha.setValue(LocalDate.parse(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescr.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotal.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
        
    }

    public void eliminarCompras() {
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {

                    int resp = JOptionPane.showConfirmDialog(null, "Confirmar eliminar el registro", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (resp == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listarCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una Compra para eliminar");
                }

                break;
        }
    }
    
    public void editar(){
        switch (tipoOperaciones) {
            case NULL:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    imgReporte.setImage(new Image("/org/josefigueroa/images/cancelar.png"));
                    imgEditar.setImage(new Image("/org/josefigueroa/images/guardar.png"));
                    btnReporte.setText("Cancelar");
                    btnEditar.setText("actualizar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnInicio.setDisable(true);
                    activarControles();
                    txtNumeroDoc.setEditable(false);
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
        Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem();
        registro.setDescripcion(txtDescr.getText());
        registro.setTotalDocumento(parseDouble(txtTotal.getText()));

        try{
            registro.setFechaDocumento(dpFecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompras(?,?,?)}");
                procedimiento.setInt(1, registro.getNumeroDocumento());
                procedimiento.setString(2, registro.getFechaDocumento());
                procedimiento.setString(3, registro.getDescripcion());


                procedimiento.execute();
                 listarCompras.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Fecha no valida", "Error", JOptionPane.ERROR_MESSAGE);
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
