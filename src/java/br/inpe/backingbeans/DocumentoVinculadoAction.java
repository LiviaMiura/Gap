/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.*;
import br.inpe.dto.*;
import br.inpe.iReport.UtilException;
import br.inpe.iReport.gConexao;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.*;
import java.io.InputStream;

/**
 *
 * @author livia.miura
 */
public class DocumentoVinculadoAction {

    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    private static final long serialVersionUID = 1L;
    private DocumentoVinculadoDTO documentoVinculadoDTO;
    private List<DocumentoVinculadoDTO> lista;
        private List<DocumentoVinculadoDTO> lista2;
    private List<DocumentoVinculadoDTO> listaDoc;
    private List<NoDTO> listaNo;
    private TreeNode root;
    TreeNode filho;
    private UploadedFile fileUp;
   // private String DIRETORIO = "C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/";
        private String DIRETORIO = "C:/GAP";
    private DefaultStreamedContent file;
    private Date dataInicio;
    private NoDTO noDTO;
    private ItemdoNoDTO itemdoNoDTO;
    private TipoDocumentoDTO tipoDocumentoDTO;
    private List<DocumentoVinculadoDTO> listaNoEspecifico;
    private DocumentoVinculadoDTO itemdoNoDTOSelecionado;
    private List<DocumentoVinculadoDTO> listaIDBusca;
    private String retorno;
    private UsuarioDTO usuarioDTO;
    private HistoricoDocVinculadoDTO historicoDocVinculadoDTO;
    private List<TipoDocumentoDTO> resultado;

    /**
     * Creates a new instance of DocumentoVinculadoAction
     */
    public DocumentoVinculadoAction() {
    }

    /*
     * Os tipo de documento estão armazenadas na Tabela TipoDocumentoDTO. Para
     * recuperar todas os identificações do tipo de documento, é preciso
     * percorrer todas as posições da Tabela TipoDocumentoDTO. Em cada posição,
     * há uma Lista, pegaremos todos os elementos de cada Lista e armazenaremos
     * em uma única Lista e a devolveremos.
     *
     * @author livia.miura
     */
    public List<SelectItem> getNoDTOs() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        resultado = tipoDocumentoDAO.listar();
        items.add(new SelectItem("", "Selecione"));

