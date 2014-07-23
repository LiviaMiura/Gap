/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.DocumentoVinculadoDAO;
import br.inpe.dao.TipoDocumentoDAO;
import br.inpe.dto.TipoDocumentoDTO;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author livia.miura
 */
@ManagedBean
@RequestScoped
public class TipoDocumentoAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    private TipoDocumentoDTO tipoDocumentoDTO;
    private List<TipoDocumentoDTO> lista;
    private UploadedFile fileUp;
    private String DIRETORIO = "C:/GAP";
    private DefaultStreamedContent file;


    /**
     * Creates a new instance of TipoDocumentoAction
     */
    public TipoDocumentoAction() {
    }

    public List<TipoDocumentoDTO> getLista() {
        listar();
        return lista;
    }

    public void setLista(List<TipoDocumentoDTO> lista) {
        this.lista = lista;
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

    //************************templates*****************************************
    //************************** inicio do upload
    public UploadedFile getFileUp() {
        return fileUp;
    }

    public void setFileUp(UploadedFile fileUp) {
        this.fileUp = fileUp;
    }

    public void up(FileUploadEvent event) {
        System.out.println("***************entrei no up************");
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
    public void gravarDocumentoNoDiretorio() {
        System.out.println("********************************Anexar Documento");

        InputStream in;
        FileOutputStream fileOut;
        try {
            File dir = new File(DIRETORIO + "/TIPOS DE DOCUMENTOS/");
            // metodo padrão
            // File dir = new File(session.getServletContext().getRealPath("/upload/"));

            if (dir.mkdirs()) {
                // se o diretório não existir será criado.
                System.out.println("Novo diretorio criado em  : " + dir.getAbsolutePath());
                this.setFileUp((UploadedFile) this.session.getAttribute("arquivoUpload"));
                //File dir = new File("C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/GAP/" + arquivos);           
                fileOut = new FileOutputStream(dir + "/" + fileUp.getFileName());
                in = fileUp.getInputstream();
                in.close();
                fileOut.write(getFileUp().getContents());
                fileOut.close();
                tipoDocumentoDTO.setTemplate(fileUp.getFileName());
            } else {
                System.out.println("****************************Diretorio ja existe!!! ");

                this.setFileUp((UploadedFile) this.session.getAttribute("arquivoUpload"));
                fileOut = new FileOutputStream(dir + "/" + fileUp.getFileName());
                in = fileUp.getInputstream();
                System.out.println("in ==>" + in.read());
                in.close();
                fileOut.write(getFileUp().getContents());
                fileOut.close();
                tipoDocumentoDTO.setTemplate(fileUp.getFileName());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(TipoDocumentoAction.class.getName()).log(Level.SEVERE, null, ioe);
        }
        // return "listar";
    }

    //*************fim do*******template******************************************
    /**
     * Instancia um tipoDocumentoDAO e invoca o método listar().
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String listar() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        lista = tipoDocumentoDAO.listar();
        return "listar";
    }

    /**
     * Instancia um tipoDocumentoDAO, obtem o idTipoDocumento e invoca o método
     * excluir
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String excluir() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        getTipoDocumentoDTO().setIdTipoDocumento(new Integer(request.getParameter("idTipoDocumento")));
        tipoDocumentoDAO.excluir(getTipoDocumentoDTO());
        return "listar";
    }

    /**
     * Controle de navegação
     *
     * @author livia.miura
     * @return String inserir, para navegação.
     *
     */
    public String inserir() {

        return "inserir";
    }

    /**
     * Controle de navegação do botão anexo para pagina template
     *
     * @author livia.miura
     * @return String inserirTemplate, para navegação.
     *
     */
    public String inserirTemplate() {

        return "inserirTemplate";
    }

    /**
     * Controle de navegação cancelar da pagina template
     *
     * @author livia.miura
     * @return String cancelarTemplate, para navegação.
     *
     */
    public String cancelarTemplate() {

        return "cancelarTemplate";
    }

    /**
     * Instancia um tipoDocumentoDAO, invoca o método gravar, passando o objeto
     * tipoDocumentoDTO
     *
     * @author livia.miura
     * @return String listar, para navegação.
     *
     */
    public String gravar() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        tipoDocumentoDAO.gravar(getTipoDocumentoDTO());

        return "listar";
    }

    /**
     * Instancia um tipoDocumentoDAO, obtem o idTipoDocumento e invoca o método
     * selecionar, passando o objeto tipoDocumentoDTO
     *
     * @author livia.miura
     * @return String inserir, para navegação.
     *
     */
    public String alterar() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        getTipoDocumentoDTO().setIdTipoDocumento(new Integer(request.getParameter("idTipoDocumento")));
        setTipoDocumentoDTO(tipoDocumentoDAO.selecionar(getTipoDocumentoDTO()));
        return "alterar";
    }
       public void alterar1() {
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        getTipoDocumentoDTO().setIdTipoDocumento(new Integer(request.getParameter("idTipoDocumento")));
        setTipoDocumentoDTO(tipoDocumentoDAO.selecionar(getTipoDocumentoDTO()));
       
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String menu, para navegação.
     *
     */
    public String menu() {
        return "menu";
    }

    /**
     * Para fazer download de um tipo de documento
     *
     * @author livia.miura
     * @return String file, para navegação.
     *
     */
    public StreamedContent getFile() {
        System.out.println("entrei no getFile DO TIPO DE DOCUMENTOS");

        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        getTipoDocumentoDTO().setIdTipoDocumento(new Integer(request.getParameter("idTipoDocumento")));
        setTipoDocumentoDTO(tipoDocumentoDAO.selecionar(getTipoDocumentoDTO()));
        StreamedContent retorno = null;

        if (tipoDocumentoDTO.getTemplate().equals("")) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Documento não encontrado",
                    "ou não anexado!"));
            retorno = null;
        } else {
            try {
                System.out.println("to no try TO TIPO DE DOCCUMENTOS");
                String x = getTipoDocumentoDTO().getTemplate();
                // FileInputStream stream = new FileInputStream(new File(session.getServletContext().getRealPath("/") + "/templates/" + x));
                // o caminho de onde será feito o download
                // FileInputStream stream = new FileInputStream(new File("//fbm-server/GAP/templates/" + x));
                FileInputStream stream = new FileInputStream(new File("C:/Users/livia.miura/Documents/NetBeansProjects/Gap/web/GAP/TIPOS DE DOCUMENTOS/" + x));

                System.out.println("stream" + stream);

                file = new DefaultStreamedContent(stream, "tipo/pdf", x);
                file = new DefaultStreamedContent(stream, "tipo/doc", x);
                file = new DefaultStreamedContent(stream, "tipo/docx", x);

                return file;

            } catch (Exception e) {

                retorno = null;
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "O download do documento foi concluído com sucesso",
                    ""));
        }
        return retorno;

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

}