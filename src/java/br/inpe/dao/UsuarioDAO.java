/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import br.inpe.backingbeans.UsuarioAction;
import br.inpe.dto.ItemdoNoDTO;
import br.inpe.dto.UsuarioDTO;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Implementa os métodos para manipulação da tabela Usuario no banco de dados.
 *
 * @author livia.miura
 */
public class UsuarioDAO {

//    Database Database = new Database();
    /**
     * Recupera todos os registros da tabela usuário.
     *
     * @author livia.miura
     * @return List - Lista preenchida com usuários.
     *
     */
    public List listar() {


        Query query = Database.manager.createNamedQuery("UsuarioDTO.findAll");


        query.setHint(QueryHints.MAINTAIN_CACHE, HintValues.FALSE);// evita consulta em cache
        List lista = query.getResultList();
        return lista;
    }

    /**
     * Recupera um registro da tabela usuário, com base no valor da chave
     * primária.
     *
     * @author Antonio Cassiano
     * @param usuarioDTO
     * @return UsuarioDTO - preenchido com o registro selecionado.
     *
     */
    public UsuarioDTO selecionar(UsuarioDTO usuarioDTO) {
        return (UsuarioDTO) Database.manager.find(UsuarioDTO.class, usuarioDTO.getIdUsuario());
    }

    /**
     * Grava um registro na tabela usuário se IdUsuario igual a zero, caso
     * contrario atualiza o registro. Verifica se chave unica do campo login e
     * valido.
     *
     * @author livia.miura
     * @param usuarioDTO
     * @return string listar para navegacao.
     *
     */
    public String gravar(UsuarioDTO usuarioDTO) {
        String retorno = null;
        UsuarioAction usuarioAction = new UsuarioAction();
        Database.manager.getTransaction().begin();
        try {
            if (usuarioDTO.getIdUsuario() == 0) {

                Database.manager.persist(usuarioDTO); // gravar
                usuarioAction.enviarEmailNovoUsuario(usuarioDTO);
                //usuarioAction.enviarEmailAteracao(usuarioDTO);
                //mudando codigo para no final -13-09-12

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                        "Usuário Registrado com Sucesso!",
                        ""));

            } else {
                Database.manager.merge(usuarioDTO); // atualizar
                usuarioAction.enviarEmail(usuarioDTO);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                        "Usuário Alterado com Sucesso!",
                        ""));
            }

            Database.manager.getTransaction().commit();
            retorno = "listar";
        } catch (Exception e) {
        }
        return retorno;
    }

    /**
     * Exclui um registro da tabela usuário, com base no valor da chave
     * primária.
     *
     * @author livia.miura
     * @param usuarioDTO
     *
     */
    
    public String excluir(UsuarioDTO usuarioDTO) {
        String retorno = "listar";
        try {
            Database.manager.getTransaction().begin();
            Database.manager.remove(Database.manager.find(UsuarioDTO.class, usuarioDTO.getIdUsuario()));
            Database.manager.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuário Excluído do Sistema.",
                    ""));
            retorno = "listar";

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível excluir o usuário."
                    + "O mesmo está responsável em um Projeto.",
                    ""));
            retorno = null;
        }
        return retorno;
    }

    /**
     * Este método localiza um usuário pelo login/senha
     *
     * @author livia.miura
     * @return usuarioDTO ou erro caso não encontre
     * @throws NoResultException
     *
     */
    public UsuarioDTO validarLogin(UsuarioDTO usuarioDTO) throws NoResultException {
//String retorno= null;
        Object object = Database.manager.createNamedQuery("UsuarioDTO.findByLoginSenha").setParameter("login", usuarioDTO.getLogin()).setParameter("senha", usuarioDTO.getSenha()).getSingleResult();
        usuarioDTO = (UsuarioDTO) object;
        if ((usuarioDTO.getStatus() == 0)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                    "Bem-Vindo(a), " + usuarioDTO.getNome() + ".",
                    ""));
            return usuarioDTO;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    usuarioDTO.getNome() + " seu Usuário com Status INATIVO",
                    "Entrar em contato com Administrador do GAP"));
            return null;
        }
        //  return usuarioDTO;
    }

    public UsuarioDTO buscarPorLogin(UsuarioDTO usuarioDTO) throws NoResultException {

        Object object = Database.manager.createNamedQuery("UsuarioDTO.findByLogin").setParameter("login", usuarioDTO.getLogin());
        usuarioDTO = (UsuarioDTO) object;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                "Welcome, " + usuarioDTO.getNome() + "!",
                ""));
        return usuarioDTO;
    }

    public UsuarioDTO checkLogin(UsuarioDTO usuarioDTO) throws NoResultException {
//String retorno= null;
        Object object = Database.manager.createNamedQuery("UsuarioDTO.findByLogin").setParameter("login", usuarioDTO.getLogin()).getSingleResult();
        usuarioDTO = (UsuarioDTO) object;
        if (usuarioDTO.getLogin() != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                    "Usuario " + usuarioDTO.getNome() + "já´existe",
                    "Escolher um novo login, por favor"));
        }

        return usuarioDTO;


    }

    public UsuarioDTO checkLogin(String teste) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Object object = Database.manager.createNamedQuery("UsuarioDTO.findByLogin").setParameter("login", usuarioDTO.getLogin()).getSingleResult();

        System.out.println("objeto" + object);
        usuarioDTO = (UsuarioDTO) object;
        return usuarioDTO;
    }

    public Collection<UsuarioDTO> selecionarLogin(UsuarioDTO usuarioDTO) {

        Query query = Database.manager.createNamedQuery("UsuarioDTO.findByLogin").setParameter("login", usuarioDTO.getLogin());
        List lista = query.getResultList();
        System.out.println("usuarioDTO.getLogin() ==" + usuarioDTO.getLogin());
        System.out.println("lista" + lista);
        return lista;
    }
}
