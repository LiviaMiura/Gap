/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.validacao;

import br.inpe.dto.UsuarioDTO;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 *
 * @author livia.miura
 */
public class Validacao {

    private UsuarioDTO usuarioDTO;
    String loginx;

    public UsuarioDTO getUsuarioDTO() {
        if (usuarioDTO == null) {
            usuarioDTO = new UsuarioDTO();
        }
        return usuarioDTO;
    }

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    //**********************************************
    public String getLoginx() {
        return loginx;
    }

    public void setLoginx(String loginx) {
        this.loginx = loginx;
    }
    //****

   
    public void validar(FacesContext context, UIComponent component, Object objeto) {

        String teste = (String) objeto;
        if (teste.trim().equals("")) {
            FacesMessage mensagem = new FacesMessage("Campo não pode estar em branco!");
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), mensagem);
        } else if (teste.trim().length() != teste.length()) {
            FacesMessage mensagem = new FacesMessage("Campo inválido!");
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), mensagem);
        }
    }
    /*
     * public void validarNome(FacesContext context, UIComponent
     * component,Object objeto){
     *
     * String teste = (String)objeto; if(teste.trim().equals("")){ FacesMessage
     * mensagem = new FacesMessage("Campo não pode estar em branco!");
     * ((UIInput)component).setValid(false);
     * context.addMessage(component.getClientId(context),mensagem); } else
     * if(teste.trim().length()!=teste.length()){ FacesMessage mensagem = new
     * FacesMessage("Campo inválido!"); ((UIInput)component).setValid(false);
     * context.addMessage(component.getClientId(context),mensagem); } else
     * if(Utilitario.isNaN(teste)){ FacesMessage mensagem = new
     * FacesMessage("Campo não pode ser número!");
     * ((UIInput)component).setValid(false);
     * context.addMessage(component.getClientId(context),mensagem); }
    }
     */

    public void validarNumero(FacesContext context, UIComponent component, Object objeto) {
        System.out.println("entrei na validação de numeros");
        int teste = (Integer) objeto;
        if (teste <= 0) {
            FacesMessage mensagem = new FacesMessage("Campo não pode ser negativo!");
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context), mensagem);
        }
    }
}
