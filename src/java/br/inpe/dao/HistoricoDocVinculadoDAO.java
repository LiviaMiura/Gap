/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.HistoricoDocVinculadoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class HistoricoDocVinculadoDAO {

    /**
     * Recupera todos os registros da tabela historico.
     *
     * @author livia.miura
     * @return List - Lista preenchida com o historico de entrada e saida de
     * cada usuario
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("HistoricoDocVinculadoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela historico, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param historicoDocVinculadoDTO
     * @return HistoricoDocVinculadoDTO - preenchido com o registro selecionado.
     *
     */
    public HistoricoDocVinculadoDTO selecionar(HistoricoDocVinculadoDTO historicoDocVinculadoDTO) {
        return (HistoricoDocVinculadoDTO) Database.manager.find(HistoricoDocVinculadoDTO.class, historicoDocVinculadoDTO.getIdHistDocVinc());
    }

    /**
     * Grava ou atualiza um registro na tabela historico.
     *
     * @author livia.miura
     * @param historicoDocVinculadoDTO
     * @return nao se aplica
     *
     */
    public void gravar(HistoricoDocVinculadoDTO historicoDocVinculadoDTO) {

        Database.manager.getTransaction().begin();
        if (historicoDocVinculadoDTO.getIdHistDocVinc() == null) {
            Database.manager.persist(historicoDocVinculadoDTO); // gravar
        } else {

            Database.manager.merge(historicoDocVinculadoDTO); // atualizar
        }

        Database.manager.getTransaction().commit();
    }

    /**
     * Exclui um registro da tabela registrologin, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param HistoricoDocVinculadoDTO historicoDocVinculadoDTO
     */
    public void excluir(HistoricoDocVinculadoDTO historicoDocVinculadoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(HistoricoDocVinculadoDTO.class, historicoDocVinculadoDTO.getIdHistDocVinc())); //Remove dados do Historico
        Database.manager.getTransaction().commit();

    }
}
