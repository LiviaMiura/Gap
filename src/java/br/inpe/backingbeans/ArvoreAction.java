/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.ArvoreDAO;
import br.inpe.dto.ArvoreDTO;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author livia.miura
 */
public class ArvoreAction {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    private ArvoreDTO arvoreDTO;
    private List<ArvoreDTO> lista;

    /** Creates a new instance of ArvoreAction */
    public ArvoreAction() {
    }

    public List<ArvoreDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<ArvoreDTO> lista) {
        this.lista = lista;
    }

    public ArvoreDTO getArvoreDTO() {
        if (arvoreDTO == null) {
            arvoreDTO = new ArvoreDTO();
        }
        return arvoreDTO;
    }

    public void setArvoreDTO(ArvoreDTO arvoreDTO) {
        this.arvoreDTO = arvoreDTO;
    }

    /**
     * Instancia um arvoreDAO e invoca o método listar().
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String listar() {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        lista = arvoreDAO.listar();
        return "listar";
    }

    /**
     * Instancia um arvoreDAO, obtem o idTipoDocumento e invoca o método excluir
     * @author livia.miura 
     * @return String listar, para navegação.
     **/
    public String excluir() {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        getArvoreDTO().setIdArvore(new Integer(request.getParameter("idArvore")));
        arvoreDAO.excluir(getArvoreDTO());
        return "listar";
    }

    /**
     * Controle de navegação
     * @author livia.miura
     * @return String inserir, para navegação.
     **/
    public String inserir() {
       getArvoreDTO().setIdArvore(0);
        return "inserir";
    }

    /**
     * Instancia um arvoreDAO, invoca o método gravar, passando  o objeto arvoreDTO
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String gravar() {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        arvoreDAO.gravar(getArvoreDTO());
        return "listar";
    }

    /**
     * Instancia um arvoreDAO, obtem o idTipoDocumento e invoca o método selecionar, passando o objeto arvoreDTO
     * @author livia.miura
     * @return String inserir, para navegação.
     **/
    
    
    public String alterar() {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        getArvoreDTO().setIdArvore(new Integer(request.getParameter("idArvore")));
        setArvoreDTO(arvoreDAO.selecionar(getArvoreDTO()));
        return "inserir";
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