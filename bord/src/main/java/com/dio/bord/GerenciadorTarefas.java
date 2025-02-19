
package com.dio.bord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorTarefas {
    private Connection connection;

    public GerenciadorTarefas() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/seubanco", "seuusuario", "suasenha"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarTarefa(String descricao) {
        String sql = "INSERT INTO tarefas (descricao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tarefas.add(new Tarefa(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getBoolean("concluida")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    public void marcarComoConcluida(int id) {
        String sql = "UPDATE tarefas SET concluida = TRUE WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}