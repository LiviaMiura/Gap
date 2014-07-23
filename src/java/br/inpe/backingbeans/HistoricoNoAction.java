/*
 * Nome do Arquivo: HistoricoNoAction.java
 * Autor: Marcos Castro
 * Descrição: Lista, Inclui , Altera e Excluir dados relacionados ao Historico do No. 
 */
package br.inpe.backingbeans;

import br.inpe.dao.HistoricoNoDAO;
import br.inpe.dto.HistoricoNoDTO;
import java.util.List;

/**
 *
 * @author MARCOS
 */
public class HistoricoNoAction {

    /**
     * Creates a new instance of HistoricoNoAction
     */
       
    private HistoricoNoDTO historicoNoDTO;
    private List<HistoricoNoDTO> lista;
    
    public HistoricoNoAction() {
    }
    
    //***************************************************************************
     public List<HistoricoNoDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<HistoricoNoDTO> lista) {
        this.lista = lista;
    }
    
    //***************************************************************************
    
    public HistoricoNoDTO getHistoricoNoDTO() {
        if (historicoNoDTO == null) {
            historicoNoDTO = new HistoricoNoDTO();
        }
        return historicoNoDTO;
    }

        public void setHistoricoNoDTO(HistoricoNoDTO historicoNoDTO) {
        this.historicoNoDTO = historicoNoDTO;
    }
//******************************************************************************

    /**
     * Lista objeto historicoDAO
     * @author Castro
     * @return String listar, para navegação.
     **/
    public String listar() {
        HistoricoNoDAO historicoNoDAO = new HistoricoNoDAO();
        lista = historicoNoDAO.listar();
        return "listar";
    }
}