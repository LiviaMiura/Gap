
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.dao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Implementa os métodos para manipulação da tabela Database no banco de dados.
 *
 * @author livia.miura
 *
 */
public class Database {

    /**
     * Implementa as funcionalidades de conexao com o banco de dados.
     *
     * @author livia.miura
     *
     */
    public static EntityManagerFactory factory;

    static {
        try {
            if (Persistence.createEntityManagerFactory("conexaoBancoDados") == null) {

                System.out.println("não foi possível conectar ao banco NO FACTORY");

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                        "não foi possível conectar ao banco NO FACTORY********************************************** ",
                        ""));

            } else {
                System.out.println(" :D   to funfando ***********");

                factory = Persistence.createEntityManagerFactory("conexaoBancoDados");

                
            }
        } catch (Exception ex) {
        }

    }
    public static EntityManager manager;
    static {
        try {
            System.out.println("conecttei no manager******************************");
            manager = factory.createEntityManager();

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                    "Banco de Dados loko*** 3******************************************* ",
                    ""));
        }

    }
}