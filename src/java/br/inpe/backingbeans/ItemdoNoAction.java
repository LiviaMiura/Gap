/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.*;
import br.inpe.dto.*;
import br.inpe.iReport.UtilException;
import br.inpe.iReport.gConexao;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.*;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.*;
import org.primefaces.model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.primefaces.context.RequestContext;
import java.io.InputStream;

/**
 *
 * @author livia.miura
 */
public class ItemdoNoAction implements Serializable {

    /*
     ******************************Atenção**************************************
     * **************Mudar o caminho quando colocar no servidor
     * "C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/
     */
    //  private String DIRETORIO = "C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/";
    private String DIRETORIO = "C:/GAP";
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);// mantém na session
    //**************************************************
    private TreeNode root;
    private TreeNode selectedNode;
    //****************************************
    private String newFolderName;   //contains the name of the new folder that the user wants to create.
    private String newDescricao;
    private int newResponsavel;
    private String newDominio;
    private int newTipoProduto;
    private int newEstadoProduto;
    private boolean newProdutoFinal;
    //**************************
    private NoDTO noDTO;
    private ArvoreDTO arvoreDTO;
    private ItemdoNoDTO itemdoNoDTO;
    private DocumentoVinculadoDTO documentoVinculadoDTO;
    private TipoDocumentoDTO tipoDocumentoDTO;
    //*****************************************************
    private int delete;
    private Collection<DocumentoVinculadoDTO> documents;
    private List<NoDTO> produtos;
    private List<ItemdoNoDTO> listaPorNo;
    private List<ItemdoNoDTO> listarNo;
    private List<ItemdoNoDTO> lista;
    private List<ItemdoNoDTO> lista2;
    private List<ItemdoNoDTO> listaDoc;
    private List<DocumentoVinculadoDTO> listaDocumentos;
    private UploadedFile fileUp;
    private EstadoNoDTO estadoNoDTO;
    private UsuarioDTO usuarioDTO;
    private HistoricoNoDTO historicoNoDTO;
    private int rs;
    private String newCodigo;
    private String newDiretorio;
    private Integer newIdFolder;
    private SelectItem[] opcaoOperacao;
    private static String[] opcao;
    private String retorno;
    private StreamedContent arquivoRetorno;
    private int tipoRelatorio;
    private Collection<ItemdoNoDTO> listaItem;
    private Collection<DocumentoVinculadoDTO> listaItemDoc;
    private Collection<NoDTO> listaNo;
    private List<ItemdoNoDTO> listaConsultas;
    private ItemdoNoDTO itemSelecionado;
    private ItemdoNoDTO selectedCar;
    private List<DocumentoVinculadoDTO> listaIDBusca;
    private DocumentoVinculadoDTO itemdoNoDTOSelecionado;
    private List<DocumentoVinculadoDTO> listaNoEspecifico;
    private TipoProdutoDTO tipoProdutoDTO;
    private String k, u;
    private List<ArvoreDTO> listaFilhos;
    private List<DocumentoVinculadoDTO> listaDocUtilizado;
    private int noPai;
    private DocumentoVinculadoDTO selectedDocument;
    private List<ArvoreDTO> listaNoFilhos;
    private Integer idNo;
    private String status;
    private String docPreview;
    private DefaultStreamedContent file;

    public ItemdoNoAction() {

        opcaoOperacao = createFilterOptions(opcao);
    }

    //*********Filtrar***********************************************************
    static {
        opcao = new String[3];

        opcao[0] = "Produto Novo";
        opcao[1] = "Produto Alterado";
        opcao[2] = "Produto Excluído";

    }

    public SelectItem[] getOpcaoOperacao() {
        return opcaoOperacao;
    }

    /**
     * FilterOptions, filtra valores predefinidos.
     *
     */
    private SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Selecione");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }

        return options;
    }

    //**************************************************************************
    public NoDTO getNoDTO() {
        if (noDTO == null) {
            noDTO = new NoDTO();
        }
        return noDTO;
    }

    public void setNoDTO(NoDTO noDTO) {
        this.noDTO = noDTO;
    }

    //**********************listar consultas  no menu
    public List<ItemdoNoDTO> getListaConsultas() {

        return listaConsultas;
    }

    public void setListaConsultas(List<ItemdoNoDTO> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }
    //********************* fim de lista consultas

    public ArvoreDTO getArvoreDTO() {
        if (arvoreDTO == null) {
            arvoreDTO =
                    new ArvoreDTO();
        }
        return arvoreDTO;
    }

    public void setArvoreDTO(ArvoreDTO arvoreDTO) {
        this.arvoreDTO = arvoreDTO;
    }

    public ItemdoNoDTO getItemdoNoDTO() {
        if (itemdoNoDTO == null) {
            itemdoNoDTO = new ItemdoNoDTO();
        }
        return itemdoNoDTO;
    }

    public void setItemdoNoDTO(ItemdoNoDTO itemdoNoDTO) {
        this.itemdoNoDTO = itemdoNoDTO;
    }

    public DocumentoVinculadoDTO getDocumentoVinculadoDTO() {

        if (documentoVinculadoDTO == null) {
            documentoVinculadoDTO = new DocumentoVinculadoDTO();
        }
        return documentoVinculadoDTO;
    }

    public void setDocumentoVinculadoDTO(DocumentoVinculadoDTO documentoVinculadoDTO) {
        this.documentoVinculadoDTO = documentoVinculadoDTO;
    }

    public TipoDocumentoDTO getTipoDocumentoDTO() {
        if (tipoDocumentoDTO == null) {
            tipoDocumentoDTO = new TipoDocumentoDTO();
        }
        return tipoDocumentoDTO;
    }

    public void setTipoDocumentoDTO(TipoDocumentoDTO tipoDocumentoDTO) {
        this.tipoDocumentoDTO = tipoDocumentoDTO;
    }

    public EstadoNoDTO getEstadoNoDTO() {
        if (estadoNoDTO == null) {
            estadoNoDTO = new EstadoNoDTO();
        }
        return estadoNoDTO;
    }

    public void setEstadoNoDTO(EstadoNoDTO estadoNoDTO) {
        this.estadoNoDTO = estadoNoDTO;
    }

    public HistoricoNoDTO getHistoricoNoDTO() {
        if (historicoNoDTO == null) {
            historicoNoDTO = new HistoricoNoDTO();
        }
        return historicoNoDTO;
    }

    public void setHistoricoNoDTO(HistoricoNoDTO historicoNoDTO) {
        this.historicoNoDTO = historicoNoDTO;
    }

    public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
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

    public Collection<ItemdoNoDTO> getListaItem() {
        if (listaItem == null) {
            ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
            listaItem = itemdoNoDAO.listarItem(listaItem);
        }
        return listaItem;
    }

    public void setListaItem(Collection<ItemdoNoDTO> listaItem) {
        this.listaItem = listaItem;
    }

    public Collection<DocumentoVinculadoDTO> getDocuments() {
        return documents;
    }

    public Collection<DocumentoVinculadoDTO> getListaItemDoc() {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        if (listaItemDoc == null) {
            listaItemDoc = documentoVinculadoDAO.listarDocumento(listaItemDoc);

        }
        return listaItemDoc;
    }

    public void setListaItemDoc(Collection<DocumentoVinculadoDTO> listaItemDoc) {
        this.listaItemDoc = listaItemDoc;
    }

    public Collection<NoDTO> getListaNo() {
        if (listaNo == null) {
            NoDAO noDAO = new NoDAO();
            listaNo = noDAO.listarNo(listaNo, 1);
        }
        return listaNo;
    }

    public void setListaNo(Collection<NoDTO> listaNo) {
        this.listaNo = listaNo;
    }

    //**************************************************************************
    public void listarItem() {

        NoDAO noDAO = new NoDAO();

        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        listaItem = getItemdoNoDTO().getNoFk().getItemdoNoDTOCollection();
    }

    public void listarItemDoc() {

        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        getItemdoNoDTO().getDocumentoVinculadofk().setTipoDocumentofk(tipoDocumentoDAO.selecionar(getItemdoNoDTO().getDocumentoVinculadofk().getTipoDocumentofk()));

        listaItemDoc = getItemdoNoDTO().getDocumentoVinculadofk().getTipoDocumentofk().getDocumentoVinculadoDTOCollection();

    }

    public void listarItemDoc1() {

        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        getDocumentoVinculadoDTO().setTipoDocumentofk(tipoDocumentoDAO.selecionar(getItemdoNoDTO().getDocumentoVinculadofk().getTipoDocumentofk()));

        listaItemDoc = getDocumentoVinculadoDTO().getTipoDocumentofk().getDocumentoVinculadoDTOCollection();
    }

    /**
     * Método para listar os Estado do Nó, selecionado através de combobox na
     * consulta.
     *
     * @author livia.miura
     */
    public void listarItemDocEstado() {
        EstadoNoDAO estadoDAO = new EstadoNoDAO();
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        getItemdoNoDTO().getNoFk().setEstadoNofk(estadoDAO.selecionar(getItemdoNoDTO().getNoFk().getEstadoNofk()));
        NoDAO noDAO = new NoDAO();
        listaItem = getItemdoNoDTO().getNoFk().getItemdoNoDTOCollection();



    }

    /**
     * Este método retorna para a página JSF a pasta root da árvore. Entretanto
     * recebe o nó(produto) através do ItemdoNoAction e cria os objetos da
     * TreeNode corretamente.
     *
     * @author livia.miura
     * @return - TreeNode root folder of the tree
     */
    public TreeNode getRoot() {
        NoDAO noDAO = new NoDAO();
        List<NoDTO> dadosNo = noDAO.listarNo(1);

        if (this.root == null) {
            root = new DefaultTreeNode(null, null);
            for (NoDTO filho : dadosNo) {
                if (filho.getNoFk().getIdNo() == null && filho.getStatus() == 1) {
                    TreeNode outroFilho = new DefaultTreeNode(filho, root);
                    outroFilho.setParent(root);
                    this.buildTreeRecursively(outroFilho);  // é necessário verficar os filhos
                }
            }
        }
        return root;
    }

    /**
     * Método recursivo para criar a estrutura da TreeNode.
     *
     * @author livia.miura
     * @param noAtual
     */
    private void buildTreeRecursively(TreeNode noAtual) {
        NoDTO folder = (NoDTO) (noAtual.getData());
        for (NoDTO child : folder.getNoFkCollection()) {

            if (child.getStatus() == 1) {
                TreeNode tnChild = new DefaultTreeNode(child, noAtual);
                tnChild.setParent(noAtual);

                buildTreeRecursively(tnChild);
            }
        }
    }

    /*
     * Esse método faz a árvore expandir. @author livia.miura
     */
    private void expandFromNode(TreeNode node) {
        while ((node = node.getParent()) != null) {
            node.setExpanded(true);
        }
    }

    //***************************Inicio List************************************
    public List<ItemdoNoDTO> getListaPorNo() {
        return listaPorNo;
    }

    public void setListaPorNo(List<ItemdoNoDTO> listaPorNo) {
        this.listaPorNo = listaPorNo;
    }


    /*
     * Pego o IdItemdoNo e coloco em uma lista, para ser utilizado no datatable
     * e usar "var", e para ser reconhecido quando for pedido para gravar os
     * dados no pdf.
     *
     */
    public List<DocumentoVinculadoDTO> getListaNoEspecifico() {

        listar1Produto();
        return listaNoEspecifico;
    }

    public void setListaNoEspecifico(List<DocumentoVinculadoDTO> listaNoEspecifico) {
        this.listaNoEspecifico = listaNoEspecifico;
    }

    public String listar1Produto() {

        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        if (documentoVinculadoDTO.getIdDocumentoVinculado() != null) {
            int idBusca = getDocumentoVinculadoDTO().getIdDocumentoVinculado();

            DocumentoVinculadoDAO listaBusca = new DocumentoVinculadoDAO();
            listaNoEspecifico = listaBusca.listarComp(idBusca);

        }

        return "listar1";
    }

    public ItemdoNoDTO getSelectedCar() {

        return selectedCar;
    }

    public void setSelectedCar(ItemdoNoDTO selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<ItemdoNoDTO> getLista() {

        listar();
        return lista;
    }

    public void setLista(List<ItemdoNoDTO> lista) {
        this.lista = lista;
    }

    public List<ItemdoNoDTO> getLista2() {
        listar2();
        return lista2;
    }

    public void setLista2(List<ItemdoNoDTO> lista2) {
        this.lista2 = lista2;
    }

    public String listar2() {

        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        lista2 = itemdoNoDAO.listarStatus2(1, 1);
        return "backtree";

    }
//******************************************************************************
    /*
     * foi feita uma lista para verificar se um pai tem filho e se esse filho
     * tem código especifico, se sim será incrementado um numero ao proximo
     * código
     */

    public List<ArvoreDTO> getListaFilhos(int c, String k) {
        listarFilhos(c, k);
        return listaFilhos;
    }

    public void setListaFilhos(List<ArvoreDTO> listaFilhos) {
        this.listaFilhos = listaFilhos;
    }

    public String listarFilhos(int c, String k) {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        listaFilhos = arvoreDAO.listarPai(c, k);

        return "backtree";

    }

    public List<DocumentoVinculadoDTO> getListaDocumentos(int w, int a) {
        listaDocumentos(w, a);
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoVinculadoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public String listaDocumentos(int w, int a) {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        listaDocumentos = documentoVinculadoDAO.listarProdutoNoDocumento(w, a);
        return "backtree";
    }

    //*** listar código já utilizado, para nao repetir criação
    public List<DocumentoVinculadoDTO> getListaDocUtilizado(int a, String codigo) {
        listaDocUtilizado(a, codigo);
        return listaDocUtilizado;
    }

    public void setListaDocUtilizado(List<DocumentoVinculadoDTO> listaDocUtilizado) {
        this.listaDocUtilizado = listaDocUtilizado;
    }
    //**************************************************************************

    public String listaDocUtilizado(int a, String codigo) {

        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        listaDocUtilizado = documentoVinculadoDAO.listarDocumentoUtilizado(a, codigo);

        return "backtree";

    }

    //***********************Fim List*******************************************
    /**
     * Instancia um itemdoNoDAO e invoca o método listar().
     *
     * @author livia.miura
     * @return String backtree, para navegação.
     *
     */
    public String listar4() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        lista = itemdoNoDAO.listar();
        return "backtree";
    }

    // lista todos os status que seja == 1 no  menu documento vinculado 
    public String listar() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        lista = itemdoNoDAO.listarStatus(1);
        return "backtree";

    }


    /*
     * Retorno da navegação para tree
     */
    public String menu() {
        return "menu";
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public String getNewDescricao() {
        return newDescricao;
    }

    public void setNewDescricao(String newDescricao) {
        this.newDescricao = newDescricao;
    }

    public String getNewDominio() {
        return newDominio;
    }

    public void setNewDominio(String newDominio) {
        this.newDominio = newDominio;
    }

    public int getNewEstadoProduto() {
        return newEstadoProduto;
    }

    public void setNewEstadoProduto(int newEstadoProduto) {
        this.newEstadoProduto = newEstadoProduto;
    }

    public boolean isNewProdutoFinal() {

        return newProdutoFinal;
    }

    public void setNewProdutoFinal(boolean newProdutoFinal) {
        this.newProdutoFinal = newProdutoFinal;
    }

    public int getNewResponsavel() {
        return newResponsavel;
    }

    public void setNewResponsavel(int newResponsavel) {
        this.newResponsavel = newResponsavel;
    }

    public int getNewTipoProduto() {
        return newTipoProduto;
    }

    public void setNewTipoProduto(int newTipoProduto) {
        this.newTipoProduto = newTipoProduto;
    }

    public String getNewCodigo() {
        return newCodigo;
    }

    public void setNewCodigo(String newCodigo) {
        this.newCodigo = newCodigo;
    }

    public String getNewDiretorio() {
        return newDiretorio;
    }

    public void setNewDiretorio(String newDiretorio) {
        this.newDiretorio = newDiretorio;
    }

    public Integer getNewIdFolder() {
        return newIdFolder;
    }

    public void setNewIdFolder(Integer newIdFolder) {
        this.newIdFolder = newIdFolder;
    }

    //**************************************************************************
    /*
     * Listar os documentos na treetableNo 27/08/13
     */
    public DocumentoVinculadoDTO getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(DocumentoVinculadoDTO selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    /*
     * Esse método faz a primeira verficação se o produto selecionado é um
     * produto final (nó folha). @author livia.miura
     */
    public void onNodeSelect(NodeSelectEvent event) {
        NoDAO noDAO = new NoDAO();

        int w = Integer.parseInt(event.getTreeNode().toString());

        int a = 1;

        //* Listar os documentos referente ao nó selecionado
        // NoDTO selectedFolder = (NoDTO) event.getTreeNode().getData();
        //documents = selectedFolder.getDocumentoVinculadoDTOCollection();
        //**********************************************************************
        listaDocumentos(w, a); // lista os documentos com nó iguais
        documents = listaDocumentos;  // atualizo os documentos

        getNoDTO().setIdNo(w);
        setNoDTO(noDAO.selecionar(getNoDTO()));
        if (getNoDTO().getProdutoFinal() == true) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Produto Final, não pode inserir filhos!",
                    ""));

        } else {
            System.out.println("Insere filhos!!!");
        }
    }

    /*
     * Esse método grava o novo produto,historico do Nó, grava a Árvore e cria o
     * diretório do novo produto gerado.
     *
     * @author livia.miura
     */
    public void gravarProduto() {

        NoDAO noDAO = new NoDAO();
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();

        Object data = selectedNode.getData();
        int c = Integer.parseInt(data.toString());


        getNoDTO().setIdNo(c);

        setNoDTO(noDAO.selecionar(getNoDTO()));

        if (getNoDTO().getProdutoFinal() != true && this.selectedNode != null) {

            //----------- gravar a identificação externa do produto-------------
            String t = newFolderName;
            String r = noDTO.getCodigo();// pega o valor do codigo pai
            int s = newTipoProduto;

            getTipoProdutoDTO().setIdTipoProduto(s);
            setTipoProdutoDTO(tipoProdutoDAO.selecionar(getTipoProdutoDTO()));

            String w = tipoProdutoDTO.getIdentificacao();
            u = getNoDTO().getIdentExterna();
            /*
             * String identAnterior = getNoDTO().getNoFk().getIdentExterna();
             * System.out.println("identid anterior" + identAnterior);
             */
            //criar produto final 01
            int i = 1;
            k = w + "0" + i;

            /*
             * Lista filhos verifica se o pai já contém um filho produto final e
             * se esse produto final já existe para ser incrementado ex: DL01 já
             * existir em algum produto final do pai selecionado o próximo será
             * DL02 e assim por diante.
             */
            listarFilhos(c, k);
            String k2 = null;
            while (!listaFilhos.isEmpty()) {
                i = i + 1;
                k2 = w + "0" + i;
                k = k2;

                listarFilhos(c, k);
            }
            // -----------fim gravar a identificação externa do produto---------
            NoDTO newFolder = noDAO.adicionarNovoProduto(this.newFolderName, this.newDescricao, this.newResponsavel, this.newDominio, this.newTipoProduto, this.newEstadoProduto, this.newProdutoFinal, (NoDTO) selectedNode.getData(), this.k, this.u);
            TreeNode tnNewNode = new DefaultTreeNode(newFolder, selectedNode);
            //dados para gravar o historicoNo
            int y = newFolder.getIdNo();
            int x = newEstadoProduto;
            System.out.println("/*x" + x);
            historicoDoNo(x, y);
            //dados abaixo somente para gravar a árvore
            int a = newFolder.getIdNo();
            System.out.println("a = " + a);
            getArvoreDTO().setIdArvore(0);
            String j = newFolder.getIdentExterna();
            System.out.println("j*************" + j);

            gravarArvore(a, c, j);

            /*
             * Essa parte que gera o diretório, no caso coloquei dentro do
             * projeto, mas o ideal é colocar em um area dentro do servidor
             */

            // File dir = new File("C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/GAP/" + newFolder.getDiretorio());
            File dir = new File(DIRETORIO +  newFolder.getDiretorio());

            if (dir.mkdirs()) {
                System.out.println("Novo diretorio criado ---> " + dir.getAbsolutePath());
            } else {
                System.out.println("Diretorio ja existe!!! ");
            }
            //fim diretório
            expandFromNode(tnNewNode);
            this.newFolderName = "";
            this.newFolderName = "";
            this.newDescricao = "";
            this.newDominio = "";

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Produto Final, não pode inserir filhos no Sistema GAP.",
                    "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    private void historicoDoNo(int x, int y) {

        //************  Registra No no Historico -  **********************
        HistoricoNoDAO historicoNoDAO = new HistoricoNoDAO();

        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        getHistoricoNoDTO().setUsuarioFk(usuarioDTO);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date e = new Date();
        dateFormat.format(e);
        getHistoricoNoDTO().setDataOperacao(e);
        getHistoricoNoDTO().getNoFk().setIdNo(y);
        //*********************************************************************
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getEstadoNoDTO().setIdEstadoNo(x);
        setEstadoNoDTO(estadoNoDAO.selecionar(getEstadoNoDTO()));
        getHistoricoNoDTO().setEstadoNo(getEstadoNoDTO().getIdentificacao());
        //**********************************************************************
        historicoNoDAO.gravar(getHistoricoNoDTO());

    }

    private void historicoDoNoAlterado(int x, int y) {

        //************  Registra No no Historico -  **********************
        HistoricoNoDAO historicoNoDAO = new HistoricoNoDAO();
        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        getHistoricoNoDTO().setUsuarioFk(usuarioDTO);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date e = new Date();
        dateFormat.format(e);
        getHistoricoNoDTO().setDataOperacao(e);
        getHistoricoNoDTO().getNoFk().setIdNo(y);
        //*********************************************************************
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getEstadoNoDTO().setIdEstadoNo(x);
        setEstadoNoDTO(estadoNoDAO.selecionar(getEstadoNoDTO()));
        getHistoricoNoDTO().setEstadoNo(getEstadoNoDTO().getIdentificacao());
        //**********************************************************************
        historicoNoDAO.gravarAlterado(getHistoricoNoDTO());
    }

    private void historicoDoNoDeletado(int x, int y) {

        //************  Registra No no Historico -  **********************
        HistoricoNoDAO historicoNoDAO = new HistoricoNoDAO();
        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        getHistoricoNoDTO().setUsuarioFk(usuarioDTO);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date e = new Date();
        dateFormat.format(e);
        getHistoricoNoDTO().setDataOperacao(e);
        getHistoricoNoDTO().getNoFk().setIdNo(y);
        //*********************************************************************
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getEstadoNoDTO().setIdEstadoNo(x);
        setEstadoNoDTO(estadoNoDAO.selecionar(getEstadoNoDTO()));
        getHistoricoNoDTO().setEstadoNo(getEstadoNoDTO().getIdentificacao());
        //**********************************************************************
        historicoNoDAO.gravarExcluido(getHistoricoNoDTO());
    }

    /**
     * Instancia noDAO e arvoreDAO e invoca o método gravarArvore, passando o
     * objeto ArvoreDTO, É instanciado toda vez que for criar um nó de nivel
     * maior do que 0, deverá receber o valor do idNo que será o noPai, e deverá
     * gravar o diretorio, caso haja filhos.
     *
     * @author livia.miura
     *
     */
    public void gravarArvore(int a, int e, String k) {


        ArvoreDAO arvoreDAO = new ArvoreDAO();
        ArvoreDTO arvore = new ArvoreDTO();
        //  NoDAO noDAO = new NoDAO();

        arvore.setNoPai(e);
        arvore.getNoFilho().setIdNo(a);
        arvore.setCodigo(k);

        arvoreDAO.gravar(arvore);
        System.out.println("/*/*/*/*////////////////////////////////" + arvore.getIdArvore());
        int b = arvore.getIdArvore();
        //  alterarNoArvore(b, a);


        /*
         * Erro que aparece e não foi resolvido ainda INFO:
         * java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
         * java.lang.IndexOutOfBoundsException: Index: 0, Size: 0 at
         * java.util.ArrayList.RangeCheck(ArrayList.java:547) at
         * java.util.ArrayList.get(ArrayList.java:322) at
         * org.primefaces.component.api.UITree.findTreeNod
         *
         * System.out.println("id arvore atual" + arvore.getIdArvore());
         * getNoDTO().setIdNo(a); setNoDTO(noDAO.selecionar(getNoDTO()));
         * System.out.println("no fk ===" + getNoDTO().getNoFk().getIdNo());
         * System.out.println("no id=== " + getNoDTO().getIdNo());
         * System.out.println("titulo==" + getNoDTO().getTitulo());
         * getNoDTO().getArvoreFk().setIdArvore(arvore.getIdArvore());
         * System.out.println("getNoDTO.getArvoreFK===" +
         * getNoDTO().getArvoreFk().getIdArvore());//
         * noDAO.alterarProduto(getNoDTO());
         */

    }
    /*
     * public void alterarNoArvore(int b, int a) { NoDAO noDAO = new NoDAO();
     * getNoDTO().setIdNo(a); setNoDTO(noDAO.selecionar(getNoDTO()));
     * getNoDTO().setArvoreId(b);
     *
     * noDAO.gravar(getNoDTO());
     *
     * }
     */

    /**
     * Esse método atualiza o dialog.
     *
     * @author livia.miura
     *
     */
    public void initDialog() {

        this.newFolderName = "";
        RequestContext.getCurrentInstance().update(":form:addFolderPanel");
    }

    /**
     * Set IdNo selecionado permita alterar o registro na tabela noDTO O produto
     * a ser alterado na Árvore de Produto - tree
     *
     * @author livia.miura
     * @return String alterarNivel1,cou para navegação.
     *
     */
    public String alterar() {

        String retorno = null;
        NoDAO noDAO = new NoDAO();

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        getItemdoNoDTO().getNoFk().setIdNo(a);
        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        if (getItemdoNoDTO().getNoFk().getNoFk().getIdNo() != null) {
            if (getItemdoNoDTO().getNoFk().getNivel() == 1) {
                retorno = "alterarNivel1";
            }
            if (getItemdoNoDTO().getNoFk().getNivel() >= 2) {
                retorno = "alterarNivel2";
            }
            if (getItemdoNoDTO().getNoFk().getNivel() >= 2 && getItemdoNoDTO().getNoFk().getProdutoFinal() == true) {
                retorno = "alterarNivel21";
            }
        } else {

            if (getItemdoNoDTO().getNoFk().getNivel() == 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Produto raiz não pode ser alterado no Sistema GAP.",
                        "");

                FacesContext.getCurrentInstance().addMessage(null, message);

                retorno = "alterarNivel0";
            }
        }
        return retorno;
    }

    /**
     * Instancia um itemdoNoDAO, invoca o método alterarNiveis, passando o
     * objeto noDTO Altera o nó por nível
     *
     * @author livia.miura
     * @return String backtree, para navegação.
     *
     */
    public String gravarAlterarNiveis() {

        NoDAO noDAO = new NoDAO();
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();


        getNoDTO().setIdNo(getItemdoNoDTO().getNoFk().getIdNo());
        getNoDTO().setTitulo(getItemdoNoDTO().getNoFk().getTitulo());
        getNoDTO().setDescricao(getItemdoNoDTO().getNoFk().getDescricao());
        getNoDTO().getResponsavel().setIdUsuario(getItemdoNoDTO().getNoFk().getResponsavel().getIdUsuario());
        getNoDTO().setTipoDominio(getItemdoNoDTO().getNoFk().getTipoDominio());
        getNoDTO().getTipoProdutofk().setIdTipoProduto(getItemdoNoDTO().getNoFk().getTipoProdutofk().getIdTipoProduto());
        System.out.println(" tipo de prodtudkdjdkfj =======" + getNoDTO().getTipoProdutofk().getIdTipoProduto());
        getNoDTO().getEstadoNofk().setIdEstadoNo(getItemdoNoDTO().getNoFk().getEstadoNofk().getIdEstadoNo());
        getNoDTO().setNivel(getItemdoNoDTO().getNoFk().getNivel());
        getNoDTO().getNoFk().setIdNo(getItemdoNoDTO().getNoFk().getNoFk().getIdNo());
        //getNoDTO().getArvoreFk().setIdArvore(getItemdoNoDTO().getNoFk().getArvoreFk().getIdArvore());
        getNoDTO().setTipoDominio(getItemdoNoDTO().getNoFk().getTipoDominio());
        getNoDTO().getTipoProdutofk().setIdTipoProduto(getItemdoNoDTO().getNoFk().getTipoProdutofk().getIdTipoProduto());

        getNoDTO().setProdutoFinal(getItemdoNoDTO().getNoFk().getProdutoFinal());
        System.out.println("  getNoDTO().getProdutoFinal==========" + getNoDTO().getProdutoFinal());
        getNoDTO().setDiretorio(getItemdoNoDTO().getNoFk().getNoFk().getDiretorio() + "/" + getItemdoNoDTO().getNoFk().getTitulo());
        getNoDTO().setIdentExterna(getItemdoNoDTO().getNoFk().getIdentExterna());
        getNoDTO().setStatus(1);
        if (getItemdoNoDTO().getNoFk().getNivel() == 1) { //se for nivel=1
            getNoDTO().setCodigo(getItemdoNoDTO().getNoFk().getNoFk().getTitulo() + "-" + getItemdoNoDTO().getNoFk().getTitulo());
        }
        if (getItemdoNoDTO().getNoFk().getNivel() >= 2) {  //senão se o nivel >=2 pega esses dados
            getNoDTO().setCodigo(getItemdoNoDTO().getNoFk().getNoFk().getCodigo() + "-" + getItemdoNoDTO().getNoFk().getTitulo());
        }
        if (getItemdoNoDTO().getNoFk().getNivel() >= 2 && getNoDTO().getProdutoFinal() == true) {  //senão se o nivel >=2 pega esses dados
            System.out.println("entrei************************************************************");
            getNoDTO().setCodigo(getItemdoNoDTO().getNoFk().getNoFk().getCodigo() + "-" + getItemdoNoDTO().getNoFk().getTitulo());

            int s = getItemdoNoDTO().getNoFk().getTipoProdutofk().getIdTipoProduto();

            getTipoProdutoDTO().setIdTipoProduto(s);
            setTipoProdutoDTO(tipoProdutoDAO.selecionar(getTipoProdutoDTO()));
            int c = getItemdoNoDTO().getNoFk().getNoFk().getIdNo();
            String w = tipoProdutoDTO.getIdentificacao();
            u = getNoDTO().getIdentExterna();

            //criar produto final 01
            int i = 1;
            k = w + "0" + i;
            System.out.println("kkk normal" + k);
            /*
             * Lista filhos verifica se o pai já contém um filho produto final e
             * se esse produto final já existe para ser incrementado ex: DL01 já
             * existir em algum produto final do pai selecionado o próximo será
             * DL02 e assim por diante.
             */
            listarFilhos(c, k);


            String k2 = null;
            while (!listaFilhos.isEmpty()) {
                i = i + 1;
                k2 = w + "0" + i;
                k = k2;
                System.out.println("existe lista====" + k2);
                listarFilhos(c, k);

                getNoDTO().setTitulo(k);
                System.out.println("getNoDTO().setTitulo(k);=============" + getNoDTO().getTitulo());
            }
            getNoDTO().setTitulo(k);
            //******************************************************************
        }
        noDAO.alterarProduto(noDTO);
        //historicodoNó
        int y = getItemdoNoDTO().getNoFk().getIdNo(); //  pegar  objeto o id do nó 
        int x = getItemdoNoDTO().getNoFk().getEstadoNofk().getIdEstadoNo(); // pegar o objeto id do estado
        System.out.println("/*x" + y);
        historicoDoNoAlterado(x, y);
        return "backtree";
    }

    //Necessario declarar o getDelete para abrir popup
    public int getDelete() {

        System.out.println("/*está sendo usado 017");
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public List<ArvoreDTO> getListaNoFilhos(int c, String k) {

        listaNoFilhos(c, k);
        return listaNoFilhos;
    }

    public void setListaNoFilhos(List<ArvoreDTO> listaNoFilhos) {
        this.listaNoFilhos = listaNoFilhos;
    }

    public String listaNoFilhos(int c, String k) {

        ArvoreDAO arvoreDAO = new ArvoreDAO();

        listaNoFilhos = arvoreDAO.listarFilho(c, k);

        return "backtree";

    }

    /**
     * Instancia um noDAO, invoca o método deleteNode, passando o objeto noDTO e
     * o arvoreDTO e atualizar a tree
     *
     * @author livia.miura
     * @return String backtree, para navegação.
     *
     */
    public String deleteNode() {

        String retorno = "listar";

        NoDAO noDAO = new NoDAO();
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        ArvoreDAO arvoreDAO = new ArvoreDAO();


        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        getNoDTO().setIdNo(a);
        setNoDTO(noDAO.selecionar(getNoDTO()));

        getArvoreDTO().setIdArvore(a);
        setArvoreDTO(arvoreDAO.selecionar(getArvoreDTO()));

        //grava no historico do nó
        int y = getNoDTO().getIdNo(); //  pegar  objeto o id do nó 
        int x = getNoDTO().getEstadoNofk().getIdEstadoNo(); // pegar o objeto id do estado

        historicoDoNoDeletado(x, y);
        //************************************************************************

        if (getNoDTO().getNivel() == 0) {
            getNoDTO().setIdNo(a);
            setNoDTO(noDAO.selecionar(getNoDTO()));

            noDTO.setStatus(0);
            selectedNode.getChildren().clear();
            selectedNode.getParent().getChildren().remove(selectedNode);
            selectedNode.setParent(null);
            selectedNode = null;


            noDAO.alterarNivel1(noDTO);
            retorno = "listar";
        }
        if (getNoDTO().getNivel() == 1) {
            getNoDTO().setIdNo(a);
            setNoDTO(noDAO.selecionar(getNoDTO()));

            noDTO.setStatus(0);
            selectedNode.getChildren().clear();
            selectedNode.getParent().getChildren().remove(selectedNode);
            selectedNode.setParent(null);
            selectedNode = null;


            noDAO.alterarNivel1(noDTO);
        }
        if (getNoDTO().getNivel() == 2) {

            getNoDTO().setIdNo(a);
            setNoDTO(noDAO.selecionar(getNoDTO()));
            noDTO.setStatus(0);
            noDAO.alterarNivel1(noDTO);

            selectedNode.getData();

            expandFromNode(selectedNode);

            selectedNode.getChildren().clear();
            selectedNode.getParent().getChildren().remove(selectedNode);
            selectedNode.setParent(null);
            selectedNode = null;

            retorno = "listar";
        }
        if (getNoDTO().getNivel() > 2) {


            noDTO.setStatus(0);
            noDAO.alterarNivel1(noDTO);
            selectedNode.getData();

            expandFromNode(selectedNode);

            selectedNode.getChildren().clear();
            selectedNode.getParent().getChildren().remove(selectedNode);
            selectedNode.setParent(null);
            selectedNode = null;

            retorno = "listar";
        } else {
            /*
             * FacesContext.getCurrentInstance().addMessage(null, new
             * FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível
             * excluir." + " O Produto está vinculado a um Documento!", ""));
             * retorno = "listar"; ;.
             */
        }


        return retorno;


    }

    /**
     * Visualizar o produto de acordo com o nível desejado.
     *
     * @author livia.miura
     * @return String viewNivel0,viewNivel1,viewNivel2,viewNivel21 para
     * navegação.
     *
     */
    public String visualizarProduto() {

        String retorno = null;
        NoDAO noDAO = new NoDAO();

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        getItemdoNoDTO().getNoFk().setIdNo(a);
        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        if (getItemdoNoDTO().getNoFk().getIdNo() != null) {

            if (getItemdoNoDTO().getNoFk().getNivel() == 0) {
                retorno = "viewNivel0";
            }
            if (getItemdoNoDTO().getNoFk().getNivel() == 1) {
                retorno = "viewNivel1";
            }
            if (getItemdoNoDTO().getNoFk().getNivel() >= 2 && getItemdoNoDTO().getNoFk().getProdutoFinal() == false) {
                retorno = "viewNivel2";
            }
            if (getItemdoNoDTO().getNoFk().getNivel() >= 2 && getItemdoNoDTO().getNoFk().getProdutoFinal() == true) {
                retorno = "viewNivel21";

            }

        } else {
            System.out.println("erro");

        }
        return retorno;
    }

    public String gerarDoc() {

        NoDAO noDAO = new NoDAO();

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        getNoDTO().setIdNo(a);

        return "doc";

    }

    //********************INICIO DO DOCUMENTO VINCULADO ************************
    //**************************************************************************
    //*** a partir dessa linha é referente o Documento Vinculado ao Produto **** 
    //**************************************************************************
    /**
     * Permite pegar o valor do selectNode selecionado na tree e inserir em um
     * novo registro na tabela ItemdoNoDTO
     *
     * @author livia.miura
     * @return String inserirDocumento, para navegação.
     *
     */
    public String inserirDocumento() {

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        getItemdoNoDTO().setIdItemdoNo(0);
        getItemdoNoDTO().getDocumentoVinculadofk().setIdDocumentoVinculado(0);
        getItemdoNoDTO().getNoFk().setIdNo(a);

        return "inserirDocumento";
    }

    public String gravarDocumentoVinculado() {


        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();

        getDocumentoVinculadoDTO().setIdDocumentoVinculado(getItemdoNoDTO().getDocumentoVinculadofk().getIdDocumentoVinculado());
        getDocumentoVinculadoDTO().setTitulo(getItemdoNoDTO().getDocumentoVinculadofk().getTitulo());
        getDocumentoVinculadoDTO().setDescricao(getItemdoNoDTO().getDocumentoVinculadofk().getDescricao());
        getDocumentoVinculadoDTO().getTipoDocumentofk().setIdTipoDocumento(getItemdoNoDTO().getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento());
        getDocumentoVinculadoDTO().setEstadoDocumento(getItemdoNoDTO().getDocumentoVinculadofk().getEstadoDocumento());
        getDocumentoVinculadoDTO().setAutores(getItemdoNoDTO().getDocumentoVinculadofk().getAutores());
        getDocumentoVinculadoDTO().setTemplate(getItemdoNoDTO().getDocumentoVinculadofk().getTemplate());
        getDocumentoVinculadoDTO().getNoFk().setIdNo(getItemdoNoDTO().getNoFk().getIdNo());
        getDocumentoVinculadoDTO().setStatus(1);

        //grava documento Vinculado
        documentoVinculadoDAO.gravar(getDocumentoVinculadoDTO());
        getItemdoNoDTO().getDocumentoVinculadofk().setIdDocumentoVinculado(getDocumentoVinculadoDTO().getIdDocumentoVinculado());
        getItemdoNoDTO().setStatus(1);


        //******************************************************************************
        //para poder criar automaticamente os códigos do Documento Vinculado
        int a = getItemdoNoDTO().getNoFk().getIdNo();
        int b = getDocumentoVinculadoDTO().getIdDocumentoVinculado();
        int d = getDocumentoVinculadoDTO().getTipoDocumentofk().getIdTipoDocumento();

        //grava o Item do No 
        gravarItemnoNo(a, b);
        codigoDocumento(a, b, d);

        return "listar";
    }

    /**
     * Instancia um itemdoNoDAO, invoca o método gravar, passando o objeto
     * itemdoNoDTO
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String gravarItemnoNo(int a, int b) {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();

        getItemdoNoDTO().setStatus(1);
        getItemdoNoDTO().getNoFk().setIdNo(a);
        getItemdoNoDTO().getDocumentoVinculadofk().setIdDocumentoVinculado(b);
        getItemdoNoDTO().setIdItemdoNo(0);

        itemdoNoDAO.gravar(getItemdoNoDTO());


        return "listar";
    }

    /**
     * Instancia um noDAO, documentoVinculadoDAO, tipoDocumentoDAO obtem o id já
     * definidos do método gravarDocumentoVinculado de cada um e invoca o método
     * selecionar, passando o objeto NoDTO,DocumentoVinculadoDTO,
     * tipoDocumentoDTO, para poder ser criado o código do documento e código
     * externo do documento Vinculado.
     *
     * @author livia.miura
     */
    private void codigoDocumento(int a, int b, int c) {

        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        NoDAO noDAO = new NoDAO();


        getNoDTO().setIdNo(a);
        int x = getNoDTO().getIdNo();
        setNoDTO(noDAO.selecionar(getNoDTO()));
        String t = noDTO.getCodigo();
        System.out.println("t = " + t);

        documentoVinculadoDTO.setDiretorio(noDTO.getDiretorio());
        //**********************************************************************
        getTipoDocumentoDTO().setIdTipoDocumento(c);
        setTipoDocumentoDTO(tipoDocumentoDAO.selecionar(getTipoDocumentoDTO()));
        String e = tipoDocumentoDTO.getIdentificacao();
        System.out.println("e ==" + getTipoDocumentoDTO().getIdentificacao());
        //**********************************************************************
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(b);
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        documentoVinculadoDTO.setCodigoDocumento(t + "-" + e);
        documentoVinculadoDTO.setCodigoExterno(t + "-" + e);
        documentoVinculadoDTO.setNoPai(x);
        documentoVinculadoDTO.setCodigoProduto(t);



        String k2;
        String codigo = documentoVinculadoDTO.getCodigoDocumento();

        String cod = documentoVinculadoDTO.getCodigoProduto();

        listaDocUtilizado(x, cod);
        int i = listaDocUtilizado.size() + 1;
        if (i < 10) {
            k2 = codigo + "-00" + i;
            System.out.println("código novo" + k2);
        } else {
            k2 = codigo + "-0" + i;
            System.out.println("código novo" + k2);
        }
        documentoVinculadoDTO.setCodigoDocumento(k2);
        documentoVinculadoDTO.setCodigoExterno(k2);
        System.out.println("b====" + b);

        documentoVinculadoDTO.setIdItemdono(b);

        documentoVinculadoDAO.gravarCodigoDocumento(getDocumentoVinculadoDTO());



    }

    /**
     * Instancia um documentoVinculadoDAO, obtem o idTipoDocumento e invoca o
     * método selecionar, passando o objeto documentoVinculadoDTO
     *
     * @author livia.miura
     * @return String alterarDoc, para navegação.
     *
     */
    public String alterarDocumentoTREE() {

        System.out.println("/*verificar se está usando mesmo");
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        System.out.println("selecetedNode" + selectedNode);
        /*
         * Object data = selectedNode.getData(); int a =
         * Integer.parseInt(data.toString()); //**convertendo para inteiro*** //
         * System.out.println("selectedDocument" + selectedDocument);
         *
         * getItemdoNoDTO().getNoFk().setIdNo(a);
         * setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));
         */
        return "alterarDoc";
    }

    /**
     * Instancia um documentoVinculadoDAO, obtem o idTipoDocumento e invoca o
     * método selecionar, passando o objeto documentoVinculadoDTO Pelo
     * GestãoDocumentação - Documento Vinculado - listarDocumentos - Alterar
     * (*********fora da tree********)
     *
     * @author livia.miura
     * @return String alterarDoc, para navegação.
     *
     */
    public String alterarDocumento() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        getItemdoNoDTO().setIdItemdoNo(new Integer(request.getParameter("idItemdoNo")));
        setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));
        return "alterar";
    }

    /**
     * Instancia um documentoVinculadoDAO, obtem o idTipoDocumento e invoca o
     * método selecionar, passando o objeto documentoVinculadoDTO
     *
     * Alterar tree- Alterar Documento - lista os documentos relacionados aquele
     * nó e alterarDoc
     *
     * @author livia.miura
     * @return String alterarDoc, para navegação.
     *
     */
    public String alterarDoc() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();

        getItemdoNoDTO().setIdItemdoNo(new Integer(request.getParameter("idItemdoNo")));
        setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));
        return "alterarDoc";
    }

    /**
     * Instancia um documentoVinculadoDAO, invoca o método gravar, passando o
     * objeto documentoVinculadoDTO
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String alterarDocumentoVinculado() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        //  DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();

        getDocumentoVinculadoDTO().setIdDocumentoVinculado(getItemdoNoDTO().getDocumentoVinculadofk().getIdDocumentoVinculado());
        getDocumentoVinculadoDTO().setTitulo(getItemdoNoDTO().getDocumentoVinculadofk().getTitulo());
        getDocumentoVinculadoDTO().setDescricao(getItemdoNoDTO().getDocumentoVinculadofk().getDescricao());
        getDocumentoVinculadoDTO().getTipoDocumentofk().setIdTipoDocumento(getItemdoNoDTO().getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento());
        getDocumentoVinculadoDTO().setEstadoDocumento(getItemdoNoDTO().getDocumentoVinculadofk().getEstadoDocumento());
        getDocumentoVinculadoDTO().setAutores(getItemdoNoDTO().getDocumentoVinculadofk().getAutores());
        getDocumentoVinculadoDTO().setTemplate(getItemdoNoDTO().getDocumentoVinculadofk().getTemplate());
        //grava documento Vinculado
        getItemdoNoDTO().getDocumentoVinculadofk().setIdDocumentoVinculado(getDocumentoVinculadoDTO().getIdDocumentoVinculado());

        getItemdoNoDTO().setStatus(1);


        itemdoNoDAO.gravarAlterar(documentoVinculadoDTO, itemdoNoDTO);
        //grava o Item do No 
        //itemdoNoDAO.gravar(getItemdoNoDTO());

        //******************************************************************************
        //para poder criar automaticamente os códigos do Documento Vinculado
        int a = getItemdoNoDTO().getNoFk().getIdNo();
        int b = getDocumentoVinculadoDTO().getIdDocumentoVinculado();
        int c = getDocumentoVinculadoDTO().getTipoDocumentofk().getIdTipoDocumento();

        codigoDocumento(a, b, c);

        return "retorno";
    }

    /**
     * Instancia um itemdoNoDAO, obtem o IdItemdoNo e invoca o método excluir,
     * Excluindo itemDoNo e DocumentoVinculado. Gravando no
     * HistoricoDocumentoVinculado as alterações que foram feitas podendo ser
     * DocumentoNovo- DocumentoAlterado - DocumentoExcluído
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String excluir() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        // Status irá para 0 e documento será desabilitado
        getDocumentoVinculadoDTO().setStatus(0);
        itemdoNoDAO.desabilitar(itemdoNoDTO);

        return "listar";
    }

    //**********************************************
    // inicio de criação do upload dos documentos.
    //**********************************************
    public UploadedFile getFileUp() {
        return fileUp;
    }

    public void setFileUp(UploadedFile fileUp) {
        this.fileUp = fileUp;
    }

    public void up(FileUploadEvent event) {
        this.setFileUp(event.getFile());
        this.session.setAttribute("arquivoUpload", fileUp);
        if (fileUp.getFileName().toLowerCase().endsWith(".pdf")) {
        }
    }

    /**
     * Método para gravar arquivoUpload da session para o diretório
     *
     * @author Livia.Miura
     */
    public String gravarDocumentoNoDiretorio() {
        NoDAO noDAO = new NoDAO();
        // byte[] arquivo = getFileUp().getContents();
        InputStream in;
        FileOutputStream fileOut;

        try {
            // File dir = new File(session.getServletContext().getRealPath("/upload/DocumentoVinculado/"));
            File dir = new File(DIRETORIO);
            if (dir.mkdirs()) {
                System.out.println("Novo diretorio criado em  : " + dir.getAbsolutePath());
                this.setFileUp((UploadedFile) this.session.getAttribute("arquivoUpload"));
                fileOut = new FileOutputStream(DIRETORIO + "/" + getNoDTO().getDiretorio() + "/" + fileUp.getFileName());

                in = fileUp.getInputstream();
                in.close();
                fileOut.write(getFileUp().getContents());
                fileOut.close();

                documentoVinculadoDTO.setTemplate(fileUp.getFileName());
                itemdoNoDTO.getDocumentoVinculadofk().setTemplate(fileUp.getFileName());

            } else {
                System.out.println("/*Diretorio ja existe!!! ");

                this.setFileUp((UploadedFile) this.session.getAttribute("arquivoUpload"));
                // para poder ver o valor do getTitulo
                getNoDTO().setIdNo(getItemdoNoDTO().getNoFk().getIdNo());
                setNoDTO(noDAO.selecionar(getNoDTO()));
                System.out.println("diretorio == " + getNoDTO().getDiretorio());
                // é o diretorio atual
                fileOut = new FileOutputStream(DIRETORIO + "/" + getNoDTO().getDiretorio() + "/" + fileUp.getFileName());
                System.out.println("diretorio completo=" + DIRETORIO);

                in = fileUp.getInputstream();
                in.close();
                fileOut.write(getFileUp().getContents());
                fileOut.close();

                itemdoNoDTO.getDocumentoVinculadofk().setTemplate(fileUp.getFileName());
                System.out.println("caminho do diretorio: do BD id item + doc***** -->" + itemdoNoDTO.getDocumentoVinculadofk().getTemplate());





            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ioe);
        }
        return "voltarForm";
    }

    /**
     * Método para listar os documentos vinculados por Nó selecionado
     *
     * @author Lívia.Miura
     */
    public void consultar() throws Exception {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***
        System.out.println("rs==============" + a);
        getItemdoNoDTO().getNoFk().setIdNo(a);
        System.out.println("NOOOOOOOOO-============" + getItemdoNoDTO().getNoFk().getIdNo());

        try {
            this.listaPorNo = itemdoNoDAO.selecionarPorNo(itemdoNoDTO);

        } catch (Exception e) {
        }
    }

    public String consultarDocumentos() {

        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        System.out.println("/*está sendo usado 019");
        String retorno = null;
        NoDAO noDAO = new NoDAO();

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***
        System.out.println("a===?" + a);
        getItemdoNoDTO().getNoFk().setIdNo(a);

        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        if (getItemdoNoDTO().getNoFk().getIdNo() != null) {
            this.listaPorNo = itemdoNoDAO.selecionarPorNo(itemdoNoDTO);
            System.out.println("retornando");
            retorno = "listarDocumentos";

        } else {
            System.out.println("erro");

        }
        return retorno;
    }

    /**
     * Método de buscar por idtipoDocumento, usado nas consultas de documentos.
     *
     * @author Lívia.Miura
     */
    public void buscarPorTipoDocumentoID() throws Exception {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        try {
            int a = 1;
            this.listaConsultas = itemdoNoDAO.listarPorTipoDocumentoID(itemdoNoDTO, a);
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um documento para consultar",
                    ""));
        }
    }

    /**
     * Método de buscar por idEstado, usado nas consultas de documentos.
     *
     * @author Lívia.Miura
     */
    public void buscarPorEstadoNoID() throws Exception {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        try {
            int a = 1;
            this.listaConsultas = itemdoNoDAO.listarPorEstadoNoID(itemdoNoDTO, a);
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um estado para consultar",
                    ""));
        }
    }

    /**
     * Método de buscar por idEstado, usado nas consultas de documentos.
     *
     * @author Lívia.Miura
     */
    public void buscarPoridentificacao() throws Exception {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        try {
            int a = 1;
            this.listaConsultas = itemdoNoDAO.listarPorIdentificacao(itemdoNoDTO, a);
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um estado para consultar",
                    ""));
        }
    }

    /**
     * Método de consulta a árvore de produto por templates relacionados aos
     * documentos vinculados ao nó. Método de buscar por idDocumento.
     *
     * @author Lívia.Miura
     */
    public void buscarPorTemplate() throws Exception {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        try {

            this.listaConsultas = itemdoNoDAO.listarPorDocumentoVinculadoID(itemdoNoDTO);
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um  template para consultar",
                    ""));
        }
    }

    /**
     * Para visualizar o pdf Ajuda do menu
     *
     * @author livia.miura
     * @return String file, para navegação.
     *
     */
    public void exibirPDF() {

        try {
            String caminho = "/GAP/Ajuda/ManualGap.pdf/";
            File file = new File(session.getServletContext().getRealPath("/") + caminho);
            System.out.println("caminho" + file);
            byte[] b = fileToByte(file);
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            res.setHeader("Content-disposition", "inline; filename=\"" + file.getName());

            res.getOutputStream().write(b);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
            retorno = null;
        }
    }

    /**
     * Método fileToByte do pdf
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public static byte[] fileToByte(File imagem) throws Exception {
        FileInputStream fis = new FileInputStream(imagem);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
    //************************** fim download

    //***********inicio do método de visualização do documento vinculado********
    /**
     * Método de Busca do documento vinculado no item(lupa)
     *
     * @author livia.miura
     * @return String listaItem, para navegação.
     *
     */
    public String visualizarItem() {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));

        int idBusca = getDocumentoVinculadoDTO().getIdDocumentoVinculado();
        DocumentoVinculadoDAO listaBusca = new DocumentoVinculadoDAO();
        listaIDBusca = listaBusca.listarComp(idBusca);
        dialogoDocumento();

        return "listaItem";
    }

    public DocumentoVinculadoDTO getItemdoNoDTOSelecionado() {
        return itemdoNoDTOSelecionado;
    }

    public void setItemdoNoDTOSelecionado(DocumentoVinculadoDTO itemdoNoDTOSelecionado) {
        this.itemdoNoDTOSelecionado = itemdoNoDTOSelecionado;
    }

    /**
     * Método para selecionar componente e montar diálogo para visualização
     *
     * @author livia.miura
     *
     */
    public void dialogoDocumento() {
        DocumentoVinculadoDTO item = null;
        Iterator teste = getLista().iterator();
        while (teste.hasNext()) {
            item = (DocumentoVinculadoDTO) teste.next();
        }
        itemdoNoDTOSelecionado = item;

    }

    /**
     * Retorno da navegação visualizar Documentos para lista
     *
     */
    public String voltarLista() {
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();


        lista = itemdoNoDAO.listar();

        System.out.println("o/*i");
        return "voltarLista";
    }

    //************fim do método de visualização do documento vinculado************
    /**
     * Gera o pdf com cabeçalho
     */
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "imagens" + File.separator + "2.png";


        pdf.add(Image.getInstance(logo));

    }

    //***********sem iReport****************************************************
    /**
     * Gera PDF de um Produto Específico utilizando API OpenSource iText, para
     * manipular e criar documentos em formato PDF.
     *
     * Menu Árvore de Produto - Exibir Árvore - Árvore de Produto - Gerar PDF
     * Produto.
     *
     * 1- Menu Árvore de Produto - Exibir Árvore - Árvore de Produto - Gerar PDF
     * Produto.
     *
     * @author livia.miura
     */
    public void produtoPDF() throws BadElementException, MalformedURLException, IOException {


        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***

        try {
            geraPDF(a);
        } catch (DocumentException ex) {
            Logger.getLogger(ItemdoNoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Desenvolvimento do PDF API iText é uma biblioteca que permite criar e
     * manipular documentos PDF.
     *
     * Gera PDF apenas a lista de produtos de um nó específico. Menu Árvore -
     * Exibir Árvore - Menu - ÁrvoreProduto - Gerar PDF Produto
     *
     * @author livia.miura
     */
    public void geraPDF(int a) throws DocumentException, BadElementException, MalformedURLException, IOException {


        NoDAO noDAO = new NoDAO();

        getItemdoNoDTO().getNoFk().setIdNo(a);
        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        File arquivo = new File("documento_Arvore_de_Produto.pdf");

        Document document = new Document();
        // criação PDF dentro de servlet 
        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemdoNoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // 1 - PDF
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, buffer);
            document.open();

            Image figura = Image.getInstance(realPath + "/imagens/2.png");
            figura.setAlignment(Image.LEFT);
            figura.scaleAbsoluteHeight(50);
            figura.scaleAbsoluteWidth(520);

            document.add(figura);
            document.add(new Paragraph("\n\n"));

            Image figura1 = Image.getInstance(realPath + "/imagens/Gap33.png");
            figura1.setAlignment(Image.MIDDLE);
            figura1.scaleAbsoluteHeight(50);
            figura1.scaleAbsoluteWidth(550);

            document.add(figura1);

            if (itemdoNoDTO.getNoFk().getProdutoFinal() == true) {
                status = "Sim";


                document.add(new Paragraph("\nIdentificação:                 " + itemdoNoDTO.getNoFk().getTitulo()
                        + "\nDescrição:                     " + itemdoNoDTO.getNoFk().getDescricao()
                        + "\nCódigo do Produto:       " + itemdoNoDTO.getNoFk().getCodigo()
                        + "\nTipo de Produto:           " + itemdoNoDTO.getNoFk().getTipoProdutofk().getDescricao()
                        + "\nResponsável:                " + itemdoNoDTO.getNoFk().getResponsavel().getNome()
                        + "\nNível:                             " + itemdoNoDTO.getNoFk().getNivel()
                        + "\nEstado:                         " + itemdoNoDTO.getNoFk().getEstadoNofk().getIdentificacao()
                        + "\nDomínio:                       " + itemdoNoDTO.getNoFk().getTipoDominio()
                        + "\nIdentificação Externa:   " + itemdoNoDTO.getNoFk().getIdentExterna()
                        + "\nProduto Final:               " + status));

            } else {
                status = "Não";
                document.add(new Paragraph("\nIdentificação:                 " + itemdoNoDTO.getNoFk().getTitulo()
                        + "\nDescrição:                     " + itemdoNoDTO.getNoFk().getDescricao()
                        + "\nCódigo do Produto:       " + itemdoNoDTO.getNoFk().getCodigo()
                        + "\nTipo de Produto:           " + itemdoNoDTO.getNoFk().getTipoProdutofk().getDescricao()
                        + "\nResponsável:                " + itemdoNoDTO.getNoFk().getResponsavel().getNome()
                        + "\nNível:                             " + itemdoNoDTO.getNoFk().getNivel()
                        + "\nEstado:                         " + itemdoNoDTO.getNoFk().getEstadoNofk().getIdentificacao()
                        + "\nDomínio:                       " + itemdoNoDTO.getNoFk().getTipoDominio()
                        + "\nIdentificação Externa:   " + itemdoNoDTO.getNoFk().getIdentExterna()
                        + "\nProduto Final:               " + status));


            }
            document.close();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            // definir  alguns cabeçalhos de resposta
            response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/documento_Arvore_de_Produto.pdf");
            response.setContentType("application/pdf");

            response.setHeader("Pragma", "no-cache");

            DataOutput dataOutput = new DataOutputStream(response.getOutputStream());
            byte[] bytes = buffer.toByteArray();
            response.setContentLength(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                dataOutput.writeByte(bytes[i]);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /*
     * iREPORT Esse método é usado em 2 lugares:
     *
     * 1 - Menu - Árvore de Produto - Exibir Árvore - Árvore de Documentação -
     * Gerar PDF - Todos os Produtos
     *
     * 2 - Menu - Consultas - Árvore de Produto - Documentos da Árvore de
     * Produto - Identificação
     *
     * @author livia.miura
     */
    public void documentoPDF() throws UtilException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Object data = selectedNode.getData();
        int noPai = Integer.parseInt(data.toString());  //**convertendo para inteiro***
        System.out.println("abc===" + noPai);
        gerarRelatorio(realPath, noPai);
    }

    /*
     * iREPORT Esse método é usado em 2 lugares:
     *
     * 1 - Menu - Consultas - Árvore de Produto- Árvore de Produto - Produto e
     * seus Documentos.
     *
     * 2 - Menu - Consultas - Árvore de Produto - Documentos Árvore de Produto -
     * Identificação
     *
     * @author livia.miura
     */
    public void consultaDocumentoPDF() throws UtilException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        try {
            int noPai = itemdoNoDTO.getNoFk().getIdNo();
            gerarRelatorio(realPath, noPai);
        } catch (IOException ex) {
            Logger.getLogger(ItemdoNoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Gera o PDF da lista selecionada por Identificação da Árvore de Produto
     * DSS.
     *
     * @author livia.miura
     */
    public void gerarRelatorio(String path, int noPai) throws IOException {

        Map parameters = new HashMap();

        parameters.put("noPai", noPai);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = path + "WEB-INF/consultas/iReport/documents.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/documents.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/documents.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");

        try {
            // 2 - envia para navegador o PDF gerado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters, con);


            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, con);
            JRExporter exporter = new JRXlsExporter();
            byte[] output;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            output = baos.toByteArray();

            servletOutputStream.write(output);


            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (JRException e) {
            e.printStackTrace();
            context.responseComplete();

        }

    }
    /*
     * O iReport é uma ferramenta que visa facilitar a construção de relatórios
     * utilizando a biblioteca JasperReports
     *
     * Esse método é usado em 2 lugares.
     *
     * 1 - Menu Consultas - Documentos Árvore de Produto - Tipo de Documento.
     *
     * 2 - Menu Consultas - Árvore de Produto - Tipo de Documento
     *
     * @author livia.miura
     *
     */

    public void consultaTipoDocumentoPDF() throws UtilException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        int tipoDoc = itemdoNoDTO.getDocumentoVinculadofk().getTipoDocumentofk().getIdTipoDocumento();

        gerarRelatorioTipoDoc(realPath, tipoDoc);
    }

    /*
     * Gera PDF com os valores recebidos pelo consultaTipoDocumentoPDF
     *
     * @author livia.miura
     */
    public void gerarRelatorioTipoDoc(String path, int tipoDoc) throws IOException {

        Map parameters = new HashMap();

        parameters.put("tipoDoc", tipoDoc);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = path + "WEB-INF/consultas/iReport/consultaTipoDocumentos.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/consultaTipoDocumentos.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/consultaTipoDocumentos.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");

        try {
            // 2 - envia para navegador o PDF gerado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters, con);

            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, con);
            JRExporter exporter = new JRXlsExporter();
            byte[] output;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            output = baos.toByteArray();

            servletOutputStream.write(output);

            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (JRException e) {
            e.printStackTrace();
            context.responseComplete();
        }
    }

    /**
     * iReport no Menu Consultas - Árvore de Produto - Templates
     *
     * @author livia.miura
     *
     */
    public void consultaporTemplate() throws UtilException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        int idDoc = itemdoNoDTO.getDocumentoVinculadofk().getIdDocumentoVinculado();
        gerarRelatorioTemplate(realPath, idDoc);


    }
    /*
     * Gera iReport com os valores recebidos pelo consulta Template
     *
     * @author livia.miura
     */

    public void gerarRelatorioTemplate(String path, int idDoc) throws IOException {


        Map parameters = new HashMap();

        parameters.put("idDoc", idDoc);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = path + "WEB-INF/consultas/iReport/consultaTemplates.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/consultaTemplates.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/consultaTemplates.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");

        try {
            // 2 - envia para navegador o PDF gerado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters, con);

            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, con);
            JRExporter exporter = new JRXlsExporter();
            byte[] output;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            output = baos.toByteArray();

            servletOutputStream.write(output);

            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (JRException e) {
            e.printStackTrace();
            context.responseComplete();
        }
    }


    /*
     *
     * O iReport é uma ferramenta que visa facilitar a construção de relatórios
     * utilizando a biblioteca JasperReports
     *
     * 1 - Menu - Árvore de Produto - Exibir Árvore - Árvore de Produto - Gerar
     * Documento Ramo Específico.
     *
     * @author livia.miura
     */
    public void geraDocumentoRamoEspecifico() throws IOException {

        Object data = selectedNode.getData();
        int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***


        try {
            geraDocumento(a, 1);

        } catch (UtilException ex) {
            Logger.getLogger(DocumentoVinculadoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(DocumentoVinculadoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Gera o PDF de todos completa de todos os produtos gerados pela DSS.
     *
     * @author livia.miura.
     */
    public void geraDocumento(int a, int parametro) throws UtilException, JRException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("a", a);
        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/ramo_especifico.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/ramo_especifico.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/ramo_especifico.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        try {
            // 2 - envia para navegador o PDF gerado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters, con);

            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, con);
            JRExporter exporter = new JRXlsExporter();
            byte[] output;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            output = baos.toByteArray();

            servletOutputStream.write(output);

            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (JRException e) {
            e.printStackTrace();
            context.responseComplete();
        }
    }
    /*
     * IREPORT por busca de um produto específico na árvore DSS.
     *
     * Menu-Consultas-Árvore de Produto - Árvore de Produto-Estado do Produto.
     *
     * recebe 1 que é status e a = idEstado
     *
     * @autor livia.miura.
     *
     */

    public void geraRelatorioDocumemtoEstadodoProduto() throws IOException {

        try {

            int a = itemdoNoDTO.getNoFk().getEstadoNofk().getIdEstadoNo();

            geraRelatorioDocumentoEstadoProduto(a, 1);

        } catch (UtilException ex) {
            Logger.getLogger(NoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(NoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     * Gera um PDF de um produto específico na árvore DSS. Menu-Consultas-Árvore
     * de Produto Final-Estado do Produto.
     *
     * @autor livia.miura
     *
     */

    public void geraRelatorioDocumentoEstadoProduto(int a, int parametro) throws UtilException, JRException, IOException {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("a", a);
        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/documentoEstadoProduto.jasper";

        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/documentoEstadoProduto.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/documentoEstadoProduto.pdf");
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");

        try {
            // 2 - envia para navegador o PDF gerado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters, con);

            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, con);
            JRExporter exporter = new JRXlsExporter();
            byte[] output;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            output = baos.toByteArray();

            servletOutputStream.write(output);

            servletOutputStream.flush();
            servletOutputStream.close();

        } catch (JRException e) {
            e.printStackTrace();
            context.responseComplete();
        }
    }

    public void setFile(DefaultStreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() {



        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        StreamedContent retorno = null;
        System.out.println("///**********************************documento ==" + documentoVinculadoDTO.getTemplate());

        if (documentoVinculadoDTO.getTemplate().equals("")) {
            System.out.println("entrei");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Documento não existe!!!!",
                    "DOC não encontrado!"));
            retorno = null;
        } else {
            try {

                String x = getDocumentoVinculadoDTO().getTemplate();
                String y = getDocumentoVinculadoDTO().getNoFk().getDiretorio();
                
               
               FileInputStream stream = new FileInputStream(new File(DIRETORIO + y + "/" + x));


            
                file = new DefaultStreamedContent(stream, "tipo/pdf", x);
                file = new DefaultStreamedContent(stream, "tipo/doc", x);
                file = new DefaultStreamedContent(stream, "tipo/docx", x);
                System.out.println("file = "+ file.getName());
                return file;

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Documento não existe!!!!",
                        "Documento não encontrado!"));
                retorno = null;
            }
        }
        return retorno;

    }
}
