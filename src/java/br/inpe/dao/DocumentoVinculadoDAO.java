/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.*;
import java.util.Collection;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class DocumentoVinculadoDAO {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);// mantém na session
    private UsuarioDTO usuarioDTO;

    //**************************************************************************
    public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
    
    
     public List listarStatus2(int status, int statusx) {
        System.out.println("status = "+status+"statusx"+ statusx);
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByStatus2").setParameter("status",status).setParameter("status", statusx);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listar = query.getResultList();
        return listar;
     }
    /**
     * Recupera todos os registros da tabela saida.
     *
     * @author livia.miura
     *
     * @return List - Lista preenchida com cargos.
     *
     */
    public List listar() {
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }
    
    
 
  
    public Collection<DocumentoVinculadoDTO> listarDocumento(Collection<DocumentoVinculadoDTO> listaDocumento) {
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listaDocumento = query.getResultList();
        return listaDocumento;


    }
    /**
     * Recupera um registro da tabela documentoVinculado, com base no valor da
     * chave primária.
     *
     * @author livia.miura
     * @param documentoVinculadoDTO
     * @return DocumentoVinculadoDTO - preenchido com o registro selecionado.
     *
     */
    public DocumentoVinculadoDTO selecionar(DocumentoVinculadoDTO documentoVinculadoDTO) {
        return (DocumentoVinculadoDTO) Database.manager.find(DocumentoVinculadoDTO.class, documentoVinculadoDTO.getIdDocumentoVinculado());
    }
  

    /**
     * Grava um registro na tabela documentoVinculado.
     *
     * @author livia.miura
     * @param documentoVinculadoDTO
     *
     */
    public void gravar(DocumentoVinculadoDTO documentoVinculadoDTO) {

     //   HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
     //   HistoricoDocVinculadoDTO historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();
      //  historicoDocVinculadoDTO.setDocumentoVinculadofk(documentoVinculadoDTO);

        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        System.out.println("valor de usuario ===" + usuarioDTO.getNome());

        Database.manager.getTransaction().begin();

        if (documentoVinculadoDTO.getIdDocumentoVinculado() == 0) {
            System.out.println("gravei o valor no DAO id doc = " + documentoVinculadoDTO.getIdDocumentoVinculado());
            Database.manager.persist(documentoVinculadoDTO); // gravar 
     //       historicoDocVinculadoDTO.setTipoAlteracao("Novo Doc Vinculado");
      //      historicoDocVinculadoDTO.setUsuarioOperacao(usuarioDTO.getNome());
        }
        Database.manager.getTransaction().commit();
     //   historicoDocVinculadoDAO.gravar(historicoDocVinculadoDTO);
    }
    
    

    public void gravarAlterar(DocumentoVinculadoDTO documentoVinculadoDTO) {

        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        HistoricoDocVinculadoDTO historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();
        historicoDocVinculadoDTO.setDocumentoVinculadofk(documentoVinculadoDTO);

        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        System.out.println("usuario = " + usuarioDTO.getNome());



        Database.manager.getTransaction().begin();


        if (documentoVinculadoDTO.getIdDocumentoVinculado() != 0) {
            Database.manager.merge(documentoVinculadoDTO); // atualizar
            historicoDocVinculadoDTO.setTipoAlteracao("Doc Alterado");
            historicoDocVinculadoDTO.setUsuarioOperacao(usuarioDTO.getNome());

        }

        Database.manager.getTransaction().commit();
        historicoDocVinculadoDAO.gravar(historicoDocVinculadoDTO);
    }

    public void gravarCodigoDocumento(DocumentoVinculadoDTO documentoVinculadoDTO) {

        Database.manager.getTransaction().begin();
        if (documentoVinculadoDTO.getIdDocumentoVinculado() != 0) {
            Database.manager.merge(documentoVinculadoDTO); // atualizar        
        }
        Database.manager.getTransaction().commit();
    }

    /**
     * Exclui um registro da tabela documentoVinculadoDTO, com base no valor da
     * chave primária.
     *
     * @author livia.miura
     * @param documentoVinculadoDTO
     *
     */
    public void excluir(DocumentoVinculadoDTO documentoVinculadoDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.remove(Database.manager.find(DocumentoVinculadoDTO.class, documentoVinculadoDTO.getIdDocumentoVinculado()));
        Database.manager.getTransaction().commit();

    }

    /**
     * Recupera todos os registros da tabela Documento Vinculado que tenham o
     * titulo selecionado
     *
     * @author livia.miura
     * @return List - Lista preenchida que contenham titulo selecionado.
     *
     */
    public List<DocumentoVinculadoDTO> listarPorTitulo(DocumentoVinculadoDTO documentoVinculadoDTO) {

        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByTitulo").setParameter("titulo", "%" + documentoVinculadoDTO.getTitulo() + "%"); // que contenham parte da palavra


        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public Collection<TipoDocumentoDTO> listarTipoDocumentoItem(Collection<TipoDocumentoDTO> lista) {
        Query query = Database.manager.createNamedQuery("TipoDocumentoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        lista = query.getResultList();
        return lista;
    }

  public List<DocumentoVinculadoDTO> listarPorDocumentoVinculadoID(DocumentoVinculadoDTO documentoVinculadoDTO) {
        
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByIdDocumentoVinculado").setParameter("idDocumentoVinculado", +documentoVinculadoDTO.getIdDocumentoVinculado()); // que contenham parte da palavra
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    
}
  
  
    /*
     * Desabilitará um registro da tabela documentoVinculado, com base no valor da chave primária.
     * @author livia.miura
     * @param documentoVinculadoDTO
     *
     */
    public void desabilitar(DocumentoVinculadoDTO documentoVinculadoDTO) {
        System.out.println("/*desabilitar");
        
         Database.manager.getTransaction().begin();
        if (documentoVinculadoDTO.getIdDocumentoVinculado() == 0) {
            Database.manager.persist(documentoVinculadoDTO); // gravar   
            System.out.println("gravar************************");
        } else {
            Database.manager.merge(documentoVinculadoDTO); // atualizar
            System.out.println("/*atualizar*********************");
        }
        Database.manager.getTransaction().commit();
   }
     /**
     * Recupera todos os registros da tabela iItemdoNoDTO
     * @author livia.miura
     **/
    public List<DocumentoVinculadoDTO> listarComp(int idDocumentoVinculado) {
        System.out.println("/*list no docvinculado dao");
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByIdDocumentoVinculado").setParameter("idDocumentoVinculado", idDocumentoVinculado);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<DocumentoVinculadoDTO> lista = query.getResultList();
        return lista;
    }  
    
    
        public List<DocumentoVinculadoDTO> listarDocumentoUtilizado(int noPai, String codigoProduto) {
       System.out.println("/*id = "+ noPai);
            System.out.println("cidigo===="+ codigoProduto);
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByCodigo").setParameter("noPai", noPai).setParameter("codigoProduto", codigoProduto);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<DocumentoVinculadoDTO> lista = query.getResultList();
        
         System.out.println("lista =="+ lista);
        return lista;

    }

  public List<DocumentoVinculadoDTO> listarProdutoNoDocumento(int noPai, int status) {
       System.out.println("/*id = "+ noPai);
            System.out.println("id Produto===="+ noPai);
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByCodigoPai").setParameter("noPai", noPai).setParameter("status",status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<DocumentoVinculadoDTO> lista = query.getResultList();
        
         System.out.println("lista =="+ lista);
        return lista;

    }
  
   // pega os valor do status = a 1 
     public List listarStatus(int status){
         //    Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", itemdoNoDTO.getNoFk().getIdNo());
         Query query =  Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByStatus").setParameter("status",status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listar = query.getResultList();
        return listar;
         
        //return Database.manager.createNamedQuery("ItemdoNoDTO.findByStatus").setParameter("status",status).getResultList();
    }
     
     
     //******************consultas teste tipo de cdocumento menu  consulta 
       public List<DocumentoVinculadoDTO> listarPorTipoDocumentoID(DocumentoVinculadoDTO documentoVinculadoDTO) {
        System.out.println("entrei no dao item  10 do documento vinculado");
  
         System.out.println("entrei no DAO do item ===="+ documentoVinculadoDTO.getTipoDocumentofk().getIdTipoDocumento());
        Query query = Database.manager.createNamedQuery("DocumentoVinculadoDTO.findByTipoDocumentoID").setParameter("idTipoDocumento", +documentoVinculadoDTO.getTipoDocumentofk().getIdTipoDocumento()).setParameter("status", documentoVinculadoDTO.getStatus()); 
      
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        System.out.println("entro do DAO=== " + documentoVinculadoDTO.getTipoDocumentofk().getIdTipoDocumento());

        return lista;
    }
       
       
       
}
