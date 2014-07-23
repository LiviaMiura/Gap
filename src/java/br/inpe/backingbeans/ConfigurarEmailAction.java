/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;


import br.inpe.dao.ConfigurarEmailDAO;
import br.inpe.dto.ConfigurarEmailDTO;
import br.inpe.dto.UsuarioDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author livia.miura
 */

public class ConfigurarEmailAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    private ConfigurarEmailDTO configurarEmailDTO;
    private Collection<ConfigurarEmailDTO> lista;
    private UsuarioDTO usuarioDTO;
    private String w;

    public ConfigurarEmailAction() {
    }

    public Collection<ConfigurarEmailDTO> getLista() {
        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        lista = configurarEmailDAO.listar(lista);
        return lista;
    }

    public void setLista(Collection<ConfigurarEmailDTO> lista) {
        this.lista = lista;
    }

    public ConfigurarEmailDTO getConfigurarEmailDTO() {
        if (configurarEmailDTO == null) {
            configurarEmailDTO = new ConfigurarEmailDTO();
        }
        return configurarEmailDTO;

    }

    public void setConfigurarEmailDTO(ConfigurarEmailDTO configurarEmailDTO) {
        this.configurarEmailDTO = configurarEmailDTO;
    }

     public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    /**
     * Método para listar configuracaoEmail
     * @author Jaqueline
     * @return String listar para navegação.
     */
    public String listar() {
        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        lista = configurarEmailDAO.listar();
        return "listar";
    }


    /**
     * Método para alterar configuração selecionada
     * @author Jaqueline
     * @return String alterar para navegação.
     */
    public String alterar() {
        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        setConfigurarEmailDTO(configurarEmailDAO.selecionar(getConfigurarEmailDTO()));
        return "alterar";
    }

    /**
     * Método para gravar uma configuração
     * @author Jaqueline
     * @return String listar para navegação
     */
    public String gravar() {
        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        
        
       
          
                configurarEmailDTO.setAutenticacao("true");
                configurarEmailDTO.setPorta("25");
                configurarEmailDTO.setId(1);
                configurarEmailDTO.setServidor("150.163.73.157");
               configurarEmailDAO.gravar(configurarEmailDTO);
              
   
        if(SendMailLocalhostAction.confirmarEmail.equals("enviado")){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Configuração Email gravado com sucesso!",
                "O Sistema deve ser reiniciado para que as configurações alteradas sejam válidas!"));

        }else{

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                 "Configuraçãos Email gravado com sucesso!",
                ""));
        }
        
 w = configurarEmailDTO.getUsuario();
        System.out.println("w==="+w);
        return "newlistar";

    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }
    
    
}
