
package Controlador;

import DAO.ExistenciaDao;
import DAO.ProductoDao;
import DAO.ProveedorDao;
import DAO.TipoDao;
import Logica.Existencias;
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


public class GestionDeProductosController implements Initializable {
    @FXML    private RadioButton rbBuscarProdPorCodigo;
    @FXML    private TextField txtBuscarProdPorCodigo;
    @FXML    private RadioButton rbBuscarProdPorNombre;
    @FXML    private TextField txtBuscarProdPorNombre;
    @FXML    private Button btnBuscarProducto;
    @FXML    private TableView tblMostrarProductos;
    @FXML    private TextField txtNombreProducto;
    @FXML    private TextField txtMarcaProducto;
    @FXML    private TextField txtPrecioProducto;
    @FXML    private Button btnNuevoProducto;
    @FXML    private Button btnModificarProducto;
    @FXML    private Button btnGuardarProducto;
    @FXML    private Label lblMensajesProdu;
    @FXML    private Label lblMensajeNomProdu;
    @FXML    private Label lblMensajeTipoProdu;
    @FXML    private Label lblMensajeSeccProdu;
    @FXML    private Label lblMensajeProveProdu;
    @FXML    private Label lblMensajePrecioProdu;
    @FXML    private Label lblMensajeMarcaProdu;
    @FXML    private Label lblMensajeCantProdu;
    @FXML    private ComboBox cbTipo;
    @FXML    private ComboBox cbSeccion;
    @FXML    private ComboBox cbProveedor;
    @FXML    private TextField txtCantidadProducto;
    @FXML    private TableColumn col;
    
    ObservableList<ObservableList> productos;
    
    private final Validaciones validacion = new Validaciones();
    
    //Objeto DAO
    private final ProductoDao productoDao = new ProductoDao();
    //Objeto DAO
    private final ExistenciaDao existenciaDao = new ExistenciaDao();
    
    private final TipoDao tipoDao = new TipoDao();
            
    private final ProveedorDao proveedorDao = new ProveedorDao();
    
    private String codigoProducto;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // CARGA DE DATOS A LOS COMBOBOX DE LA GUI 
        ResultSet resultadoTipo = tipoDao.comboBoxTipo();
        ResultSet resultadoUbicacion = existenciaDao.comboBoxUbicacion();
        ResultSet resultadoProveedor = proveedorDao.comboBoxProveedor();
        
        try {
            // COMBOBOX DE TIPO
            while(resultadoTipo.next()) {
                cbTipo.getItems().add(resultadoTipo.getString("nombre"));
            }
            
            // COMBOBOX DE UBICACION
             while(resultadoUbicacion.next()) {
                cbSeccion.getItems().add(resultadoUbicacion.getString("seccion"));
            }
             
            // COMBOBOX DE PROVEEDOR
            while(resultadoProveedor.next()) {
                cbProveedor.getItems().add(resultadoProveedor.getString("nombre"));
            }
             resultadoTipo.close();
             resultadoUbicacion.close();
             resultadoProveedor.close();

        } catch (SQLException ex) {
            Logger.getLogger(GestionDeProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    

    @FXML
    private void selecBuscarProdPorCodigo(ActionEvent event) {
        txtBuscarProdPorNombre.setEditable(false);
        txtBuscarProdPorCodigo.setEditable(true);
        rbBuscarProdPorNombre.setSelected(false);
        btnBuscarProducto.setDisable(false);
    }

    @FXML
    private void selecBuscarProdPorNombre(ActionEvent event) {
        txtBuscarProdPorNombre.setEditable(true);
        txtBuscarProdPorCodigo.setEditable(false);
        rbBuscarProdPorCodigo.setSelected(false);
        btnBuscarProducto.setDisable(false);
    }

    @FXML
    private void limpiarBusquedaProducto(ActionEvent event) {
        tblMostrarProductos.getColumns().clear();
        txtBuscarProdPorNombre.setText("");
        txtBuscarProdPorCodigo.setText("");
        btnBuscarProducto.setDisable(true);
        btnNuevoProducto.setDisable(true);
        txtBuscarProdPorNombre.setEditable(false);
        txtBuscarProdPorCodigo.setEditable(false);
        btnModificarProducto.setDisable(false);
        rbBuscarProdPorNombre.setSelected(false);
        rbBuscarProdPorCodigo.setSelected(false);
        lblMensajesProdu.setText("");
        limpiarCamposProd();
        limpiarMensajesProd();
    }

    @FXML
    private void buscarProducto(ActionEvent event) throws SQLException {
                     
        tblMostrarProductos.getColumns().clear();
        lblMensajesProdu.setText("");
        ResultSet resultadoProducto;
        
        // Si se selecciona el filtro buscarPorCodigo
        if(rbBuscarProdPorCodigo.isSelected()){
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarProdPorCodigo.getText())) {
           lblMensajesProdu.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtBuscarProdPorCodigo.getText())) {
           lblMensajesProdu.setText("El filtro seleccionado solo recibe numeros");
           return;
        }
        
        //Se realiza la busqueda mediante el dao
        String busquedaPorCodigo = txtBuscarProdPorCodigo.getText();
        boolean existeProducto = productoDao.buscarProductoPorCodigo(busquedaPorCodigo).next();
        resultadoProducto = productoDao.buscarProductoPorCodigo(busquedaPorCodigo);
        //Si se encontro el proveedor cargar resultado de la busqueda a la tabla
            if(existeProducto){
            cargarDatosTablaProductos(resultadoProducto, tblMostrarProductos);
            btnNuevoProducto.setDisable(true);
            }else{
            lblMensajesProdu.setText("Producto no encontrado, opción nuevo producto habilitada para el registro");
            btnNuevoProducto.setDisable(false);
            btnModificarProducto.setDisable(true);
            }
        }
        
