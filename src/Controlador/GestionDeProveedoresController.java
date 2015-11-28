
package Controlador;

import DAO.ProveedorDao;
import DAO.TipoDao;
import Logica.Proveedores;
import Logica.Validaciones;
import Vista.Principal;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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


public class GestionDeProveedoresController implements Initializable {
    @FXML    private RadioButton rbBuscarProvPorRut;
    @FXML    private TextField txtBuscarProvPorRut;
    @FXML    private RadioButton rbBuscarProvPorNombre;
    @FXML    private TextField txtBuscarProvPorNombre;
    @FXML    private Button btnBuscarProv;
    @FXML    private TableView tblMostrarProveedores;
    @FXML    private TextField txtRutProveedor;
    @FXML    private TextField txtNombreProveedor;
    @FXML    private TextField txtDireccionProveedor;
    @FXML    private TextField txtCiudadProveedor;
    @FXML    private TextField txtTelefonoProveedor;
    @FXML    private TextField txtEmailProveedor;
    @FXML    private RadioButton rbEstadoActivoProv;
    @FXML    private RadioButton rbEstadoInactivoProv;
    @FXML    private Button btnNuevoProveedor;
    @FXML    private Button btnModificarProveedor;
    @FXML    private Button btnGuardarProveedor;
    @FXML    private Label lblMensajesProv;
    @FXML    private Label lblMensajeRutProv;
    @FXML    private Label lblMensajeNombreProv;
    @FXML    private Label lblMensajeDirProv;
    @FXML    private Label lblMensajeCiudadProv;
    @FXML    private Label lblMensajeEmailProv;
    @FXML    private Label lblMensajeEstadoProv;
    @FXML    private Label lblMensajeTelefProv;
    @FXML    private Label lblMensajeTipoProv;
    @FXML    private ComboBox cbTipos;
    @FXML    private TableColumn col;
    
    ObservableList<ObservableList> proveedor;
    
    private String codigoProveedor;
    
    private int estadoProveedor;
    
    private final Validaciones validacion = new Validaciones();
    //Objeto DAO
    private final ProveedorDao proveedorDao = new ProveedorDao();
    
    private final TipoDao tipoDao = new TipoDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // CARGA DE DATOS A LOS COMBOBOX DE LA GUI 
        ResultSet resultadoTipo = tipoDao.comboBoxTipo();
        
        try {
            // COMBOBOX DE TIPO
            while(resultadoTipo.next()) {
                cbTipos.getItems().add(resultadoTipo.getString("nombre"));
            }
             resultadoTipo.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestionDeProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    // METODO ENCARGADO DE LIMPIAR LA BUSQUEDA DE PROVEEDORES
     @FXML
    private void limpiarBusquedaProv(ActionEvent event) {
        
        tblMostrarProveedores.getColumns().clear();
        txtBuscarProvPorNombre.setText("");
        txtBuscarProvPorRut.setText("");
        btnBuscarProv.setDisable(true);
        btnNuevoProveedor.setDisable(true);
        txtBuscarProvPorNombre.setEditable(false);
        txtBuscarProvPorRut.setEditable(false);
        btnModificarProveedor.setDisable(false);
        rbBuscarProvPorNombre.setSelected(false);
        rbBuscarProvPorRut.setSelected(false);
        lblMensajesProv.setText("");
        limpiarCamposProv();
        limpiarMensajesProv();
    }
    
    // METODO ENCARGADO DE REALIZAR LA BUSQUEDA DEL PROVEEDOR SEGUN EL FILTRO INGRESADO
    @FXML
    private void buscarProv(ActionEvent event) throws SQLException {
               
        tblMostrarProveedores.getColumns().clear();
        lblMensajesProv.setText("");
        ResultSet resultadoProveedor;
        
        // Si se selecciona el filtro buscarPorCodigo
        if(rbBuscarProvPorRut.isSelected()){
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarProvPorRut.getText())) {
           lblMensajesProv.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtBuscarProvPorRut.getText())) {
           lblMensajesProv.setText("El filtro seleccionado solo recibe numeros");
           return;
        }
        
