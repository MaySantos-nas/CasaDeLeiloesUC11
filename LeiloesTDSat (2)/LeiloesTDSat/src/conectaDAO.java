import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectaDAO {
    private Connection conn; // Variável de conexão

    // Configurações do banco de dados
    String url = "jdbc:mysql://localhost:3306/uc11";  
    String user = "root";                       
    String password = "May2006*";                   

    // Método para conectar ao banco de dados
    public void connectDB() {
        try {
            // Estabelece a conexão
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexão bem-sucedida!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obter a conexão (já existente)
    public Connection getConexao() {
        return conn;
    }

    // Método para obter a conexão, novo método adicionado
    public Connection getConnection() {
        return conn; // Retorna a conexão estabelecida
    }

    // Método para desconectar do banco de dados
    public void desconectar() {
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
