package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GestionDeUsuariosController implements Initializable {
    @FXML
    private RadioButton rbBuscarSucPorNombre;
    @FXML
    private TextField txtBuscarSucPorNombre;
    @FXML
    private RadioButton rbBuscarSucPorCodigo;
    @FXML
    private TextField txtBuscarSucPorCodigo;
    @FXML
    private Button btnLimpiarBusquedaSuc;
    @FXML
    private Button btnBuscarSuc;
    @FXML
    private TableView<?> tblMostrarSucursales;
    @FXML
    private TextField txtCodigoSuc;
    @FXML
    private TextField txtNombreJefeSuc;
    @FXML
    private TextField txtApellidoJefeSuc;
    @FXML
    private TextField txtNombreSuc;
    @FXML
    private TextField txtCiudadSucursal;
    @FXML
    private TextField txtDireccionSuc;
    @FXML
    private TextField txtTelefonoSuc;
    @FXML
    private TextField txtEmailSuc;
    @FXML
    private RadioButton rbEstadoActivoSuc;
    @FXML
    private RadioButton rbEstadoInactivoSuc;
    @FXML
    private Button btnNuevoJefeSuc;
    @FXML
    private Button btnModificarJefeSuc;
    @FXML
    private Button btnGuardarJefeSuc;
    @FXML
    private Label lblMensajesSuc;
    @FXML
    private Label lblMensajeCodigoSuc;
    @FXML
    private Label lblMensajeNombreSuc;
    @FXML
    private Label lblMensajeApellidoSuc;
    @FXML
    private Label lblMensajeNomSucursal;
    @FXML
    private Label lblMensajeCiudadSuc;
    @FXML
    private Label lblMensajeDirecSuc;
    @FXML
    private Label lblMensajeTelefSuc;
    @FXML
    private Label lblMensajeEmailSuc;
    @FXML
    private Label lblMensajeEstadoSuc;
    @FXML
    private Label lblMensajeDireccSUc;
    @FXML
    private RadioButton rbBuscarEmpPorNombre;
    @FXML
    private TextField txtBuscarEmpPorNombre;
    @FXML
    private RadioButton rbBuscarEmpPorCodigo;
    @FXML
    private TextField txtBuscarEmpPorCodigo;
    @FXML
    private Button btnLimpiarBusquedaEmp;
    @FXML
    private Button btnBuscarEmp;
    @FXML
    private TableView<?> tblMostrarUsuarioEmp;
    @FXML
    private Label lblMensajeBd;
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtNombreEmpleado;
    @FXML
    private TextField txtApellidoEmpleado;
    @FXML
    private TextField txtCargoEmpleado;
    @FXML
    private TextField txtTelefonoEmpleado;
    @FXML
    private TextField txtEmailEmpleado;
    @FXML
    private RadioButton rbEstadoActivoEmpleado;
    @FXML
    private RadioButton rbEstadoInactivoEmpleado;
    @FXML
    private Button btnNuevoEmpleado;
    @FXML
    private Button btnModificarEmpleado;
    @FXML
    private Button btnGuardarEmpleado;
    @FXML
    private Label lblMensajeCodigoEmp;
    @FXML
    private Label lblMensajeNombreEmp;
    @FXML
    private Label lblMensajeApellidoEmp;
    @FXML
    private Label lblMensajeCargoEmp;
    @FXML
    private Label lblMensajeTelefonoEmp;
    @FXML
    private Label lblMensajeEmailEmp;
    @FXML
    private Label lblMensajeEstadoEmp;
    @FXML
    private Button btnMenuPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void limpiarBusquedaSuc(ActionEvent event) {
        txtBuscarSucPorNombre.setText("");
        txtBuscarSucPorCodigo.setText("");
        btnBuscarSuc.setDisable(true);
        txtBuscarSucPorNombre.setEditable(false);
        txtBuscarSucPorCodigo.setEditable(false);
        rbBuscarSucPorNombre.setSelected(false);
        rbBuscarSucPorCodigo.setSelected(false);
    }

    @FXML
    private void buscarSuc(ActionEvent event) {
    }

    @FXML
    private void getSucSeleccionada(MouseEvent event) {
    }

    @FXML
    private void nuevoJefeSuc(ActionEvent event) {
    }

    @FXML
    private void modificarJefeSuc(ActionEvent event) {
    }

    @FXML
    private void guardarJefeSuc(ActionEvent event) {
    }

    @FXML
    private void limpiarBusquedaEmpleado(ActionEvent event) {
        txtBuscarEmpPorNombre.setText("");
        txtBuscarEmpPorCodigo.setText("");
        btnBuscarEmp.setDisable(true);
        txtBuscarEmpPorNombre.setEditable(false);
        txtBuscarEmpPorCodigo.setEditable(false);
        rbBuscarEmpPorNombre.setSelected(false);
        rbBuscarEmpPorCodigo.setSelected(false);
    }

    @FXML
    private void buscarUsuarioEmpleado(ActionEvent event) {
    }

    @FXML
    private void getUsuarioEmpSeleccionado(MouseEvent event) {
    }

    @FXML
    private void nuevoEmpleado(ActionEvent event) {
    }

    @FXML
    private void modificarEmpleado(ActionEvent event) {
    }

    @FXML
    private void guardarEmpleado(ActionEvent event) {
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
    }
    
    //Acciones de los radio buton
    @FXML
    private void selecBuscarSucPorNombre(ActionEvent event) {
        txtBuscarSucPorNombre.setEditable(true);
        txtBuscarSucPorCodigo.setEditable(false);
        rbBuscarSucPorCodigo.setSelected(false);
        btnBuscarSuc.setDisable(false);
    }
    
    @FXML
    private void selecBuscarSucPorCodigo(ActionEvent event) {
        txtBuscarSucPorNombre.setEditable(false);
        txtBuscarSucPorCodigo.setEditable(true);
        rbBuscarSucPorNombre.setSelected(false);
        btnBuscarSuc.setDisable(false);
    }
    
    @FXML
    private void selecSucActivo(ActionEvent event) {
        rbEstadoInactivoSuc.setSelected(false);
    }
    
    @FXML
    private void selecSucInactivo(ActionEvent event) {
        rbEstadoActivoSuc.setSelected(false);
    }
    
    @FXML
    private void selecBuscarEmpPorNombre(ActionEvent event) {
        txtBuscarEmpPorNombre.setEditable(true);
        txtBuscarEmpPorCodigo.setEditable(false);
        rbBuscarEmpPorCodigo.setSelected(false);
        btnBuscarEmp.setDisable(false);
    }
    
    @FXML
    private void selecBuscarEmpPorCodigo(ActionEvent event) {
        txtBuscarEmpPorNombre.setEditable(false);
        txtBuscarEmpPorCodigo.setEditable(true);
        rbBuscarEmpPorNombre.setSelected(false);
        btnBuscarEmp.setDisable(false);
    }
    
    @FXML
    private void selecEmpActivo(ActionEvent event) {
        rbEstadoInactivoEmpleado.setSelected(false);
    }
    
    @FXML
    private void selecEmpInactivo(ActionEvent event) {
        rbEstadoActivoEmpleado.setSelected(false);
    }
}
