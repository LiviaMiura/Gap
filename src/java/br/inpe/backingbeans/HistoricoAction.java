/*
 * Projeto - GAP
 * Nome Arquivo : HistoricoAction.Java
 * author castro
 */
package br.inpe.backingbeans;


import br.inpe.dao.HistoricoDAO;
import br.inpe.dto.HistoricoDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
* Construtor da classe HistoricoAction
* author castro
* @return 
**/
public class HistoricoAction implements Serializable{

    
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
        
    public HistoricoDTO historicoDTO;
    public Collection<HistoricoDTO> lista;
    
    
    public HistoricoAction() {
    }

    /**
     * Retorna lista de historico
     * author castro
     * @return lista
     **/
    public Collection<HistoricoDTO> getLista() {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        lista = historicoDAO.listar();
        return lista;
    }

    /**
     * Retorna historicoDTO se for null
     * author castro
     * @return historicoDTO
     **/
    public HistoricoDTO getHistoricoDTO() {
        if (historicoDTO == null) {
            historicoDTO = new HistoricoDTO();
        }
        return historicoDTO;
    }

    /**
     * Seta historicoDTO 
     * author castro
     * @return void
     **/
    public void setHistoricoDTO(HistoricoDTO historicoDTO) {
        this.historicoDTO = historicoDTO;
    }
//******************************************************************************

    /**
     * Lista objeto historicoDAO
     * @author Castro
     * @return String listar, para navegação.
     **/
    public String listar() {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        lista = historicoDAO.listar();
        return "listar";
    }

    /**
     * Exclui historicoDAO
     * @author Castro
     * @return String listar, para navegação.
     **/
    public String excluir() {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        getHistoricoDTO().setIdHistorico(new Integer(request.getParameter("idHistorico")));
        historicoDAO.excluir(getHistoricoDTO());
        return "listar";
    }

    /**
     * Inserir parametro.
     * @author Castro
     * @return String inserir, para navegação.
     **/
    public String inserir() {
        getHistoricoDTO().setIdHistorico(0);
        return "inserir";
    }

    /**
     * Invoca o método gravar, Instancia um HistoricoDAO, passando  o objeto HistoricoDTO
     * @author Castro
     * @return String listar, para navegação.
     **/
    public String gravar() {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        historicoDAO.gravar(getHistoricoDTO());
        return "listar";
    }

    /**
     * Obtem o idHistorico e invoca o método selecionar, passando o objeto HistoricoDTO
     * @author Castro
     * @return String inserir, para navegação.
     **/
    public String alterar() {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        getHistoricoDTO().setIdHistorico(new Integer(request.getParameter("idHistorico")));
        setHistoricoDTO(historicoDAO.selecionar(getHistoricoDTO()));
        return "inserir";
    }

    /**
     * Controle de navegação para o menu
     * @author Castro
     * @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }
}
