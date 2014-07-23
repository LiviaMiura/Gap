    /*
 * Projeto - GAP
 * Nome Arquivo : UsuarioAction.Java
 * author castro
 */
package br.inpe.backingbeans;

import br.inpe.dao.ConfigurarEmailDAO;
import br.inpe.dao.HistoricoDAO;
import br.inpe.dao.UnidadeDAO;
import br.inpe.dao.UsuarioDAO;
import br.inpe.dto.ConfigurarEmailDTO;
import br.inpe.dto.HistoricoDTO;
import br.inpe.dto.UsuarioDTO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Construtor da Classe UsuarioAction
 *
 * @author Castro
 *
 */
public class UsuarioAction {

    public static int idAtual = 0;
    public static Date dataEntradaAtual;
    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    private UsuarioDTO usuarioDTO;
    private HistoricoDTO historicoDTO;
    private Collection<UsuarioDTO> lista;
    String loginx;
    String senhax;
    private final static String ativo = "/imagens/ativo4.png";
    private final static String inativo = "/imagens/inativo3.png";
    private ArrayList<UsuarioDTO> filtList;
    private ConfigurarEmailDTO configurarEmailDTO;

    public UsuarioAction() {
    }

    public ConfigurarEmailDTO getConfigurarEmailDTO() {
        if (configurarEmailDTO == null) {
            configurarEmailDTO = new ConfigurarEmailDTO();
        }
        return configurarEmailDTO;

    }

    public void setConfigurarEmailDTO(ConfigurarEmailDTO configurarEmailDTO) {
        this.configurarEmailDTO = configurarEmailDTO;
    }

    /**
     * Retorna data hora de entrada atual
     *
     * @author castro
     * @return dataEntradaAtual
     */
    public static Date getDataEntradaAtual() {
        return dataEntradaAtual;
    }

    /**
     * Seta data hora de entrada atual
     *
     * @author castro
     * @return void
     */
    public static void setDataEntradaAtual(Date dataEntradaAtual) {
        UsuarioAction.dataEntradaAtual = dataEntradaAtual;
    }

    public static int getIdAtual() {
        return idAtual;
    }

    public static void setIdAtual(int idAtual) {
        UsuarioAction.idAtual = idAtual;
    }

    /**
     * Retorna historicoDTO
     *
     * @author castro
     * @return historicoDTO
     */
    public HistoricoDTO getHistoricoDTO() {
        if (historicoDTO == null) {
            historicoDTO = new HistoricoDTO();
        }
        return historicoDTO;
    }

    /**
     * Seta historicoDTO
     *
     * @author castro
     * @return
     */
    public void setHistoricoDTO(HistoricoDTO historicoDTO) {
        this.historicoDTO = historicoDTO;
    }

    /**
     * Retorna lista de Usuarios
     *
     * @author castro
     * @return lista
     */
    public Collection<UsuarioDTO> getLista() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        lista = usuarioDAO.listar();
        return lista;
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

    public String getLoginx() {
        return loginx;
    }

    public void setLoginx(String loginx) {
        this.loginx = loginx;
    }

    /**
     * Retorna senha do Usuario
     *
     * @author castro
     * @return loginx
     */
    public String getSenhax() {
        return senhax;
    }

    /**
     * Seta senha do Usuario
     *
     * @author castro
     * @return
     */
    public void setSenhax(String senhax) {
        this.senhax = senhax;
    }

    ///??????quem usa esse método? verificar
    public void listarFornecedor() {
        System.out.println("/*ver");
        UnidadeDAO unidadeDAO = new UnidadeDAO();
        getUsuarioDTO().setUnidadeFk(unidadeDAO.selecionar(getUsuarioDTO().getUnidadeFk()));
        lista = getUsuarioDTO().getUnidadeFk().getUsuarioDTOCollection();
    }

