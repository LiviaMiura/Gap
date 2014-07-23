/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.*;
import br.inpe.dto.*;
import br.inpe.iReport.UtilException;
import br.inpe.iReport.gConexao;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import java.io.InputStream;

/**
 *
 * @author livia.miura
 */
public class NoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);// mantém na session
    private NoDTO noDTO;
    private List<NoDTO> lista;
    private List<NoDTO> lista1;
    private Collection<NoDTO> lista2;
    private Collection<NoDTO> listaNo;
    private Collection<NoDTO> listaProdutoFinal;
    private UsuarioDTO usuarioDTO;
    private List<NoDTO> listaConsultas;
    private Date dataInicio;
    private Date dataConclusao;
    private ItemdoNoDTO itemdoNoDTO;
    private EstadoNoDTO estadoNoDTO;
    private HistoricoNoDTO historicoNoDTO;
    private SelectItem[] opcaoOperacao;
    private static String[] opcao;
    private String status;
    
    
     /*
     ******************************Atenção**************************************
     * **************Mudar o caminho quando colocar no servidor
     * "C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/
     */
  //  private String DIRETORIO = "C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/";
   
    private String DIRETORIO = "C:/GAP";
    public NoAction() {
        opcaoOperacao = createFilterOptions(opcao);
    }

    //*********Filtrar**********************************************************
    static {
        opcao = new String[3];

        opcao[0] = "Interno";
        opcao[1] = "Externo";
        opcao[2] = "Privado";

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

    //***********************fim de filtro**************************************
    public List<NoDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<NoDTO> lista) {
        this.lista = lista;
    }

    public List<NoDTO> getLista1() {
        listar1();
        return lista1;
    }

    public void setLista1(List<NoDTO> lista1) {
        this.lista1 = lista1;
    }

    public Collection<NoDTO> getLista2() {

        NoDAO noDAO = new NoDAO();
        lista2 = noDAO.listarNo(lista2, 1);
        return lista2;
    }

    public void setLista2(Collection<NoDTO> lista2) {
        this.lista2 = lista2;
    }

    public String listar1() {

        NoDAO noDAO = new NoDAO();
        lista1 = noDAO.listarNo(1);

        return "listar";
    }

    /**
     * Método para listar os Estado do Nó, selecionado através de combobox na
     * consulta. Produto final ==1
     *
     * @author livia.miura
     */
    // usado para listar na consulta com produto final =1
    public Collection<NoDTO> getListaProdutoFinal() {
        if (listaProdutoFinal == null) {
            NoDAO noDAO = new NoDAO();
            listaProdutoFinal = noDAO.listarNoProdutoFinal(listaProdutoFinal, 1);
        }
        return listaProdutoFinal;
    }

    public void setListaProdutoFinal(Collection<NoDTO> listaProdutoFinal) {
        this.listaProdutoFinal = listaProdutoFinal;
    }

    //**************************************************************************
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

    //listar consultas
    public List<NoDTO> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(List<NoDTO> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    //**************************************************************************
    public ItemdoNoDTO getItemdoNoDTO() {
        if (itemdoNoDTO == null) {
            itemdoNoDTO = new ItemdoNoDTO();
        }
        return itemdoNoDTO;
    }

    public void setItemdoNoDTO(ItemdoNoDTO itemdoNoDTO) {
        this.itemdoNoDTO = itemdoNoDTO;
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

    /**
     * Método para listar os Estado do Nó, selecionado através de combobox na
     * consulta.
     *
     * @author livia.miura
     */
    public void listarNoEstadoNo2() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getNoDTO().setEstadoNofk(estadoNoDAO.selecionar(getNoDTO().getEstadoNofk()));
        lista2 = getNoDTO().getEstadoNofk().getNoDTOCollection();

    }

    public void listarNoEstadoNo() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        getNoDTO().setEstadoNofk(estadoNoDAO.selecionar(getNoDTO().getEstadoNofk()));
        listaNo = getNoDTO().getEstadoNofk().getNoDTOCollection();

    }

    /**
     * Método para listar os Estado do Nó que sejam no folha da árvore,
     * selecionado através de combobox na consulta.
     *
     * @author livia.miura
     */
    public void listarEstadoNo() {
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();
        NoDAO noDAO = new NoDAO();
        getNoDTO().setEstadoNofk(estadoNoDAO.selecionar(getNoDTO().getEstadoNofk()));
        if (getNoDTO().getEstadoNofk().getIdEstadoNo() == null) {
            listaProdutoFinal = getNoDTO().getEstadoNofk().getNoDTOCollection();
        } else {
            int idEstado = getNoDTO().getEstadoNofk().getIdEstadoNo();
            listaProdutoFinal = noDAO.tipoEstadoNo(idEstado);
        }
    }

    /**
     * Método para listar os tipos de produtos que sejam no folha da árvore,
     * selecionado através de combobox na consulta.
     *
     * @author livia.miura
     */
    public void listarTipoProduto() {
        TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
        NoDAO noDAO = new NoDAO();
        getNoDTO().setTipoProdutofk(tipoProdutoDAO.selecionar(getNoDTO().getTipoProdutofk()));
        if (getNoDTO().getTipoProdutofk().getIdTipoProduto() == null) {
            listaProdutoFinal = getNoDTO().getTipoProdutofk().getNoDTOCollection();
        } else {
            int idProd = getNoDTO().getTipoProdutofk().getIdTipoProduto();
            listaProdutoFinal = noDAO.tipoProdutoFinal(idProd);
        }
    }

    /**
     * Método para listar todos os usuários que sejam um nó folha, selecionado
     * através de combobox na consulta.
     *
     * @author livia.miura
     */
    public void listarResponsavelProdutoFinal() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        NoDAO noDAO = new NoDAO();

        getNoDTO().setResponsavel(usuarioDAO.selecionar(getNoDTO().getResponsavel()));
        if (getNoDTO().getResponsavel().getIdUsuario() == null) {
            listaProdutoFinal = getNoDTO().getResponsavel().getNoDTOCollection();
        } else {
            int idUser = getNoDTO().getResponsavel().getIdUsuario();
            listaProdutoFinal = noDAO.responsavel(idUser);
        }

    }

    /**
     * Método para listar todos os produtos por identificação que sejam um nó
     * folha, selecionado através de combobox na consulta.
     *
     * @author livia.miura
     */
    public void listarIdentificacao() {

        NoDAO noDAO = new NoDAO();
        setNoDTO(noDAO.selecionar(getNoDTO()));
        if (getNoDTO().getIdNo() == null) {
            listaProdutoFinal = getNoDTO().getNoFkCollection();
        } else {
            System.out.println("entrei");
            int idIdent = getNoDTO().getIdNo();
            listaProdutoFinal = noDAO.identificacao(idIdent, 1);
        }

    }

    /**
     * Método para listar todos os produtos por nó específico, selecionado
     * através de combobox o pai e irá aparecer os filhos na consulta.
     *
     * @author livia.miura
     */
    public void listarNoTitulo() {

        NoDAO noDAO = new NoDAO();
        setNoDTO(noDAO.selecionar(getNoDTO()));
        listaNo = getNoDTO().getNoFkCollection();
    }

    /**
     * Método para listar os usuarios selecionado através de combobox
     *
     * @author livia.miura
     */
    public void listarUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        getNoDTO().setResponsavel(usuarioDAO.selecionar(getNoDTO().getResponsavel()));
        listaNo = getNoDTO().getResponsavel().getNoDTOCollection();

    }

    /**
     * Instancia um noDAO e invoca o método listar().
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String listar() {
        System.out.println("********listar");
        NoDAO noDAO = new NoDAO();
        lista = noDAO.listar();
        System.out.println("sai do listar");
        return "listar";
    }

    /**
     * Set idNo com zero e permite inserir novo registro na tabela no Do menu
     * abre a tela cria nova raiz
     *
     * @author livia.miura
     * @return String inserirNivel0, para navegação.
     *
     */
    public String inserirNivel() {
        System.out.println("USEI 0.1*INSERIR NIVEL 0  pelo menu &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        getNoDTO().setIdNo(0);
        getNoDTO().setNivel(0);
        // para ser selecionado - conteudo - não se aplica para os combox
        getNoDTO().getTipoProdutofk().setIdTipoProduto(7);
        getNoDTO().getEstadoNofk().setIdEstadoNo(11);
        return "inserirNivel";
    }

    /**
     * Set idNo com zero e permite inserir novo registro na tabela no DaTREE
     * cria nova raiz
     *
     * @author livia.miura
     * @return String inserirNivel0, para navegação.
     *
     */
    public String inserirNivel0() {
        System.out.println("USEI 0.2***INSERIR NIVEL 0 pela tree&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        getNoDTO().setIdNo(0);
        getNoDTO().setNivel(0);
        // para ser selecionado - conteudo - não se aplica para os combox
        getNoDTO().getTipoProdutofk().setIdTipoProduto(7);
        getNoDTO().getEstadoNofk().setIdEstadoNo(11);
        getNoDTO().setTipoDominio("Interno");
        return "inserirNivel0";
    }

    /**
     * Instancia um noDAO e invoca o método gravarPai, passando o objeto NoDTO É
     * Instanciado toda vez que for criar um nó de nivel 0.
     *
     * @author livia.miura
     * @return String listar, para navegação. TESTADO E SENDO USADO NA TREE OK
     */
    public String gravarPai() {
 
        NoDAO noDAO = new NoDAO();

        getNoDTO().setCodigo(noDTO.getTitulo());
        // é necessario declarar de novo igual inserirNivel 0, dado é perdido
        getNoDTO().setTipoDominio("Interno");
        getNoDTO().getTipoProdutofk().setIdTipoProduto(8);
        getNoDTO().getEstadoNofk().setIdEstadoNo(11);
        getNoDTO().setProdutoFinal(false);
        getNoDTO().setStatus(1);
        getNoDTO().setIdentExterna("Não se Aplica");
        //*******************************************************************
        noDAO.gravar(getNoDTO());

        int a = getNoDTO().getIdNo();
        criarDiretorioRaiz(a);

        //aqui 
        int x = getNoDTO().getEstadoNofk().getIdEstadoNo();
        System.out.println("/*x===" + x);
        historicoDoNo(x, getNoDTO());
        return "listar";

    }

    private void historicoDoNo(int x, NoDTO noDTO) {
        //************  Registra No no Historico -  **********************
        HistoricoNoDAO historicoNoDAO = new HistoricoNoDAO();

        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        getHistoricoNoDTO().setUsuarioFk(usuarioDTO);


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date e = new Date();
        dateFormat.format(e);
        getHistoricoNoDTO().setDataOperacao(e);
        getHistoricoNoDTO().setNoFk(noDTO);

        //*********************************************************************
        EstadoNoDAO estadoNoDAO = new EstadoNoDAO();

        getEstadoNoDTO().setIdEstadoNo(x);
        setEstadoNoDTO(estadoNoDAO.selecionar(getEstadoNoDTO()));

        getHistoricoNoDTO().setEstadoNo(getEstadoNoDTO().getIdentificacao());
        //**********************************************************************
        historicoNoDAO.gravar(getHistoricoNoDTO());

    }

    /**
     * Quando for gerado um nó (raiz), automaticamente será criado um diretorio
     * onde ficarão guardados seus documentos vinculado.
     *
     * @author livia.miura
     */
    public void criarDiretorioRaiz(int a) {
        NoDAO noDAO = new NoDAO();
        getNoDTO().setIdNo(a);
        setNoDTO(noDAO.selecionar(getNoDTO()));

        try {
            String arquivos = "/" + noDTO.getTitulo();
            if (getNoDTO().getDiretorio() == null) {
                
       
                // File dir = new File(session.getServletContext().getRealPath("/GAP/") + arquivos);
                File dir = new File(DIRETORIO+"/" + arquivos);

               if (dir.mkdirs()) {
                   atualizacaoNoRaiz(a, arquivos);
                    File arquivo = new File(dir, "teste.xthml");
                    // cria um arquivo dentro do diretorio recem-criado 
                    if (arquivo.createNewFile()) {
                        System.out.println("Arquivo criado em: " + arquivo.getAbsolutePath());
                    } else {
                        System.out.println("Nao foi possivel criar o arquivo");
                    }
                } else {
                    System.out.println("Diretorio ja existe!!! ");
                    atualizacaoNoRaiz(a, arquivos);
                    for (File files : dir.listFiles()) {
                        System.out.println(" socorro:-->" + files);
                    }
                }


                for (File files : dir.listFiles()) {

                    System.out.println("fora do if:-->" + files);

                }
            } else {
                System.out.println("NAo FAZ NADA!!!!!!!!!!!!!!");
            }
        } catch (IOException e) {
            // Possiveis erros são tratados aqui
        }
    }

    /**
     * É feita a atualização do idNo selecionado, com o caminho do diretorio
     * onde se encontra os os documentos vinculados
     *
     * @author livia.miura
     *
     */
    private void atualizacaoNoRaiz(int a, String arquivos) {

        System.out.println("usei 3******30 10 12***************************************");
        System.out.println("entrei no atualizacao do No ");
        NoDAO noDAO = new NoDAO();
        getNoDTO().setIdNo(a);
        System.out.println("a" + a);
        setNoDTO(noDAO.selecionar(getNoDTO()));
        System.out.println(" ID dentro da Atualização" + getNoDTO().getIdNo());
        System.out.println("Titulo que sera atualizado" + noDTO.getTitulo());
        System.out.println("arquivo" + arquivos);
        getNoDTO().setDiretorio(arquivos);
        noDAO.gravarAlterar(getNoDTO());
    }

    //***************************************************************************
    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String menu, para navegação.
     *
     */
    public String menu() {
        System.out.println("pppp*********************");
        return "menu";
    }

    /**
     * Método para validar o campo DataInicial no Consulta por Data de Criação
     * de Produto
     *
     * @author livia.miura
     * @param context
     * @param component
     * @param value
     */
    public void validaDataCadastro(FacesContext context, UIComponent component, Object value) {
        System.out.println(" ***********qqqqq");
        dataInicio = (Date) value;

    }

    /**
     * Método para validar o campo DataFinal no Consulta por Data de Criação de
     * Produto
     *
     * @author livia.miura
     * @param context
     * @param component
     * @param value
     */
    public void validateDataConclusao(FacesContext context, UIComponent component, Object value) {
       dataConclusao = (Date) value;
        if (dataConclusao.after(dataInicio) || dataConclusao.equals(dataInicio)) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Inicial maior que Data Final", "Data Inicial maior que Data Final"));
        }
    }

    /**
     * Método para filtar a busca por data Inicial e data final
     *
     * @author livia.miura
     */
    public void dataConsulta() throws Exception {
       NoDAO noDAO = new NoDAO();


        System.out.println("data inicial digitada= " + noDTO.getDataInicial());
        System.out.println("data inicial digitada= " + noDTO.getDataFinal());

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(noDTO.getDataFinal());
            cal.add(Calendar.DATE, 1);

            noDTO.setDataFinal(cal.getTime());

            this.listaConsultas = noDAO.selecionarPorData(noDTO, 1);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione uma Data Inicial e Data Final!",
                    ""));
        }

    }

    public void buscarPorNoID() throws Exception {
        NoDAO noDAO = new NoDAO();

        try {
            this.listaConsultas = noDAO.selecionarPorUsuarioID(noDTO);
            System.out.println("itemdo no========" + itemdoNoDTO.getNoFk().getTitulo());
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um identificaca para consultar",
                    ""));
        }

    }
    /*
     * Fazer a busca na consulta por idEstado e status =1
     */

    
    public void buscarPorEstadoNoID() throws Exception {
        NoDAO noDAO = new NoDAO();
        try {
            this.listaConsultas = noDAO.selecionarPorEstadoNoIDStatus(noDTO, 1);
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um estado para consultar",
                    ""));
        }
    }

    public void buscarPorTipoProdutoID() throws Exception {
        NoDAO noDAO = new NoDAO();
        try {
            this.listaConsultas = noDAO.selecionarPorTipoProduto(noDTO);
            System.out.println("itemdo no========" + itemdoNoDTO.getNoFk().getTitulo());
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um estado para consultar",
                    ""));
        }
    }

    public void buscarPorProdutoFinalEstadoNo() throws Exception {
        NoDAO noDAO = new NoDAO();
        try {
            this.listaConsultas = noDAO.selecionarProdutoFinalPorEstadoNo(noDTO);

        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um estado para consultar",
                    ""));
        }
    }

    public void buscarPorIdentificacao() throws Exception {
    
        NoDAO noDAO = new NoDAO();
        try {
           
            this.listaConsultas = noDAO.selecionarPorTitulo(noDTO);
            System.out.println("itemdo no========" + itemdoNoDTO.getNoFk().getTitulo());
        } catch (Exception e) {
            //throw e;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selecione um identificaca para consultar",
                    ""));
        }
    }
    //**************************************************************************
    //***************************INICIO****iREPORT******************************
    //**************************************************************************

    /*
     * iReport - no Menu Árvore de Produto - Exibir Árvore - Árvore de Produto -
     * Gerar PDF Árvore.
     *
     * @author livia.miura
     */
    public void geraRelatorioProduto() throws IOException {

        try {
            geraRelatorio(1);
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
    public void geraRelatorio(int parametro) throws UtilException, JRException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/Arvore_de_Produto_DSS.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/Arvore_de_Produto_DSS.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/Arvore_de_Produto_DSS.pdf");
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
     * iReport no menu Consultas - Árvore de Produto - Por Data de Criação.
     *
     * @author livia.miura
     *
     */
    public void relatorioPorData() throws UtilException, IOException {

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        System.out.println("data Inicial==" + noDTO.getDataInicial());
        Date dataInicial = noDTO.getDataInicial();
        System.out.println("DataInicial = " + dataInicial);
        System.out.println("-------------------------------------------------");
        System.out.println("data Final==" + noDTO.getDataFinal());
        Date dataFinal = noDTO.getDataFinal();
        System.out.println("dataFinal = " + dataFinal);

        gerarRelatorio(realPath, dataInicial, dataFinal, 1);


    }
    /*
     * Gera relatório por data de criação do Produto.
     *
     * @author livia.miura
     */

    public void gerarRelatorio(String path, Date dataInicial, Date dataFinal, int parametro) throws IOException {


        Map parameters = new HashMap();

        parameters.put("dataInicial", dataInicial);
        parameters.put("dataFinal", dataFinal);
        parameters.put("status", parametro);


        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = path + "WEB-INF/consultas/iReport/consultaPorData.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/consultaPorData.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/consultaPorData.pdf");
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
     * IREPORT por busca de um produto específico na árvore DSS.
     * Menu-Consultas-Árvore de Produto Final-Estado do Produto.
     *
     * @autor livia.miura.
     *
     */
    public void geraRelatorioProdutoFinalEstado() throws IOException {

        try {

            int a = noDTO.getEstadoNofk().getIdEstadoNo();
            System.out.println("a == " + a);
            geraRelatorioEstadoProdutoFinal(a, 1);

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
    public void geraRelatorioEstadoProdutoFinal(int a, int parametro) throws UtilException, JRException, IOException {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("a", a);
        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/consultaEstadoProdutoFinal.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/consultaEstadoProdutoFinal.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/consultaEstadoProdutoFinal.pdf");
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
     * IREPORT por busca de um produto específico na árvore DSS.
     *
     * Menu-Consultas-Árvore de Produto - Árvore de Produto-Estado do Produto.
     *
     * recebe 1 que é status e a = idEstado
     *
     * @autor livia.miura.
     *
     */
    public void geraRelatorioEstadodoProduto() throws IOException {

        try {

            int a = noDTO.getEstadoNofk().getIdEstadoNo();
            System.out.println("a == " + a);
            geraRelatorioEstadoProduto(a, 1);

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

    public void geraRelatorioEstadoProduto(int a, int parametro) throws UtilException, JRException, IOException {
        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        Map parameters = new HashMap();

        parameters.put("a", a);
        parameters.put("status", parametro);

        java.sql.Connection con = gConexao.getConnection();

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String caminhoRelatorio = realPath + "WEB-INF/consultas/iReport/consultaEstadoProduto.jasper";
        System.out.println("caminho =" + caminhoRelatorio);
        // 1.0 - import java.io.InputStream; 
        // 1.1 - pega o caminho do arquivo .jasper da aplicação
        InputStream reportStream = context.getExternalContext().getResourceAsStream("/WEB-INF/consultas/iReport/consultaEstadoProduto.jasper");
        response.setHeader("Content-Disposition", "attachment; filename=" + "/consultas/pdf/consultaEstadoProduto.pdf");
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
     * IREPORT por busca de um produto específico na árvore DSS.
     *
     * Menu-Consultas-Árvore de Produto - Árvore de Produto-Ramo Específico.
     *
     * @autor livia.miura.
     *
     */
    public void geraConsultaRamoEspecifico() throws IOException {

        int a = noDTO.getIdNo();
        System.out.println("a" + a);
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
    //**************************************************************************
    //******************************FIM****iREPORT******************************
    //**************************************************************************

    /**
     * Gera PDF de um Produto Específico utilizando API OpenSource iText, para
     * manipular e criar documentos em formato PDF.
     *
     * 1 - Menu - Consultas - Árvore de Produto - Arvore de Produto -
     * Identificação.
     *
     * @author livia.miura
     */
    public void relatorioPorIdentificacao() throws IOException {
        System.out.println("/*28 08 13 realmente usado no ItemDoNo***62***");


        System.out.println("no ID==" + noDTO.getIdNo());
        System.out.println("nome" + noDTO.getTitulo());
        int a = noDTO.getIdNo();

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
     * Gera apenas a lista de produtos de um nó específico.
     *
     * @author livia.miura
     */
    public void geraPDF(int a) throws DocumentException, IOException {


        NoDAO noDAO = new NoDAO();

        getItemdoNoDTO().getNoFk().setIdNo(a);
        getItemdoNoDTO().setNoFk(noDAO.selecionar(getItemdoNoDTO().getNoFk()));

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context1 = (ServletContext) aFacesContext.getExternalContext().getContext();
        String realPath = context1.getRealPath("/");

        File arquivo = new File("documento_Arvore_de_Produto.pdf");

        Document document = new Document();

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

            document.open();

            Image figura = Image.getInstance(realPath + "/imagens/2.png");
            figura.setAlignment(Image.LEFT);
            figura.scaleAbsoluteHeight(50);
            figura.scaleAbsoluteWidth(520);

            document.add(figura);
            document.add(new Paragraph("\n\n"));

            Image figura1 = Image.getInstance(realPath + "/imagens/gap3.png");
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
// 
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
// Fim da classe 
}