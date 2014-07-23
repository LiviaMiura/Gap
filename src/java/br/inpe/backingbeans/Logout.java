/*
 * Projeto - GAP
 * Nome Arquivo : Logout.Java
 * author castro
 */
package br.inpe.backingbeans;

import br.inpe.dao.HistoricoDAO;
import br.inpe.dto.HistoricoDTO;
import br.inpe.dto.UsuarioDTO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Construtor da Classe Logout
 * @author Castro
 **/
public class Logout {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    private UsuarioDTO usuarioDTO;
    private HistoricoDTO historicoDTO;
    String loginx;
    String senhax;

    /**
     * Retorna usuarioDTO se for igual a null
     * author castro
     * @return usuarioDTO
     **/
    public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    /**
     * Seta usuarioDTO 
     * author castro
     * @return void
     **/
    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    
    public HistoricoDTO getHistoricoDTO() {
        if (historicoDTO == null) {
            historicoDTO = new HistoricoDTO();
        }
        return historicoDTO;
    }

     
    public void setHistoricoDTO(HistoricoDTO historicoDTO) {
        this.historicoDTO = historicoDTO;
    }

    
    public String getLoginx() {
        return loginx;
    }

    public void setLoginx(String loginx) {
        this.loginx = loginx;
    }

   
    public String getSenhax() {
        return senhax;
    }

    
    public void setSenhax(String senhax) {
        this.senhax = senhax;
    }

    
   /**
    * Efetua o Logout , grava no historico e fecha sessão. (o administrador não será gravado no historico)
    * author castro
    * @return string
    **/
    public String Logout() {
        System.out.println("entrei no LOGOUT&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        FacesContext fc = FacesContext.getCurrentInstance();      
        //Pegar Id Atual
        int id = UsuarioAction.getIdAtual();
        //Verificar se Id Atual é diferente de zero (Id=0 ==> admin)
        if(id!=0){
        Date entrada = UsuarioAction.getDataEntradaAtual();
        System.out.println(" id Atual: " + id);
        System.out.println(" Data entrada Atual: " + entrada);
      
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date s = new Date();
        dateFormat.format(s);     
        
        getHistoricoDTO().setIdHistorico(id);
        getHistoricoDTO().setUsuarioFk((UsuarioDTO) session.getAttribute("usuarioDTO"));
        getHistoricoDTO().setDataEntrada(entrada);
        getHistoricoDTO().setDataSaida(s);
      
        HistoricoDAO historicoDAO = new HistoricoDAO();
        historicoDAO.gravar(historicoDTO); 
            
        System.out.println("\n Casio gravando SAÍDA...");
        System.out.println(" id ATUAL: " + getHistoricoDTO().getIdHistorico());
        System.out.println(" Usuario:" + getHistoricoDTO().getUsuarioFk().getNome());
        System.out.println(" Entrada: " + getHistoricoDTO().getDataEntrada());
        System.out.println(" Saída: " + getHistoricoDTO().getDataSaida());

        // invalidate session
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        session.invalidate();
        // redirect to the login / home page
        try {
            ec.redirect(ec.getRequestContextPath());
        } catch (IOException e) {
            throw new FacesException(e);
        }
        //Setar Id Atual em zero.
        UsuarioAction.setIdAtual(0);
        return null;
        }
        //Id = 0 ==> não é preciso registrar no Historico - Admin
        else{
            
            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(false);
            session.invalidate();
            // redirect to the login / home page
            try {
                ec.redirect(ec.getRequestContextPath());
            } catch (IOException e) {
                throw new FacesException(e);
            }
            return null;
        }
            
    }
}
