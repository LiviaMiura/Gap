package br.inpe.backingbeans;

import br.inpe.dto.ConfigurarEmailDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author livia.miura
 */
public class SendMailLocalhostAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    String arquivo = session.getServletContext().getRealPath("/") + "imagens/rss.png";
    private String mensagemEmail;
    private String usuario = null;
    private String senha = null;
    private String servidor = null;
    private String porta = null;
    private String autenticacao = null;
    ConfigurarEmailAction configurarEmail = new ConfigurarEmailAction();
    public static String confirmarEmail = "naoEnviado";

     

    public String getMensagemEmail() {
        return mensagemEmail;
    }

    public void setMensagemEmail(String mensagemEmail) {
        this.mensagemEmail = mensagemEmail;
    }



    /**
     * Método para resgatar resgistro da tabela configuraremail
     * @author livia.miura
     */
    public void configuracaoAtual() {
        System.out.println("entrei na configuração atual");

        ConfigurarEmailDTO item = null;
        Iterator teste = configurarEmail.getLista().iterator();
        while (teste.hasNext()) {
            item = (ConfigurarEmailDTO) teste.next();
            servidor = item.getServidor();
            porta = item.getPorta();
            autenticacao = item.getAutenticacao();
            usuario = item.getUsuario();
            senha = item.getSenha();
            System.out.println("to saindo");

        }


    }

    /**
     * Metodo para enviar email com anexo (utilizando  hMailServer) localhost
     * @author livia.miura
     * @param  to
     * @param  subject
     * @param  message
     **/
    public void sendMailLocalhostAnexo(String to, String subject, String message) {
        
            System.out.println("Entrei no send email local host COM anexo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    
        confirmarEmail = "enviado";

        configuracaoAtual();

        Session session1=null;
        Properties props=null;
        Authenticator auth=null;
        MimeMessage msg = null;

        props = new Properties();
        props.put("mail.host", servidor);//ip do servidor inpe
        props.put("mail.smtp.port", porta);
        props.put("mail.smtp.auth", autenticacao);
        auth = new SimpleAuth(usuario, senha);
        session1 = Session.getDefaultInstance(props, auth);
        msg = new MimeMessage(session1);
        System.out.println("\nCaminho arquivo : " + arquivo);

        try {
            msg.setFrom(new InternetAddress(usuario)); //Setando a origem do email
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); //Setando o destinatário
            msg.setSentDate(new Date());
            msg.setSubject(subject);//Setando o assunto

            MimeBodyPart p1 = new MimeBodyPart();
            //Setando o conteúdo/corpo do email
            p1.setContent(message, "text/plain");

            MimeBodyPart p2 = new MimeBodyPart();                       
            FileDataSource fds = new FileDataSource(arquivo);          
            p2.setDataHandler(new DataHandler(fds));
            p2.setFileName(fds.getName());

            Multipart mp = new MimeMultipart();                         
            mp.addBodyPart(p1);
            mp.addBodyPart(p2);

            msg.setContent(mp);                                         
            Transport.send(msg);

            System.out.println("\nEmail enviado com sucesso!");
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", "Email enviado com sucesso!"));
        } catch (Exception e) {
            System.out.println("\n>> Erro: Completar Mensagem");
            setMensagemEmail("Erro:"+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email não foi enviado!", ""));
        }
    }

    /**
     * Metodo para enviar email sem anexo (utilizando  hMailServer) localhost
     * @author livia.miura
     * @param  to
     * @param  subject
     * @param  message
     **/
    public void sendMailLocalhost(String to, String subject, String message) {
        
        System.out.println("Entrei no send email local host sem anexo*********************************************");
        confirmarEmail = "enviado";
        
        configuracaoAtual();

        
        Properties props = new Properties();
        props.put("mail.host", servidor);
        System.out.println("servidor = " + servidor);
        props.put("mail.smtp.port", porta);
        System.out.println("porta =" + porta);
        props.put("mail.smtp.auth", autenticacao);
        System.out.println("auth = " + autenticacao);
        Authenticator auth = new SimpleAuth(usuario, senha);
        Session session = Session.getDefaultInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);

        System.out.println("passei 2");
        try {
            //Setando a origem do email
            msg.setFrom(new InternetAddress(usuario));
            //Setando o destinatário
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSentDate(new Date());
            //Setando o assunto
            msg.setSubject(subject);
            //Setando o conteúdo/corpo do email
            msg.setContent(message, "text/plain");
            
            Transport.send(msg);
            System.out.println("\nEmail enviado com sucesso!");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", ""));
        } catch (Exception e) {
            System.out.println("\n>> Erro: Completar Mensagem:"+e);
            setMensagemEmail("Erro: "+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email não foi enviado!", ""));
        }
    }
}

/**
 * Autenticar o envio de  email (utilizando  hMailServer)
 * @author livia.miura
 * @return autenticacao do username e password
 **/
class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = pwd;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
