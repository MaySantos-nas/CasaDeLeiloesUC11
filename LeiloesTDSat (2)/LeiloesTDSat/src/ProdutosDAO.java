/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Component;


public class ProdutosDAO {

    private Connection conn;

    // Conexão única para toda a classe
    private conectaDAO conexao = new conectaDAO();

    public ProdutosDAO() {
        this.conn = conexao.getConnection(); // Estabelece a conexão no construtor
    }

    // Método para cadastrar um produto
    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (PreparedStatement consulta = conn.prepareStatement(sql, new String[]{"ID"})) {
            consulta.setString(1, produto.getNome());
            consulta.setInt(2, produto.getValor());
            consulta.setString(3, produto.getStatus());

            int status = consulta.executeUpdate();

            if (status == 1) {
                try (ResultSet generatedKeys = consulta.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
                        System.out.println("Produto cadastrado com sucesso. ID gerado: " + idGerado);
                    } else {
                        System.out.println("Erro ao recuperar o ID gerado.");
                    }
                }
            } else {
                System.out.println("Erro ao cadastrar o produto.");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar dados: " + ex.getMessage());
        }
    }

    // Método para vender um produto
    public void venderProduto(Component parent, Integer id) {
        String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try (PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(parent, "Produto vendido!");
            } else {
                JOptionPane.showMessageDialog(parent, "Nenhum produto foi encontrado com esse ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent, "Erro ao vender produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para listar produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String query = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try (PreparedStatement prep = conn.prepareStatement(query);
             ResultSet resultset = prep.executeQuery()) {

            while (resultset.next()) {
                ProdutosDTO prod = new ProdutosDTO();
                prod.setId(resultset.getInt("id"));
                prod.setNome(resultset.getString("nome"));
                prod.setValor(resultset.getInt("valor"));
                prod.setStatus(resultset.getString("status"));

                listagem.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listagem;
    }

    // Método para listar todos os produtos
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> produtos = new ArrayList<>();

        String sql = "SELECT * FROM produtos";

        try (PreparedStatement consulta = conn.prepareStatement(sql);
             ResultSet resposta = consulta.executeQuery()) {

            while (resposta.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resposta.getInt("id"));
                p.setNome(resposta.getString("nome"));
                p.setValor(resposta.getInt("valor"));
                p.setStatus(resposta.getString("status"));
                produtos.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO ao tentar listar todos: " + ex.getMessage());
        }

        return produtos;
    }

    // Método para fechar a conexão (se necessário)
    public void fecharConexao() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão encerrada com sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
