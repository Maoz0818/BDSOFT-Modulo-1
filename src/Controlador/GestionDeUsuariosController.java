package Controlador;

import DAO.JefeSucursalDao;
import DAO.SucursalDao;
import DAO.UsuarioDao;
import Logica.Sucursal;
import Logica.Validaciones;
import Logica.Usuario;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class GestionDeUsuariosController implements Initializable {
    @FXML    private RadioButton rbBuscarSucPorNombre;
    @FXML    private TextField txtBuscarSucPorNombre;
    @FXML    private RadioButton rbBuscarSucPorCodigo;
    @FXML    private TextField txtBuscarSucPorCodigo;
    @FXML    private Button btnLimpiarBusquedaSuc;
    @FXML    private Button btnBuscarSuc;
    @FXML    private TableView tblMostrarSucursales;
    @FXML    private TextField txtCodigoSuc;
    @FXML    private TextField txtNombreJefeSuc;
    @FXML    private TextField txtApellidoJefeSuc;
    @FXML    private TextField txtNombreSuc;
    @FXML    private TextField txtCiudadSucursal;
    @FXML    private TextField txtDireccionSuc;
    @FXML    private TextField txtTelefonoSuc;
    @FXML    private TextField txtEmailSuc;
    @FXML    private RadioButton rbEstadoActivoSuc;
    @FXML    private RadioButton rbEstadoInactivoSuc;
    @FXML    private Button btnNuevoJefeSuc;
    @FXML    private Button btnModificarJefeSuc;
    @FXML    private Button btnGuardarJefeSuc;
    @FXML    private Label lblMensajesSuc;
    @FXML    private Label lblMensajeNombreSuc;
    @FXML    private Label lblMensajeApellidoSuc;
    @FXML    private Label lblMensajeNomSucursal;
    @FXML    private Label lblMensajeCiudadSuc;
    @FXML    private Label lblMensajeDirecSuc;
    @FXML    private Label lblMensajeTelefSuc;
    @FXML    private Label lblMensajeEmailSuc;
    @FXML    private Label lblMensajeEstadoSuc;
    @FXML    private RadioButton rbBuscarEmpPorNombre;
    @FXML    private TextField txtBuscarEmpPorNombre;
    @FXML    private RadioButton rbBuscarEmpPorCodigo;
    @FXML    private TextField txtBuscarEmpPorCodigo;
    @FXML    private Button btnLimpiarBusquedaEmp;
    @FXML    private Button btnBuscarEmp;
    @FXML    private TableView tblMostrarUsuarioEmp;
    @FXML    private Label lblMensajeBd;
    @FXML    private TextField txtCodigoEmpleado;
    @FXML    private TextField txtNombreEmpleado;
    @FXML    private TextField txtApellidoEmpleado;
    @FXML    private ComboBox cbCargo;
    @FXML    private TextField txtTelefonoEmpleado;
    @FXML    private TextField txtEmailEmpleado;
    @FXML    private RadioButton rbEstadoActivoEmpleado;
    @FXML    private RadioButton rbEstadoInactivoEmpleado;
    @FXML    private Button btnNuevoEmpleado;
    @FXML    private Button btnModificarEmpleado;
    @FXML    private Button btnGuardarEmpleado;
    @FXML    private Label lblMensajeNombreEmp;
    @FXML    private Label lblMensajeApellidoEmp;
    @FXML    private Label lblMensajeCargoEmp;
    @FXML    private Label lblMensajeTelefonoEmp;
    @FXML    private Label lblMensajeEmailEmp;
    @FXML    private Label lblMensajeEstadoEmp;
    @FXML    private Button btnMenuPrincipal;
    @FXML    private TableColumn col;
    
    private int jefeId;
    
    private int estadoUsuario;
    
    private String codigoUsuario;
    
    private final Validaciones validacion = new Validaciones();
    //objetos clave
    private final Usuario claveGenerada = new Usuario();
    //Objeto DAO
    private final UsuarioDao usuarioDao = new UsuarioDao();
    //Objeto DAO
    private final SucursalDao sucursalDao = new SucursalDao();
    //Objeto DAO
    private final JefeSucursalDao jefeSucursalDao = new JefeSucursalDao();
    
    ObservableList<ObservableList> usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se llenan los items del combobox 
        cbCargo.getItems().add("Gerente");
        cbCargo.getItems().add("Jefe de Bodega");
    }    

    @FXML
    private void limpiarBusquedaSuc(ActionEvent event) {
        tblMostrarSucursales.getColumns().clear();
        txtBuscarSucPorNombre.setText("");
        txtBuscarSucPorCodigo.setText("");
        btnBuscarSuc.setDisable(true);
        btnNuevoJefeSuc.setDisable(true);
        txtBuscarSucPorNombre.setEditable(false);
        txtBuscarSucPorCodigo.setEditable(false);
        btnModificarJefeSuc.setDisable(false);
        rbBuscarSucPorNombre.setSelected(false);
        rbBuscarSucPorCodigo.setSelected(false);
        lblMensajesSuc.setText("");
        
    }

    @FXML
    private void buscarSuc(ActionEvent event) throws SQLException {
        
        tblMostrarSucursales.getColumns().clear();
        lblMensajesSuc.setText("");
        ResultSet resultadoUsuario;
        
        // Si se selecciona el filtro buscarPorNombre
        if(rbBuscarSucPorNombre.isSelected()){
            
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarSucPorNombre.getText())) {
           lblMensajesSuc.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtBuscarSucPorNombre.getText())) {
           lblMensajesSuc.setText("El filtro seleccionado solo recibe letras");
           return;
        }
                
        //Se realiza la busqueda mediante el dao
        String busquedaPorNombre = txtBuscarSucPorNombre.getText();
        boolean existeUsuario = usuarioDao.buscarUsuarioPorNombre(busquedaPorNombre, "Sucursal").next();
        resultadoUsuario = usuarioDao.buscarUsuarioPorNombre(busquedaPorNombre, "Sucursal");
        
        //Si se encontro el usuario cargar resultado de la busqueda a la tabla
            if(existeUsuario){
            cargarDatosTablaUsuarios(resultadoUsuario, tblMostrarSucursales);
            btnNuevoJefeSuc.setDisable(true);
            }else{
            lblMensajesSuc.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoJefeSuc.setDisable(false);
            btnModificarJefeSuc.setDisable(true);
            }
        }
        
        // Si se selecciona el filtro buscarPorCodigo
        if(rbBuscarSucPorCodigo.isSelected()){
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarSucPorCodigo.getText())) {
           lblMensajesSuc.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtBuscarSucPorCodigo.getText())) {
           lblMensajesSuc.setText("El filtro seleccionado solo recibe numeros");
           return;
        }
        
        //Se realiza la busqueda mediante el dao
        String busquedaPorCodigo = txtBuscarSucPorCodigo.getText();
        boolean existeUsuario = usuarioDao.buscarUsuarioPorCodigo(busquedaPorCodigo, "Sucursal").next();
        resultadoUsuario = usuarioDao.buscarUsuarioPorCodigo(busquedaPorCodigo, "Sucursal");
        //Si se encontro el usuario cargar resultado de la busqueda a la tabla
            if(existeUsuario){
            cargarDatosTablaUsuarios(resultadoUsuario, tblMostrarSucursales);
            btnNuevoJefeSuc.setDisable(true);
            }else{
            lblMensajesSuc.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoJefeSuc.setDisable(false);
            btnModificarJefeSuc.setDisable(true);
            }
        }
        
    }

    @FXML
    private void getSucSeleccionada(MouseEvent event) {
        
        tblMostrarSucursales.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tblMostrarSucursales != null) {
                                        
                    String valor = tblMostrarSucursales.getSelectionModel().getSelectedItems().get(0).toString();
                    
                    String cincoDigitos = valor.substring(1, 6);
                    String cuatroDigitos = valor.substring(1, 5);
                    String tresDigitos = valor.substring(1, 4);
                    String dosDigitos = valor.substring(1, 3);
                    String unDigitos = valor.substring(1, 2);
                    
                    Pattern p = Pattern.compile("^[0-9]*$");
                    
                    Matcher m5 = p.matcher(cincoDigitos);
                    Matcher m4 = p.matcher(cuatroDigitos);
                    Matcher m3 = p.matcher(tresDigitos);
                    Matcher m2 = p.matcher(dosDigitos);
                    
                    if (m5.find()) {
                        cargarDatosSucursalAlFormulario(cincoDigitos);
                        codigoUsuario = cincoDigitos;
                    } else {
                        if (m4.find()) {
                            cargarDatosSucursalAlFormulario(cuatroDigitos);
                            codigoUsuario = cuatroDigitos;
                        } else {
                            if (m3.find()) {
                                cargarDatosSucursalAlFormulario(tresDigitos);
                                codigoUsuario = tresDigitos;
                            } else {
                                if (m2.find()) {
                                    cargarDatosSucursalAlFormulario(dosDigitos);
                                    codigoUsuario = dosDigitos;
                                } else {
                                    cargarDatosSucursalAlFormulario(unDigitos);
                                    codigoUsuario = unDigitos;
                                }
                             }
                        }
                    }
                }
            }
        });
        
        btnModificarJefeSuc.setDisable(false);
    }
    
    private void cargarDatosSucursalAlFormulario(String dato){
        
        ResultSet cargaDatosParaFormulario = usuarioDao.datosParaFormulario(dato);
        
        try {
                                 
            while (cargaDatosParaFormulario.next()) {
                
                txtNombreJefeSuc.setText(cargaDatosParaFormulario.getString("NOMBRE_USUARIO").trim());
                txtApellidoJefeSuc.setText(cargaDatosParaFormulario.getString("APELLIDOS").trim());
                txtNombreSuc.setText(cargaDatosParaFormulario.getString("NOMBRE_SUCURSAL").trim());
                txtCiudadSucursal.setText(cargaDatosParaFormulario.getString("CIUDAD").trim());
                txtDireccionSuc.setText(cargaDatosParaFormulario.getString("DIRECCION").trim());
                txtTelefonoSuc.setText(cargaDatosParaFormulario.getString("TELEFONO").trim());
                txtEmailSuc.setText(cargaDatosParaFormulario.getString("E_MAIL").trim());
                int estadoJefeSuc = cargaDatosParaFormulario.getInt("ESTADOID");
                rbEstadoActivoSuc.setDisable(false);
                rbEstadoInactivoSuc.setDisable(false);
                if(estadoJefeSuc == 1){
                rbEstadoActivoSuc.setSelected(true);
                }else{rbEstadoInactivoSuc.setSelected(true);}
                
            }
            cargaDatosParaFormulario.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
        txtNombreJefeSuc.setEditable(true);
        txtApellidoJefeSuc.setEditable(true);
        txtNombreSuc.setEditable(true);
        txtCiudadSucursal.setEditable(true);
        txtDireccionSuc.setEditable(true);
        txtTelefonoSuc.setEditable(true);
        txtEmailSuc.setEditable(true);
        btnModificarJefeSuc.setDisable(false);
    }
        
    @FXML
    private void nuevoJefeSuc(ActionEvent event) {
        txtNombreJefeSuc.setEditable(true);
        txtApellidoJefeSuc.setEditable(true);
        txtNombreSuc.setEditable(true);
        txtCiudadSucursal.setEditable(true);
        txtDireccionSuc.setEditable(true);
        txtTelefonoSuc.setEditable(true);
        txtEmailSuc.setEditable(true);
        rbEstadoActivoSuc.setDisable(false);
        btnGuardarJefeSuc.setDisable(false);
    }

    @FXML
    private void modificarJefeSuc(ActionEvent event) throws SQLException {
        
         limpiarMensajesSuc();
        
       //Validacion de campos vacios
        if (validacion.validarVacios(txtNombreJefeSuc.getText())) {
           lblMensajeNombreSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreJefeSuc.getText())) {
           lblMensajeNombreSuc.setText("este campo solo recibe letras");
           return;
           
        }
        
        if (validacion.validarVacios(txtApellidoJefeSuc.getText())) {
           lblMensajeApellidoSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtApellidoJefeSuc.getText())) {
           lblMensajeApellidoSuc.setText("este campo solo recibe letras");
           return;
           
        }
         
        if (validacion.validarVacios(txtNombreSuc.getText())) {
           lblMensajeNomSucursal.setText("campo obligatorio");
           return;
        }
        
         
        if (validacion.validarVacios(txtCiudadSucursal.getText())) {
           lblMensajeCiudadSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtCiudadSucursal.getText())) {
           lblMensajeCiudadSuc.setText("este campo solo recibe letras");
           return;
        }
        
        if (validacion.validarVacios(txtDireccionSuc.getText())) {
           lblMensajeDirecSuc.setText("campo obligatorio");
           return;
        } 
        
        if (validacion.validarVacios(txtTelefonoSuc.getText())) {
           lblMensajeTelefSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtTelefonoSuc.getText())) {
           lblMensajeTelefSuc.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtEmailSuc.getText())) {
           lblMensajeEmailSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtEmailSuc.getText())) {
           lblMensajeEmailSuc.setText("correo invalido");
           return;
        }
        
        String nombres = txtNombreJefeSuc.getText();
        String apellidos = txtApellidoJefeSuc.getText();
        String nombreSucursal = txtNombreSuc.getText();
        String ciudadSucursal = txtCiudadSucursal.getText();
        String direccionSucursal = txtDireccionSuc.getText();
        int telefono = Integer.parseInt(txtTelefonoSuc.getText());
        String e_mail = txtEmailSuc.getText();
        
        if(rbEstadoActivoSuc.isSelected()){
            estadoUsuario = 1;
        }
        
        if(rbEstadoInactivoSuc.isSelected()){
            estadoUsuario = 2;
        }
        
        Usuario modificarUsuario = new Usuario();
        modificarUsuario.setEstado(estadoUsuario);
        modificarUsuario.setNombres(nombres);
        modificarUsuario.setApellidos(apellidos);
        modificarUsuario.setTelefono(telefono);
        modificarUsuario.setE_mail(e_mail);
        modificarUsuario.setCodigoUsuario(Integer.parseInt(codigoUsuario));
      
        PreparedStatement estado = usuarioDao.modificarUsuario(modificarUsuario);
        
            try {
                int n = estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Usuario modificado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        //Consulta para obtener el jefeid
        ResultSet resultadoJefeId = jefeSucursalDao.consultarJefeId(codigoUsuario);
        
        //While para recorrer el ResultSet y obtener el jefeid del Jefe_Sucursal
        while(resultadoJefeId.next()){
            jefeId = resultadoJefeId.getInt("jefeid");
        }
        
        Sucursal modificarSucursal = new Sucursal();
        
        modificarSucursal.setCodigoJefe(jefeId);
        modificarSucursal.setNombre(nombreSucursal);
        modificarSucursal.setCiudad(ciudadSucursal);
        modificarSucursal.setDireccion(direccionSucursal);
        
        PreparedStatement estado2 = sucursalDao.modificarSucursal(modificarSucursal);
        
        try {
                int n = estado2.executeUpdate();
                estado2.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        limpiarCamposSuc();
        
    }

    @FXML
    private void guardarJefeSuc(ActionEvent event) throws SQLException {
        
        limpiarMensajesSuc();
        
       //Validacion de campos vacios
        if (validacion.validarVacios(txtNombreJefeSuc.getText())) {
           lblMensajeNombreSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreJefeSuc.getText())) {
           lblMensajeNombreSuc.setText("este campo solo recibe letras");
           return;
           
        }
        
        if (validacion.validarVacios(txtApellidoJefeSuc.getText())) {
           lblMensajeApellidoSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtApellidoJefeSuc.getText())) {
           lblMensajeApellidoSuc.setText("este campo solo recibe letras");
           return;
           
        }
         
        if (validacion.validarVacios(txtNombreSuc.getText())) {
           lblMensajeNomSucursal.setText("campo obligatorio");
           return;
        }
        
         
        if (validacion.validarVacios(txtCiudadSucursal.getText())) {
           lblMensajeCiudadSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtCiudadSucursal.getText())) {
           lblMensajeCiudadSuc.setText("este campo solo recibe letras");
           return;
        }
        
        if (validacion.validarVacios(txtDireccionSuc.getText())) {
           lblMensajeDirecSuc.setText("campo obligatorio");
           return;
        } 
        
        if (validacion.validarVacios(txtTelefonoSuc.getText())) {
           lblMensajeTelefSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtTelefonoSuc.getText())) {
           lblMensajeTelefSuc.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtEmailSuc.getText())) {
           lblMensajeEmailSuc.setText("campo obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtEmailSuc.getText())) {
           lblMensajeEmailSuc.setText("correo invalido");
           return;
        }
        
        //validar el radiobuton
        if(!rbEstadoActivoSuc.isSelected()){
           lblMensajeEstadoSuc.setText("debe seleccionar el estado");
           return;
        }
        
        String nombres = txtNombreJefeSuc.getText();
        String apellidos = txtApellidoJefeSuc.getText();
        String nombreSucursal = txtNombreSuc.getText();
        String ciudadSucursal = txtCiudadSucursal.getText();
        String direccionSucursal = txtDireccionSuc.getText();
        int telefono = Integer.parseInt(txtTelefonoSuc.getText());
        String e_mail = txtEmailSuc.getText();
        
        estadoUsuario = 1;
        
        String clave = claveGenerada.GenerarClave();
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEstado(estadoUsuario);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setCargo("Jefe de Sucursal");
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setE_mail(e_mail);
        nuevoUsuario.setClave(clave);
        
        PreparedStatement estado = usuarioDao.nuevoUsuario(nuevoUsuario);
        
            try {
                int n = estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Usuario registrado exitosamente\nClave asignada: " + clave);
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        Sucursal nuevaSucursal = new Sucursal();
        
        //Consulta del id del jefe ingresado
        ResultSet resultadoUltimoId = sucursalDao.ultimoId();
        
        //While para recorrer el ResultSet y obtener el ultimoid
        while(resultadoUltimoId.next()){
            jefeId = resultadoUltimoId.getInt("jefeid");
        }
        
        nuevaSucursal.setCodigoJefe(jefeId);
        nuevaSucursal.setNombre(nombreSucursal);
        nuevaSucursal.setCiudad(ciudadSucursal);
        nuevaSucursal.setDireccion(direccionSucursal);
        
        PreparedStatement estado2 = sucursalDao.nuevaSucursal(nuevaSucursal);
        
        try {
                int n = estado2.executeUpdate();
                estado2.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        limpiarCamposSuc();
    }

    @FXML
    private void limpiarBusquedaEmpleado(ActionEvent event) {
        tblMostrarUsuarioEmp.getColumns().clear();
        txtBuscarEmpPorNombre.setText("");
        txtBuscarEmpPorCodigo.setText("");
        btnBuscarEmp.setDisable(true);
        btnNuevoEmpleado.setDisable(true);
        btnModificarEmpleado.setDisable(true);
        txtBuscarEmpPorNombre.setEditable(false);
        txtBuscarEmpPorCodigo.setEditable(false);
        rbBuscarEmpPorNombre.setSelected(false);
        rbBuscarEmpPorCodigo.setSelected(false);
        lblMensajeBd.setText("");
    }

    @FXML
    private void buscarUsuarioEmpleado(ActionEvent event) throws SQLException {
        
        tblMostrarUsuarioEmp.getColumns().clear();
        lblMensajeBd.setText("");
        ResultSet resultadoUsuario;
        
        // Si se selecciona el filtro buscarPorNombre
        if(rbBuscarEmpPorNombre.isSelected()){
            
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarEmpPorNombre.getText())) {
           lblMensajeBd.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtBuscarEmpPorNombre.getText())) {
           lblMensajeBd.setText("El filtro seleccionado solo recibe letras");
           return;
        }
                
        //Se realiza la busqueda mediante el dao
        String busquedaPorNombre = txtBuscarEmpPorNombre.getText();
        boolean existeUsuario = usuarioDao.buscarUsuarioPorNombre(busquedaPorNombre, "Bodega").next();
        resultadoUsuario = usuarioDao.buscarUsuarioPorNombre(busquedaPorNombre, "Bodega");
        //Si se encontro el usuario cargar resultado de la busqueda a la tabla
            if(existeUsuario){
            cargarDatosTablaUsuarios(resultadoUsuario, tblMostrarUsuarioEmp);
            btnNuevoEmpleado.setDisable(true);
            btnModificarEmpleado.setDisable(false);
            }else{
            lblMensajeBd.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoEmpleado.setDisable(false);
            btnModificarEmpleado.setDisable(true);
            }
        
        }
        
        // Si se selecciona el filtro buscarPorCodigo
        if(rbBuscarEmpPorCodigo.isSelected()){
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarEmpPorCodigo.getText())) {
           lblMensajeBd.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtBuscarEmpPorCodigo.getText())) {
           lblMensajeBd.setText("El filtro seleccionado solo recibe numeros");
           return;
        }
        
        //Se realiza la busqueda mediante el dao
        String busquedaPorCodigo = txtBuscarEmpPorCodigo.getText();
        boolean existeUsuario = usuarioDao.buscarUsuarioPorCodigo(busquedaPorCodigo, "Bodega").next();
        resultadoUsuario = usuarioDao.buscarUsuarioPorCodigo(busquedaPorCodigo, "Bodega");
        //Si se encontro el usuario cargar resultado de la busqueda a la tabla
            if(existeUsuario){
            cargarDatosTablaUsuarios(resultadoUsuario, tblMostrarUsuarioEmp);
            btnNuevoEmpleado.setDisable(true);
            btnModificarEmpleado.setDisable(false);
            }else{
            lblMensajeBd.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoEmpleado.setDisable(false);
            btnModificarEmpleado.setDisable(true);
            }
        }
        
    }

    @FXML
    private void getUsuarioEmpSeleccionado(MouseEvent event) {
    }

    @FXML
    private void nuevoEmpleado(ActionEvent event) {
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        cbCargo.setDisable(false);
        txtTelefonoEmpleado.setEditable(true);
        txtEmailEmpleado.setEditable(true);
        rbEstadoActivoEmpleado.setDisable(false);
        btnGuardarEmpleado.setDisable(false);
    }

    @FXML
    private void modificarEmpleado(ActionEvent event) {
        
        
    }

    @FXML
    private void guardarEmpleado(ActionEvent event) {
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        
        try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("MenuGerente.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
                    
            } catch (IOException e) {}
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
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA JEFE DE SUCURSAL
    private void cargarDatosTablaUsuarios(ResultSet resultadoUsuario, TableView tabla) {
        tblMostrarSucursales.getColumns().clear();
        usuario = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaUsuarios = resultadoUsuario;

        try {
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"CODIGO","NOMBRES", "APELLIDOS", "CARGO", "EMAIL", "ESTADO"};

            //AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaUsuarios.getMetaData().getColumnCount(); i++) {
                final int j = i;
                col = new TableColumn(titulos[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                        return new SimpleStringProperty((String) parametro.getValue().get(j));
                    }
                });
                tabla.getColumns().addAll(col);
                // ASIGNAMOS UN TAMAÑO A LAS COLUMNAS
                col.setMinWidth(140);
                System.out.println("Column [" + i + "] ");
                // CENTRAMOS LOS DATOS EN LA TABLA
                col.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                    @Override
                    public TableCell<String, String> call(TableColumn<String, String> p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object t, boolean bln) {
                                if (t != null) {
                                    super.updateItem(t, bln);
                                    System.out.println(t);
                                    setText(t.toString());
                                    setAlignment(Pos.CENTER_LEFT);
                                }
                            }
                        };
                        return cell;
                    }
                });
            }

            //CARGAMOS DE LA BASE DE DATOS
            while (resultadoDatosTablaUsuarios.next()) {
                //ITERACION DE FILA}
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaUsuarios.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaUsuarios.getString(i));
                }
                System.out.println("Row [i] added " + row);
                usuario.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA JEFE DE SUCURSAL
            tabla.setItems(usuario);
            resultadoDatosTablaUsuarios.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
    
    private void limpiarCamposSuc(){
        txtNombreJefeSuc.setText("");
        txtNombreJefeSuc.setEditable(false);
        txtApellidoJefeSuc.setText("");
        txtApellidoJefeSuc.setEditable(false);
        txtNombreSuc.setText("");
        txtNombreSuc.setEditable(false);
        txtCiudadSucursal.setText("");
        txtCiudadSucursal.setEditable(false);
        txtDireccionSuc.setText("");
        txtDireccionSuc.setEditable(false);
        txtTelefonoSuc.setText("");
        txtTelefonoSuc.setEditable(false);
        txtEmailSuc.setText("");
        txtEmailSuc.setEditable(false);
        rbEstadoActivoSuc.setSelected(false);
        rbEstadoInactivoSuc.setSelected(false);
        rbEstadoActivoSuc.setDisable(true);
        rbEstadoInactivoSuc.setDisable(true);
        btnModificarJefeSuc.setDisable(true);
        btnGuardarJefeSuc.setDisable(true);
    }
    
    private void limpiarMensajesSuc(){
        lblMensajeNomSucursal.setText("");
        lblMensajeApellidoSuc.setText("");
        lblMensajeNombreSuc.setText("");
        lblMensajeCiudadSuc.setText("");
        lblMensajeDirecSuc.setText("");
        lblMensajeTelefSuc.setText("");
        lblMensajeEmailSuc.setText("");
        lblMensajeEstadoSuc.setText("");
    }
    
    private void limpiarCamposEmp(){
        txtNombreEmpleado.setText("");
        txtApellidoEmpleado.setText("");
        cbCargo.setPromptText("Seleccionar");
        txtTelefonoEmpleado.setText("");
        txtEmailEmpleado.setText("");
        rbEstadoActivoEmpleado.setSelected(false);

    }
    
    private void limpiarMensajesEmp(){
        lblMensajeNombreEmp.setText("");
        lblMensajeApellidoEmp.setText("");
        lblMensajeCargoEmp.setText("");
        lblMensajeTelefonoEmp.setText("");
        lblMensajeEmailEmp.setText("");
        lblMensajeEstadoEmp.setText("");
    }
    
}
