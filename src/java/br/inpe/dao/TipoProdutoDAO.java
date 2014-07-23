/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.TipoDocumentoDTO;
import br.inpe.dto.TipoProdutoDTO;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author livia.miura
 */
public class TipoProdutoDAO {
//Database Database = new Database(); 

    /**
     * Recupera todos os registros da tabela saida. author livia.miura
     *
     * @return List - Lista preenchida com cargos.
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("TipoProdutoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela saida, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param tipoProdutoDTO
     * @return TipoProdutoDTO - preenchido com o registro selecionado.
     *
     */
    public TipoProdutoDTO selecionar(TipoProdutoDTO tipoProdutoDTO) {
        return (TipoProdutoDTO) Database.manager.find(TipoProdutoDTO.class, tipoProdutoDTO.getIdTipoProduto());
    }

    /**
     * Grava um registro na tabela saida.
     *
     * @author livia.miura
     * @param tipoProdutoDTO
     *
     */
    public void gravar(TipoProdutoDTO tipoProdutoDTO) {
        Database.manager.getTransaction().begin();
        if (tipoProdutoDTO.getIdTipoProduto() == 0) {
            Database.manager.persist(tipoProdutoDTO); // gravar   
        } else {
            Database.manager.merge(tipoProdutoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param tipoProdutoDTO
     *
     */   
     public String excluir(TipoProdutoDTO tipoProdutoDTO) {
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
           Database.manager.remove(Database.manager.find(TipoProdutoDTO.class, tipoProdutoDTO.getIdTipoProduto()));
    Database.manager.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Tipo de Produto foi excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o Tipo de Produto."
                    + "O mesmo está sendo usado no Nível 2 da Árvore de Produto.",
                    ""));
            retorno = null;
        }
        return retorno;
    }
    
    
}
