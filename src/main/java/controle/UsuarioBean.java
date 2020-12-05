package controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entidade.Telefone;
import entidade.Usuario;

@ManagedBean(name="UsuarioBean")
@SessionScoped
public class UsuarioBean {
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuarios;
	private String emailUsuario;
	
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBean(){
		
		this.iniciarCampor();
		
		this.usuarioDAO = new UsuarioDAOImpl();
		
	}
	
	private void iniciarCampor(){
		
		this.usuario = new Usuario();
		this.usuario.setTelefone(new Telefone());
		
	}
	
	public void salvarUsuario(){
		
		this.usuarioDAO.inserirUsuario(this.usuario);
		
		this.iniciarCampor();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO , "Sucesso", "Usuario cadastrado!") );
	}
	
	public void pesquisarUsuario(){
		
		this.listaUsuarios = this.usuarioDAO.pesquisarUsuario(
				this.usuario.getEmail(),
				this.usuario.getNome());
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO , "Sucesso", "Usuario pesquisado!") );
		
	}
	
	public String removerUsuario(){
		
		Usuario usuarioRemover = null;
		
		for(Usuario usuarioLista : listaUsuarios){
			
			usuarioRemover = usuarioLista;
			
		}
		
		if(usuarioRemover != null){
			
			this.usuarioDAO.deletarUsuario(usuarioRemover);
			this.pesquisarUsuario();
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO , "Sucesso", "Usuario removido!") );
			
		}
		
		return "";
	}
	
	public void editarUsuario(){
		
		this.usuarioDAO.editarUsuario(this.usuario);
		
		this.iniciarCampor();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO , "Sucesso", "Usuario alterado!") );
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getCpfUsuario() {
		return emailUsuario;
	}

	public void setCpfUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	
}
