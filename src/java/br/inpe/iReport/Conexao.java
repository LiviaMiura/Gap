/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.iReport;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class Conexao {
    
    /*

    public static final int RELATORIO_PDF = 1;
    public static final int RELATORIO_EXCEL = 2;
    public static final int RELATORIO_HTML = 3;
    public static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public Conexao() {
        System.out.println("******************************entrei em Conexão************************************");
        emf = Persistence.createEntityManagerFactory("conexaoBancoDados");
        em = emf.createEntityManager();
    }

    public StreamedContent geraRelatorio(HashMap parametrosRelatorio, String nomeRelatorioJasper,
            String nomeRelatorioSaida, int tipoRelatorio) throws UtilException {
        StreamedContent arquivoRetorno = null;

        System.out.println("entrei em gera relaltodjflkdjflksdjfklsdjfklsjdkljfksljflskjf");
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Connection conexao = this.getConexao();
            String caminhoRelatorio = context.getExternalContext().getRealPath("relatorios");
            System.out.println("caminho relatorio = "+ caminhoRelatorio);
            String caminhoArquivoJasper = caminhoRelatorio + File.separator + nomeRelatorioJasper + ".jasper";
            System.out.println("nome relatorioJapasper==="+ nomeRelatorioJasper);
            System.out.println("caminho arquivo jasper" + caminhoArquivoJasper);
            String caminhoArquivoRelatorio = null;

            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(caminhoArquivoJasper);
            JasperPrint impressoraJasper;
            System.out.println("/* relatrio jasper"+ relatorioJasper);
            impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, conexao);
            JRExporter tipoArquivoExportado = null;
            String extensaoArquivoExportado = "";
            File arquivoGerado = null;

            switch (tipoRelatorio) {
                case Conexao.RELATORIO_PDF:
                    tipoArquivoExportado = new JRPdfExporter();
                    extensaoArquivoExportado = "pdf";
                    break;
                case Conexao.RELATORIO_HTML:
                    tipoArquivoExportado = new JRHtmlExporter();
                    extensaoArquivoExportado = "html";
                    break;
                case Conexao.RELATORIO_EXCEL:
                    tipoArquivoExportado = new JRXlsExporter();
                    extensaoArquivoExportado = "xls";
                    break;
                case Conexao.RELATORIO_PLANILHA_OPEN_OFFICE:
                    tipoArquivoExportado = new JROdtExporter();
                    extensaoArquivoExportado = "ods";
                    break;
                default:
                    tipoArquivoExportado = new JRPdfExporter();
                    extensaoArquivoExportado = "pdf";
                    break;
            }
            caminhoArquivoRelatorio = caminhoRelatorio + File.separator + nomeRelatorioSaida + "." + extensaoArquivoExportado;
            System.out.println("/*caminho relatprio===" + caminhoArquivoRelatorio);
            System.out.println("nomeRelatorioSaida====" + nomeRelatorioSaida);

            arquivoGerado = new java.io.File(caminhoArquivoRelatorio);
            tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
            tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
            tipoArquivoExportado.exportReport();
            arquivoGerado.deleteOnExit();

            InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
            arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "application/"
                    + extensaoArquivoExportado, nomeRelatorioSaida + "." + extensaoArquivoExportado);
        } catch (JRException e) {
            throw new UtilException("/*Não foi possível gerar o relatório.", e);
        } catch (FileNotFoundException e) {
            throw new UtilException("Arquivo do relatório não encontrado.", e);
        }
        return arquivoRetorno;
    }

    private Connection getConexao() throws UtilException {
        //   java.sql.Connection conexao = null;
        java.sql.Connection con = Conexao.getConnection();

        try {

            con = (com.mysql.jdbc.Connection) ((EntityManagerImpl) (getEm().getDelegate())).getServerSession().getAccessor().getConnection();

        } catch (Exception e) {
            throw new UtilException("Não foi possível encontrar o nome da conexão do banco.", e);

        }
        return con;
    }

    public static com.mysql.jdbc.Connection getConnection() {

        com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ((EntityManagerImpl) (getEm().getDelegate())).getServerSession().getAccessor().getConnection();
        return conn;
    }

    public static EntityManager getEm() {
        if (em == null) {
            emf = Persistence.createEntityManagerFactory("conexaoBancoDados");
            em = emf.createEntityManager();
        }
        return em;
    }*/
}
