
package model.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;

import model.VO.PeliculaVO;
import model.VO.*;

/**
 * @author usuario
 */
public class RepositoryJDBC {
    public static RepositoryJDBC modelJDBC;

    private RepositoryJDBC() {

    }

    public static RepositoryJDBC getSingletonInstance() {
        if (modelJDBC == null) {
            modelJDBC = new RepositoryJDBC();
        }
        return modelJDBC;
    }

    public boolean insertarCliente(UsuarioVO usuario) {
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();

            comando.executeUpdate("insert into usuarios (nombre,password) values('" + usuario.getNombreUsuario() + "','" + usuario.getPwsd() + "');");

            conex.desconectar();
            return true;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }

    }

    public boolean comprobarUsuario(UsuarioVO usuario) {
        boolean b = false;
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();

            ResultSet rs = comando.executeQuery("select count(*) from usuarios where nombre='" + usuario.getNombreUsuario() + "' and password='" + usuario.getPwsd() + "';");

            while (rs.next()) {
                if (rs.getInt("count(*)") == 0) {
                    b = false;
                } else {
                    b = true;
                }

            }
            conex.desconectar();
            return b;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return b;
        }
    }

    public boolean comprobarPelicula(String pelicula) {

        boolean b = false;
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();

            ResultSet rs = comando.executeQuery("select count(*) from peliculas where nombre='" + pelicula + "';");

            while (rs.next()) {
                if (rs.getInt("count(*)") == 0) {
                    b = false;
                } else {
                    b = true;
                }

            }
            conex.desconectar();
            return b;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return b;
        }
    }

    public PeliculaVO cargarPelicula(String pelicula) {
        PeliculaVO pel = new PeliculaVO();
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();

            ResultSet rs = comando.executeQuery("select * from peliculas where nombre='" + pelicula + "';");

            while (rs.next()) {

                pel.setNombre(rs.getString(1));
                pel.setDirector(rs.getString(2));
                pel.setReparto(rs.getString(3));
                pel.setEnlace(rs.getString(4));
                pel.setFav(rs.getInt(5));


            }
            return pel;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return pel;
        }
    }

    public boolean aumentarVisitas() {
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();
            int id = 1;
            comando.executeUpdate("update visitas set iniciosesion = iniciosesion+1 where visitas.id = " + id + "; ");
            conex.desconectar();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int obtenerVisitas() {
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();
            ResultSet rs = comando.executeQuery("select * from visitas;");
            while (rs.next()) {

                return rs.getInt(2);
            }
            return -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;

        }
    }

    public boolean disminuirVisitas() {
        try {
            ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();
            int id = 1;
            comando.executeUpdate("update visitas set iniciosesion = iniciosesion-1 where visitas.id = " + id + "; ");
            conex.desconectar();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean incrementarFav(String nombre){
        try{ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();
            System.out.println("update peliculas set favorita = favorita+1 where nombre = '" + nombre + "'; ");
            comando.executeUpdate("update peliculas set favorita = favorita+1 where peliculas.nombre = '" + nombre + "'; ");
            conex.desconectar();
            return true;


        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean quitarFav(String nombre){
        try{ConexionJDBC conex = new ConexionJDBC();
            Statement comando = conex.getConnection().createStatement();

            comando.executeUpdate("update peliculas set favorita = favorita-1 where peliculas.nombre = '" + nombre + "'; ");
            conex.desconectar();
            return true;


        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }


}



