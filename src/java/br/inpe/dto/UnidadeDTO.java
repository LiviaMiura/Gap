/** 
 * Projeto : GAP
 * Nome Arquivo : UnidadeDTO.Java
 * UnidadeDTO - Manipula dados referentes a tabela Unidade - (Select na tabela Unidade) - métodos Set e Get dos atributos
 * @return : 
 * @author : castro
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "unidade", catalog = "gap", schema = "")
@XmlRootElement                                                          
@NamedQueries({
    @NamedQuery(name = "UnidadeDTO.findAll", query = "SELECT u FROM UnidadeDTO u order by u.nomeUnidade asc"),
    @NamedQuery(name = "UnidadeDTO.findByIdUnidade", query = "SELECT u FROM UnidadeDTO u WHERE u.idUnidade = :idUnidade"),
    @NamedQuery(name = "UnidadeDTO.findByNomeUnidade", query = "SELECT u FROM UnidadeDTO u  WHERE u.nomeUnidade = :nomeUnidade"),
    @NamedQuery(name = "UnidadeDTO.findByLocalidade", query = "SELECT u FROM UnidadeDTO u WHERE u.localidade = :localidade")})

public class UnidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_unidade", nullable = false)
    private Integer idUnidade;
    @Column(name = "nome_unidade", length = 45)
    private String nomeUnidade;
    @Column(name = "localidade", length = 45)
    private String localidade;
  
    //***********Unidade versus Usuario (para relacionamento OneToMany - nao altera******
    @OneToMany(mappedBy = "unidadeFk")
    private Collection<UsuarioDTO> usuarioDTOCollection;
//***************************************************************************************
    
    /** 
     * Construtor 
     * author castro
     * @param  nao se aplica
     * @return nao se aplica
     **/
    public UnidadeDTO() {
    }

    /** 
     * Construtor 
     * author castro
     * @param  Integer idUnidade
     * @return nao se aplica
     **/
    public UnidadeDTO(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

     /** 
     * Obtem Id da Unidade
     * author castro
     * @param  nao se aplica
     * @return idUnidade
     **/
    public Integer getIdUnidade() {
        return idUnidade;
    }

    /** 
     * Setar Id da Unidade
     * author castro
     * @param  Integer idUnidade
     * @return void
     **/
    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    /** 
     * Obtem o Nome da Unidade
     * author castro
     * @param  nao se aplica
     * @return nomeUnidade
     **/
    public String getNomeUnidade() {
        return nomeUnidade;
    }

    /** 
     * Setar o  nome da Unidade
     * author castro
     * @param  String nomeUnidade
     * @return void
     **/
    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    /** 
     * Obtem a localidade
     * author castro
     * @param  nao se aplica
     * @return localidade
     **/
    public String getLocalidade() {
        return localidade;
    }

    /** 
     * Setar Localidade
     * author castro
     * @param  String localidade
     * @return void
     **/
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @XmlTransient
    public Collection<UsuarioDTO> getUsuarioDTOCollection() {
        return usuarioDTOCollection;
    }

    public void setUsuarioDTOCollection(Collection<UsuarioDTO> usuarioDTOCollection) {
        this.usuarioDTOCollection = usuarioDTOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidade != null ? idUnidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadeDTO)) {
            return false;
        }
        UnidadeDTO other = (UnidadeDTO) object;
        if ((this.idUnidade == null && other.idUnidade != null) || (this.idUnidade != null && !this.idUnidade.equals(other.idUnidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.UnidadeDTO[ idUnidade=" + idUnidade + " ]";
    }
    
}
