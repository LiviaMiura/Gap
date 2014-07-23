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
@Table(name = "arvore", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArvoreDTO.findByNo", query = "SELECT a FROM ArvoreDTO a WHERE a.noFilho.idNo = :idNo"),
    @NamedQuery(name = "ArvoreDTO.findAll", query = "SELECT a FROM ArvoreDTO a"),
    @NamedQuery(name = "ArvoreDTO.findByIdArvore", query = "SELECT a FROM ArvoreDTO a WHERE a.idArvore = :idArvore"),
    @NamedQuery(name = "ArvoreDTO.findByNoPai", query = "SELECT a FROM ArvoreDTO a WHERE a.noPai = :noPai and a.codigo = :codigo"),
    @NamedQuery(name = "ArvoreDTO.findByNoFilho2", query = "SELECT a FROM ArvoreDTO a WHERE a.noFilho = :noFilho and a.codigo = :codigo"),
    @NamedQuery(name = "ArvoreDTO.findByNoFilho", query = "SELECT a FROM ArvoreDTO a WHERE a.noFilho = :noFilho")
})
public class ArvoreDTO implements Serializable {

    //  @OneToMany(mappedBy = "arvoreFk")
    //  private Collection<NoDTO> noDTOCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_arvore", nullable = false)
    private Integer idArvore;
    @Column(name = "no_pai")
    private Integer noPai;
    @Column(name = "codigo", length = 70)
    private String codigo;
    
    //**************************************************************************
    @JoinColumn(name = "no_filho", referencedColumnName = "id_No")
    @ManyToOne(optional = false)
    private NoDTO noFilho;

    public ArvoreDTO() {
    }

    public ArvoreDTO(Integer idArvore) {
        this.idArvore = idArvore;
    }

    public Integer getIdArvore() {
        return idArvore;
    }

    public void setIdArvore(Integer idArvore) {
        this.idArvore = idArvore;
    }

    public Integer getNoPai() {
        return noPai;
    }

    public void setNoPai(Integer noPai) {
        this.noPai = noPai;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   
//******************************************************************************
    public NoDTO getNoFilho() {
        if (noFilho == null) {
            noFilho = new NoDTO();
        }
        return noFilho;
    }

    public void setNoFilho(NoDTO noFilho) {
        this.noFilho = noFilho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArvore != null ? idArvore.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArvoreDTO)) {
            return false;
        }
        ArvoreDTO other = (ArvoreDTO) object;
        if ((this.idArvore == null && other.idArvore != null) || (this.idArvore != null && !this.idArvore.equals(other.idArvore))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.ArvoreDTO idArvore=" + idArvore + " ]";
        // return "" + noPai;
    }

    /*
     * @XmlTransient public Collection<NoDTO> getNoDTOCollection() { return
     * noDTOCollection; }
     *
     * public void setNoDTOCollection(Collection<NoDTO> noDTOCollection) {
     * this.noDTOCollection = noDTOCollection; }
     */
}
