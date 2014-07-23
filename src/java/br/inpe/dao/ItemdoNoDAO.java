/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.*;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class ItemdoNoDAO {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
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

    // pega os valor do status = a 1 
    public List listarStatus(int status) {
        //    Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", itemdoNoDTO.getNoFk().getIdNo());
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByStatus").setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listar = query.getResultList();
        return listar;

        //return Database.manager.createNamedQuery("ItemdoNoDTO.findByStatus").setParameter("status",status).getResultList();
    }
 public List listarStatus2(int status, int statusx) {
        //    Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", itemdoNoDTO.getNoFk().getIdNo());
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByStatus2").setParameter("status",status).setParameter("status", statusx);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listar = query.getResultList();
        return listar;

        //return Database.manager.createNamedQuery("ItemdoNoDTO.findByStatus").setParameter("status",status).getResultList();
    }
    
 

    
    public Collection<ItemdoNoDTO> listarItem(Collection<ItemdoNoDTO> listaItem) {
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listaItem = query.getResultList();
        return listaItem;


    }

    public List listar() {
        System.out.println("entrei no dao item  1");
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela saida, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param itemdoNoDTO
     * @return ItemdoNoDTO - preenchido com o registro selecionado.
     *
     */
    public ItemdoNoDTO selecionar(ItemdoNoDTO itemdoNoDTO) {
        System.out.println("entrei no dao item  2");
        return (ItemdoNoDTO) Database.manager.find(ItemdoNoDTO.class, itemdoNoDTO.getIdItemdoNo());
    }

    public ItemdoNoDTO selecionarNo(ItemdoNoDTO itemdoNoDTO) {
        System.out.println("entrei no dao item  3");
        return (ItemdoNoDTO) Database.manager.find(ItemdoNoDTO.class, itemdoNoDTO.getNoFk().getIdNo());
    }

    /**
     * Grava um registro na tabela itemdoNoDTO.
     *
     * @author livia.miura
     * @param itemdoNoDTO
     *
     */
    public String gravar(ItemdoNoDTO itemdoNoDTO) {
        String retorno = null;

        try {
            if (itemdoNoDTO.getIdItemdoNo() == 0) {

                Database.manager.persist(itemdoNoDTO); // gravar

               FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                        "Documento Registrado com Sucesso!",
                        ""));

            } else {
                System.out.println("atualizei");
                Database.manager.merge(itemdoNoDTO);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                        "Documento Alterado com Sucesso!",
                        ""))
                        ;
            }

            Database.manager.getTransaction().commit();

            retorno = "backtree";
        } catch (Exception e) {
        }

      
        return retorno;
    }

    /**
     * ok Grava um registro na tabela noDTO. usado no método gravarArvore
     *
     * @author livia.miura
     * @param itemdoNoDTO
     *
     */
    public void atualizarNo(NoDTO noDTO) {
        System.out.println("entrei no dao item  5");

        Database.manager.getTransaction().begin();
        if (noDTO.getIdNo() == 0) {
            Database.manager.persist(noDTO); // gravar   
            System.out.println("gravar************************");
        } else {
            Database.manager.merge(noDTO); // atualizar
            System.out.println("atualizar*********************");
        }
        Database.manager.getTransaction().commit();
    }

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param itemdoNoDTO
     *
     */
    public void desabilitar(ItemdoNoDTO itemdoNoDTO) {
        System.out.println("/*desabilitar");

        Database.manager.getTransaction().begin();
        if (itemdoNoDTO.getIdItemdoNo() == 0) {
            Database.manager.persist(itemdoNoDTO); // gravar   
            System.out.println("gravar************************");
        } else {
            Database.manager.merge(itemdoNoDTO); // atualizar
            System.out.println("/*atualizar*********************");
        }
        Database.manager.getTransaction().commit();
    }
    
     

    /**
     * Exclui um registro da tabela saida, com base no valor da chave primária.
     *
     * @author livia.miura
     * @param itemdoNoDTO
     *
     */
    public String excluir(ItemdoNoDTO itemdoNoDTO, DocumentoVinculadoDTO documentoVinculadoDTO) {
        System.out.println("entrei no dao item  6");
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
            System.out.println(" Item = " + itemdoNoDTO.getIdItemdoNo() + "doc ==" + documentoVinculadoDTO.getIdDocumentoVinculado());

            // está excluido o item e o documento vinculado
            Database.manager.remove(Database.manager.find(ItemdoNoDTO.class, itemdoNoDTO.getIdItemdoNo()));
            Database.manager.remove(Database.manager.find(DocumentoVinculadoDTO.class, documentoVinculadoDTO.getIdDocumentoVinculado()));

            Database.manager.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Documento Excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o Documento."
                    + "O mesmo está sendo usado em algum Produto.",
                    ""));
            retorno = null;
        }
        return retorno;
    }

    /**
     * Recupera todos os registros da tabela ItemdoNoDTO que tenham o idNó
     * selecionado
     *
     * @author livia.miura
     *
     * @return List - Lista preenchida que contenham idNo selecionado. usado
     * 03-05-13
     */
    public List<ItemdoNoDTO> selecionarPorNo(ItemdoNoDTO itemdoNoDTO) {
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", itemdoNoDTO.getNoFk().getIdNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listaPorNo = query.getResultList();
        return listaPorNo;
    }
     public List<ItemdoNoDTO> procurarPorNo(int idNo) {
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", idNo);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listaPorNo = query.getResultList();
        return listaPorNo;
    }

    public Collection<ItemdoNoDTO> listar(Collection<ItemdoNoDTO> listar) {
        System.out.println("entrei no dao item  8");
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listar = query.getResultList();
        System.out.println("listar == no dao ===" + listar);
        return listar;
    }

    public List<ItemdoNoDTO> listarPorTipoDocumento(ItemdoNoDTO itemdoNoDTO) {

        System.out.println("entrei no dao item 9");
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByTipoDocumento").setParameter("identificacao", "%" + itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdentificacao() + "%"); // que contenham parte da palavra
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        System.out.println("entro do DAO=== " + itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdentificacao());

        return lista;
    }
