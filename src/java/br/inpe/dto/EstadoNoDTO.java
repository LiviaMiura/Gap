/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author livia.miura
 */
@Entity
@Table(name = "estadono", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoNoDTO.findAll", query = "SELECT e FROM EstadoNoDTO e order by e.identificacao asc"),
    @NamedQuery(name = "EstadoNoDTO.findByIdEstadoNo", query = "SELECT e FROM EstadoNoDTO e WHERE e.idEstadoNo = :idEstadoNo"),
    @NamedQuery(name = "EstadoNoDTO.findByIdentificacao", query = "SELECT e FROM EstadoNoDTO e WHERE e.identificacao = :identificacao"),
    @NamedQuery(name = "EstadoNoDTO.findByDescricao", query = "SELECT e FROM EstadoNoDTO e WHERE e.descricao = :descricao")})
public class EstadoNoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estadoNo", nullable = false)
    private Integer idEstadoNo;
    @Column(name = "identificacao", length = 45)
    private String identificacao;
    @Column(name = "descricao", length = 300)
    private String descricao;
    @OneToMany(mappedBy = "estadoNofk")
    private Collection<NoDTO> noDTOCollection;

    public EstadoNoDTO() {
    }

    public EstadoNoDTO(Integer idEstadoNo) {
        this.idEstadoNo = idEstadoNo;
    }

    public Integer getIdEstadoNo() {
        return idEstadoNo;
    }

    public void setIdEstadoNo(Integer idEstadoNo) {
        this.idEstadoNo = idEstadoNo;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
 
    public Collection<NoDTO> getNoDTOCollection() {
        return noDTOCollection;
    }

    public void setNoDTOCollection(Collection<NoDTO> noDTOCollection) {
        this.noDTOCollection = noDTOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoNo != null ? idEstadoNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoNoDTO)) {
            return false;
        }
        EstadoNoDTO other = (EstadoNoDTO) object;
        if ((this.idEstadoNo == null && other.idEstadoNo != null) || (this.idEstadoNo != null && !this.idEstadoNo.equals(other.idEstadoNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.EstadoNoDTO[ idEstadoNo=" + idEstadoNo + " ]";
              //  return "" + idEstadoNo;
    }

   
   
    
}
