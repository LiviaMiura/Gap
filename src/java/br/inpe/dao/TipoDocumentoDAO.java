/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.TipoDocumentoDTO;
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
public class TipoDocumentoDAO {
//Database Database = new Database(); 

    /**
     * Recupera todos os registros da tabela saida. author livia.miura
     *
     * @return List - Lista preenchida com cargos.
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("TipoDocumentoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela saida, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param tipoDocumentoDTO
     * @return TipoDocumentoDTO - preenchido com o registro selecionado.
     *
     */
    public TipoDocumentoDTO selecionar(TipoDocumentoDTO tipoDocumentoDTO) {
        return (TipoDocumentoDTO) Database.manager.find(TipoDocumentoDTO.class, tipoDocumentoDTO.getIdTipoDocumento());
    }

    /**
     * Grava um registro na tabela saida.
     *
     * @author livia.miura
     * @param tipoDocumentoDTO
     *
     */
    public void gravar(TipoDocumentoDTO tipoDocumentoDTO) {
        Database.manager.getTransaction().begin();
        if (tipoDocumentoDTO.getIdTipoDocumento() == 0) {
            Database.manager.persist(tipoDocumentoDTO); // gravar   
        } else {
            Database.manager.merge(tipoDocumentoDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param tipoDocumentoDTO
     *
     */
    public String excluir(TipoDocumentoDTO tipoDocumentoDTO) {
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
            Database.manager.remove(Database.manager.find(TipoDocumentoDTO.class, tipoDocumentoDTO.getIdTipoDocumento()));
            Database.manager.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Tipo de Documento foi excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o Tipo de Documento."
                    + "O mesmo está sendo usado em um Documento.",
                    ""));
            retorno = null;
        }
        return retorno;
    }
}
