/** 
  * Projeto : GAP
  * Nome Arquivo : UnidadeDAO.Java
  * UnidadeoDAO - Manipula dados referentes a tabela Unidade
  * @return : 
  * @author : castro
  */

package br.inpe.dao;
import br.inpe.dto.UnidadeDTO;
import br.inpe.dto.UsuarioDTO;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
/**
* Construtor da classe UnidadeDAO
* author castro
* @return 
**/
public class UnidadeDAO {
 //   Database Database = new Database(); 
    /** 
     * Recupera todos os registros da tabela Unidade.
     * author castro
     * @return List - Lista preenchida com dados da unidades cadastradas.
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("UnidadeDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
     }
    
    /** 
    * Selecionar objeto da tabela Unidade.
    * author castro
    * @return UnidadeDTO
    **/
    //Metodo selecionar será chamado dentro do arquivo : UnidadeAction.java
    public UnidadeDTO selecionar(UnidadeDTO unidadeDTO) {
        return (UnidadeDTO) Database.manager.find(UnidadeDTO.class, unidadeDTO.getIdUnidade());
    }
      
    
    /**
     * Grava um registro na tabela Unidade.
     * author castro
     * @param UnidadeDTO unidadeDTO
     * @return String
     **/
    public String gravar(UnidadeDTO unidadeDTO) {

    String retorno = "listar";
    Database.manager.getTransaction().begin();
    if (unidadeDTO.getIdUnidade() == 0) {
        Database.manager.persist(unidadeDTO); // gravar
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                "Unidade Registrada com Sucesso!",
                ""));
    } else {
        Database.manager.merge(unidadeDTO); // atualizar
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                "Usuário Atualizado com Sucesso!",
                ""));
    }
    try {
        Database.manager.getTransaction().commit();
        retorno = "listar";
    } catch (Exception e) {

       
        retorno = null;
    }
    return retorno;
    }

    /**
    * Exclui um registro da tabela Unidade, dado um objeto especifico
    * author castro
    * @param UnidadeDTO unidadeDTO
    **/
    
       public String excluir(UnidadeDTO unidadeDTO) {
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
          Database.manager.remove(Database.manager.find(UnidadeDTO.class, unidadeDTO.getIdUnidade()));
          Database.manager.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Unidade Excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o Unidade."
                    + "O mesmo está sendo usado por algum Usuário.",
                    ""));
            retorno = null;
        }
        return retorno;
    }  
}
