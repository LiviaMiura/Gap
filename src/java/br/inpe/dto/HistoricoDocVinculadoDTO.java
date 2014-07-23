/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author livia.miura
 */
@Entity
@Table(name = "historicodocvinc", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoricoDocVinculadoDTO.findAll", query = "SELECT h FROM HistoricoDocVinculadoDTO h order by h.idHistDocVinc desc"),
    @NamedQuery(name = "HistoricoDocVinculadoDTO.findByIdHistDocVinc", query = "SELECT h FROM HistoricoDocVinculadoDTO h WHERE h.idHistDocVinc = :idHistDocVinc"),
    @NamedQuery(name = "HistoricoDocVinculadoDTO.findByDataOperacao", query = "SELECT h FROM HistoricoDocVinculadoDTO h WHERE h.dataOperacao = :dataOperacao")})

public class HistoricoDocVinculadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_HistDocVinc", nullable = false)
    private Integer idHistDocVinc;
    @Basic(optional = false)
    @Column(name = "usuarioOperacao", nullable = false, length = 45)
    private String usuarioOperacao;
    @Basic(optional = false)
    @Column(name = "data_operacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOperacao;
  
    @Column(name = "tipoAlteracao", length = 45)
    private String tipoAlteracao;
    //**************************************************************************
    @JoinColumn(name = "documentoVinculado_fk")
    @ManyToOne(optional = false)
    private DocumentoVinculadoDTO documentoVinculadofk;
    //**************************************************************************

    public HistoricoDocVinculadoDTO() {
    }

    public HistoricoDocVinculadoDTO(Integer idHistDocVinc) {
        this.idHistDocVinc = idHistDocVinc;
    }

    public HistoricoDocVinculadoDTO(Integer idHistDocVinc, Date dataOperacao) {
        this.idHistDocVinc = idHistDocVinc;
        this.dataOperacao = dataOperacao;
    }

    public Integer getIdHistDocVinc() {
        return idHistDocVinc;
    }

    public void setIdHistDocVinc(Integer idHistDocVinc) {
        this.idHistDocVinc = idHistDocVinc;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(Date dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getUsuarioOperacao() {
        return usuarioOperacao;
    }

    public void setUsuarioOperacao(String usuarioOperacao) {
        this.usuarioOperacao = usuarioOperacao;
    }

//******************************************************************************
    public String getTipoAlteracao() {
        return tipoAlteracao;
    }

    public void setTipoAlteracao(String tipoAlteracao) {
        this.tipoAlteracao = tipoAlteracao;
    }

    public DocumentoVinculadoDTO getDocumentoVinculadofk() {
        if (documentoVinculadofk == null) {
            documentoVinculadofk = new DocumentoVinculadoDTO();
        }
        return documentoVinculadofk;
    }

    public void setDocumentoVinculadofk(DocumentoVinculadoDTO documentoVinculadofk) {
        this.documentoVinculadofk = documentoVinculadofk;
    }
//******************************************************************************

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistDocVinc != null ? idHistDocVinc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoDocVinculadoDTO)) {
            return false;
        }
        HistoricoDocVinculadoDTO other = (HistoricoDocVinculadoDTO) object;
        if ((this.idHistDocVinc == null && other.idHistDocVinc != null) || (this.idHistDocVinc != null && !this.idHistDocVinc.equals(other.idHistDocVinc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.HistoricoDocVinculadoDTO[ idHistDocVinc=" + idHistDocVinc + " ]";
    }
}
