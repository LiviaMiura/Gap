/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inpe.backingbeans;

import br.inpe.dao.ArvoreDAO;
import br.inpe.dao.NoDAO;
import br.inpe.dto.ArvoreDTO;
import br.inpe.dto.NoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.w3c.dom.Document;

/**
 *
 * @author livia.miura
 */
public final class TreeAction implements Serializable {

    private NoDTO noDTO;
    private Document document;
    private ArvoreDTO arvoreDTO;
    private TreeNode root;
    private List<NoDTO> noLista = new ArrayList();
    private List<NoDTO> listaNo = new ArrayList();
    private List<ArvoreDTO> listaArvore = new ArrayList();
    private List<ArvoreDTO> arvoreLista = new ArrayList();
    //*************
    int linha = 2;
    int coluna = 0;
    int linha2 = 2;
    int coluna2 = 0;
    String[][] matrizNoArvore;
    int[][] matrizNoArvoreID;
    int colunaAux = 0;
    int colunaAux1 = 0;
    private TreeNode filho = null;
    private TreeNode filhoID = null;
    //******  
    int nivel = 0;
    private TreeNode selectedNode;

    public TreeAction() {


        montaMatriz();
        montaMatrizID();
        imprimeMatriz();
        imprimeMatrizID();
        System.out.println("\nMonta Tree ");

        montaTree();
        System.out.println("\nFim Monta Tree ");

    }

    public void montaTree() {
        String nomeRoot = matrizNoArvore[1][0];
        int nomeRootID = matrizNoArvoreID[1][0];
        this.root = new DefaultTreeNode(null, null, null);
        TreeNode pai = new DefaultTreeNode(nomeRoot, nomeRootID, this.root);
        // TreeNode pai = new DefaultTreeNode(nomeRoot, this.root);
        linha = 2;
        coluna = 0;
        linha2 = 2;
        coluna2 = 0;
        String dado = matrizNoArvore[linha][coluna];
        int id = matrizNoArvoreID[linha2][coluna2];

        while (dado != null && id != 0) {
            //  while (dado != null) {
            criaNo(dado, id, pai);
            temFilho(1, (coluna + 1), dado, id);

            //  criaNo(dado, pai);

            //  temFilho(1, (coluna + 1), dado);
            linha++;
            linha2++;
            dado = matrizNoArvore[linha][coluna];
            id = matrizNoArvoreID[linha][coluna];

        }
    }

    private void temFilho(int linha, int coluna, String dado, int id) {
        int novacoluna = coluna;
        for (coluna = novacoluna; coluna < matrizNoArvore.length; coluna++) {
            String novo = matrizNoArvore[linha][coluna];
            int novoID = matrizNoArvoreID[linha2][coluna2];
            if ((dado.equals(novo)) && (id == (novoID))) {
                //  if ((dado.equals(novo))) {
                linha++;
                String novoDado = matrizNoArvore[linha][coluna];
                int novoIDdoID = matrizNoArvoreID[linha2][coluna2];
                while (novoDado != null && novoIDdoID != 0) {
                    // while (novoDado != null) {


                    System.out.printf("   ");
                    int guardaLinha = linha;
                    int guardaColuna = coluna;
                    int guardaLinha2 = linha2;
                    int guardaColuna2 = coluna2;

                    TreeNode guardafilho = filho;
                    TreeNode guardaFilhoID = filhoID;

                    criaNo(novoDado, novoIDdoID, filho);
                    this.temFilho(1, coluna + 1, novoDado, novoIDdoID);  //cria recursivamente
                    // criaNo(novoDado, filho);
                    //  this.temFilho(1, coluna + 1, novoDado);  //cria recursivamente

                    filho = guardafilho;
                    filhoID = guardaFilhoID;
                    linha = guardaLinha;
                    coluna = guardaColuna;


                    linha++;

                    novoDado = matrizNoArvore[linha][coluna];
                    novoIDdoID = matrizNoArvoreID[linha2][coluna2];
                }                            //termine varredura de linhas                                         
            }
            linha = 1;
        }
        coluna = 0;
    }

    private void criaNo(String dado, int id, TreeNode pai) {
        filho = new DefaultTreeNode(dado, id, pai);
        System.out.println(" " + dado);
        System.out.println("  " + id);
    }

    private void montaMatriz() {
        NoDAO no1 = new NoDAO();
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        noLista = no1.listar();
        arvoreLista = arvoreDAO.listar();
        int offsetlinha = 3;
        int offsetColuna = 2;
        //declaração e uso de matriz (preenche a matriz)
        matrizNoArvore = new String[arvoreLista.size() + offsetlinha][noLista.size() + offsetColuna];
        for (NoDTO no : noLista) {
            //  matrizNoArvore[0][colunaAux] = no.getNivel();
            matrizNoArvore[1][colunaAux] = no.getTitulo();

            colunaAux++;
            //varredura de linhas
            for (ArvoreDTO arvore : arvoreLista) {
                if (arvore.getNoPai() == no.getIdNo()) {
                    matrizNoArvore[linha][coluna] = arvore.getNoFilho().getTitulo();
                    linha++;
                }
            }
            coluna++;
            linha = 2;
        }
        System.out.println("ARVORE:" + (arvoreLista.size() + offsetlinha) + "  NO:" + (noLista.size() + offsetColuna));

    }

