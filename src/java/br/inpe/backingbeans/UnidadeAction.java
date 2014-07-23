/*
 * Projeto - GAP
 * Nome Arquivo : UnidadeAction.Java
 * author castro
 */
package br.inpe.backingbeans;
import br.inpe.dao.UnidadeDAO;
import br.inpe.dto.UnidadeDTO;
import java.util.Collection;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * Construtor da Classe UnidadeAction
 * @author Castro
 **/
public class UnidadeAction {
     //inserir codigo - base para todas as Action
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    //Criar Variaveis
    private UnidadeDTO unidadeDTO;
    private List<UnidadeDTO> lista;

   
    public UnidadeAction() {
    }
    
    //************************ INSERIR METODOS ************************ 
    /**
     * Retorna lista de Unidade cadastradas
     * author castro
     * @return lista
     **/
     public Collection<UnidadeDTO> getLista() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        lista = unidadeDAO.listar();
        return lista;
    }
    
     public void setLista(List<UnidadeDTO> lista) {
        this.lista = lista;
    }
     
     /**
     * Retorna nova Unidade se Unidade igual a null
     * author castro
     * @return unidadeDTO
     **/
     public UnidadeDTO getUnidadeDTO() {
        if (unidadeDTO == null) {
            unidadeDTO = new UnidadeDTO();
        }
        return unidadeDTO;
    }
     
     public void setUnidadeDTO(UnidadeDTO unidadeDTO) {
        this.unidadeDTO = unidadeDTO;
    }
    /**
     *Instancia unidadeDAO e invoca o método listar().
     * @author castro 
     * @return String listar, para navegação.
     **/
    public String listar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        lista = unidadeDAO.listar();
        return "listar";
    }
    
    /**
     *Instancia unidadeDAO, obtem o idUnidade  e invoca o método excluir
     * @author castro 
     * @return String listar, para navegação.
     **/
    public String excluir() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        getUnidadeDTO().setIdUnidade(new Integer(request.getParameter("idUnidade")));
        unidadeDAO.excluir(getUnidadeDTO());
        return "listar";
    }
    /**
     * Inserir Nova Unidade
     * @author castro
     * @return String inserir, para navegação.
     **/
    public String inserir() {
        getUnidadeDTO().setIdUnidade(0);
         return "inserir";
    }
    
    /**
     *Instancia um unidadeDAO, invoca o método gravar, passando  o objeto UnidadeDAO
     * @author castro 
     * @return String listar se gravar/alterar é permitido ou null caso contrário, para navegação.
     **/
    public String gravar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        return unidadeDAO.gravar(getUnidadeDTO());
    }
    /**
     * Instancia um unidadeDAO, obtem o idUnidade e invoca o método selecionar, passando  o objeto UnidadeDTO
     * @author castro 
     * @return String alterar, para navegação.
     **/
    public String alterar() {
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        getUnidadeDTO().setIdUnidade(new Integer(request.getParameter("idUnidade")));
        setUnidadeDTO(unidadeDAO.selecionar(getUnidadeDTO()));
        return "alterar";
    }
     /**
     *Controle de navegação para o menu
     * @author castro
     * @return String menu, para navegação.
     **/
    public String menu() {
        return "menu";
    }

}

