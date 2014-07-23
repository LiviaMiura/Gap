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
@Table(name = "tipodocumento", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumentoDTO.findAll", query = "SELECT t FROM TipoDocumentoDTO t"),
    @NamedQuery(name = "TipoDocumentoDTO.findByIdTipoDocumento", query = "SELECT t FROM TipoDocumentoDTO t WHERE t.idTipoDocumento = :idTipoDocumento"),
    @NamedQuery(name = "TipoDocumentoDTO.findByIdentificacao", query = "SELECT t FROM TipoDocumentoDTO t WHERE t.identificacao = :identificacao"),
    @NamedQuery(name = "TipoDocumentoDTO.findByDescricao", query = "SELECT t FROM TipoDocumentoDTO t WHERE t.descricao = :descricao"),
    
    @NamedQuery(name = "TipoDocumentoDTO.findByTemplate", query = "SELECT t FROM TipoDocumentoDTO t WHERE t.template = :template")})
public class TipoDocumentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_TipoDocumento", nullable = false)
    private Integer idTipoDocumento;
    @Column(name = "identificacao", length = 45)
    private String identificacao;
    @Column(name = "descricao", length = 245)
    private String descricao;
     @Column(name = "tipoDocumento", length = 100)
    private String tipoDocumento;
    @Column(name = "template", length = 145)
    private String template;
    @OneToMany(mappedBy = "tipoDocumentofk")
    private Collection<DocumentoVinculadoDTO> documentoVinculadoDTOCollection;

    public TipoDocumentoDTO() {
    }

    public TipoDocumentoDTO(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    
     public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @XmlTransient
    public Collection<DocumentoVinculadoDTO> getDocumentoVinculadoDTOCollection() {
        return documentoVinculadoDTOCollection;
    }

    public void setDocumentoVinculadoDTOCollection(Collection<DocumentoVinculadoDTO> documentoVinculadoDTOCollection) {
        this.documentoVinculadoDTOCollection = documentoVinculadoDTOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDocumento != null ? idTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumentoDTO)) {
            return false;
        }
        TipoDocumentoDTO other = (TipoDocumentoDTO) object;
        if ((this.idTipoDocumento == null && other.idTipoDocumento != null) || (this.idTipoDocumento != null && !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.TipoDocumentoDTO[ idTipoDocumento=" + idTipoDocumento + " ]";
    }
    
}
