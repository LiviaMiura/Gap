/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.ArvoreDTO;
import br.inpe.dto.NoDTO;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class ArvoreDAO {
Database Database = new Database(); 
    /**
     * Recupera todos os registros da tabela saida. author livia.miura
     *
     * @return List - Lista preenchida com cargos.
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("ArvoreDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela saida, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param arvoreDTO
     * @return ArvoreDTO - preenchido com o registro selecionado.
     *
     */
    public ArvoreDTO selecionar(ArvoreDTO arvoreDTO) {
        return (ArvoreDTO) Database.manager.find(ArvoreDTO.class, arvoreDTO.getIdArvore());
    }

    /**
     * Atualiza um registro na tabela arvore.
     *
     * @author livia.miura
     * @param arvoreDTO
     *
     */
    public void atualizarArvore(ArvoreDTO arvoreDTO) {
        Database.manager.getTransaction().begin();
        if (arvoreDTO.getIdArvore() != 0) {
            System.out.println("entrei atualizar DAO");
            Database.manager.merge(arvoreDTO); // atualizar
        }
        Database.manager.getTransaction().commit();
    }

    /** ok
     * Grava um registro na tabela arvoreDTO.
     * usado no método gravarArvore
     * @author livia.miura
     * @param arvoreDTO
     *
     */
    public void gravar(ArvoreDTO arvoreDTO) {
        Database.manager.getTransaction().begin();
        if (arvoreDTO.getIdArvore() == null) {
            System.out.println("entrei gravar DAO");
            Database.manager.persist(arvoreDTO); // gravar   
        } else {
            Database.manager.merge(arvoreDTO); // atualizar
        }
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param arvoreDTO
     *
     */
    public void excluir(ArvoreDTO arvoreDTO) {
        System.out.println("entrei no excluir da arvore");
        System.out.println("valor do ID =" + arvoreDTO.getIdArvore());
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(ArvoreDTO.class, arvoreDTO.getIdArvore()));
        Database.manager.getTransaction().commit();
         
    }

    public Collection<ArvoreDTO> listarNo() {

        Query query = Database.manager.createNamedQuery("ArvoreDTO.findByJoin");

        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        Collection<ArvoreDTO> lista = query.getResultList();
        return lista;

    }

    public Collection<ArvoreDTO> listar(Collection<ArvoreDTO> lista) {
        Query query = Database.manager.createNamedQuery("ArvoreDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        lista = query.getResultList();
        return lista;
    }

    public List<ArvoreDTO> selecionarPorNo(NoDTO noDTO) {
        Query query = Database.manager.createNamedQuery("ArvoreDTO.findByNo").setParameter("idNo", noDTO.getIdNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

   /* public List<ArvoreDTO> listarArvore(int idNo) {

        Query query = Database.manager.createNamedQuery("ArvoreDTO.findByJoin").setParameter("idNo", idNo);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<ArvoreDTO> lista = query.getResultList();
        return lista;

    }*/
    
     public List<ArvoreDTO> listarPai(int noPai, String codigo) {
         System.out.println("no pai = "+ noPai);
        Query query = Database.manager.createNamedQuery("ArvoreDTO.findByNoPai").setParameter("noPai", noPai).setParameter("codigo", codigo);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<ArvoreDTO> lista = query.getResultList();
        
         System.out.println("lista =="+ lista);
        return lista;

    }
       public List<ArvoreDTO> listarFilho(int noFilho, String codigo) {
         System.out.println("no noFilho = "+ noFilho);
        Query query = Database.manager.createNamedQuery("ArvoreDTO.findByNoFilho2").setParameter("noFilho", noFilho).setParameter("codigo", codigo);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<ArvoreDTO> lista = query.getResultList();
        
         System.out.println("lista =="+ lista);
        return lista;

    }
}