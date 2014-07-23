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
@Table(name = "no", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "NoDTO.findAll", query = "SELECT n FROM NoDTO n "),
    @NamedQuery(name = "NoDTO.findAll1", query = "SELECT n FROM NoDTO n WHERE n.status = :status"),
       @NamedQuery(name = "NoDTO.findByStatus", query = "SELECT n FROM NoDTO n WHERE n.status = :status"),
    @NamedQuery(name = "NoDTO.findByIdNo", query = "SELECT n FROM NoDTO n WHERE n.idNo = :idNo"),
    @NamedQuery(name = "NoDTO.findByIdNoNULL", query = "SELECT n FROM NoDTO n WHERE n.noFk is NULL"),
    @NamedQuery(name = "NoDTO.findByTitulo", query = "SELECT n FROM NoDTO n WHERE n.titulo = :titulo"),
    // alterado 
    @NamedQuery(name = "NoDTO.findByEstadoNo", query = "SELECT n FROM NoDTO n WHERE n.estadoNofk.idEstadoNo = :idEstadoNo"),
    @NamedQuery(name = "NoDTO.findByEstadoNoStatus", query = "SELECT n FROM NoDTO n WHERE n.estadoNofk.idEstadoNo = :idEstadoNo and n.status = :status"),
    @NamedQuery(name = "NoDTO.findByUsuarioID", query = "SELECT n FROM NoDTO n WHERE n.responsavel.idUsuario = :idUsuario"),
    @NamedQuery(name = "NoDTO.findByTipoProduto", query = "SELECT n FROM NoDTO n WHERE n.tipoProdutofk.idTipoProduto = :idTipoProduto and n.produtoFinal = true "),
    @NamedQuery(name = "NoDTO.findByProdutoFinalEstadoNo", query = "SELECT n FROM NoDTO n WHERE n.estadoNofk.idEstadoNo = :idEstadoNo and n.produtoFinal = true "),
    @NamedQuery(name = "NoDTO.findByProdutoFinalResponsavel", query = "SELECT n FROM NoDTO n WHERE n.responsavel.idUsuario = :idUsuario and n.produtoFinal = true "),
    @NamedQuery(name = "NoDTO.findByProdutoFinalIdentificacao", query = "SELECT n FROM NoDTO n WHERE n.noFk.idNo = :idNo and n.produtoFinal = true and n.status = :status"),
    // fim alterado
    @NamedQuery(name = "NoDTO.findByDescricao", query = "SELECT n FROM NoDTO n WHERE n.descricao = :descricao"),
    @NamedQuery(name = "NoDTO.findByDataCriacao", query = "SELECT n FROM NoDTO n WHERE n.dataCriacao = :dataCriacao"),
    @NamedQuery(name = "NoDTO.findByData", query = "SELECT n FROM NoDTO n WHERE n.dataCriacao between :dataInicial and :dataFinal and n.status = :status order by n.dataCriacao asc"),
    @NamedQuery(name = "NoDTO.findByTipoDominio", query = "SELECT n FROM NoDTO n WHERE n.tipoDominio = :tipoDominio"),
    @NamedQuery(name = "NoDTO.findByProdutoFinal1", query = "SELECT n FROM NoDTO n WHERE n.produtoFinal = :produtoFinal"),
    @NamedQuery(name = "NoDTO.findByProdutoFinal", query = "SELECT n FROM NoDTO n WHERE n.produtoFinal = true and n.status = :status"),
   
   
    @NamedQuery(name = "NoDTO.findByNivel", query = "SELECT n FROM NoDTO n WHERE n.nivel = :nivel")})
public class NoDTO implements Serializable {

