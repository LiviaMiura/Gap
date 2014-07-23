/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;
import br.inpe.dao.EstadoNoDAO;
import br.inpe.dto.EstadoNoDTO;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author livia.miura
 */
public class EstadoNoAction {

 
    
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    private EstadoNoDTO estadoNoDTO;
    private List<EstadoNoDTO> lista;

      /** Creates a new instance of EstadoNoAction */
    public EstadoNoAction() {  
    }
    
   public List<EstadoNoDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<EstadoNoDTO> lista) {
        this.lista = lista;
    }

    public EstadoNoDTO getEstadoNoDTO() {
        if (estadoNoDTO == null) {
            estadoNoDTO = new EstadoNoDTO();
        }
        return estadoNoDTO;
    }

    public void setEstadoNoDTO(EstadoNoDTO estadoNoDTO) {
        this.estadoNoDTO = estadoNoDTO;
    }

    /**
     * Instancia um estadoNoDAO e invoca o método listar().
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String listar() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        lista = estadoNoDAO.listar();
        return "listar";
    }

    /**
     * Instancia um estadoNoDAO, obtem o idEstadoNo e invoca o método excluir
     * @author livia.miura 
     * @return String listar, para navegação.
     **/
    public String excluir() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getEstadoNoDTO().setIdEstadoNo(new Integer(request.getParameter("idEstadoNo")));
        estadoNoDAO.excluir(getEstadoNoDTO());
        return "listar";
    }

    /**
     * Controle de navegação
     * @author livia.miura
     * @return String inserir, para navegação.
     **/
 
            
          public String inserir() {
        return "inserir";
    }

    /**
     * Instancia um estadoNoDAO, invoca o método gravar, passando  o objeto estadoNoDTO
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String gravar() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        estadoNoDAO.gravar(getEstadoNoDTO());
        return "listar";
    }

    /**
     * Instancia um estadoNoDAO, obtem o idEstadoNo e invoca o método selecionar, passando o objeto estadoNoDTO
     * @author livia.miura
     * @return String inserir, para navegação.
     **/
    public String alterar() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getEstadoNoDTO().setIdEstadoNo(new Integer(request.getParameter("idEstadoNo")));
        setEstadoNoDTO(estadoNoDAO.selecionar(getEstadoNoDTO()));
        return "alterar";
    }

    /**
     * Controle de navegação para o menu
     * @author livia.miura
     * @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }
}

