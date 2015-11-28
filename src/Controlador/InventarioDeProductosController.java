
package Controlador;

import DAO.ProductoDao;
import Vista.Principal;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class InventarioDeProductosController implements Initializable {
    @FXML    private TableView tblMostrarInventarioDeProductos;
    @FXML    private TableColumn col;
    
    ObservableList<ObservableList> inventario;
    
    String cargo;
    
    //Objeto DAO
    private final ProductoDao productoDao = new ProductoDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA INVENTARIO SI QUIEN LO LLAMA ES EL GERANTE
    private void cargarDatosTablaSiGerente(ResultSet resultadoInventario, TableView tabla) {
        tabla.getColumns().clear();
        inventario = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProductos = resultadoInventario;

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
                inventario.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PRODUCTOS
            tabla.setItems(inventario);
            resultadoDatosTablaProductos.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA INVENTARIO SI QUIEN LO LLAMA ES EL JEFE DE BODEGA
    private void cargarDatosTablaSiJefeDeBodega(ResultSet resultadoInventario, TableView tabla) {
        tabla.getColumns().clear();
        inventario = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProductos = resultadoInventario;

        try {
            
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"CODIGO", "NOMBRE", "SECCION", "PASILLO", "ESTANTERIA", "CANTIDAD", "PRECIO"};
            
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
                inventario.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PRODUCTOS
            tabla.setItems(inventario);
            resultadoDatosTablaProductos.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
    
    // METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA INVENTARIO SI QUIEN LO LLAMA ES EL JEFE DE SUCURSAL
    private void cargarDatosTablaSiJefeDeSucursal(ResultSet resultadoInventario, TableView tabla) {
        tabla.getColumns().clear();
        inventario = FXCollections.observableArrayList();
        ResultSet resultadoDatosTablaProductos = resultadoInventario;

        try {
            
            //TITULOS DE LAS COLUMNAS
            String[] titulos = {"CODIGO", "NOMBRE", "MARCA", "PRECIO"};
            
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
                inventario.addAll(row);
            }
            //FINALMENTE AGREGAMOS A LA TABLA PRODUCTOS
            tabla.setItems(inventario);
            resultadoDatosTablaProductos.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
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
    
    @FXML
    private void mostrarInventario(ActionEvent event) throws SQLException {
        
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        cargo = primaryStage.getTitle();
        
        switch(cargo){
                    case "Inventario Gerente":
                        
                        tblMostrarInventarioDeProductos.getColumns().clear();
                        ResultSet resultadoInventario1;
                        //Se realiza la busqueda mediante el dao
                        boolean existeInventario1 = productoDao.mostrarInventarioGerente().next();
                        resultadoInventario1 = productoDao.mostrarInventarioGerente();
                        //Si se encontro el inventario cargar resultado en la tabla
                        if(existeInventario1){
                        cargarDatosTablaSiGerente(resultadoInventario1, tblMostrarInventarioDeProductos);
                        }
                        
                        break;
                        
                    case "Inventario Jefe de Bodega":
                        
                        tblMostrarInventarioDeProductos.getColumns().clear();
                        ResultSet resultadoInventario2;
                        //Se realiza la busqueda mediante el dao
                        boolean existeInventario2 = productoDao.mostrarInventarioJefeDeBodega().next();
                        resultadoInventario2 = productoDao.mostrarInventarioJefeDeBodega();
                        //Si se encontro el inventario cargar resultado en la tabla
                        if(existeInventario2){
                        cargarDatosTablaSiJefeDeBodega(resultadoInventario2, tblMostrarInventarioDeProductos);
                        
                        }
                                
                        break;
                            
                    case "Inventario Jefe de Sucursal":
                        
                        tblMostrarInventarioDeProductos.getColumns().clear();
                        ResultSet resultadoInventario3;
                        //Se realiza la busqueda mediante el dao
                        boolean existeInventario3 = productoDao.mostrarInventarioJefeDeSucursal().next();
                        resultadoInventario3 = productoDao.mostrarInventarioJefeDeSucursal();
                        //Si se encontro el inventario cargar resultado en la tabla
                        if(existeInventario3){
                        cargarDatosTablaSiJefeDeSucursal(resultadoInventario3, tblMostrarInventarioDeProductos);
                        }
                        
                        break;
        }
    }
    
}