// menu consulta ok 03-05-13

    public List<ItemdoNoDTO> listarPorTipoDocumentoID(ItemdoNoDTO itemdoNoDTO, int status) {
        System.out.println("entrei no dao status  10" +status);
        System.out.println("entrei no DAO do item idtipodedocumento ====" + itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento());
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByTipoDocumentoID").setParameter("idTipoDocumento", +itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento()).setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        System.out.println("entro do DAO=== " + itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento());

        return lista;
    }

    public List<ItemdoNoDTO> listarPorDocumentoVinculadoID(ItemdoNoDTO itemdoNoDTO) {
        System.out.println("entrei no dao item  11");
        System.out.println("entrei no DAO do item ====" + itemdoNoDTO);
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByDocumentoVinculadoID").setParameter("idDocumentoVinculado", +itemdoNoDTO.getDocumentoVinculadofk().getIdDocumentoVinculado()); // que contenham parte da palavra
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        System.out.println("***************************entro do DAO=== " + itemdoNoDTO.getDocumentoVinculadofk().getIdDocumentoVinculado());

        return lista;
    }
// está sendo usado 03-05-13

    public List<ItemdoNoDTO> listarPorEstadoNoID(ItemdoNoDTO itemdoNoDTO, int status) {
        System.out.println("entrei no dao item  12");
        System.out.println("entrei no DAO do item id estado ====" + itemdoNoDTO.getNoFk().getEstadoNofk().getIdEstadoNo());
        System.out.println("status===" + status);
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByEstadoNoID").setParameter("idEstadoNo", +itemdoNoDTO.getNoFk().getEstadoNofk().getIdEstadoNo()).setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();


        return lista;
    }

    public List<ItemdoNoDTO> listarPorIdentificacao(ItemdoNoDTO itemdoNoDTO, int status) {
        System.out.println("entrei no dao item  12.9");
        System.out.println("status===" + status);
        System.out.println("id do no no docv===" + itemdoNoDTO.getDocumentoVinculadofk().getNoFk().getIdNo());
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByIdent").setParameter("idNo", +itemdoNoDTO.getNoFk().getIdNo()).setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();


        return lista;
    }

    public List<ItemdoNoDTO> listarPorTemplate(ItemdoNoDTO itemdoNoDTO) {
        System.out.println("entrei no dao item  13");
        System.out.println("entrei no DAO do item");
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByNo").setParameter("idNo", +itemdoNoDTO.getNoFk().getIdNo()); // que contenham parte da palavra
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();


        return lista;
    }

    public void gravarAlterar(DocumentoVinculadoDTO documentoVinculadoDTO, ItemdoNoDTO itemdoNoDTO) {
        System.out.println("entrei no dao item  14");

        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        HistoricoDocVinculadoDTO historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();
        historicoDocVinculadoDTO.setDocumentoVinculadofk(documentoVinculadoDTO);

        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        System.out.println("usuario = " + usuarioDTO.getNome());


        Database.manager.getTransaction().begin();


        if (documentoVinculadoDTO.getIdDocumentoVinculado() != 0) {
            Database.manager.merge(documentoVinculadoDTO); // atualizar
            Database.manager.merge(itemdoNoDTO);

            historicoDocVinculadoDTO.setTipoAlteracao("Doc Alterado");
            historicoDocVinculadoDTO.setUsuarioOperacao(usuarioDTO.getNome());
        }
        Database.manager.getTransaction().commit();
        historicoDocVinculadoDAO.gravar(historicoDocVinculadoDTO);
    }

    /**
     * Recupera todos os registros da tabela iItemdoNoDTO
     *
     * @author livia.miura
     *
     */
    public List<ItemdoNoDTO> listarComp(int idItemdoNo) {
        System.out.println("/*listarComp");
        Query query = Database.manager.createNamedQuery("ItemdoNoDTO.findByIditemDoNo").setParameter("idItemdoNo", idItemdoNo);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List<ItemdoNoDTO> lista = query.getResultList();
        return lista;
    }

    

   
}
