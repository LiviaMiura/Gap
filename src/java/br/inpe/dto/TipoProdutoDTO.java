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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "tipoproduto", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProdutoDTO.findAll", query = "SELECT t FROM TipoProdutoDTO t"),
    @NamedQuery(name = "TipoProdutoDTO.findByIdTipoProduto", query = "SELECT t FROM TipoProdutoDTO t WHERE t.idTipoProduto = :idTipoProduto"),
    @NamedQuery(name = "TipoProdutoDTO.findByIdentificacao", query = "SELECT t FROM TipoProdutoDTO t WHERE t.identificacao = :identificacao"),
    @NamedQuery(name = "TipoProdutoDTO.findByDescricao", query = "SELECT t FROM TipoProdutoDTO t WHERE t.descricao = :descricao")})
public class TipoProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_TipoProduto", nullable = false)
    private Integer idTipoProduto;
    @Column(name = "identificacao", length = 45)
    private String identificacao;
    @Column(name = "descricao", length = 45)
    private String descricao;
    @Lob
    @Column(name = "observacao", length = 65535)
    private String observacao;
    //**************************************************************************
    /*@JoinColumn(name = "responsavel", referencedColumnName = "id_Usuario")
    @ManyToOne
    private UsuarioDTO responsavel;*/
   // @JoinColumn(name = "responsavel")
   // @ManyToOne(fetch = FetchType.LAZY)
   // private UsuarioDTO responsavel;
    //**************************************************************************
    @OneToMany(mappedBy = "tipoProdutofk")
    private Collection<NoDTO> noDTOCollection;

    public TipoProdutoDTO() {
    }

    public TipoProdutoDTO(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public Integer getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
//******************************************************************************
/*    public UsuarioDTO getResponsavel() {
         if (responsavel == null) {
            responsavel = new UsuarioDTO();
        } 
        return responsavel;
    }

    public void setResponsavel(UsuarioDTO responsavel) {
        this.responsavel = responsavel;
    }*/
//******************************************************************************
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
        hash += (idTipoProduto != null ? idTipoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProdutoDTO)) {
            return false;
        }
        TipoProdutoDTO other = (TipoProdutoDTO) object;
        if ((this.idTipoProduto == null && other.idTipoProduto != null) || (this.idTipoProduto != null && !this.idTipoProduto.equals(other.idTipoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.TipoProdutoDTO[ idTipoProduto=" + idTipoProduto + " ]";
    }
}
