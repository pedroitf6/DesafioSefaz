package controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entidade.Usuario;

@ManagedBean(name="LoginBean")
@SessionScoped
public class LoginBean {
	
	private String emailTela;
	private String senhaTela;
	private Usuario usuario;
	
	private UsuarioDAO usuarioDAO;
	
	public LoginBean(){
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAOImpl();
	}
	
	public String entrar(){
		
		Usuario usuarioBanco = this.usuarioDAO.pesquisarLogin(this.emailTela);
		System.out.println(usuarioBanco);
		if(usuarioBanco != null){
			if(usuarioBanco.getSenha().equals(this.senhaTela)){
				HttpSession sessao =  (HttpSession)FacesContext.getCurrentInstance()
						.getExternalContext().getSession(true);
				sessao.setAttribute("usuarioLogado", usuarioBanco);
				return "telaPrincipal.xhtml?faces-redirect=true&amp;includeViewParams=true";
			} else{
				System.out.println("-- Senha invalida --");
			}
		} else{
			System.out.println("-- Usuario invalido --");
		}
		
		return "";
	}
	
	public String getCpfTela() {
		return emailTela;
	}
	
	public void setCpfTela(String cpfTela) {
		this.emailTela = cpfTela;
	}
	
	public String getSenhaTela() {
		return senhaTela;
	}
	
	public void setSenhaTela(String senhaTela) {
		this.senhaTela = senhaTela;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
