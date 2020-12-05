package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidade.Telefone;
import entidade.Usuario;
import util.JdbcUtil;

public class UsuarioDAOImpl implements UsuarioDAO{

	@Override
	public void inserirUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
		String sqlUsuario = "INSERT INTO USUARIO (NOME, EMAIL, SENHA, TELEFONE) VALUES (?,?,?,?,?)";
		
		String sqlTelefone = "INSERT INTO TELEFONE (DDD, NUMERO, TIPO) VALUES (?,?,?)";
		
		Connection conexao = null;
		
		try {
			
			conexao = JdbcUtil.getConexao();
			
			conexao.setAutoCommit(false);
			
			PreparedStatement ps = conexao.prepareStatement(sqlTelefone);
			
			ps.setInt(1, usuario.getTelefone().getDdd());
			ps.setString(2, usuario.getTelefone().getNumero());
			ps.setString(3, usuario.getTelefone().getTipo());
			
			ps.execute();
			
			ps = conexao.prepareStatement(sqlUsuario);
			
			ps.setString(2, usuario.getNome());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getSenha());
			ps.setString(5, usuario.getTelefone().getNumero());
			
			ps.execute();
			
			ps.close();
			conexao.commit();
			conexao.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			if(conexao != null){
				try {
					conexao.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();					
				}
			}
		}
		
	}

	@Override
	public void editarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
		String sqlUsuario = "UPDATE USUARIO U SET U.NOME = ?, U.EMAIL = ?, U.SENHA = ?, U.TELEFONE = ?"
							+ "WHERE U.EMAIL = ?";
		String sqlTelefone = "UPDATE TELEFONE T SET T.DDD = ?, T.NUMERO = ?, T.TIPO = ?"
							+ "WHERE T.NUMERO = ?";
		
		Connection conexao = null;
		
		try {
			
			conexao = JdbcUtil.getConexao();
			
			conexao.setAutoCommit(false);
			
			PreparedStatement ps = conexao.prepareStatement(sqlTelefone);
			
			ps.setInt(1, usuario.getTelefone().getDdd());
			ps.setString(2, usuario.getTelefone().getNumero());
			ps.setString(3, usuario.getTelefone().getTipo());
			ps.setString(4, usuario.getTelefone().getNumero());
			
			ps.execute();
			
			ps = conexao.prepareStatement(sqlUsuario);
			

			ps.setString(2, usuario.getNome());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getSenha());
			ps.setString(5, usuario.getTelefone().getNumero());
			ps.setString(6, usuario.getEmail());
			
			ps.execute();
			
			ps.close();
			conexao.commit();
			conexao.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			if(conexao != null){
				try {
					conexao.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
				
	}

	@Override
	public void deletarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
		String sqlTelefone = "DELETE FROM TELEFONE T WHERE T.NUMERO = ?";
		String sqlUsuario = "DELETE FROM USUARIO U WHERE U.EMAIL = ?";
		
		Connection conexao = null;
		
		try {
			
			conexao = JdbcUtil.getConexao();
			conexao.setAutoCommit(false);
			
			PreparedStatement ps = conexao.prepareStatement(sqlUsuario);
			
			ps.setString(1, usuario.getEmail());
			
			ps.execute();
			
			ps = conexao.prepareStatement(sqlTelefone);
			
			ps.setString(1, usuario.getTelefone().getNumero());
			
			ps.execute();
			
			ps.close();
			conexao.commit();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(conexao != null){
				try {
					conexao.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public Usuario pesquisarLogin(String cpf) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT U.NOME, U.EMAIL, U.SENHA, U.TELEFONE FROM USUARIO U WHERE EMAIL = ?";
		
		Usuario usuario = null;
		
		Connection conexao;
		
		try {
			
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, cpf);
						
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				
				usuario = new Usuario();				
				usuario.setNome(res.getString("NOME"));
				usuario.setEmail(res.getString("EMAIL"));
				usuario.setSenha(res.getString("SENHA"));
				
				Telefone telefone = new Telefone();
				telefone.setDdd(res.getInt("DDD"));
				telefone.setNumero(res.getString("NUMERO"));
				telefone.setTipo("TIPO");
				
				usuario.setTelefone(telefone);
				
			}
			
			ps.close();
			conexao.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}

	@Override
	public List<Usuario> pesquisarUsuario(String email, String nome) {
		// TODO Auto-generated method stub
		
		List<Usuario> listaRetorno = new ArrayList<Usuario>();
		
		String sql = "SELECT DISTINCT U.EMAIL," +					
					" 	U.EMAIL, " +
					" 	T.DDD, " +
					" 	T.NUMERO, " +
					" 	T.TIPO " +
					" FROM USUARIO U " +
					" JOIN TELEFONE T " +
					"   ON U.TELEFONE = T.NUMERO " +
					" WHERE U.TELEFONE = T.NUMERO " +
											this.montarWherePesquisa(email, nome);
		
		System.out.println(sql);
		
		Connection conexao;
		
		try {
			
			conexao = JdbcUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);

			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				
				Usuario usuario = new Usuario();				
				usuario.setNome(res.getString("NOME"));
				usuario.setEmail(res.getString("EMAIL"));
				usuario.setSenha(res.getString("SENHA"));
				
				Telefone telefone = new Telefone();
				telefone.setDdd(res.getInt("DDD"));
				telefone.setNumero(res.getString("NUMERO"));
				telefone.setTipo("TIPO");
				
				usuario.setTelefone(telefone);
				
				listaRetorno.add(usuario);
				
			}
			
			ps.close();
			conexao.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaRetorno;
	}
	
	private String montarWherePesquisa(String email, String nome){
		
		String where = "";
		
		if(email != null && !email.isEmpty()){
			where += " AND UPPER(U.EMAIL) = UPPER('%" + email + "%') ";
		}
		
		if(nome != null && !nome.isEmpty()){
			where += " AND UPPER(U.NOME) LIKE UPPER('%" + nome + "%')  ";
		}
		
		return where;
	}

}
