/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.NoDAO;
import br.inpe.dto.NoDTO;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.primefaces.component.fileupload.FileUpload;

/**
 *
 * @author livia.miura
 */
public class DiretorioArquivo  extends HttpServlet{
    private NoDTO noDTO;
    
    
      private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    String arquivo = session.getServletContext().getRealPath("/") + "relatorioExcel/Avaliacoes.xls";
    

    /**
     * Creates a new instance of File
     */
    public DiretorioArquivo() {
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
    
    
        public void criarDiretorio(int a ) {
        NoDAO noDAO = new NoDAO();

       // StreamedContent retorno = null;
        System.out.println("A dentro do criar diretorio---->" + a);

        getNoDTO().setIdNo(a);
        setNoDTO(noDAO.selecionar(getNoDTO()));

        
                try{
            File dir = new File("//GAP//" + noDTO.getTitulo()); 
  
            if(dir.mkdirs()){ 
                System.out.println("Novo diretorio criado em: " + dir.getAbsolutePath()); 
            } 
            else{ 
                System.out.println("Diretorio ja existe"); 
            } 
  
            // cria um arquivo dentro do diretorio recem-criado 
  
            File arquivo = new File(dir, "teste.txt"); 
  
            if(arquivo.createNewFile()){ 
                System.out.println("Arquivo criado em: " + arquivo.getAbsolutePath()); 
            } 
            else{ 
                System.out.println("Nao foi possivel criar o arquivo"); 
            }
        }
        catch(IOException e){
            // Possiveis erros são tratados aqui
        } 
        }
             
  
 
 public void inserirDiretorio() throws FileNotFoundException, IOException{

            //Pega o diretório /logo dentro do diretório atual de onde a

            //aplicação está rodando

            String caminho = getServletContext().getRealPath("/GAP")

                             + "/";

     

             // Cria o diretório caso ele não exista

            File diretorio = new File(caminho);

            if (!diretorio.exists()){

                diretorio.mkdir();

            }

 

            // Mandar o arquivo para o diretório informado

            String nome = getNoDTO().getTitulo();

            String arq[] = nome.split("\\\\");

            for (int i = 0; i < arq.length; i++) {

                nome = arq[i];

            }
            
    



            

  

            File file = new File(diretorio, nome);

            FileOutputStream output = new FileOutputStream(file);
         
          /*  FileItem item = (FileItem) iter.next();

           InputStream is =  item.getInputStream();
*/
            byte[] buffer = new byte[2048];

            int nLidos;

      /*      while ((nLidos = is.read(buffer)) >= 0) {

                output.write(buffer, 0, nLidos);

            }

            output.flush();

            output.close();

 
/*
        
        try {
            String caminho = "/GAP/" + noDTO.getTitulo();
            System.out.println("caminho= " + caminho);
            FileInputStream stream = new FileInputStream(new File(session.getServletContext().getRealPath("/") + "/arquivo/" + caminho));
           // file = new DefaultStreamedContent(stream, "tipo/pdf", caminho);
           // return file;
        } catch (Exception e) {
          
        }
  */  }

}