        // Si se selecciona el filtro buscarPorNombre
        if(rbBuscarProdPorNombre.isSelected()){
            
        //Validacion de campos vacios
        if (validacion.validarVacios(txtBuscarProdPorNombre.getText())) {
           lblMensajesProdu.setText("Por favor ingrese el filtro de busqueda seleccionado");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtBuscarProdPorNombre.getText())) {
           lblMensajesProdu.setText("El filtro seleccionado solo recibe letras");
           return;
        }
                
        //Se realiza la busqueda mediante el dao
        String busquedaPorNombre = txtBuscarProdPorNombre.getText();
        boolean existeProducto = productoDao.buscarProductoPorNombre(busquedaPorNombre).next();
        resultadoProducto = productoDao.buscarProductoPorNombre(busquedaPorNombre); 
        //Si se encontro el producto cargar resultado de la busqueda a la tabla
            if(existeProducto){
            cargarDatosTablaProductos(resultadoProducto, tblMostrarProductos);
            btnNuevoProducto.setDisable(true);
            }else{
            lblMensajesProdu.setText("Producto no encontrado, opción nuevo producto habilitada para el registro");
            btnNuevoProducto.setDisable(false);
            btnModificarProducto.setDisable(true);
            }
        }
    }

    @FXML
    private void getProductoSeleccionado(MouseEvent event) {
         tblMostrarProductos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (tblMostrarProductos != null) {
                                        
                    String valor = tblMostrarProductos.getSelectionModel().getSelectedItems().get(0).toString();
                    
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
                        limpiarCamposProd();
                        cargarDatosProductoAlFormulario(cincoDigitos);
                        codigoProducto = cincoDigitos;
                    } else {
                        if (m4.find()) {
                            limpiarCamposProd();
                            cargarDatosProductoAlFormulario(cuatroDigitos);
                            codigoProducto = cuatroDigitos;
                        } else {
                            if (m3.find()) {
                                limpiarCamposProd();
                                cargarDatosProductoAlFormulario(tresDigitos);
                                codigoProducto = tresDigitos;
                            } else {
                                if (m2.find()) {
                                    limpiarCamposProd();
                                    cargarDatosProductoAlFormulario(dosDigitos);
                                    codigoProducto = dosDigitos;
                                } else {
                                    limpiarCamposProd();
                                    cargarDatosProductoAlFormulario(unDigitos);
                                    codigoProducto = unDigitos;
                                }
                             }
                        }
                    }
                }
            }
        });
        
        btnModificarProducto.setDisable(false);
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA PRODUCTOS
    private void cargarDatosTablaProductos(ResultSet resultadoProducto, TableView tabla) {
        tabla.getColumns().clear();
        productos = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProductos = resultadoProducto;

        try {
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"CODIGO", "NOMBRE", "TIPO", "CANTIDAD", "PROVEEDOR", "PRECIO"};

            //AGREGAMOS LOS DATOS A LA TABLA DINAMICAMENTE
            for (int i = 0; i < resultadoDatosTablaProductos.getMetaData().getColumnCount(); i++) {
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
            while (resultadoDatosTablaProductos.next()) {
                //ITERACION DE FILA}
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultadoDatosTablaProductos.getMetaData().getColumnCount(); i++) {
                    //ITERACION DE COLUMNA
                    row.add(resultadoDatosTablaProductos.getString(i));
                }
                System.out.println("Row [i] added " + row);
                productos.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PRODUCTOS
            tabla.setItems(productos);
            resultadoDatosTablaProductos.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS AL FORMULARIO PRODUCTOS SEGUN EL ITEM SELECCIONADO
    private void cargarDatosProductoAlFormulario(String dato){
        
        ResultSet cargaDatosParaFormularioProductos = productoDao.datosParaFormulario(dato);
        
        try {
                                 
            while (cargaDatosParaFormularioProductos.next()) {
                
                txtNombreProducto.setText(cargaDatosParaFormularioProductos.getString("NOMBRE").trim());
                cbTipo.setDisable(false);
                cbTipo.setValue(cargaDatosParaFormularioProductos.getString("TIPO"));
                cbSeccion.setDisable(false);
                cbSeccion.setValue(cargaDatosParaFormularioProductos.getString("SECCION"));
                cbProveedor.setDisable(false);
                cbProveedor.setValue(cargaDatosParaFormularioProductos.getString("PROVEEDOR"));
                txtMarcaProducto.setText(cargaDatosParaFormularioProductos.getString("MARCA").trim());
                txtPrecioProducto.setText(cargaDatosParaFormularioProductos.getString("PRECIO").trim());
                txtCantidadProducto.setText(cargaDatosParaFormularioProductos.getString("CANTIDAD").trim());
                                
            }
            cargaDatosParaFormularioProductos.close();
          } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
        
        txtNombreProducto.setEditable(true);
        txtMarcaProducto.setEditable(true);
        txtPrecioProducto.setEditable(true);
        txtCantidadProducto.setEditable(true);
        btnModificarProducto.setDisable(false);
      
    }

    @FXML
    private void nuevoProducto(ActionEvent event) {
        limpiarCamposProd();
        txtNombreProducto.setEditable(true);
        cbTipo.setDisable(false);
        cbSeccion.setDisable(false);
        cbProveedor.setDisable(false);
        txtMarcaProducto.setEditable(true);
        txtPrecioProducto.setEditable(true);
        txtCantidadProducto.setEditable(true);
        btnGuardarProducto.setDisable(false);
        btnNuevoProducto.setDisable(true);
    }
    
     // METODO PARA MODIFICAR UN PRODUCTO 
    private void modificarProducto(Existencias modificarExistencia, int codigo){
                
        PreparedStatement estado = productoDao.modificarProducto(modificarExistencia, codigo);
        
            try {
                estado.executeUpdate();
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        limpiarCamposProd();
      
    }

    @FXML
    private void modificarExistencia(ActionEvent event) {
        
         limpiarMensajesProd();
        
        if (validacion.validarVacios(txtNombreProducto.getText())) {
           lblMensajeNomProdu.setText("campo obligatorio");
           return;
        }
                
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreProducto.getText())) {
           lblMensajeNomProdu.setText("este campo solo recibe letras");
           return;
        }
        
        //validar comboBox
        if(cbTipo.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeTipoProdu.setText("debe seleccionar un tipo de producto");
           return;
        }
        
        if(cbSeccion.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeSeccProdu.setText("debe seleccionar una seccion");
           return;
        }
        
        if(cbProveedor.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeProveProdu.setText("debe seleccionar un proveedor");
           return;
        }
        
        if (validacion.validarVacios(txtMarcaProducto.getText())) {
           lblMensajeMarcaProdu.setText("campo obligatorio");
           return;
        }
               
        if (validacion.validarVacios(txtPrecioProducto.getText())) {
           lblMensajePrecioProdu.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtPrecioProducto.getText())) {
           lblMensajePrecioProdu.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtCantidadProducto.getText())) {
           lblMensajeCantProdu.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtCantidadProducto.getText())) {
           lblMensajeCantProdu.setText("este campo solo recibe numeros");
           return;
        }
        
        String nombre = txtNombreProducto.getText();
        Integer tipo = cbTipo.getSelectionModel().getSelectedIndex() + 1;
        Integer seccion = cbSeccion.getSelectionModel().getSelectedIndex() + 1;
        Integer proveedor = cbProveedor.getSelectionModel().getSelectedIndex() + 1;
        String marca = txtMarcaProducto.getText();
        Double precio = Double.parseDouble(txtPrecioProducto.getText());
        Integer cantidad = Integer.parseInt(txtCantidadProducto.getText());     
        
        
        Existencias modificarExistencia = new Existencias();
        modificarExistencia.setNombre(nombre);
        System.out.println(nombre);
        modificarExistencia.setTipo(tipo);
        modificarExistencia.setMarca(marca);
        modificarExistencia.setPrecio(precio);
        
        Integer codigo  = productoDao.buscarCodigoDeProducto(nombre);
        
        modificarProducto(modificarExistencia, codigo);
                        
        modificarExistencia.setCodigo(codigo);
        modificarExistencia.setProveedor(proveedor);
        modificarExistencia.setUbicacion(seccion);
        modificarExistencia.setCantidad(cantidad); 
        
         PreparedStatement estado = existenciaDao.modificarExistencia(modificarExistencia, codigo);
        
            try {
                estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Producto modificado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
        
        limpiarCamposProd();
        
    }
    
    // METODO PARA CREAR UN NUEVO PRODUCTO 
    private void guardarProducto(Existencias guardarProducto) {      
                
        PreparedStatement estado = productoDao.nuevoProducto(guardarProducto);
        try {
          estado.executeUpdate();
          estado.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionDeProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void guardarExistencia(ActionEvent event) {
         limpiarMensajesProd();
        
        if (validacion.validarVacios(txtNombreProducto.getText())) {
           lblMensajeNomProdu.setText("campo obligatorio");
           return;
        }
                
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombreProducto.getText())) {
           lblMensajeNomProdu.setText("este campo solo recibe letras");
           return;
        }
        
        //validar comboBox
        if(cbTipo.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeTipoProdu.setText("debe seleccionar un tipo de producto");
           return;
        }
        
        if(cbSeccion.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeSeccProdu.setText("debe seleccionar una seccion");
           return;
        }
        
        if(cbProveedor.getSelectionModel().getSelectedIndex() == -1){
           lblMensajeProveProdu.setText("debe seleccionar un proveedor");
           return;
        }
        
        if (validacion.validarVacios(txtMarcaProducto.getText())) {
           lblMensajeMarcaProdu.setText("campo obligatorio");
           return;
        }
               
        if (validacion.validarVacios(txtPrecioProducto.getText())) {
           lblMensajePrecioProdu.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtPrecioProducto.getText())) {
           lblMensajePrecioProdu.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtCantidadProducto.getText())) {
           lblMensajeCantProdu.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtCantidadProducto.getText())) {
           lblMensajeCantProdu.setText("este campo solo recibe numeros");
           return;
        }
        
               
        String nombre = txtNombreProducto.getText();
        Integer tipo = cbTipo.getSelectionModel().getSelectedIndex() + 1;
        Integer seccion = cbSeccion.getSelectionModel().getSelectedIndex() + 1;
        Integer proveedor = cbProveedor.getSelectionModel().getSelectedIndex() + 1;
        String marca = txtMarcaProducto.getText();
        Double precio = Double.parseDouble(txtPrecioProducto.getText());
        Integer cantidad = Integer.parseInt(txtCantidadProducto.getText());     
        
        
        Existencias nuevaExistencia = new Existencias();
        
        nuevaExistencia.setNombre(nombre);
        nuevaExistencia.setTipo(tipo);
        nuevaExistencia.setMarca(marca);
        nuevaExistencia.setPrecio(precio);
        
        guardarProducto(nuevaExistencia);
        
        Integer codigo  = productoDao.buscarCodigoDeProducto(nombre);
                        
        nuevaExistencia.setCodigo(codigo);
        nuevaExistencia.setProveedor(proveedor);
        nuevaExistencia.setUbicacion(seccion);
        nuevaExistencia.setCantidad(cantidad); 
        
        PreparedStatement estado = existenciaDao.nuevaExistencia(nuevaExistencia);
        
            try {
                estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Producto registrado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
            
        limpiarCamposProd();
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        
         try {
            //Cargamos la scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("MenuJefeDeBodega.fxml"));
            AnchorPane Gerente = (AnchorPane) loader.load();

            //Agregamos a la ventana
            Scene scene = new Scene(Gerente);
            Node node = (Node) event.getSource();
            Stage primaryStage = (Stage) node.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Menu Jefe de Bodega");
            primaryStage.show();
                    
            } catch (IOException e) {}
    }
    
    private void limpiarCamposProd(){
        txtNombreProducto.setText("");
        txtNombreProducto.setEditable(false);
        cbTipo.setValue("Seleccionar");
        cbTipo.setDisable(true);
        cbSeccion.setValue("Seleccionar");
        cbSeccion.setDisable(true);
        cbProveedor.setValue("Seleccionar");
        cbProveedor.setDisable(true);
        txtMarcaProducto.setText("");
        txtMarcaProducto.setEditable(false);
        txtPrecioProducto.setText("");
        txtPrecioProducto.setEditable(false);
        txtCantidadProducto.setText("");
        txtCantidadProducto.setEditable(false);
        btnModificarProducto.setDisable(true);
        btnGuardarProducto.setDisable(true);
        
    }
    
    private void limpiarMensajesProd(){
        lblMensajeNomProdu.setText("");
        lblMensajeTipoProdu.setText("");
        lblMensajeSeccProdu.setText("");
        lblMensajeProveProdu.setText("");
        lblMensajeMarcaProdu.setText("");
        lblMensajePrecioProdu.setText("");
        lblMensajeCantProdu.setText("");
        
    }
    
}
