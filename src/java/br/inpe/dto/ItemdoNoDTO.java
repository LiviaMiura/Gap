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
@Table(name = "itemdono", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    
          @NamedQuery(name = "ItemdoNoDTO.findByStatus", query = "SELECT i FROM ItemdoNoDTO i WHERE i.status = :status"),
          @NamedQuery(name = "ItemdoNoDTO.findByStatus2", query = "SELECT i FROM ItemdoNoDTO i WHERE i.status = :status and i.noFk.status = :status"),
    
    @NamedQuery(name = "ItemdoNoDTO.findByNo", query = "SELECT i FROM ItemdoNoDTO i WHERE i.noFk.idNo = :idNo"),
    //********* criado manualmente
    @NamedQuery(name = "ItemdoNoDTO.findByTipoDocumento", query = "SELECT i FROM ItemdoNoDTO i WHERE i.documentoVinculadofk.tipoDocumentofk.identificacao like :identificacao"),//alterado de = para like
    @NamedQuery(name = "ItemdoNoDTO.findByTipoDocumentoID", query = "SELECT i FROM ItemdoNoDTO i WHERE i.documentoVinculadofk.tipoDocumentofk.idTipoDocumento = :idTipoDocumento and i.status = :status "),
    @NamedQuery(name = "ItemdoNoDTO.findByDocumentoVinculadoID", query = "SELECT i FROM ItemdoNoDTO i WHERE i.documentoVinculadofk.idDocumentoVinculado = :idDocumentoVinculado"),
    @NamedQuery(name = "ItemdoNoDTO.findByEstadoNoID", query = "SELECT i FROM ItemdoNoDTO i WHERE i.noFk.estadoNofk.idEstadoNo = :idEstadoNo and i.status = :status"),
    @NamedQuery(name = "ItemdoNoDTO.findByIdent", query = "SELECT i FROM ItemdoNoDTO i WHERE i.noFk.idNo = :idNo and i.status = :status"),
  
    //***** fim da criacao
    @NamedQuery(name = "ItemdoNoDTO.findAll", query = "SELECT i FROM ItemdoNoDTO i"),
    @NamedQuery(name = "ItemdoNoDTO.findByIditemDoNo", query = "SELECT i FROM ItemdoNoDTO i WHERE i.idItemdoNo = :idItemdoNo"),
    @NamedQuery(name = "ItemdoNoDTO.findByDataitemdoNo", query = "SELECT i FROM ItemdoNoDTO i WHERE i.dataItemdoNo = :dataItemdoNo")})
public class ItemdoNoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_itemdoNo", nullable = false)
    private Integer idItemdoNo;
    @Basic(optional = false)
    @Column(name = "data_itemDoNo", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataItemdoNo;
    @Column(name = "status")
    private Integer status;

    //**************************************************************************
    @JoinColumn(name = "no_fk", referencedColumnName = "id_No")
    @ManyToOne(optional = false)
    private NoDTO noFk;
    //**************************************************************************
    @JoinColumn(name = "documentoVinculado_fk", referencedColumnName = "id_DocumentoVinculado")
    @ManyToOne(optional = false)
    private DocumentoVinculadoDTO documentoVinculadofk;

    public ItemdoNoDTO() {
    }

    public ItemdoNoDTO(Integer iditemDoNo) {
        this.idItemdoNo = iditemDoNo;
    }

    public ItemdoNoDTO(Integer idItemdoNo, Date dataItemdoNo) {
        this.idItemdoNo = idItemdoNo;
        this.dataItemdoNo = dataItemdoNo;
    }

    public Integer getIdItemdoNo() {
        return idItemdoNo;
    }

    public void setIdItemdoNo(Integer idItemdoNo) {
        this.idItemdoNo = idItemdoNo;
    }

    public Date getDataItemDoNo() {
        return dataItemdoNo;
    }

    public void setDataItemdoNo(Date dataItemdoNo) {
        this.dataItemdoNo = dataItemdoNo;
    }
    
 public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
//******************************************************************************

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
        hash += (idItemdoNo != null ? idItemdoNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemdoNoDTO)) {
            return false;
        }
        ItemdoNoDTO other = (ItemdoNoDTO) object;
        if ((this.idItemdoNo == null && other.idItemdoNo != null) || (this.idItemdoNo != null && !this.idItemdoNo.equals(other.idItemdoNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idItemdoNo;
    }
}
