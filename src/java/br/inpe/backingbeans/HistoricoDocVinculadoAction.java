/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;


import br.inpe.dao.HistoricoDocVinculadoDAO;
import br.inpe.dto.HistoricoDocVinculadoDTO;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author livia.miura
 */

public class HistoricoDocVinculadoAction  implements Serializable {

    
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();     
    public HistoricoDocVinculadoDTO historicoDocVinculadoDTO;
    public Collection<HistoricoDocVinculadoDTO> lista;
    
    /**
     * Creates a new instance of HistoricoDocVinculadoAction
     */
    public HistoricoDocVinculadoAction() {
    }
    
  

     public Collection<HistoricoDocVinculadoDTO> getLista() {
         HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
       lista = historicoDocVinculadoDAO.listar();
        return lista;
    }
    
     public void setLista(List<HistoricoDocVinculadoDTO> lista) {
        this.lista = lista;
    }
  
    public HistoricoDocVinculadoDTO getHistoricoDocVinculadoDTO() {
        if (historicoDocVinculadoDTO == null) {
            historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();
        }
        return historicoDocVinculadoDTO;
    }

    
    public void setHistoricoDocVinculadoDTO(HistoricoDocVinculadoDTO historicoDocVinculadoDTO) {
        this.historicoDocVinculadoDTO = historicoDocVinculadoDTO;
    }
    
    public String listar() {
        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        lista = historicoDocVinculadoDAO.listar();
        return "listar";
    }
   
    public String excluir() {
        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        getHistoricoDocVinculadoDTO().setIdHistDocVinc(new Integer(request.getParameter("idHistDocVinc")));
        historicoDocVinculadoDAO.excluir(getHistoricoDocVinculadoDTO());
        return "listar";
    }
    
    public String inserir() {
        getHistoricoDocVinculadoDTO().setIdHistDocVinc(0);
        return "inserir";
    }
   
    public String gravar() {
        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        historicoDocVinculadoDAO.gravar(getHistoricoDocVinculadoDTO());
        return "listar";
    }
  
    public String alterar() {
        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        getHistoricoDocVinculadoDTO().setIdHistDocVinc(new Integer(request.getParameter("idHistDocVinc")));
        setHistoricoDocVinculadoDTO(historicoDocVinculadoDAO.selecionar(getHistoricoDocVinculadoDTO()));
        return "inserir";
    }

    public String menu() {
        return "menu";
    }
}


