/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author livia.miura
 */
@Entity
@Table(name = "documentovinculado", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoVinculadoDTO.findAll", query = "SELECT d FROM DocumentoVinculadoDTO d"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByIdDocumentoVinculado", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.idDocumentoVinculado = :idDocumentoVinculado"),
    
    @NamedQuery(name = "DocumentoVinculadoDTO.findByCodigo", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.noPai = :noPai and d.codigoProduto = :codigoProduto"),
    
    @NamedQuery(name = "DocumentoVinculadoDTO.findByStatus2", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.status = :status and d.noFk.status = :status"),
   
    @NamedQuery(name = "DocumentoVinculadoDTO.findByTipoDocumentoID", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.tipoDocumentofk.idTipoDocumento = :idTipoDocumento and d.status = :status "),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByCodigoPai", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.noPai = :noPai and d.status = :status"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByStatus", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.status = :status"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByDescricao", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.descricao = :descricao"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByTitulo", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.titulo like :titulo"),//alterado de = para like
    @NamedQuery(name = "DocumentoVinculadoDTO.findByEstadoDocumento", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.estadoDocumento = :estadoDocumento"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByDataAlteracao", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.dataAlteracao = :dataAlteracao"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByTemplate", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.template = :template"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByCodigoExterno", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.codigoExterno = :codigoExterno"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByCodigoDocumento", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.codigoDocumento = :codigoDocumento"),
    @NamedQuery(name = "DocumentoVinculadoDTO.findByAutores", query = "SELECT d FROM DocumentoVinculadoDTO d WHERE d.autores = :autores")})
public class DocumentoVinculadoDTO implements Serializable {

    @Basic(optional = false)
    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Basic(optional = false)
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_DocumentoVinculado", nullable = false)
    private Integer idDocumentoVinculado;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Column(name = "titulo", length = 100)
    private String titulo;
    @Column(name = "estadoDocumento", length = 45)
    private String estadoDocumento;
    @Column(name = "template", length = 145)
    private String template;
    @Column(name = "codigoExterno", length = 45)
    private String codigoExterno;
    @Column(name = "codigoDocumento", length = 45)
    private String codigoDocumento;
    @Column(name = "autores", length = 145)
    private String autores;
    @Column(name = "status", length = 5)
    private Integer status;
    @Column(name = "noPai", length = 5)
    private Integer noPai;
    @Column(name = "codigoProduto", length = 70)
    private String codigoProduto;
    @Column(name = "idItemdono", length = 5)
    private Integer idItemdono;
    @Column(name = "diretorio", length = 150)
    private String diretorio;
    //*********************
    //**************************************************************************
    @JoinColumn(name = "tipoDocumento_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoDocumentoDTO tipoDocumentofk;
    //***************************************************************************
    @JoinColumn(name = "no_fk", referencedColumnName = "id_No")
    @ManyToOne(optional = false)
    private NoDTO noFk;
    //**************************************************************************
    /*
     * @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoVinculadofk")
     * private List<DocumentoVinculadoDTO> documentoVinculadoDTOs;
     */
    //***************************************************************************
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoVinculadofk")
    private List<ItemdoNoDTO> itemdoNoDTOCollection;
    //***************************************************************************

    public DocumentoVinculadoDTO() {
    }

    public DocumentoVinculadoDTO(Integer idDocumentoVinculado) {
        this.idDocumentoVinculado = idDocumentoVinculado;
    }

    public DocumentoVinculadoDTO(Integer idDocumentoVinculado, Date dataAlteracao) {
        this.idDocumentoVinculado = idDocumentoVinculado;
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdDocumentoVinculado() {
        return idDocumentoVinculado;
    }

    public void setIdDocumentoVinculado(Integer idDocumentoVinculado) {
        this.idDocumentoVinculado = idDocumentoVinculado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNoPai() {
        return noPai;
    }

    public void setNoPai(Integer noPai) {
        this.noPai = noPai;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Integer getIdItemdono() {
        return idItemdono;
    }

    public void setIdItemdono(Integer idItemdono) {
        this.idItemdono = idItemdono;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    //**************************************************************************
    public TipoDocumentoDTO getTipoDocumentofk() {
        if (tipoDocumentofk == null) {
            tipoDocumentofk = new TipoDocumentoDTO();
        }
        return tipoDocumentofk;
    }

    public void setTipoDocumentofk(TipoDocumentoDTO tipoDocumentofk) {
        this.tipoDocumentofk = tipoDocumentofk;
    }
    //**************************************************************************

    public NoDTO getNoFk() {
        if (noFk == null) {
            noFk = new NoDTO();
        }
        return noFk;
    }

    public void setNoFk(NoDTO noFk) {
        this.noFk = noFk;
    }
    //**************************************************************************

    //***************************************************************************
    @XmlTransient
    public List<ItemdoNoDTO> getItemdoNoDTOCollection() {
        return itemdoNoDTOCollection;
    }

    public void setItemdoNoDTOCollection(List<ItemdoNoDTO> itemdoNoDTOCollection) {
        this.itemdoNoDTOCollection = itemdoNoDTOCollection;
    }

    //********************************************************************
  /*
     * public List<DocumentoVinculadoDTO> getDocumentoVinculadoDTOs() { return
     * documentoVinculadoDTOs; }
     *
     * public void setDocumentoVinculadoDTOs(List<DocumentoVinculadoDTO>
     * documentoVinculadoDTOs) { this.documentoVinculadoDTOs =
     * documentoVinculadoDTOs; }
     */
//**************************************************************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentoVinculado != null ? idDocumentoVinculado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoVinculadoDTO)) {
            return false;
        }
        DocumentoVinculadoDTO other = (DocumentoVinculadoDTO) object;
        if ((this.idDocumentoVinculado == null && other.idDocumentoVinculado != null) || (this.idDocumentoVinculado != null && !this.idDocumentoVinculado.equals(other.idDocumentoVinculado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //  return "br.inpe.dto.DocumentoVinculadoDTO[ idDocumentoVinculado=" + idDocumentoVinculado + " ]";
        return "" + idDocumentoVinculado;
    }
}