        //Se realiza la busqueda mediante el dao
        String busquedaPorRut = txtBuscarProvPorRut.getText();
        boolean existeProveedor = proveedorDao.buscarProveedorPorRut(busquedaPorRut).next();
        resultadoProveedor = proveedorDao.buscarProveedorPorRut(busquedaPorRut);
        //Si se encontro el proveedor cargar resultado de la busqueda a la tabla
            if(existeProveedor){
            cargarDatosTablaProveedores(resultadoProveedor, tblMostrarProveedores);
            btnNuevoProveedor.setDisable(true);
            }else{
            lblMensajesProv.setText("Proveedor no encontrado, opción nuevo proveedor habilitada para el registro");
            btnNuevoProveedor.setDisable(false);
            btnModificarProveedor.setDisable(true);
            }
        }
        
        // Si se selecciona el filtro buscarPorNombre
        if(rbBuscarProvPorNombre.isSelected()){
            
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarProvPorNombre.getText())) {
           lblMensajesProv.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtBuscarProvPorNombre.getText())) {
           lblMensajesProv.setText("El filtro seleccionado solo recibe letras");
           return;
        }
                
        //Se realiza la busqueda mediante el dao
        String busquedaPorNombre = txtBuscarProvPorNombre.getText();
        boolean existeProveedor = proveedorDao.buscarProveedorPorNombre(busquedaPorNombre).next();
        resultadoProveedor = proveedorDao.buscarProveedorPorNombre(busquedaPorNombre); 
        //Si se encontro el usuario cargar resultado de la busqueda a la tabla
            if(existeProveedor){
            cargarDatosTablaProveedores(resultadoProveedor, tblMostrarProveedores);
            btnNuevoProveedor.setDisable(true);
            }else{
            lblMensajesProv.setText("Proveedor no encontrado, opción nuevo proveedor habilitada para el registro");
            btnNuevoProveedor.setDisable(false);
            btnModificarProveedor.setDisable(true);
            }
        }
    }
    
    // METODO QUE HABILITA EL CAMPO BUSQUEDA POR RUT
    @FXML
    private void selecBuscarProvPorRut(ActionEvent event){
        txtBuscarProvPorNombre.setEditable(false);
        txtBuscarProvPorRut.setEditable(true);
        rbBuscarProvPorNombre.setSelected(false);
        btnBuscarProv.setDisable(false);
    }
    
    @FXML
    private void getProvSeleccionado(MouseEvent event) {
         tblMostrarProveedores.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tblMostrarProveedores != null) {
                                        
                    String valor = tblMostrarProveedores.getSelectionModel().getSelectedItems().get(0).toString();
                    
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
                        limpiarCamposProv();
                        cargarDatosProveedorAlFormulario(cincoDigitos);
                        codigoProveedor = cincoDigitos;
                    } else {
                        if (m4.find()) {
                            limpiarCamposProv();
                            cargarDatosProveedorAlFormulario(cuatroDigitos);
                            codigoProveedor = cuatroDigitos;
                        } else {
                            if (m3.find()) {
                                limpiarCamposProv();
                                cargarDatosProveedorAlFormulario(tresDigitos);
                                codigoProveedor = tresDigitos;
                            } else {
                                if (m2.find()) {
                                    limpiarCamposProv();
                                    cargarDatosProveedorAlFormulario(dosDigitos);
                                    codigoProveedor = dosDigitos;
                                } else {
                                    limpiarCamposProv();
                                    cargarDatosProveedorAlFormulario(unDigitos);
                                    codigoProveedor = unDigitos;
                                }
                             }
                        }
                    }
                }
            }
        });
        
        btnModificarProveedor.setDisable(false);
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA PROVEEDORES
    private void cargarDatosTablaProveedores(ResultSet resultadoProveedores, TableView tabla) {
        tabla.getColumns().clear();
        proveedor = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProveedores = resultadoProveedores;

        try {
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"CODIGO", "RUT", "NOMBRE", "TELEFONO", "EMAIL", "TIPO_DE_PRODUCTOS", "ESTADO"};

            //AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaProveedores.getMetaData().getColumnCount(); i++) {
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
            while (resultadoDatosTablaProveedores.next()) {
                //ITERACION DE FILA}
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaProveedores.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaProveedores.getString(i));
                }
                System.out.println("Row [i] added " + row);
                proveedor.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA JEFE DE SUCURSAL
            tabla.setItems(proveedor);
            resultadoDatosTablaProveedores.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    @FXML
    private void selecBuscarProvPorNombre(ActionEvent event) {
        txtBuscarProvPorNombre.setEditable(true);
        txtBuscarProvPorRut.setEditable(false);
        rbBuscarProvPorRut.setSelected(false);
        btnBuscarProv.setDisable(false);
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS AL FORMULARIO PROVEEDORES SEGUN EL ITEM SELECCIONADO
    private void cargarDatosProveedorAlFormulario(String dato){
        
        ResultSet cargaDatosParaFormularioProveedor = proveedorDao.datosParaFormulario(dato);
        
        try {
                                 
            while (cargaDatosParaFormularioProveedor.next()) {
                
                txtRutProveedor.setText(cargaDatosParaFormularioProveedor.getString("RUT").trim());
                txtNombreProveedor.setText(cargaDatosParaFormularioProveedor.getString("NOMBRE").trim());
                rbEstadoActivoProv.setDisable(false);
                rbEstadoInactivoProv.setDisable(false);
                txtDireccionProveedor.setText(cargaDatosParaFormularioProveedor.getString("DIRECCION").trim());
                txtCiudadProveedor.setText(cargaDatosParaFormularioProveedor.getString("CIUDAD").trim());
                txtTelefonoProveedor.setText(cargaDatosParaFormularioProveedor.getString("TELEFONO").trim());
                txtEmailProveedor.setText(cargaDatosParaFormularioProveedor.getString("E_MAIL").trim());
                cbTipos.setDisable(false);
                cbTipos.setValue(cargaDatosParaFormularioProveedor.getString("TIPO_DE_PRODUCTO"));
                estadoProveedor = cargaDatosParaFormularioProveedor.getInt("ESTADO");
                rbEstadoActivoProv.setDisable(false);
                rbEstadoInactivoProv.setDisable(false);
                if(estadoProveedor == 1){
                rbEstadoActivoProv.setSelected(true);
                }else{rbEstadoInactivoProv.setSelected(true);}
                
            }
            cargaDatosParaFormularioProveedor.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
        txtNombreProveedor.setEditable(true);
        txtRutProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtCiudadProveedor.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        btnModificarProveedor.setDisable(false);
      
    }

    @FXML
    private void selecProvActivo(ActionEvent event) {
        rbEstadoInactivoProv.setSelected(false);
    }

    @FXML
    private void selecProvInactivo(ActionEvent event) {
        rbEstadoActivoProv.setSelected(false);
    }

    @FXML
    private void nuevoProveedor(ActionEvent event) {
        limpiarCamposProv();
        txtNombreProveedor.setEditable(true);
        txtRutProveedor.setEditable(true);
        txtCiudadProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        rbEstadoActivoProv.setDisable(false);
        btnGuardarProveedor.setDisable(false);
        cbTipos.setDisable(false);
        btnNuevoProveedor.setDisable(true);
    }

    @FXML
    private void modificarProveedor(ActionEvent event) {
        
         limpiarMensajesProv();
         
        if (validacion.validarVacios(txtRutProveedor.getText())) {
           lblMensajeRutProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtRutProveedor.getText())) {
           lblMensajeRutProv.setText("este campo solo recibe numeros");
           return;
        }
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtNombreProveedor.getText())) {
           lblMensajeNombreProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreProveedor.getText())) {
           lblMensajeNombreProv.setText("este campo solo recibe letras");
           return;
           
        }
        
        if (validacion.validarVacios(txtDireccionProveedor.getText())) {
           lblMensajeDirProv.setText("campo obligatorio");
           return;
        }
        
        if (validacion.validarVacios(txtCiudadProveedor.getText())) {
           lblMensajeCiudadProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtCiudadProveedor.getText())) {
           lblMensajeCiudadProv.setText("este campo solo recibe letras");
           return;
        }
        
        if (validacion.validarVacios(txtTelefonoProveedor.getText())) {
           lblMensajeTelefProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtTelefonoProveedor.getText())) {
           lblMensajeTelefProv.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtEmailProveedor.getText())) {
           lblMensajeEmailProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtEmailProveedor.getText())) {
           lblMensajeEmailProv.setText("correo invalido");
           return;
        }
        
        //validar comboBox
        if(cbTipos.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeTipoProv.setText("debe seleccionar un tipo de producto");
           return;
        }
        
        //validar el radiobuton
        if(!(rbEstadoActivoProv.isSelected() || rbEstadoInactivoProv.isSelected())){
           lblMensajeEstadoProv.setText("debe seleccionar el estado");
           return;
        }
        
        String nombre = txtNombreProveedor.getText();
        String rut = txtRutProveedor.getText();
        String direccion = txtDireccionProveedor.getText();
        String ciudad = txtCiudadProveedor.getText();
        String telefono = txtTelefonoProveedor.getText();
        String e_mail = txtEmailProveedor.getText();
        String tipo = (String) cbTipos.getSelectionModel().getSelectedItem();
        
        if(rbEstadoActivoProv.isSelected()){
            estadoProveedor = 1;
        }
        
        if(rbEstadoInactivoProv.isSelected()){
            estadoProveedor = 2;
        }
        
        Proveedores modificarProveedor = new Proveedores();
        
        modificarProveedor.setEstado(estadoProveedor);
        modificarProveedor.setNombre(nombre);
        modificarProveedor.setRut(rut);
        modificarProveedor.setDireccion(direccion);
        modificarProveedor.setCiudad(ciudad);
        modificarProveedor.setTelefono(telefono);
        modificarProveedor.setTipo(tipo);
        modificarProveedor.seteMail(e_mail);
      
        PreparedStatement estado = proveedorDao.modificarProveedor(modificarProveedor, codigoProveedor);
        
            try {
                estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Usuario modificado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        limpiarCamposProv();
    }

    @FXML
    private void guardarProveedor(ActionEvent event) {
        limpiarMensajesProv();
        
        if (validacion.validarVacios(txtRutProveedor.getText())) {
           lblMensajeRutProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtRutProveedor.getText())) {
           lblMensajeRutProv.setText("este campo solo recibe numeros");
           return;
        }
        
       //Validacion de campos vacios
        if (validacion.validarVacios(txtNombreProveedor.getText())) {
           lblMensajeNombreProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreProveedor.getText())) {
           lblMensajeNombreProv.setText("este campo solo recibe letras");
           return;
           
        }
        
        if (validacion.validarVacios(txtDireccionProveedor.getText())) {
           lblMensajeDirProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtCiudadProveedor.getText())) {
           lblMensajeCiudadProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtCiudadProveedor.getText())) {
           lblMensajeCiudadProv.setText("este campo solo recibe letras");
           return;
        }
        
        if (validacion.validarVacios(txtTelefonoProveedor.getText())) {
           lblMensajeTelefProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtTelefonoProveedor.getText())) {
           lblMensajeTelefProv.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtEmailProveedor.getText())) {
           lblMensajeEmailProv.setText("campo obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtEmailProveedor.getText())) {
           lblMensajeEmailProv.setText("correo invalido");
           return;
        }
        
        //validar comboBox
        if(cbTipos.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeTipoProv.setText("debe seleccionar un tipo de producto");
           return;
        }
        
        //validar el radiobuton
        if(!(rbEstadoActivoProv.isSelected() || rbEstadoInactivoProv.isSelected())){
           lblMensajeEstadoProv.setText("debe seleccionar el estado");
           return;
        }
        
        String nombre = txtNombreProveedor.getText();
        String rut = txtRutProveedor.getText();
        String direccion = txtDireccionProveedor.getText();
        String ciudad = txtCiudadProveedor.getText();
        String telefono = txtTelefonoProveedor.getText();
        String e_mail = txtEmailProveedor.getText();
        String tipo = (String) cbTipos.getSelectionModel().getSelectedItem();
        estadoProveedor = 1;        
        
        
        Proveedores nuevoProveedor = new Proveedores();
        nuevoProveedor.setEstado(estadoProveedor);
        nuevoProveedor.setNombre(nombre);
        nuevoProveedor.setRut(rut);
        nuevoProveedor.setDireccion(direccion);
        nuevoProveedor.setCiudad(ciudad);
        nuevoProveedor.setTelefono(telefono);
        nuevoProveedor.setTipo(tipo);
        nuevoProveedor.seteMail(e_mail);
        
        PreparedStatement estado = proveedorDao.nuevoProveedor(nuevoProveedor);
        
            try {
                estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Proveedor registrado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
            
        limpiarCamposProv();
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
            primaryStage.setTitle("Menu Gerente");
            primaryStage.show();
                    
            } catch (IOException e) {}
    }
    
    private void limpiarCamposProv(){
        txtNombreProveedor.setText("");
        txtNombreProveedor.setEditable(false);
        txtRutProveedor.setText("");
        txtRutProveedor.setEditable(false);
        txtCiudadProveedor.setText("");
        txtCiudadProveedor.setEditable(false);
        txtDireccionProveedor.setText("");
        txtDireccionProveedor.setEditable(false);
        txtTelefonoProveedor.setText("");
        txtTelefonoProveedor.setEditable(false);
        txtEmailProveedor.setText("");
        txtEmailProveedor.setEditable(false);
        rbEstadoActivoProv.setSelected(false);
        rbEstadoInactivoProv.setSelected(false);
        rbEstadoActivoProv.setDisable(true);
        rbEstadoInactivoProv.setDisable(true);
        btnModificarProveedor.setDisable(true);
        btnGuardarProveedor.setDisable(true);
        cbTipos.setValue("Seleccionar");
        cbTipos.setDisable(true);
    }
    
    private void limpiarMensajesProv(){
        lblMensajeNombreProv.setText("");
        lblMensajeRutProv.setText("");
        lblMensajeCiudadProv.setText("");
        lblMensajeDirProv.setText("");
        lblMensajeTelefProv.setText("");
        lblMensajeEmailProv.setText("");
        lblMensajeEstadoProv.setText("");
        lblMensajeTipoProv.setText("");
    }
}