    @Basic(optional = false)
    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noFk")
    private Collection<HistoricoNoDTO> historicoNoDTOCollection;
    @Basic(optional = false)
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_No", nullable = false)
    private Integer idNo;
    @Column(name = "titulo", length = 150)
    private String titulo;
    @Column(name = "descricao", length = 350)
    private String descricao;
    @Column(name = "tipoDominio", length = 45)
    private String tipoDominio;
    @Column(name = "produtoFinal")
    private Short produtoFinal;
    @Column(name = "nivel")
    private Integer nivel;
    @Column(name = "diretorio", length = 150)
    private String diretorio;
    @Column(name = "codigo", length = 70)
    private String codigo;
     @Column(name = "identExterna", length = 70)
    private String identExterna;
    @Column(name = "status", length = 5)
    private Integer status;
    // Apenas no programa e não no Banco de dados
    @Transient
    Date dataInicial;
    @Transient
    Date dataFinal;
    //**************************************************************************
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noFk")
    private List<ItemdoNoDTO> itemdoNoDTOCollection;
    //**************************************************************************
   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "noFilho")
   // private Collection<ArvoreDTO> arvoreDTOCollection;
    //**************************************************************************
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noFk", orphanRemoval = true)
    private List<NoDTO> noFkCollection;
    //*******************************************************
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "noFk")
    //private Collection<DocumentoVinculadoDTO> documentoVinculadoDTOCollection;
    //**************************************************************************
    @JoinColumn(name = "responsavel")
    @ManyToOne(optional = false)
    private UsuarioDTO responsavel;
    //**************************************************************************
    @JoinColumn(name = "tipoProduto_fk")
    @ManyToOne(optional = false)
    private TipoProdutoDTO tipoProdutofk;
    //************************************************************************** 
    @JoinColumn(name = "estadoNo_fk")
    @ManyToOne(optional = false)
    private EstadoNoDTO estadoNofk;
    //**************************************************************************
    @JoinColumn(name = "no_fk")
    @ManyToOne(optional = false)
    private NoDTO noFk;
    //*****************************************************************************
    //@JoinColumn(name = "arvore_fk")
   // @ManyToOne(optional = false)
   // private ArvoreDTO arvoreFk;
    //*****************************************************************************

    public NoDTO() {
    }

    public NoDTO(Integer idNo) {
        this.idNo = idNo;
    }

    public NoDTO(Integer idNo, Date dataCriacao) {
        this.idNo = idNo;
        this.dataCriacao = dataCriacao;
    }

    public Integer getIdNo() {
        return idNo;
    }

    public void setIdNo(Integer idNo) {
        this.idNo = idNo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTipoDominio() {
        return tipoDominio;
    }

    public void setTipoDominio(String tipoDominio) {
        this.tipoDominio = tipoDominio;
    }

    public Boolean getProdutoFinal() {
        if (produtoFinal == null) {
            produtoFinal = new Short("0");
        }
        return (produtoFinal == 1) ? true : false;
    }

    public void setProdutoFinal(Boolean produtoFinal) {
        this.produtoFinal = (short) (produtoFinal.booleanValue() ? 1 : 0);
    }

    public void setProdutoFinal(Short produtoFinal) {
        this.produtoFinal = produtoFinal;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

//******************************************************************************
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
//******************************************************************************

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentExterna() {
        return identExterna;
    }

    public void setIdentExterna(String identExterna) {
        this.identExterna = identExterna;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
    
    
    //**************************************************************************

    public List<ItemdoNoDTO> getItemdoNoDTOCollection() {
        return itemdoNoDTOCollection;
    }

    public void setItemdoNoDTOCollection(List<ItemdoNoDTO> itemdoNoDTOCollection) {
        this.itemdoNoDTOCollection = itemdoNoDTOCollection;
    }
    //**************************************************************************
//tree

    public void addChildFolder(NoDTO child) {
        this.noFkCollection.add(child);
    }

    public List<NoDTO> getNoFkCollection() { 
        return noFkCollection;
    }

    public void setNoFkCollection(List<NoDTO> noFkCollection) {
        this.noFkCollection = noFkCollection;
    }

    //*********************************************************************************** 
   /* public Collection<ArvoreDTO> getArvoreDTOCollection() {
        return arvoreDTOCollection;
    }

    public void setArvoreDTOCollection(Collection<ArvoreDTO> arvoreDTOCollection) {
        this.arvoreDTOCollection = arvoreDTOCollection;
    }
*/
//******************************************************************************
    public UsuarioDTO getResponsavel() {
        if (responsavel == null) {
            responsavel = new UsuarioDTO();
        }
        return responsavel;
    }

    public void setResponsavel(UsuarioDTO responsavel) {
        this.responsavel = responsavel;
    }
//******************************************************************************

    public TipoProdutoDTO getTipoProdutofk() {
        if (tipoProdutofk == null) {
            tipoProdutofk = new TipoProdutoDTO();
        }
        return tipoProdutofk;
    }

    public void setTipoProdutofk(TipoProdutoDTO tipoProdutofk) {
        this.tipoProdutofk = tipoProdutofk;
    }
//******************************************************************************

    public EstadoNoDTO getEstadoNofk() {
        if (estadoNofk == null) {
            estadoNofk = new EstadoNoDTO();
        }
        return estadoNofk;
    }

    public void setEstadoNofk(EstadoNoDTO estadoNofk) {
        this.estadoNofk = estadoNofk;
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
 /*   public ArvoreDTO getArvoreFk() {
        if (arvoreFk == null) {
            arvoreFk = new ArvoreDTO();
        }
        return arvoreFk;
    }

    public void setArvoreFk(ArvoreDTO arvoreFk) {
        this.arvoreFk = arvoreFk;
    }
*/
//******************************************************************************
/*    @XmlTransient
    public Collection<DocumentoVinculadoDTO> getDocumentoVinculadoDTOCollection() {
        return documentoVinculadoDTOCollection;
    }

    public void setDocumentoVinculadoDTOCollection(Collection<DocumentoVinculadoDTO> documentoVinculadoDTOCollection) {
        this.documentoVinculadoDTOCollection = documentoVinculadoDTOCollection;
    }*/
    //**************************************************************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNo != null ? idNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoDTO)) {
            return false;
        }
        NoDTO other = (NoDTO) object;
        if ((this.idNo == null && other.idNo != null) || (this.idNo != null && !this.idNo.equals(other.idNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        //  return "br.inpe.dto.NoDTO[ idNo=" + idNo + " ]";
        // System.out.println("o valor de = " + idNo);
        return "" + idNo;
        // return getTitulo();
    }

    @XmlTransient
    public Collection<HistoricoNoDTO> getHistoricoNoDTOCollection() {
        return historicoNoDTOCollection;
    }

    public void setHistoricoNoDTOCollection(Collection<HistoricoNoDTO> historicoNoDTOCollection) {
        this.historicoNoDTOCollection = historicoNoDTOCollection;
    }
}
