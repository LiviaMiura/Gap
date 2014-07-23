/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.Database;
import br.inpe.dao.TipoProdutoDAO;
import br.inpe.dto.TipoProdutoDTO;
import br.inpe.dto.UsuarioDTO;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author livia.miura
 */
public class TipoProdutoAction {


  
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    private TipoProdutoDTO tipoProdutoDTO;
    private List<TipoProdutoDTO> lista;

      /** Creates a new instance of TipoProdutoAction */
    public TipoProdutoAction() {
  
    }
    
   public List<TipoProdutoDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<TipoProdutoDTO> lista) {
        this.lista = lista;
    }

    public TipoProdutoDTO getTipoProdutoDTO() {
        if (tipoProdutoDTO == null) {
            tipoProdutoDTO = new TipoProdutoDTO();
        }
        return tipoProdutoDTO;
    }

    public void setTipoProdutoDTO(TipoProdutoDTO tipoProdutoDTO) {
        this.tipoProdutoDTO = tipoProdutoDTO;
    }

    /**
     * 
     * 
     * 
     * Instancia um tipoProdutoDAO e invoca o método listar().
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String listar() {
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
        lista = tipoProdutoDAO.listar();
        return "listar";
    }

    /**
     * Instancia um tipoProdutoDAO, obtem o idTipoDocumento e invoca o método excluir
     * @author livia.miura 
     * @return String listar, para navegação.
     **/
    public String excluir() throws Exception {
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
        getTipoProdutoDTO().setIdTipoProduto(new Integer(request.getParameter("idTipoProduto")));
        tipoProdutoDAO.excluir(getTipoProdutoDTO());
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
     * Instancia um tipoProdutoDAO, invoca o método gravar, passando  o objeto tipoProdutoDTO
     * @author livia.miura
     * @return String listar, para navegação.
     **/
    public String gravar() {
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
        tipoProdutoDAO.gravar(getTipoProdutoDTO());
        return "listar";
    }

    /**
     * Instancia um tipoProdutoDAO, obtem o idTipoDocumento e invoca o método selecionar, passando o objeto tipoProdutoDTO
     * @author livia.miura
     * @return String inserir, para navegação.
     **/
    public String alterar() {
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
        getTipoProdutoDTO().setIdTipoProduto(new Integer(request.getParameter("idTipoProduto")));
        setTipoProdutoDTO(tipoProdutoDAO.selecionar(getTipoProdutoDTO()));
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