    private void imprimeMatriz() {

        String aux;
        System.out.println("******IMPRESSÃO DA MATRIZ  ******************");
        for (int linha1 = 0; linha1 < (matrizNoArvore.length); linha1++) {
            for (int coluna1 = 0; coluna1 < (matrizNoArvore.length); coluna1++) {
                aux = matrizNoArvore[linha1][coluna1];
                if (aux != null) {
                    System.out.printf("\t" + aux);
                } else {
                    System.out.printf("    ");
                }
            }
            System.out.println(" ");
        }
    }

    private void montaMatrizID() {

        int linha3 = 2;
        int coluna3 = 0;

        NoDAO no1 = new NoDAO();
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        noLista = no1.listar();
        arvoreLista = arvoreDAO.listar();
        int offsetlinha = 3;
        int offsetColuna = 2;
        //declaração e uso de matriz (preenche a matriz)
        matrizNoArvoreID = new int[arvoreLista.size() + offsetlinha][noLista.size() + offsetColuna];
        for (NoDTO no : noLista) {
            //    matrizNoArvoreID[0][colunaAux] = no.getNivel();
            matrizNoArvoreID[1][colunaAux1] = no.getIdNo();

            colunaAux1++;
            //varredura de linhas
            for (ArvoreDTO arvore : arvoreLista) {
                if (arvore.getNoPai() == no.getIdNo()) {
                    matrizNoArvoreID[linha2][coluna2] = arvore.getNoFilho().getIdNo();
                    linha2++;
                }
            }
            coluna2++;
            linha2 = 2;
        }
        System.out.println("ARVORE:" + (arvoreLista.size() + offsetlinha) + "  NO:" + (noLista.size() + offsetColuna));

    }

    private void imprimeMatrizID() {

        int aux;
        System.out.println("######################IMPRESSÃO DA MATRIZ de ID  ########################");
        for (int linha2 = 0; linha2 < (matrizNoArvoreID.length); linha2++) {
            for (int coluna2 = 0; coluna2 < (matrizNoArvoreID.length); coluna2++) {
                aux = matrizNoArvoreID[linha2][coluna2];
                if (aux != 0) {
                    System.out.printf("\t" + aux);
                } else {
                    System.out.printf("    ");
                }
            }
            System.out.println(" ");
        }
    }

    //  1- declaração do getRoot
    public TreeNode getRoot() {

        return this.root;
    }

    public NoDTO getNoDTO() {
        if (noDTO == null) {
            noDTO = new NoDTO();
        }
        return noDTO;
    }

    public void setNoDTO(NoDTO noDTO) {
        this.noDTO = noDTO;
    }

    //************** declaração do get e set**Arvore****************************
    public ArvoreDTO getArvoreDTO() {
        if (arvoreDTO == null) {
            arvoreDTO = new ArvoreDTO();
        }
        return arvoreDTO;
    }

    public void setArvoreDTO(ArvoreDTO arvoreDTO) {
        this.arvoreDTO = arvoreDTO;
    }
    //3- listar o for listaNo

    public List<NoDTO> getLista() {
        listar();
        return listaNo;
    }

    public void setLista(List<NoDTO> lista) {
        this.listaNo = lista;
    }
    //4- metodo listar

    public String listar() {
        NoDAO noDAO = new NoDAO();
        listaNo = noDAO.listar();
        return "listar";
    }
    //**************************Arvore*****************************************

    public List<ArvoreDTO> getListaArvore() {
        listarArvore();
        return listaArvore;
    }

    public void setListaArvore(List<ArvoreDTO> lista) {
        this.listaArvore = lista;
    }
    //4- metodo listar

    public String listarArvore() {
        ArvoreDAO arvoreDAO = new ArvoreDAO();
        listaNo = arvoreDAO.listar();
        return "listar";
    }
    //*****************************fim arvore***********************************

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void deleteNode() {
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);
        selectedNode = null;

    }

    public void deletar() {
    }

    public void doSth() {
        Object data = selectedNode.getData();
        System.out.println("***********NODE************" + selectedNode.getData());
        System.out.println("DATA///////////" + data);

        // String a = data.toString();// converter um Object to String
        // System.out.println("a==" + a);
    }

    public String alterar() {

        NoDAO noDAO = new NoDAO();
        ArvoreDAO arvoreDAO = new ArvoreDAO();

        Object data = selectedNode.getData();
        String a = data.toString();// converter um Object to String
        System.out.println("a  =" + a);
        getNoDTO().getNoFk().setTitulo(a);
        return "inserir";
    }

    public String inserir2() {
        System.out.println("******************entrei no inserir* da treee****************");
        getNoDTO().setIdNo(0);
        getArvoreDTO().setIdArvore(0);
        getNoDTO().setTipoDominio("Interno");
        Object data = selectedNode.getData();
        System.out.println("selectNode =   " + selectedNode.getData());
        // String a = data.toString();// converter um Object to String
        //System.out.println("a  =" + a);
        //getNoDTO().getNoFk().setTitulo(a);

        // System.out.println(" o valor de titulo = " + getNoDTO().getNoFk().getTitulo());
        System.out.println("to saindo do inserir");

        return "inserir";
    }
}
