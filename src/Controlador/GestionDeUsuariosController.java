package Controlador;

import DAO.UsuarioDao;
import Logica.Validaciones;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    @FXML    private Label lblMensajeCodigoSuc;
    @FXML    private Label lblMensajeNombreSuc;
    @FXML    private Label lblMensajeApellidoSuc;
    @FXML    private Label lblMensajeNomSucursal;
    @FXML    private Label lblMensajeCiudadSuc;
    @FXML    private Label lblMensajeDirecSuc;
    @FXML    private Label lblMensajeTelefSuc;
    @FXML    private Label lblMensajeEmailSuc;
    @FXML    private Label lblMensajeEstadoSuc;
    @FXML    private Label lblMensajeDireccSUc;
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
    @FXML    private Label lblMensajeCodigoEmp;
    @FXML    private Label lblMensajeNombreEmp;
    @FXML    private Label lblMensajeApellidoEmp;
    @FXML    private Label lblMensajeCargoEmp;
    @FXML    private Label lblMensajeTelefonoEmp;
    @FXML    private Label lblMensajeEmailEmp;
    @FXML    private Label lblMensajeEstadoEmp;
    @FXML    private Button btnMenuPrincipal;
    @FXML    private TableColumn col;
    
    private final Validaciones validacion = new Validaciones();
    
    //Objeto DAO
    private final UsuarioDao usuarioDao = new UsuarioDao();
    
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
            }
        }
        
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
        tblMostrarUsuarioEmp.getColumns().clear();
        txtBuscarEmpPorNombre.setText("");
        txtBuscarEmpPorCodigo.setText("");
        btnBuscarEmp.setDisable(true);
        btnNuevoEmpleado.setDisable(true);
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
            }else{
            lblMensajeBd.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoEmpleado.setDisable(false);
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
            }else{
            lblMensajeBd.setText("Usuario no encontrado, opción nuevo usuario habilitada para el registro");
            btnNuevoEmpleado.setDisable(false);
            }
        }
        
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
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA JEFE DE SUCURSAL
    public void cargarDatosTablaUsuarios(ResultSet resultadoUsuario, TableView tabla) {
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
    
}
