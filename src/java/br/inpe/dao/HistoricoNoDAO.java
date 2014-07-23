/*
 * Nome do Arquivo: HistoricoNoDAO
 * Autor: Marcos Castro
 * Descrição: Historico do No 
 */
package br.inpe.dao;

import br.inpe.dto.HistoricoNoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author MARCOS
 */
public class HistoricoNoDAO {

    /**
     * Recupera todos os registros da tabela historico. 
     *
     * @return List - Lista preenchida com o historico de entrada e saida de
     * cada usuario
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("HistoricoNoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela historico, com base no valor da chave
     * primária. author castro
     *
     * @param historicoNoDTO
     * @return HistoricoDTO - preenchido com o registro selecionado.
     *
     */
    public HistoricoNoDTO selecionar(HistoricoNoDTO historicoNoDTO) {
        return (HistoricoNoDTO) Database.manager.find(HistoricoNoDTO.class, historicoNoDTO.getIdhistoricoNo());
    }

    /**
     * Grava ou atualiza um registro na tabela historico. 
     *
     * @param historicoNoDTO
     * @return nao se aplica
     *
     */
    public void gravar(HistoricoNoDTO historicoNoDTO) {

        Database.manager.getTransaction().begin();
        historicoNoDTO.setOperacao("Produto Novo");
        Database.manager.persist(historicoNoDTO); // grava    
        Database.manager.getTransaction().commit();
    }
 /**
     * Atualiza um registro na tabela historico. 
     *
     * @param historicoNoDTO
     * @return nao se aplica
     *
     */
    public void gravarAlterado(HistoricoNoDTO historicoNoDTO) {

        Database.manager.getTransaction().begin();
        historicoNoDTO.setOperacao("Produto Alterado");
        Database.manager.merge(historicoNoDTO); // atualizar
        Database.manager.getTransaction().commit();
    }

    
    public void gravarExcluido(HistoricoNoDTO historicoNoDTO) {

        Database.manager.getTransaction().begin();
       historicoNoDTO.setOperacao("Produto Excluído");
        Database.manager.merge(historicoNoDTO); // atualizar
        Database.manager.getTransaction().commit();
    }
    /**
     * Exclui um registro da tabela registrologin, com base no valor da chave
     * primária. author castro
     *
     * @param historicoNoDTO
     *
     */
    public void excluir(HistoricoNoDTO historicoNoDTO) {
       
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(HistoricoNoDTO.class, historicoNoDTO.getIdhistoricoNo()));//Remove dados do Historico
        Database.manager.getTransaction().commit();

    }
}