    //**************************************************************************
    /**
     * Instancia um usuarioDAO e invoca o método listar().
     *
     * @author castro
     * @return String listar, para navegação.
     */
    public String listar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        lista = usuarioDAO.listar();
        return "listar";
    }

    /**
     *
     * Mando para uma tela em branco, quando clico em Cancelar em Editar Usuário
     *
     * @author livia.miura
     * @return String listar, para navegação.
     */
    public String cancelar() {
        return "cancelar";
    }

    /**
     * Instancia um usuarioDAO, obtem o idUsuario e invoca o método excluir
     *
     * @author castro
     * @return String listar, para navegação.
     */
    public String excluir() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        getUsuarioDTO().setIdUsuario(new Integer(request.getParameter("idUsuario")));
        usuarioDAO.excluir(getUsuarioDTO());
        System.out.println("sai do excluir do usuarioAction");
        return "listar";
    }

    /**
     * Set IdUsuario com zero e permiti inserir novo registro na tabela usuario,
     * verificar o perfil do usuario para setar o Perfil e Status
     *
     * @author castro
     * @return String inserir, para navegação.
     */
    public String inserir() {
        getUsuarioDTO().setIdUsuario(0);
        getUsuarioDTO().setPerfil("Visitante");//Perfil=5 -Visitante
        getUsuarioDTO().setStatus(1);//Inativo
        getUsuarioDTO().setStatusImagem(inativo);
        return "inserir";
    }

    public String avançar() {
        String retorno = null;

        getUsuarioDTO().setIdUsuario(0);
        System.out.println("Perfil====" + getUsuarioDTO().getPerfil());


        if (getUsuarioDTO().getPerfil().equals("Administrador")) {
            getUsuarioDTO().setIdUsuario(0);
            getUsuarioDTO().setPerfil("Administrador");//Perfil=5 -Visitante
            getUsuarioDTO().setStatus(0);//Ativo
            getUsuarioDTO().setStatusImagem(ativo);

            retorno = "novo1";

        } else {
            System.out.println("entrei nos outros perfis");
            getUsuarioDTO().setIdUsuario(0);
            getUsuarioDTO().setPerfil(getUsuarioDTO().getPerfil());
            getUsuarioDTO().setStatus(1);//Inativo
            getUsuarioDTO().setStatusImagem(ativo);
            retorno = "novo2";
        }
        return retorno;
    }

    /**
     * inserir novo registro na tabela usuario - botao cadastre-se (visitante -
     * perfil=3)
     *
     * @author castro
     * @return String "inserirUsuario", para navegação.
     *
     */
    public String inserirUsuario() {
        getUsuarioDTO().setIdUsuario(0);//inserir um novo usuario
        return "inserirUsuario";
    }

    /**
     * Gravar um usuario visitante - pré-cadastro Setar o perfil:3-visitante e
     * status:0 - ativo tela Inicial Novo Usuario
     *
     * @author castro/livia.miura
     * @return String voltar, para navegação.
     */
    public String gravarUsuario() {
        String retorno = null;
        System.out.println("oi");
        UsuarioDAO usuarioDAO = new UsuarioDAO();

            this.lista = usuarioDAO.selecionarLogin(usuarioDTO);
        System.out.println("lista" + lista.iterator());
        if (lista.isEmpty()) {
            getUsuarioDTO().setPerfil("Visitante");//Perfil=5 -Visitante
            getUsuarioDTO().setStatus(0);//ativo
            getUsuarioDTO().setStatusImagem(ativo);
            getUsuarioDTO().setIdUsuario(0);
            usuarioDAO.gravar(getUsuarioDTO());
         
            
            retorno = "cancelCad";
        } else {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login " + usuarioDTO.getLogin() + " já´existe",
                    "Use o número do seu Registro Funcional do crachá do INPE, por favor"));

            System.out.println("ok");
            retorno = "listar";
        }
        return retorno;
    }
    

    

    /**
     * Cancelar um pré-cadastro
     *
     * @author castro
     * @return String cancelCad, para navegação.(continuar na tela de index)
     *
     */
    public String cancelarCadastro() {
        return "cancelCad";
    }

    /**
     * Instancia um usuarioDAO, invoca o método gravarEditar, passando o objeto
     * UsuarioDTO sem verificar se o login já existe, pq está editando os dados
     * de um usuário já cadastrado.
     *
     * @author livia.Miura
     * @return String msg
     */
    public String gravarEditar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDTO.getStatus() == 1) {
            usuarioDTO.setStatusImagem(inativo);
        } else {
            usuarioDTO.setStatusImagem(ativo);
        }
        usuarioDAO.gravar(getUsuarioDTO());
        return "msg";
    }

    /**
     * Instancia um usuarioDAO, invoca o método gravarEditar, passando o objeto
     * UsuarioDTO e verificando se o login já existe noi BD.
     *
     * @author livia.Miura
     * @return String msg
     */
    public String gravar() {

        System.out.println("Perfil no gravar====" + getUsuarioDTO().getPerfil());
        String retorno = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        //verificar se o login digitado já tem no BD
        this.lista = usuarioDAO.selecionarLogin(usuarioDTO);
        System.out.println("lista" + lista.iterator());
        if (lista.isEmpty() && usuarioDTO.getStatus() == 1) {
            System.out.println("lista vazia e usuario inativo!!!!");
            usuarioDTO.setStatusImagem(inativo);
            usuarioDAO.gravar(getUsuarioDTO());
            retorno = "msg";
        } else {
            if (lista.isEmpty() && usuarioDTO.getStatus() == 0) {
                System.out.println("lista vazia e usuario ativo!!!!");
                usuarioDTO.setStatusImagem(ativo);

                System.out.println("email ====" + usuarioDTO.getEmail());
                usuarioDAO.gravar(getUsuarioDTO());




                retorno = "msg";
            } else {
                System.out.println("/*já existe");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Login " + usuarioDTO.getLogin() + " já existe! Use seu Registro Funcional no campo Login",
                        ""));
            }
        }
        return retorno;
    }

    /**
     * Instancia um usuarioDAO, invoca o método gravarEditar, passando o objeto
     * UsuarioDTO e verificando se o login já existe noi BD.
     *
     * @author livia.Miura
     * @return String msg
     */
    public String gravarAdmin() {

        String retorno = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        ConfigurarEmailDAO configurarEmailDAO = new ConfigurarEmailDAO();
        //verificar se o login digitado já tem no BD
        this.lista = usuarioDAO.selecionarLogin(usuarioDTO);
        System.out.println("lista" + lista.iterator());

        if (lista.isEmpty() && usuarioDTO.getStatus() == 0) {

            getConfigurarEmailDTO().setId(1);
            setConfigurarEmailDTO(configurarEmailDAO.selecionar(getConfigurarEmailDTO()));
            usuarioDTO.setEmail(configurarEmailDTO.getUsuario());

            System.out.println("lista vazia e usuario ativo!!!!");
            usuarioDTO.setStatusImagem(ativo);
            usuarioDTO.setPerfil("Administrador");

            usuarioDAO.gravar(getUsuarioDTO());

            retorno = "msg";
        } else {
            System.out.println("/*já existe");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login " + usuarioDTO.getLogin() + " já existe! Use seu Registro Funcional no campo Login",
                    ""));
        }

        return retorno;
    }

    /**
     * Instancia um usuarioDAO, obtem o idUsuario e invoca o método selecionar,
     * passando o objeto UsuarioDTO
     *
     * @author castro
     * @return String alterar, para navegação.
     */
    public String alterar() {
        System.out.println("Alteração com email");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        getUsuarioDTO().setIdUsuario(new Integer(request.getParameter("idUsuario")));
        setUsuarioDTO(usuarioDAO.selecionar(getUsuarioDTO()));
        return "alterar";
    }

    public void enviarEmailNovoUsuario(UsuarioDTO usuarioDTO) {

        SendMailLocalhostAction sm = new SendMailLocalhostAction();
        String email = usuarioDTO.getEmail();
        String nome = usuarioDTO.getNome();
        String login = usuarioDTO.getLogin();
        String senha = usuarioDTO.getSenha();
        String perfil = usuarioDTO.getPerfil();

        String mensagem = " "
                + ""
                + "Seja bem-vindo ao Sistema GAP \n\n"
                + " O GAP - Gerenciamento da Árvore de Produto tem como objetivo permitir a definição e manutenção da Árvore de Produtos da DSS \n"
                + " para área de software, hardware e redes via Intranet, possibilitando assim registrar e documentar os projetos e pesquisa da Divisão.\n"
                + "\n"
                + " E para todos usuários terem connhecimento de todos os projetos desenvolvidos na Divisão \n"
                + "\n"
                + " Dados do Novo Usuario GAP \n\n"
                + "Nome:    " + nome + "\n"
                + "Login:   " + login + "\n"
                + "Senha:   " + senha + "\n"
                + "Perfil:  " + perfil + "\n"
                + "\n"
                + "\n"
                + "Atenciosamente,\n"
                + "Administrador GAP\n";




        String assunto = "Bem-vindo ao GAP ";
        sm.sendMailLocalhost(email, assunto, mensagem);

    }

    public void enviarEmailAteracao(UsuarioDTO usuarioDTO) {
        SendMailLocalhostAction sm = new SendMailLocalhostAction();

        String email = usuarioDTO.getEmail();
        String nome = usuarioDTO.getNome();

        String mensagem = " "
                + ""
                + "Usuário  " + nome + ",\n"
                + "\n"
                + "Você pode facilmente alterar seu dados cadastrais,em Editar Dados no Menu Usuário.\n"
                + "É permitido alterar os dados cadastrais básicos como  Nome, Email, Ramal, Prédio, Senha, Unidade.  \n"
                + "Porém não é possível alterar Login e Perfil.\n"
                + "Para alterar esses dados é necessário solicitar, através do email, para que nós façamos a alteração.\n"
                + "Em Menu Usuário - Alteração de Perfil\n"
                + "Ou em Fale Conosco na tela Menu Principal para alterar login ou outros assuntos \n"
                + "\n"
                + "\n"
                + "Atenciosamente,\n"
                + "Administrador GAP\n";



        String assunto = "GAP - Alteração dos dados do usuário. ";
        sm.sendMailLocalhost(email, assunto, mensagem);

    }

    public void enviarEmail(UsuarioDTO usuarioDTO) {

        SendMailLocalhostAction sm = new SendMailLocalhostAction();

        String email = usuarioDTO.getEmail();
        String nome = usuarioDTO.getNome();
        String login = usuarioDTO.getLogin();
        String senha = usuarioDTO.getSenha();
        String perfil = usuarioDTO.getPerfil();

        String mensagem = " Usuário " + nome + ",\n\n"
                + "Seus dados foram atualizados com sucesso \n\n"
                + "Novo Perfil - " + perfil + "\n"
                + "Login - " + login + "\n"
                + "Senha - " + senha + "\n"
                + "Email - " + email + "\n"
                + "\n"
                + "\n"
                + "Atenciosamente,\n\n"
                + "Administrador GAP\n\n";


        String assunto = "GAP - Atualização dados cadastrais. ";
        sm.sendMailLocalhost(email, assunto, mensagem);

    }

    public void enviarEmailPerfil() {

        //  setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));

        SendMailLocalhostAction sm = new SendMailLocalhostAction();

        UsuarioDAO usuarioDAO = new UsuarioDAO();// pega os dados
        setUsuarioDTO(usuarioDAO.selecionar(getUsuarioDTO()));

        String email = usuarioDTO.getEmail();
        String nome = usuarioDTO.getNome();
        String perfil = usuarioDTO.getPerfil();

        String mensagem = " Alteração de Perfil:\n\n"
                + "Usuário: " + nome + " \n\n"
                + "Email:" + email + "\n\n"
                + "Antigo Perfil: " + perfil + "\n";


        String assunto = "GAP - Pedido de alteração de perfil ";
        sm.sendMailLocalhost(email, assunto, mensagem);

    }

    /**
     * Instancia o UsuarioDTO que está na session e obtem o idUsuario e invoca o
     * método selecionar, passando o objeto UsuarioDTO
     *
     * @author castro
     * @return String alterar, para navegação.
     */
    public String alterarUsuario() {
        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        setUsuarioDTO(usuarioDAO.selecionar(getUsuarioDTO()));
        return "usuarioForm";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return String menu, para navegação.
     */
    public String menu() {
        return "menu";
    }

    /**
     * Este método valida o login do usuário e grava o registro login
     *
     * @author castro
     * @return String para o case do rule navigator no arquivo faces-config.xml
     */
    public String validarLogin() {
        String retorno = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();


        if ((usuarioDTO.getLogin().equals(getLoginx())) && (usuarioDTO.getSenha().equals(getSenhax()))) {

            System.out.println(getLoginx()); // pega os valores
            System.out.println(getSenhax());
            usuarioDTO.setLogin(getLoginx());
            usuarioDTO.setSenha(getSenhax());
            usuarioDTO.setNome("Administrador Root");
            usuarioDTO.setPerfil("Administrador");//Administrador = 1
            retorno = "menu";
        } else {
            try {
                System.out.println("entrei no try linha 333 validar login");

                //Valida Usuario - somente após essa chamada que será conhecido os dados do usuario
                usuarioDTO = usuarioDAO.validarLogin(getUsuarioDTO());

                if (usuarioDTO == null) {
                    retorno = "voltar";


                    return retorno;
                }



//************  Registra usuario no Historico - Apos usa validação **********************
                HistoricoDAO historicoDAO = new HistoricoDAO();
                getHistoricoDTO().setUsuarioFk(usuarioDTO);

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date e = new Date();
                dateFormat.format(e);
                getHistoricoDTO().setDataEntrada(e);

                historicoDAO.gravar(getHistoricoDTO());

                //guarda os valores do idAtual e  DaTaEntradaAtual   
                idAtual = getHistoricoDTO().getIdHistorico();
                setIdAtual(idAtual);
                System.out.println(" id Atual: " + idAtual);
                setDataEntradaAtual(e);



//************* fim registrar o usuario no Historico************************/


                retorno = "menu";

            } catch (NoResultException erro) {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Usuário não cadastrado ou senha inválida!",
                        "Digite novamente!"));
                //JOptionPane.showMessageDialog(null, "Usuário não cadastrado ou senha inválida!", "Atenção!", JOptionPane.ERROR_MESSAGE);
                retorno = null;

            }

            //***************Para colocar o usuario na session******************
            session.setAttribute("usuarioDTO", this.getUsuarioDTO());
        }

        return retorno;

    }

    /**
     * Método para mandar email perfil
     *
     * @author livia.miura
     */
    public String paginaperfil() {
        setUsuarioDTO((UsuarioDTO) session.getAttribute("usuarioDTO"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();// pega os dados
        setUsuarioDTO(usuarioDAO.selecionar(getUsuarioDTO()));


        System.out.println("Usuario = " + usuarioDTO.getNome());
        System.out.println("Email = " + usuarioDTO.getEmail());
        return "listarperfil";
    }

    public String exibir() {

        try {
            String caminho = "GAP/Ajuda/EspecificaçãoRequisitosSistemaGap.pdf/";
            File file = new File(session.getServletContext().getRealPath("/") + caminho);

            byte[] b = fileToByte(file);
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            
            res.setHeader("Pragma", "no-cache");
            //"attachment; filename=" + file.getName());  faz o manual ser baixado pelo usuario automaticamente;
            res.setHeader("Content-disposition","attachment; filename=" + file.getName());

            res.getOutputStream().write(b);
            res.getCharacterEncoding();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "listar";
    }

    /**
     * Método fileToByte do pdf
     *
     * @author Antonio Cassiano
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
}
