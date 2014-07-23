/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.ConfigurarEmailDTO;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class ConfigurarEmailDAO extends Database {

    /**
     * Recupera todos os registros da tabela configuraremail
     *
     * @author livia.miura
     * @return
     */
    public List listar() {
        return manager.createNamedQuery("ConfigurarEmailDTO.findAll").getResultList();
    }

    /**
     * Recupera o registro com base no valor da chave primária
     *
     * @author livia.miura
     *
     * @param configurarEmailDTO
     * @return
     */
    public ConfigurarEmailDTO selecionar(ConfigurarEmailDTO configurarEmailDTO) {
        return manager.find(ConfigurarEmailDTO.class, configurarEmailDTO.getId());
    }

    /**
     * Altera um registro na tabela configuraremail
     *
     * @author livia.miura
     *
     * @param configurarEmailDTO
     */
    public void gravar(ConfigurarEmailDTO configurarEmailDTO) {
        manager.getTransaction().begin();
        manager.merge(configurarEmailDTO); // atualizar
        manager.getTransaction().commit();

    }

    /**
     * Lista todos as configurações
     *
     * @author livia.miura
     *
     * @param lista
     * @return lista
     */
    public Collection<ConfigurarEmailDTO> listar(Collection<ConfigurarEmailDTO> lista) {
        Query query = manager.createNamedQuery("ConfigurarEmailDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        lista = query.getResultList();
        return lista;
    }
}
