/** 
 * Projeto : GAP
 * Nome Arquivo : HistoricoDTO.Java
 * HistoricoDTO - Manipula dados referentes a tabela Historico (Select na tabela Historico) - métodos Set e Get dos atributos
 * @return : 
 * @author : castro
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "historico", catalog = "gap", schema = "")
@XmlRootElement
@NamedQueries({
    //desc em 29/06/2012 Cassiano
    @NamedQuery(name = "HistoricoDTO.findAll", query = "SELECT h FROM HistoricoDTO h order by h.idHistorico desc"),
    
    
    @NamedQuery(name = "HistoricoDTO.findByIdHistorico", query = "SELECT h FROM HistoricoDTO h WHERE h.idHistorico = :idHistorico"),
    @NamedQuery(name = "HistoricoDTO.findByDataEntrada", query = "SELECT h FROM HistoricoDTO h WHERE h.dataEntrada = :dataEntrada"),
    @NamedQuery(name = "HistoricoDTO.findByDataSaida", query = "SELECT h FROM HistoricoDTO h WHERE h.dataSaida = :dataSaida")})

public class HistoricoDTO implements Serializable {
    @Column(name = "data_saida")
    @Temporal(TemporalType.TIMESTAMP)
    
    //Alterar dataEntrada e dataSaida para datatime no banco de dados
    private Date dataSaida;
    @Basic(optional = false)
    @Column(name = "data_entrada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrada;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Historico", nullable = false)
    private Integer idHistorico;
    
    /****** Historico versus Usuario - Relacionamento ManyToOne - Alterar *********
    @JoinColumn(name = "usuario_fk", referencedColumnName = "id_Usuario", nullable = false)
    @ManyToOne(optional = false)
    private UsuarioDTO usuarioFk;
    *****************************************************************************/
         
    //************** Relacionamento ManyToOne - Alterar (31-05-12- no Lab esse relacionamento nao esta alterado ??? **************************
    @JoinColumn(name = "usuario_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioDTO usuarioFk; //Instanciar UsuarioDTO
    //****************************************************************************
    
    /** 
     * Construtor 
     * author castro
     * @param  nao se aplica
     * @return nao se aplica
     **/
    public HistoricoDTO() {
    }

     /** 
     * Construtor 
     * author castro
     * @param  Integer idHistorico
     * @return nao se aplica
     **/
    public HistoricoDTO(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    /** 
     * Construtor 
     * author castro
     * @param  Integer idHistorico, Date dataEntrada, Date dataSaida
     * @return nao se aplica
     **/
    public HistoricoDTO(Integer idHistorico, Date dataEntrada, Date dataSaida) {
        this.idHistorico = idHistorico;
        this.dataEntrada = dataEntrada;
        this.dataSaida   = dataSaida;
    }

    /** 
     * Obtem Id do Historico
     * author castro
     * @param  nao se aplica
     * @return idHistorico
     **/
    public Integer getIdHistorico() {
        return idHistorico;
    }

    /** 
     * Setar Id do Historico
     * author castro
     * @param  Integer idHistorico
     * @return void
     **/
    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    /** 
     * Obtem a Data de entrada
     * author castro
     * @param  nao se aplica
     * @return dataEntrada
     **/
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /** 
     * Setar Data de Entrada
     * author castro
     * @param  Date dataEntrada
     * @return void
     **/
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /** 
     * Obtem a Data de saida
     * author castro
     * @param  nao se aplica
     * @return dataSaida
     **/
    public Date getDataSaida() {
        return dataSaida;
    }

    /** 
     * Setar Data de Saida
     * author castro
     * @param  Date dataSaida
     * @return void
     **/
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    //***************** Alterar o Get e Set de UsuarioFk ************************
    /** 
     * Obtem Usuario utilizando a chave estrangeira -UsuarioFk
     * author castro
     * @param  nao se aplica
     * @return usuarioFk
     **/
    public UsuarioDTO getUsuarioFk() {
        if(usuarioFk == null){
            usuarioFk = new UsuarioDTO();
        }
        return usuarioFk;
    }

    /** 
     * Setar chave estrangeira - UsuarioFK
     * author castro
     * @param  UsuarioDTO usuarioFk
     * @return void
     **/
    public void setUsuarioFk(UsuarioDTO usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorico != null ? idHistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoDTO)) {
            return false;
        }
        HistoricoDTO other = (HistoricoDTO) object;
        if ((this.idHistorico == null && other.idHistorico != null) || (this.idHistorico != null && !this.idHistorico.equals(other.idHistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.HistoricoDTO[ idHistorico=" + idHistorico + " ]";
    }

}
