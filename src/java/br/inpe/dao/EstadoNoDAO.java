/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.EstadoNoDTO;
import br.inpe.dto.TipoProdutoDTO;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class EstadoNoDAO {
//Database Database = new Database(); 

    /**
     * Recupera todos os registros da tabela saida. author livia.miura
     *
     * @return List - Lista preenchida com cargos.
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("EstadoNoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela saida, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param estadoNoDTO
     * @return EstadoNoDTO - preenchido com o registro selecionado.
     *
     */
    public EstadoNoDTO selecionar(EstadoNoDTO estadoNoDTO) {
        return (EstadoNoDTO) Database.manager.find(EstadoNoDTO.class, estadoNoDTO.getIdEstadoNo());
    }

    /**
     * Grava um registro na tabela saida.
     *
     * @author livia.miura
     * @param estadoNoDTO
     *
     */
    public void gravar(EstadoNoDTO estadoNoDTO) {
        Database.manager.getTransaction().begin();
        if (estadoNoDTO.getIdEstadoNo() == 0) {

            Database.manager.persist(estadoNoDTO); // gravar  
        } else {
            Database.manager.merge(estadoNoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param estadoNoDTO
     *
     */

    
    
    public String excluir(EstadoNoDTO estadoNoDTO) {
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
    Database.manager.remove(Database.manager.find(EstadoNoDTO.class, estadoNoDTO.getIdEstadoNo()));
       Database.manager.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Estado do Produto foi excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o Estado."
                    + "O mesmo está sendo usado na Árvore de Produto.",
                    ""));
            retorno = null;
        }
        return retorno;
    }
}
