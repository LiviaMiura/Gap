/** 
  * Projeto : GAP
  * Nome Arquivo : HistoricoDAO.Java
  * HistoricoDAO - Mantem o historico de entrada e saida de cada usuario
  * @return : 
  * @author : castro
  */

package br.inpe.dao;

import br.inpe.dto.HistoricoDTO;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;


/**
* Construtor da classe HistoricoDAO
* author castro
* @return 
**/
public class HistoricoDAO {
   // Database Database = new Database(); 
    /** 
     * Recupera todos os registros da tabela historico.
     * author castro
     * @return List - Lista preenchida com o historico de entrada e saida de cada usuario
     **/
    public List listar() {
        Query query = Database.manager.createNamedQuery("HistoricoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela historico, com base no valor da chave primária.
     * author castro
     * @param  historicoDTO
     * @return HistoricoDTO - preenchido com o registro selecionado.
     **/
    public HistoricoDTO selecionar(HistoricoDTO historicoDTO) {
        return (HistoricoDTO) Database.manager.find(HistoricoDTO.class, historicoDTO.getIdHistorico());
    }

    /**
     * Grava ou atualiza um registro na tabela historico.
     * author castro
     * @param  historicoDTO
     * @return nao se aplica
     **/
    public void gravar(HistoricoDTO historicoDTO) {

        Database.manager.getTransaction().begin();
        if (historicoDTO.getIdHistorico() == null) {
            Database.manager.persist(historicoDTO); // gravar
            System.out.println("PASSEI NO GRAVAR  SAIDA ");   
          
        } else {
          
        Database.manager.merge(historicoDTO); // atualizar
            
             System.out.println("PASSEI NO ATUALIZAR SAIDA");
        }
       
            Database.manager.getTransaction().commit();
    }
  
    
 
    /**
     * Exclui um registro da tabela registrologin, com base no valor da chave primária.
     * author castro
     * @param HistoricoDTO historicoDTO
     **/
    
    public void excluir(HistoricoDTO historicoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(HistoricoDTO.class, historicoDTO.getIdHistorico()));//Remove dados do Historico
        Database.manager.getTransaction().commit();

    }
    
    
}
