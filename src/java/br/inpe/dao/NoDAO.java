/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.dto.ArvoreDTO;
import br.inpe.dto.NoDTO;
import br.inpe.dto.TipoProdutoDTO;
import br.inpe.dto.UsuarioDTO;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author livia.miura
 */
public class NoDAO {

    private NoDTO noDTO;
    private TipoProdutoDTO tipoProdutoDTO;

    /**
     * Recupera todos os registros da tabela noDTO.
     *
     * @author livia.miura
     *
     * @return listaNo.
     *
     */
    public Collection<NoDTO> listarNo(Collection<NoDTO> listaNo, int status) {
        Query query = Database.manager.createNamedQuery("NoDTO.findAll1").setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listaNo = query.getResultList();
        return listaNo;


    }
   public Collection<NoDTO> listarNo(Collection<NoDTO> listaNo) {
        Query query = Database.manager.createNamedQuery("NoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listaNo = query.getResultList();
        return listaNo;


    }
    /**
     * Recupera todos os registros da tabela TipoProduto e que Produto Final
     * seja true. Usa-se em conjunto listarNoProdutoFinal e tipoProdutoFinal na
     * consulta
     *
     * @author livia.miura
     *
     * @return listaProdutoFinal.
     *
     */
    public Collection<NoDTO> listarNoProdutoFinal(Collection<NoDTO> listaProdutoFinal,int status) {
        Query query = Database.manager.createNamedQuery("NoDTO.findByProdutoFinal").setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        listaProdutoFinal = query.getResultList();
        return listaProdutoFinal;
    }

    /**
     * Filtra todos os registros da tabela TipoProduto e que Produto Final ==1.
     * Usa-se em conjunto listarNoProdutoFinal e tipoProdutoFinal na consulta
     *
     * @author livia.miura.
     *
     * @return List - Lista.
     *
     */
    public Collection<NoDTO> tipoProdutoFinal(int idProd) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByTipoProduto").setParameter("idTipoProduto", idProd);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Filtra todos os registros da tabela EstadoNo e que tenha Produto Final
     * ==1.
     *
     * @author livia.miura.
     *
     * @return List - Lista.
     *
     */
    public Collection<NoDTO> tipoEstadoNo(int idEstado) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByProdutoFinalEstadoNo").setParameter("idEstadoNo", idEstado);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Filtra todos os registros da tabela Usuario e que tenha Produto Final
     * ==1.
     *
     * @author livia.miura.
     *
     * @return List - Lista.
     *
     */
    public Collection<NoDTO> responsavel(int idUser) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByProdutoFinalResponsavel").setParameter("idUsuario", idUser);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Filtra todos os registros da tabela Usuário e que tenha Produto Final
     * ==1.
     *
     * @author livia.miura.
     *
     * @return List - Lista.
     *
     */
    public Collection<NoDTO> identificacao(int idIdent, int status) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByProdutoFinalIdentificacao").setParameter("idNo", idIdent).setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    //***************************************************************************
    public List listarNo(int status) {
        // Query query = Database.manager.createNamedQuery("NoDTO.findAll");
        // 1 - Ativo
        // 0 - Inativo
        Query query = Database.manager.createNamedQuery("NoDTO.findByStatus").setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List listaNo = query.getResultList();
    //    System.out.println("lista===" + listaNo);
        return listaNo;
    }

    public List listar() {
        Query query = Database.manager.createNamedQuery("NoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }
    //**** é usado singleResult************/

    public NoDTO getRaiz() {
        Query query = Database.manager.createNamedQuery("NoDTO.findByIdNoNULL");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        return (NoDTO) query.getSingleResult();
    }
    //*****e não getResultList******************/

    public List getRaiz1() {
        Query query = Database.manager.createNamedQuery("NoDTO.findAll");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List listarPai(UsuarioDTO usuario) {
        Query query = Database.manager.createNamedQuery("NoDTO.findByPai");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela no, com base no valor da chave id
     * primária.
     *
     * @author livia.miura
     * @param noDTO
     * @return NoDTO - preenchido com o registro selecionado.
     *
     */
    public NoDTO selecionar(NoDTO noDTO) {
        return (NoDTO) Database.manager.find(NoDTO.class, noDTO.getIdNo());
    }

    public boolean validar(NoDTO noDTO) {
        Query query = Database.manager.createNamedQuery("NoDTO.findByIdNo").setParameter("idNo", noDTO.getIdNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);
        return query.getResultList().isEmpty();
    }

    /**
     * ok Grava um a alteração da tabela nó, gravar Diretório e o Nível que se
     * encontra. no método criarDiretorioOutrosNiveis na Classe itemdoNoAction
     *
     * @author livia.miura
     * @param noDTO
     * @return NoDTO - preenchido com o registro selecionado.
     *
     */
    public void gravarAlterar(NoDTO noDTO) {
        System.out.println("id No = " + noDTO.getIdNo());
        Database.manager.getTransaction().begin();
        Database.manager.merge(noDTO); // atualizar
        Database.manager.getTransaction().commit();

    }

    /**
     * Exclui um registro da tabela noDTO e arvoreDTO, com base no valor da
     * chave primária.
     *
     * @author livia.miura
     * @param noDTO, arvoreDTO
     *
     */
    public void excluir(NoDTO noDTO, ArvoreDTO arvoreDTO) {
        Database.manager.getTransaction().begin();
        System.out.println("IDArvore ===" + arvoreDTO.getIdArvore() + "  id no = " + noDTO.getIdNo());
        Database.manager.remove(Database.manager.find(ArvoreDTO.class, arvoreDTO.getIdArvore()));
        Database.manager.remove(Database.manager.find(NoDTO.class, noDTO.getIdNo()));
        Database.manager.getTransaction().commit();

    }

    public void excluirRaiz(NoDTO noDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.merge(noDTO); // atualizar
        System.out.println("atualizei");
        Database.manager.getTransaction().commit();
    }
  public void alterarNivel1(NoDTO noDTO) {
        Database.manager.getTransaction().begin();
        Database.manager.merge(noDTO); // atualizar
        System.out.println("atualizei");
        Database.manager.getTransaction().commit();
  }

    /**
     * Recupera todos os registros da tabela venda por data
     *
     * @author livia.miura
     * @return List - Lista preenchida com as vendas
     */
    public List<NoDTO> selecionarPorData(NoDTO noDTO,  int status) {
        Query query = Database.manager.createNamedQuery("NoDTO.findByData").setParameter("dataInicial", noDTO.getDataInicial()).setParameter("dataFinal", noDTO.getDataFinal()).setParameter("status",status);
        System.out.println("data saiu ===> " + noDTO.getDataFinal());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorNo(NoDTO noDTO) {

        System.out.println("******Tô no selecionar por Nó********" + noDTO.getIdNo());
        Query query = Database.manager.createNamedQuery("NoDTO.findByIdNo").setParameter("idNo", noDTO.getIdNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorEstadoNoID(NoDTO noDTO) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByEstadoNo").setParameter("idEstadoNo", noDTO.getEstadoNofk().getIdEstadoNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorEstadoNoIDStatus(NoDTO noDTO, int status) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByEstadoNoStatus").setParameter("idEstadoNo", noDTO.getEstadoNofk().getIdEstadoNo()).setParameter("status", status);
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorUsuarioID(NoDTO noDTO) {

        System.out.println("******Tô no selecionar por Nó********" + noDTO.getIdNo());
        Query query = Database.manager.createNamedQuery("NoDTO.findByUsuarioID").setParameter("idUsuario", noDTO.getResponsavel().getIdUsuario());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorTipoProduto(NoDTO noDTO) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByTipoProduto").setParameter("idTipoProduto", noDTO.getTipoProdutofk().getIdTipoProduto());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarProdutoFinalPorEstadoNo(NoDTO noDTO) {

        Query query = Database.manager.createNamedQuery("NoDTO.findByProdutoFinalEstadoNo").setParameter("idEstadoNo", noDTO.getEstadoNofk().getIdEstadoNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorTitulo(NoDTO noDTO) {

        System.out.println("******Selecionar por titulo********" + noDTO.getTitulo());
        Query query = Database.manager.createNamedQuery("NoDTO.findByIdNo").setParameter("idNo", noDTO.getIdNo());
        System.out.println("oi");
        System.out.println("*****titulo********" + noDTO.getTitulo());

        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    public List<NoDTO> selecionarPorNoNULL(NoDTO noDTO) {

        System.out.println("******selecionar nó seja NULLLLL***" + noDTO.getIdNo());
        Query query = Database.manager.createNamedQuery("NoDTO.findByIdNoNULL").setParameter("idNo", noDTO.getIdNo());
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    // declaração da tree gets e sets 
    public NoDTO getNoDTO() {
        if (noDTO == null) {
            noDTO = new NoDTO();
        }
        return noDTO;
    }

    public void setNoDTO(NoDTO noDTO) {
        this.noDTO = noDTO;
    }

    public TipoProdutoDTO getTipoProdutoDTO() {
        if (tipoProdutoDTO == null) {
            tipoProdutoDTO = new TipoProdutoDTO();
        }
        return tipoProdutoDTO;
    }

    public void setTipoProdutoDTO(TipoProdutoDTO tipoProdutoDTO) {
        this.tipoProdutoDTO = tipoProdutoDTO;
    }

    /**
     * Grava um registro na tabela no. Grava o noPai, nivel 0
     *
     * @author livia.miura
     * @param noDTO
     *
     */
    public void gravar(NoDTO noDTO) {
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
     * Grava um registro na tabela no. Grava todos os niveis e cria a arvore
     *
     * @author livia.miura
     * @param noDTO
     *
     */
    public NoDTO adicionarNovoProduto(String FolderName, String newDescricao, int newResponsavel, String newDominio, int newTipoProduto, int newEstadoProduto, boolean newProdutoFinal, NoDTO parent, String ident, String u) {
        NoDTO noDTO = new NoDTO();

        noDTO.setTitulo(FolderName);
        noDTO.setIdNo(0);
        System.out.println("titulo========== " + noDTO.getTitulo());
        noDTO.setNoFk(parent);
        System.out.println("parent =========" + parent);
        noDTO.setDescricao(newDescricao);
        noDTO.getResponsavel().setIdUsuario(newResponsavel);
        noDTO.setTipoDominio(newDominio);
        noDTO.getTipoProdutofk().setIdTipoProduto(newTipoProduto);
        noDTO.getEstadoNofk().setIdEstadoNo(newEstadoProduto);
        noDTO.setProdutoFinal(newProdutoFinal);
        noDTO.setIdentExterna(ident);
        System.out.println("identicação atual===" + noDTO.getIdentExterna());
        noDTO.getNoFk().setIdentExterna(u);
        System.out.println("identifacação anterior -====" + noDTO.getNoFk().getIdentExterna());
        noDTO.setStatus(1);
        //noDTO.setNivel(1); // só para não receber nulo, redefinido no método criarDiretorioOutrosNiveis 
        //******************************
        String t = noDTO.getTitulo();
        System.out.println("titulo " + t);
        //gravar o diretorio de 2 nivel-----------------------------------------------------------------------------
        String q = noDTO.getNoFk().getDiretorio();// pega o valor do diretorio pai
        System.out.println("diretório anterior" + q);

        noDTO.setDiretorio(q + "/" + t);


        ///gravar código de 2 nivel
        String r = noDTO.getNoFk().getCodigo();// pega o valor do codigo pai
        noDTO.setCodigo(r + "-" + t);


        int somaNivel = noDTO.getNoFk().getNivel();
        somaNivel++;
        noDTO.setNivel(somaNivel);
        System.out.println("noDTO.getNoFk().getNivel()====" + noDTO.getNoFk().getNivel());



        if (noDTO.getNivel() <= 1 && noDTO.getProdutoFinal() == false) {
            System.out.println("entrei no < 1");
            noDTO.getTipoProdutofk().setIdTipoProduto(8);
            noDTO.setIdentExterna("Não se aplica");

        }

        if (noDTO.getNivel() >= 2 && noDTO.getProdutoFinal() == false) {
            System.out.println("entrei no nivel 2 sem ser produto final");
            noDTO.getTipoProdutofk().setIdTipoProduto(8);
            noDTO.setIdentExterna("Não se aplica");

        }

        //??? 403 e 413 poderia ser o mesmo.
        if (noDTO.getNivel() >= 2 && noDTO.getProdutoFinal() == true) {
            System.out.println("/*entrei no nivel >2 como produto final");
            noDTO.setTitulo(ident);
            noDTO.setIdentExterna(ident);
            noDTO.setDiretorio(q + "/" + ident);
            noDTO.setCodigo(r + "-" + ident);

        }

        if (noDTO.getNivel() < 2 && noDTO.getProdutoFinal() == true) {
            System.out.println("/*entrei no nivel >2 como produto final");
            noDTO.setTitulo(ident);
            noDTO.setIdentExterna(ident);
            noDTO.setDiretorio(q + "/" + ident);
            noDTO.setCodigo(r + "-" + ident);

        }
        //******************************

        parent.addChildFolder(noDTO);

        Database.manager.getTransaction().begin();
        if (noDTO.getIdNo() == 0) {

            Database.manager.persist(noDTO); // gravar 
            System.out.println("id criado" + noDTO.getIdNo());
            System.out.println("gravar************************");
        } else {
            Database.manager.merge(parent); // atualizar
            System.out.println("atualizar*********************");
            System.out.println("id criado" + noDTO.getIdNo());
        }
        Database.manager.getTransaction().commit();
        return noDTO;

    }

    public void alterarProduto(NoDTO noDTO) {

        Database.manager.getTransaction().begin();
        Database.manager.merge(noDTO); // atualizar
        System.out.println("atualizei");
        Database.manager.getTransaction().commit();
    }
    
    
    
     public List listaDominio() {
        Query query = Database.manager.createNamedQuery("NoDTO.findByTipoDominio");
        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }
}
