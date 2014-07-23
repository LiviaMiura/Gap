/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dto;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author livia.miura
 */
@Entity
@Table(name = "configuraremail", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigurarEmailDTO.findAll", query = "SELECT c FROM ConfigurarEmailDTO c"),
    @NamedQuery(name = "ConfigurarEmailDTO.findById", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.id = :id"),
    @NamedQuery(name = "ConfigurarEmailDTO.findByServidor", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.servidor = :servidor"),
    @NamedQuery(name = "ConfigurarEmailDTO.findByPorta", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.porta = :porta"),
    @NamedQuery(name = "ConfigurarEmailDTO.findByAutenticacao", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.autenticacao = :autenticacao"),
    @NamedQuery(name = "ConfigurarEmailDTO.findByUsuario", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "ConfigurarEmailDTO.findBySenha", query = "SELECT c FROM ConfigurarEmailDTO c WHERE c.senha = :senha")})
public class ConfigurarEmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "servidor", nullable = false, length = 45)
    private String servidor;
    @Basic(optional = false)
    @Column(name = "porta", nullable = false, length = 5)
    private String porta;
    @Basic(optional = false)
    @Column(name = "autenticacao", nullable = false, length = 5)
    private String autenticacao;
    @Basic(optional = false)
    @Column(name = "usuario", nullable = false, length = 60)
    private String usuario;
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 35)
    private String senha;

    public ConfigurarEmailDTO() {
    }

    public ConfigurarEmailDTO(Integer id) {
        this.id = id;
    }

    public ConfigurarEmailDTO(Integer id, String servidor, String porta, String autenticacao, String usuario, String senha) {
        this.id = id;
        this.servidor = servidor;
        this.porta = porta;
        this.autenticacao = autenticacao;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getAutenticacao() {
        return autenticacao;
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurarEmailDTO)) {
            return false;
        }
        ConfigurarEmailDTO other = (ConfigurarEmailDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.configurarEmailDTO[ id=" + id + " ]";
    }

   
    
}
