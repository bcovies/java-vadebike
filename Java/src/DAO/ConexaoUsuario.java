/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Models.Usuario;

/**
 *
 * @author bcovies
 */
public class ConexaoUsuario {

    Usuario usuario = new Usuario();

    private String jdbcURL = "jdbc:postgresql://localhost:11000/bicicletario";

    private String jdbcUsername = "admin";
    private String jdbcPassword = "admin";

    private static final String INSERT_USER_SQL = "INSERT INTO usuario (nome,sobrenome,cpf,email,senha,endereco,nascimento,cartao) VALUES (?,?,?,?,?,?,?,?);";

    private static final String SELEC_USER_EMAIL_PASS_SQL = "SELECT email,senha FROM usuario WHERE email = ? AND senha = ?;";

    private static final String SELEC_ID_NAME_EMAIL_SQL = "SELECT id,nome,email FROM usuario WHERE email = ? AND senha = ?;";

    protected Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println("\nCONEXAO:\nIniciada a conexão com o banco de dados:\t" + connection);
        return connection;
    }

    public void insertUser(Usuario usuario) {

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {

            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getSobrenome());
            preparedStatement.setString(3, usuario.getCPF());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getSenha());
            preparedStatement.setString(6, usuario.getEndereco());
            preparedStatement.setString(7, usuario.getNascimento());
            preparedStatement.setString(8, usuario.getCartao());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

//            System.out.println("\nINSERTUSUARIO:\n" + preparedStatement);
        } catch (Exception e) {
            e.getMessage();
        }
    }

   

    public Usuario returnUser(String email, String senha) {

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELEC_ID_NAME_EMAIL_SQL)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return usuario;
    }

}
