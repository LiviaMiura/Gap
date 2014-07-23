/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dto.NoDTO;
import javax.faces.context.FacesContext;
import javax.persistence.metamodel.ListAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Métodos para ações relativas ao Menu, get/set e strings para navegação.
 *
 * @author livia.miura
 *
 */
public class MenuAction {

    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    //  private NoDTO noDTO;

    public MenuAction() {
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String menu.
     *
     */
    public String menu() {

        return "menu";
    }

    public String apresentacao() {

        return "apresentacao";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String tipoDocumentoListar.
     *
     */
    public String tipoDocumentoListar() {
        return "tipoDocumentoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String estadoNoListar.
     *
     */
    public String estadoNoListar() {
        return "estadoNoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String tipoProdutoListar.
     *
     */
    public String tipoProdutoListar() {
        return "tipoProdutoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String noListar.
     *
     */
    public String noListar() {
        return "noListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String documentoVinculadoListar.
     *
     */
    public String documentoVinculadoListar() {
        return "documentoVinculadoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String inserirNivel.
     *
     */
    public String inserirNivel() {

        return "inserirNivel";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String tree
     */
    public String tree() {
        return "tree";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String tree3
     */
    public String tree3() {
        return "tree3";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String tree4
     */
    public String tree4() {
        System.out.println("-------------------------------------------------------");
        NoAction atualizaSession = new NoAction();
        atualizaSession.listar1();
        session.setAttribute("noAction", atualizaSession);
        System.out.println("-------------------------------------------------------");
        return "tree4";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar1
     */
    public String consultar1() {
        return "consultar1";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar2
     */
    public String consultar2() {
        return "consultar2";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar3
     */
    public String consultar3() {
        return "consultar3";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar4
     */
    public String consultar4() {
        return "consultar4";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultarMenu
     */
    public String consultarDocumento() {
        return "consultarDocumento";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultarProduto
     */
    public String consultarProduto() {
        return "consultarProduto";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String download
     */
    public String download() {
        return "download";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return String usuarioListar.
     *
     */
    public String usuarioListar() {
        return "usuarioListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return String unidadeListar.
     *
     */
    public String unidadeListar() {
        return "unidadeListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return String historicoListar.
     *
     */
    public String historicoListar() {
        return "historicoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return logout.
     *
     */
    public String logout() {
        return "logout";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String perfilListar.
     *
     */
    public String perfilListar() {
        return "perfilListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultarPorData.
     *
     */
    public String consultarPorData() {
        return "consultarPorData";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String historicoDocVinculadoListar.
     *
     */
    public String historicoDocVinculadoListar() {
        return "historicoDocVinculadoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultartipoDocumentoListar.
     *
     */
    public String consultartipoDocumentoListar() {
        return "consultartipoDocumentoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultarTipo.
     *
     */
    public String consultarTipo() {
        return "consultarTipo";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar5
     */
    public String consultar5() {
        return "consultar5";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar6
     */
    public String consultar6() {
        return "consultar6";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar7
     */
    public String consultar7() {
        return "consultar7";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar8
     */
    public String consultar8() {
        return "consultar8";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar9
     */
    public String consultar9() {
        return "consultar9";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar9
     */
    public String consultar10() {
        return "consultar10";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author castro
     * @return String historicoNoListar.
     *
     */
    public String historicoNoListar() {
        return "historicoNoListar";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar20
     */
    public String consultar20() {
        return "consultar20";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar21
     */
    public String consultar21() {
        return "consultar21";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar22
     */
    public String consultar22() {
        return "consultar22";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar23
     */
    public String consultar23() {
        return "consultar23";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String consultar24
     */
    public String consultar24() {
        return "consultar24";
    }

    /**
     * Controle de navegação para o menu
     *
     * @author livia.miura
     * @return String emailListar
     *
     */
    public String emailListar() {
        return "emailListar";
    }
    
      public String teste() {

        return "teste";
    }
}