        for (int i = 1; i <= (resultado.size()); i++) {
            SelectItem item = new SelectItem();
            item.setLabel(resultado.get(i - 1).getIdentificacao());
            items.add(item);
        }
        return items;

    }

    /*
     * Os Produto estão armazenadas na Tabela NoDTO. Para recuperar todas os
     * titulos do produto, é preciso percorrer todas as posições da Tabela
     * NoDTO. Em cada posição, há uma Lista, pegaremos todos os elementos de
     * cada Lista e armazenaremos em uma única Lista e a devolveremos.
     *
     * @author livia.miura
     */
    public List<SelectItem> getNoDTOXs() {
        NoDAO noDAO = new NoDAO();

        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        List<NoDTO> resultadox = noDAO.listarNo(1);

        items.add(new SelectItem("", "Selecione"));
        for (int i = 1; i <= (resultadox.size()); i++) {

            SelectItem item = new SelectItem();

            item.setLabel(resultadox.get(i - 1).getTitulo());
            items.add(item);

        }


        return items;

    }

    //**************************************************************************
    public List<DocumentoVinculadoDTO> getListaDoc() {
        //listar();   //para nao listar na consulta todos os itens    
        return listaDoc;
    }

    public void setListaDoc(List<DocumentoVinculadoDTO> listaDoc) {
        this.listaDoc = listaDoc;
    }

    //**************************************************************************
    public List<DocumentoVinculadoDTO> getLista() {
        listar();   //para nao listar na consulta todos os itens       
        return lista;
    }

    public void setLista(List<DocumentoVinculadoDTO> lista) {
        this.lista = lista;
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

    public NoDTO getNoDTO() {
        if (noDTO == null) {
            noDTO = new NoDTO();
        }
        return noDTO;
    }

    public void setNoDTO(NoDTO noDTO) {
        this.noDTO = noDTO;
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

    public TipoDocumentoDTO getTipoDocumentoDTO() {
        if (tipoDocumentoDTO == null) {
            tipoDocumentoDTO = new TipoDocumentoDTO();
        }
        return tipoDocumentoDTO;
    }

    public void setTipoDocumentoDTO(TipoDocumentoDTO tipoDocumentoDTO) {
        this.tipoDocumentoDTO = tipoDocumentoDTO;
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

    public HistoricoDocVinculadoDTO getHistoricoDocVinculadoDTO() {
        if (historicoDocVinculadoDTO == null) {
            historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();
        }
        return historicoDocVinculadoDTO;
    }

    public void setHistoricoDocVinculadoDTO(HistoricoDocVinculadoDTO historicoDocVinculadoDTO) {
        this.historicoDocVinculadoDTO = historicoDocVinculadoDTO;
    }

    public String inserirTemplate() {

        return "inserirTemplate";
    }

    
        
    public List<DocumentoVinculadoDTO> getLista2() {
       
        System.out.println("lista2 = " + lista2);
        listar2();
        return lista2;
    }

    public void setLista2(List<DocumentoVinculadoDTO> lista2) {
        this.lista2 = lista2;
    }
    
      public String listar2() {
        System.out.println("/*28 08 13 realmente usado no ItemDoNo***1111***");
      DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        lista2 = documentoVinculadoDAO.listarStatus2(1, 1);
        return "listar";

    }
    
    /**
     * Instancia um documentoVinculadoDAO e invoca o método listar().
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String listarteste() {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        lista = documentoVinculadoDAO.listar();
        return "listar";
    }

    // lista todos os status que seja == 1 no  menu documento vinculado 
    public String listar() {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        lista = documentoVinculadoDAO.listarStatus(1);
        return "listar";

    }

    public String listar1() {
        return "listar1";

    }

    /**
     * Controle de navegação
     *
     * @author livia.miura
     * @return String inserirNovo, para navegação.
     */
    //verificar se ira usar
    public String inserir() {
        System.out.println("$$$$$$$$$ ver se usa linha 139 docVinculadoAction");
        //   getDocumentoVinculadoDTO().setIdDocumentoVinculado(0);
        return "inserir";
    }

    public String inserirZ() {

        System.out.println("$$$$$$$$$ ver se usa linha 147 docVinculadoAction");

        // Object data = selectedNode.getData();
        //  int a = Integer.parseInt(data.toString());  //**convertendo para inteiro***


        getNoDTO().setTipoDominio("Interno");
        // getNoDTO().getNoFk().setIdNo(a);
        getNoDTO().setIdNo(3);
        return "inserirZ";

    }

    public String inserirNo(int a) {
        System.out.println("$$$$$$$$$ ver se usa linha 160 docVinculadoAction");


        // NoAction noAction = new NoAction();
        System.out.println("******usei 16******** inserir DOC Vinculado****************");


        getDocumentoVinculadoDTO().setIdDocumentoVinculado(0);
        //   getDocumentoVinculadoDTO().getNoFk().setIdNo(a);
        //    System.out.println(" valor do IDNO---> " + getDocumentoVinculadoDTO().getNoFk().getIdNo());
        System.out.println("valor do IDDOCVINCUlado--->" + getDocumentoVinculadoDTO().getIdDocumentoVinculado());

        return "inserirNo";
    }

    public String dados(int a) {
        System.out.println("$$$$$$$$$ ver se usa linha 176 docVinculadoAction");

///por enquanto nao utilizado
        System.out.println("A dentro do dados" + a);
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(a);
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
//        getDocumentoVinculadoDTO().getDocumentoVinculadoFk().setIdDocumentoVinculado(a);
        getDocumentoVinculadoDTO().setTitulo(null);
        getDocumentoVinculadoDTO().setDescricao(null);
        //  getDocumentoVinculadoDTO().setCodigoDocumento(null);
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(0);

        return "inserir";
    }

    /**
     * Instancia um documentoVinculadoDAO, obtem o idTipoDocumento e invoca o
     * método selecionar, passando o objeto documentoVinculadoDTO Pelo
     * GestãoDocumentação - Documento Vinculado - listarDocumentos - Alterar
     *
     * @author livia.miura
     * @return String alterarDoc, para navegação.
     *
     */
    public String alterarDocumento() {
        System.out.println("/*está sendo usado 026 doc");
        System.out.println("entrei no alterar documento");
        //  ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        //getItemdoNoDTO().setIdItemdoNo(new Integer(request.getParameter("idItemdoNo")));
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        // setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        return "alterar";
    }

    public String alterarDocumentoVinculado() {
        System.out.println("/entreino alterar doc dentro documentoVinculadoAction");
        System.out.println("*************************************Atualizando Documento Vinculado");

        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        //fixar o valor do status
        getDocumentoVinculadoDTO().setStatus(1);

        documentoVinculadoDAO.gravarAlterar(documentoVinculadoDTO);
        /*
         * //******************************************************************************
         * //para poder criar automaticamente os códigos do Documento Vinculado
         * int a = getDocumentoVinculadoDTO().getNoFk().getIdNo(); int b =
         * getDocumentoVinculadoDTO().getIdDocumentoVinculado(); int c =
         * getDocumentoVinculadoDTO().getTipoDocumentofk().getIdTipoDocumento();
         *
         * codigoDocumento(a, b, c);
         */
        return "retorno";
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
        System.out.println("/*está sendo usado 024 doc");
        System.out.println("USEI A.4 codigoDocumento NA TREE   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        NoDAO noDAO = new NoDAO();
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        getNoDTO().setIdNo(a);
        setNoDTO(noDAO.selecionar(getNoDTO()));
        String t = noDTO.getCodigo();
        System.out.println("t = " + t);
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

        documentoVinculadoDAO.gravarCodigoDocumento(getDocumentoVinculadoDTO());

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
        System.out.println("/*Desabilitar no documentoVinculado");
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();

        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));

        // Status irá para 0 e documento será desabilitado
        getDocumentoVinculadoDTO().setStatus(0);

        // desabilitar o documento e itemdoNo, o usuario pensará que deletou o documento porém continua no BD

        int a = getDocumentoVinculadoDTO().getIdDocumentoVinculado();
        int b = getDocumentoVinculadoDTO().getIdItemdono();

        getItemdoNoDTO().setIdItemdoNo(b);
        setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));

        getItemdoNoDTO().setStatus(0);
        //**********************************************************************
        documentoVinculadoDAO.desabilitar(documentoVinculadoDTO);
        historicoDocumentoExcluir(a);
        //**********************************************************************


        return "listar";
    }

    public void historicoDocumentoExcluir(int a) {

        HistoricoDocVinculadoDAO historicoDocVinculadoDAO = new HistoricoDocVinculadoDAO();
        HistoricoDocVinculadoDTO historicoDocVinculadoDTO = new HistoricoDocVinculadoDTO();

        historicoDocVinculadoDTO.getDocumentoVinculadofk().setIdDocumentoVinculado(a);
        historicoDocVinculadoDTO.setTipoAlteracao("Doc Desvinculado");
        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        System.out.println("valor de usuario ===" + usuarioDTO.getNome());
        historicoDocVinculadoDTO.setUsuarioOperacao(usuarioDTO.getNome());

        System.out.println("entrando no gravar historioco");
        historicoDocVinculadoDAO.gravar(historicoDocVinculadoDTO);
        System.out.println("saindo");
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String menu, para navegação.
     */
    public String menu() {
        return "menu";
    }

    //**************************************************************************
    /**
     * Método de buscar por titulo
     *
     * @author Lívia.Miura
     */
    public void consultar() throws Exception {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        try {
            this.listaDoc = documentoVinculadoDAO.listarPorTitulo(documentoVinculadoDTO);
            System.out.println("valor do titulo que foi buscar no dao -->" + documentoVinculadoDTO.getTitulo());
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um titulo para consultar",
                    ""));
        }
    }

    /**
     * Método para listar todos os documentos vinculados
     *
     * @author livia.miura
     */
    public void mostrarTodos() {
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        lista = documentoVinculadoDAO.listar();
    }
    // referente o download do arquivo

    public void setFile(DefaultStreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() {

        System.out.println("/*fazer o download do docuneto Vinculado 01 08 2013");

        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));
        StreamedContent retorno = null;

        if (documentoVinculadoDTO.getTemplate().equals("")) {
            System.out.println("estou equal ****************************");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Documento não existe!!!!",
                    "DOC não encontrado!"));
            retorno = null;
        } else {
            try {
                System.out.println("to no try do documento");
                String x = getDocumentoVinculadoDTO().getTemplate();
                String y = getDocumentoVinculadoDTO().getNoFk().getDiretorio();
                String d = DIRETORIO + y + "/" + x;
                System.out.println("d==="+d);
                FileInputStream stream = new FileInputStream(new File(DIRETORIO + y + "/" + x));

          
                System.out.println("passei por aqui");
                file = new DefaultStreamedContent(stream, "tipo/pdf", x);
                file = new DefaultStreamedContent(stream, "tipo/doc", x);
                file = new DefaultStreamedContent(stream, "tipo/docx", x);
             
                return file;

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Documento não existe!!!!",
                        "Doc não encontrado!"));
                retorno = null;
            }
        }
        return retorno;

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
        System.out.println("***************entrei no up do ITEM*******************");
        this.setFileUp(event.getFile());

        this.session.setAttribute("arquivoUpload", fileUp);
        System.out.println("fileUP =====" + fileUp);

        if (fileUp.getFileName().toLowerCase().endsWith(".pdf")) {

            System.out.println("fileUP dentro do IF UP ===" + fileUp.getFileName());

        }
    }

    /**
     * Método para gravar arquivoUpload da session para o diretório
     *
     * @author Livia.Miura
     */
    public String gravarDocumentoNoDiretorio() {
        NoDAO noDAO = new NoDAO();
        System.out.println("entrei no diretorio alterado by livia 31 07 13");
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
                //   itemdoNoDTO.getDocumentoVinculadofk().setTemplate(fileUp.getFileName());
                System.out.println("caminho do diretorio: -->" + documentoVinculadoDTO.getTemplate());

            } else {
                System.out.println("/*Diretorio ja existe!!! ");

                this.setFileUp((UploadedFile) this.session.getAttribute("arquivoUpload"));
                System.out.println(" o valor do nó dentro do documento" + getDocumentoVinculadoDTO().getNoFk().getIdNo());

                getNoDTO().setIdNo(getDocumentoVinculadoDTO().getNoFk().getIdNo());
                setNoDTO(noDAO.selecionar(getNoDTO()));
                System.out.println("diretorio == " + getNoDTO().getDiretorio());
                // é o diretorio atual
                fileOut = new FileOutputStream(DIRETORIO + getNoDTO().getDiretorio() + "/" + fileUp.getFileName());
                System.out.println("diretorio completo@@@@@@@@@@@=" + DIRETORIO  + getNoDTO().getDiretorio() + "/" + fileUp.getFileName());

                System.out.println("arquivo" + getFileUp().getContents());
                System.out.println("file out= ==" + fileOut);
                in = fileUp.getInputstream();
                System.out.println("in ==>" + in.read());
                in.close();
                fileOut.write(getFileUp().getContents());
                fileOut.close();
                documentoVinculadoDTO.setDiretorio(getNoDTO().getDiretorio());
                documentoVinculadoDTO.setTemplate(fileUp.getFileName());
                //itemdoNoDTO.getDocumentoVinculadofk().setTemplate(fileUp.getFileName());
                System.out.println("caminho do diretorio: -->" + documentoVinculadoDTO.getTemplate());


            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ioe);
        }
        return "voltarForm";
    }

    /*
     * Pego o IdItemdoNo e coloco em uma lista, para ser utilizado no datatable
     * e usar "var", e para ser reconhecido quando for pedido para gravar os
     * dados no pdf.
     *
     */
    public List<DocumentoVinculadoDTO> getListaNoEspecifico() {
        System.out.println("entrei getListaNoEspecifico ");
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
            System.out.println("listaespecifico ====" + listaNoEspecifico);
        }

        return "listar";
    }

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
        //  ItemdoNoDAO itemdoNoDAO = new ItemdoNoDAO();
        // setItemdoNoDTO(itemdoNoDAO.selecionar(getItemdoNoDTO()));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));

        //  int idBusca = getItemdoNoDTO().getIdItemdoNo();
        int idBusca = getDocumentoVinculadoDTO().getIdDocumentoVinculado();

        //  ItemdoNoDAO listaBusca = new ItemdoNoDAO();
        DocumentoVinculadoDAO listaBusca = new DocumentoVinculadoDAO();

        listaIDBusca = listaBusca.listarComp(idBusca);

        dialogoDocumento();

        return "listaItem";
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
     * Para visualizar o pdf do documento
     *
     * @author livia.miura
     * @return String file, para navegação.
     *
     */
    public void exibirPDF() throws Exception {

        System.out.println("entrei exibir pdf");
try{
        DocumentoVinculadoDAO documentoVinculadoDAO = new DocumentoVinculadoDAO();
        getDocumentoVinculadoDTO().setIdDocumentoVinculado(new Integer(request.getParameter("idDocumentoVinculado")));
        setDocumentoVinculadoDTO(documentoVinculadoDAO.selecionar(getDocumentoVinculadoDTO()));


     
            String x = getDocumentoVinculadoDTO().getTemplate();
            String y = getDocumentoVinculadoDTO().getNoFk().getTitulo();

            System.out.println("x = " + x);
            System.out.println("y" + y);
            String caminho = "/GAP/" + getDocumentoVinculadoDTO().getDiretorio() + "/" + x;
            System.out.println("caminho= " + caminho);
            File file = new File(session.getServletContext().getRealPath("/") + caminho);

            byte[] b = fileToByte(file);
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            // Seta o tipo de conteudo no cabecalho da resposta. No caso, indica que o
            // conteudo será um documento pdf.
            res.setContentType("application/pdf"); // para abrir pdf
            res.setContentType("application/doc"); // para abrir doc
            res.setContentType("application/vnd.ms-excel"); // para abrir excel
            res.setHeader("Content-disposition", "inline; filename=\"" + file.getName());

            // Seta o tamanho do conteudo no cabecalho da resposta. No caso, o tamanho
            // em bytes do pdf
            res.setContentLength(b.length);
            // Envia o conteudo do arquivo PDF para o res
         
                
            res.getOutputStream().write(b);

            res.getCharacterEncoding();
            // Descarrega o conteudo do stream, forçando a escrita de qualquer byte
            // ainda em buffer
            res.getOutputStream().flush();

            // Fecha o stream, liberando seus recursos
            res.getOutputStream().close();

            // Sinaliza ao JSF que a resposta HTTP para este pedido já foi gerada
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

    //**************************************************************************
    //********************************IREPORT***********************************
    //**************************************************************************

    /*
     * Menu - Documentação - Documento Vinculado.
     *
     * @autor livia.miura
     *
     */
    public void geraRelatorioFeitas() throws IOException {
        System.out.println("entrei Ireport 1");
        try {
            geraRelatorio(1);
        } catch (UtilException ex) {
            Logger.getLogger(DocumentoVinculadoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(DocumentoVinculadoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Gera PDF de todos os documentos vinculado a árvore DSS.
     *
     * Menu - Documentação - Documento Vinculado.
     *
     * @autor livia.miura
     *
     */
    public void geraRelatorio(int parametro) throws UtilException, JRException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/documentosDSS.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/documentosDSS.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/documentosDSS.pdf");
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
    //**************************************************************************
//****************************FIM*IREPORT***********************************
//**************************************************************************

   

}
