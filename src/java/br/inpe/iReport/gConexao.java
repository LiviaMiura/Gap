/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.iReport;

import com.mysql.jdbc.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

/**
 *
 * @author livia.miura
 */
public class gConexao {


    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public gConexao(){
           System.out.println("*******12134444***********************entrei em Conexão************************************");
     
        emf = Persistence.createEntityManagerFactory("conexaoBancoDados");
        em = emf.createEntityManager();
    }

    public static Connection getConnection(){

Connection conn = (Connection) ((EntityManagerImpl)(getEm().getDelegate())).getServerSession().getAccessor().getConnection();

return conn;

    }

    public static EntityManager getEm(){
        System.out.println("/*entrei no EntityManager getEm() no gConexao linha 39");
        if (em == null){
            emf = Persistence.createEntityManagerFactory("conexaoBancoDados");
            em = emf.createEntityManager();
        }
        return em;
    }
}
