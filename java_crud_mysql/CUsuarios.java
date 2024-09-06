/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_crud_mysql;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author usuario
 */
public class CUsuarios {
    int codigo; 
    String nombreUsuarios;
    String apellidosUsuarios;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreUsuarios() {
        return nombreUsuarios;
    }

    public void setNombreUsuarios(String nombreUsuarios) {
        this.nombreUsuarios = nombreUsuarios;
    }

    public String getApellidosUsuarios() {
        return apellidosUsuarios;
    }

    public void setApellidosUsuarios(String apellidosUsuarios) {
        this.apellidosUsuarios = apellidosUsuarios;
    }
    public void InsertarUsuario(JTextField paramNombres, JTextField paramApellidos){
        setNombreUsuarios(paramNombres.getText());
        setApellidosUsuarios(paramApellidos.getText());
        CConexion objetoConexion = new CConexion ();
        
        String consulta = "Insert into Usuarios (nombres,apellidos) values (?,?);";
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombreUsuarios());
            cs.setString(2, getApellidosUsuarios());
            cs.execute();
            
            
            JOptionPane.showMessageDialog(null, "Se insertó correctamente el usuario");
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "No se insertó correctamente el usuario: "+e.toString());
        }
        
        
    }
    public void MostrarUsuarios (JTable paramTablaTotalUsuarios) {
    CConexion objetoConexion = new CConexion ();
    
        DefaultTableModel modelo = new DefaultTableModel ();
        
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaTotalUsuarios.setRowSorter(OrdenarTabla);
        
        String sql = "";
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        
        paramTablaTotalUsuarios.setModel(modelo);
        
        sql = "select * from usuarios";
        
        String [] datos = new String [3];
        Statement st;
        
        try {
            st = objetoConexion.estableceConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                
                modelo.addRow(datos);
            }
            paramTablaTotalUsuarios.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: "+e.toString());
        }
        
    }
    public void SeleccionarUsuario(JTable paramTablaUsuarios, JTextField paramId, JTextField paramNombres, JTextField paramApellidos){
        try {
            int fila = paramTablaUsuarios.getSelectedRow();
            
            if (fila >=0) {
                
             paramId.setText(paramTablaUsuarios.getValueAt(fila, 0).toString());
             paramNombres.setText(paramTablaUsuarios.getValueAt(fila, 1).toString());
             paramApellidos.setText(paramTablaUsuarios.getValueAt(fila, 2).toString());
            }
            
            else
            {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion: "+ e.toString());
        }
    }
    public void ModificarUsuarios (JTextField paramCodigo, JTextField  paramNombres, JTextField paramApellidos){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreUsuarios(paramNombres.getText());
        setApellidosUsuarios(paramApellidos.getText());
        
        CConexion objetoConexion = new CConexion();
        String consulta = "UPDATE usuarios SET usuarios.nombres = ?, usuarios.apellidos=? WHERE usuarios.id=?;";
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setString(1, getNombreUsuarios());
            cs.setString(2, getApellidosUsuarios());
            cs.setInt(3, getCodigo());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificacion exitosa");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modificó, error: "+e.toString());
        }
    }
    public void EliminarUsuarios (JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        
        CConexion objetoConexion = new CConexion ();
        
        String consulta = "DELETE FROM usuarios WHERE usuarios.id=?;";
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "El usuario se eliminó correctamente");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puedo eliminar, error: "+e.toString());
        }
    }
}

   
    
    
  