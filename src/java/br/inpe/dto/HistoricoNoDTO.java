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
@Table(name = "historicono", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoricoNoDTO.findAll", query = "SELECT h FROM HistoricoNoDTO h order by h.idhistoricoNo desc"),
    @NamedQuery(name = "HistoricoNoDTO.findByIdhistoricoNo", query = "SELECT h FROM HistoricoNoDTO h WHERE h.idhistoricoNo = :idhistoricoNo"),
    @NamedQuery(name = "HistoricoNoDTO.findByDataOperacao", query = "SELECT h FROM HistoricoNoDTO h WHERE h.dataOperacao = :dataOperacao"),
   @NamedQuery(name = "HistoricoNoDTO.findByOperacao", query = "SELECT h FROM HistoricoNoDTO h WHERE h.operacao = :operacao"),
  
    @NamedQuery(name = "HistoricoNoDTO.findByEstadoNo", query = "SELECT h FROM HistoricoNoDTO h WHERE h.estadoNo = :estadoNo")})
public class HistoricoNoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historicoNo", nullable = false)
    private Integer idhistoricoNo;
    @Basic(optional = false)
    @Column(name = "data_operacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOperacao;
    @Column(name = "estadoNo", length = 60)
    private String estadoNo;
       @Column(name = "operacao", length = 60)
    private String operacao;
    //**************************************************************************
    @JoinColumn(name = "no_fk", referencedColumnName = "id_No")
    @ManyToOne(optional = false)
    private NoDTO noFk;
    //**************************************************************************
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id_Usuario")
    @ManyToOne(optional = false)
    private UsuarioDTO usuarioFk;

    public HistoricoNoDTO() {
    }

    public HistoricoNoDTO(Integer idhistoricoNo) {
        this.idhistoricoNo = idhistoricoNo;
    }

    public HistoricoNoDTO(Integer idhistoricoNo, Date dataOperacao) {
        this.idhistoricoNo = idhistoricoNo;
        this.dataOperacao = dataOperacao;
    }

    public Integer getIdhistoricoNo() {
        return idhistoricoNo;
    }

    public void setIdhistoricoNo(Integer idhistoricoNo) {
        this.idhistoricoNo = idhistoricoNo;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(Date dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    

    public String getEstadoNo() {
        return estadoNo;
    }

    public void setEstadoNo(String estadoNo) {
        this.estadoNo = estadoNo;
    }
//******************************************************************************

    public NoDTO getNoFk() {
        if (noFk == null) {
            noFk = new NoDTO();
        }
        return noFk;
    }

    public void setNoFk(NoDTO noFk) {
        this.noFk = noFk;
    }

    public UsuarioDTO getUsuarioFk() {
        if (usuarioFk == null) {
            usuarioFk = new UsuarioDTO();
        }
        return usuarioFk;
    }

    public void setUsuarioFk(UsuarioDTO usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    //**************************************************************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistoricoNo != null ? idhistoricoNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoNoDTO)) {
            return false;
        }
        HistoricoNoDTO other = (HistoricoNoDTO) object;
        if ((this.idhistoricoNo == null && other.idhistoricoNo != null) || (this.idhistoricoNo != null && !this.idhistoricoNo.equals(other.idhistoricoNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.HistoricoNoDTO[ idhistoricoNo=" + idhistoricoNo + " ]";
    }
}
