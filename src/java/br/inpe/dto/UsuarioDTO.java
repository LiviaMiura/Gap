/**
 * Projeto : GAP Nome Arquivo : UsuarioDTO.Java UnidadeDTO - Manipula dados
 * referentes a tabela Usuario - (Select na tabela Usuario) - métodos Set e Get
 * dos atributos
 *
 * @return :
 * @author : castro
 */
package br.inpe.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "usuario", catalog = "gap", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})
@XmlRootElement
@NamedQueries({
    //Primeira linha foi inserido na mão
    @NamedQuery(name = "UsuarioDTO.findByLoginSenha", query = "SELECT u FROM UsuarioDTO u WHERE u.login = :login and u.senha = :senha"),
    @NamedQuery(name = "UsuarioDTO.findAll", query = "SELECT u FROM UsuarioDTO u"),
    @NamedQuery(name = "UsuarioDTO.findByIdUsuario", query = "SELECT u FROM UsuarioDTO u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioDTO.findByNome", query = "SELECT u FROM UsuarioDTO u WHERE u.nome = :nome"),
    @NamedQuery(name = "UsuarioDTO.findByEmail", query = "SELECT u FROM UsuarioDTO u WHERE u.email = :email"),
    @NamedQuery(name = "UsuarioDTO.findByPredio", query = "SELECT u FROM UsuarioDTO u WHERE u.predio = :predio"),
    @NamedQuery(name = "UsuarioDTO.findByRamal", query = "SELECT u FROM UsuarioDTO u WHERE u.ramal = :ramal"),
    @NamedQuery(name = "UsuarioDTO.findByLogin", query = "SELECT u FROM UsuarioDTO u WHERE u.login = :login"),
    @NamedQuery(name = "UsuarioDTO.findBySenha", query = "SELECT u FROM UsuarioDTO u WHERE u.senha = :senha"),
    @NamedQuery(name = "UsuarioDTO.findByStatus", query = "SELECT u FROM UsuarioDTO u WHERE u.status = :status")
})
public class UsuarioDTO implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<HistoricoNoDTO> historicoNoDTOCollection;
    @OneToMany(mappedBy = "responsavel")
    private Collection<NoDTO> noDTOCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Usuario", nullable = false)
    private Integer idUsuario;
    @Column(name = "nome", length = 45)
    private String nome;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "predio", length = 45)
    private String predio;
    @Column(name = "ramal")
    private Integer ramal;
    @Column(name = "login", length = 45)
    private String login;
    @Column(name = "senha", length = 45)
    private String senha;
    @Column(name = "perfil", length = 60)
    private String perfil;
    @Column(name = "status")
    private Integer status;
    @Column(name = "statusImagem", length = 70)
    private String statusImagem;
  
    /**
     * Usuario em relaçao a Unidade - Relacionamento ManyToOne - Alterar
     * codigo***** @JoinColumn(name = "unidade_fk", referencedColumnName =
     * "id_unidade") @ManyToOne private UnidadeDTO unidadeFk;
     * ******************************************************************************
     */
    //Acrescentar novo codigo -(Many to One) baseado no anterior.
    @JoinColumn(name = "unidade_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadeDTO unidadeFk;
    //Instanciar a Classe UnidadeDTO
    //*******************************************************************************
   //******** Usuario em relaçao ao Historico - relacionamnteo OneToMany - não altera*****
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioFk")
    private Collection<HistoricoDTO> historicoDTOCollection;
    //************************************************************************************

    /**
     * Construtor author castro
     *
     * @param nao se aplica
     * @return nao se aplica
     *
     */
    public UsuarioDTO() {
    }

    /**
     * Construtor author castro
     *
     * @param Integer idUsuario
     * @return nao se aplica
     *
     */
    public UsuarioDTO(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Construtor author castro
     *
     * @param Integer idUsuario, String login
     * @return nao se aplica
     *
     */
    //Codigo Inserido
    public UsuarioDTO(Integer idUsuario, String login) {
        this.idUsuario = idUsuario;
        this.login = login;
    }

    /**
     * Obtem Id do Usuario author castro
     *
     * @param nao se aplica
     * @return idUsuario
     *
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Setar Id do Usuario author castro
     *
     * @param Integer idUsuario
     * @return void
     *
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtem o Nome do Usuario author castro
     *
     * @param nao se aplica
     * @return nome
     *
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setar o nome do Usuario author castro
     *
     * @param String nome
     * @return void
     *
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtem a email do usuar9o author castro
     *
     * @param nao se aplica
     * @return email
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setar email do usuario author castro
     *
     * @param String email
     * @return void
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtem o nome do Predio do usuario author castro
     *
     * @param nao se aplica
     * @return predio
     *
     */
    public String getPredio() {
        return predio;
    }

    /**
     * Setar o nome do Predio do usuario author castro
     *
     * @param String predio
     * @return void
     *
     */
    public void setPredio(String predio) {
        this.predio = predio;
    }

    /**
     * Obtem o ramal do usuario author castro
     *
     * @param nao se aplica
     * @return ramal
     *
     */
    public Integer getRamal() {
        return ramal;
    }

    /**
     * Setar o ramal do usuario author castro
     *
     * @param Integer ramal
     * @return void
     *
     */
    public void setRamal(Integer ramal) {
        this.ramal = ramal;
    }

    /**
     * Obtem o login do usuario author castro
     *
     * @param nao se aplica
     * @return login
     *
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setar o login do usuario author castro
     *
     * @param String login
     * @return void
     *
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Obtem a senha do usuario author castro
     *
     * @param nao se aplica
     * @return senha
     *
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Setar a senha do usuario author castro
     *
     * @param String senha
     * @return void
     *
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

   
    
    

    /**
     * Obtem status do usuario author castro
     *
     * @param nao se aplica
     * @return status
     *
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Setar o status do usuario author castro
     *
     * @param Integer status
     * @return void
     *
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusImagem() {
        return statusImagem;
    }

    public void setStatusImagem(String statusImagem) {
        this.statusImagem = statusImagem;
    }

//************* Alterar Get e Set UnidadeFk *********************
    /**
     * Obtem a unidade do usuario, se nao existir, criar uma nova author castro
     *
     * @param nao se aplica
     * @return unidadeFk
     *
     */
    public UnidadeDTO getUnidadeFk() {
        if (unidadeFk == null) {
            unidadeFk = new UnidadeDTO();
        }
        return unidadeFk;
    }

    
    public void setUnidadeFk(UnidadeDTO unidadeFk) {
        this.unidadeFk = unidadeFk;
    }
//*******************************************************

   

    //**************************************************************
    @XmlTransient
    public Collection<HistoricoDTO> getHistoricoDTOCollection() {
        return historicoDTOCollection;
    }

    public void setHistoricoDTOCollection(Collection<HistoricoDTO> historicoDTOCollection) {
        this.historicoDTOCollection = historicoDTOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDTO)) {
            return false;
        }
        UsuarioDTO other = (UsuarioDTO) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.inpe.dto.UsuarioDTO[ idUsuario=" + idUsuario + " ]";
    }

    @XmlTransient
    public Collection<NoDTO> getNoDTOCollection() {
        return noDTOCollection;
    }

    public void setNoDTOCollection(Collection<NoDTO> noDTOCollection) {
        this.noDTOCollection = noDTOCollection;
    }

    @XmlTransient
    public Collection<HistoricoNoDTO> getHistoricoNoDTOCollection() {
        return historicoNoDTOCollection;
    }

    public void setHistoricoNoDTOCollection(Collection<HistoricoNoDTO> historicoNoDTOCollection) {
        this.historicoNoDTOCollection = historicoNoDTOCollection;
    }
}
