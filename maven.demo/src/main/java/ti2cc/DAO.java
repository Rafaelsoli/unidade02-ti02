package ti2cc;

import java.sql.*;
import java.security.*;
import java.math.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	
    public boolean inserirRelogio(Relogio relogio) {
        boolean status = false;
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO relogio (codigo, marca, modelo, anofabricacao) VALUES (?, ?, ?, ?)");
            ps.setInt(1, relogio.getCodigo());
            ps.setString(2, relogio.getMarca());
            ps.setString(3, relogio.getModelo());
            ps.setInt(4, relogio.getAnoFabricacao());
            ps.executeUpdate();
            ps.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

 
    public boolean atualizarRelogio(Relogio relogio) {
        boolean status = false;
        try {
            PreparedStatement ps = conexao.prepareStatement("UPDATE relogio SET marca = ?, modelo = ?, anofabricacao = ? WHERE codigo = ?");
            ps.setString(1, relogio.getMarca());
            ps.setString(2, relogio.getModelo());
            ps.setInt(3, relogio.getAnoFabricacao());
            ps.setInt(4, relogio.getCodigo());
            ps.executeUpdate();
            ps.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    
    public boolean excluirRelogio(int codigo) {
        boolean status = false;
        try {
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM relogio WHERE codigo = ?");
            ps.setInt(1, codigo);
            ps.executeUpdate();
            ps.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    
    public Relogio[] getRelogios() {
        Relogio[] relogios = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM relogio");
            if (rs.next()) {
                rs.last();
                relogios = new Relogio[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    relogios[i] = new Relogio(rs.getInt("codigo"), rs.getString("marca"),
                            rs.getString("modelo"), rs.getInt("anofabricacao"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return relogios;
    }

    
    public Relogio getRelogioPorCodigo(int codigo) {
        Relogio relogio = null;
        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM relogio WHERE codigo = ?");
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                relogio = new Relogio(rs.getInt("codigo"), rs.getString("marca"),
                        rs.getString("modelo"), rs.getInt("anofabricacao"));
            }

            ps.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return relogio;
    }

	
	public static String toMD5(String senha) throws Exception {
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0, senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
}