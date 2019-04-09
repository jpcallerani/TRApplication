package application.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import application.controller.Objeto;

public class scriptRead {
	
	//cria uma lista de objetos referentes ao do xml lido
	public static List<Objeto> lerXML(File file)  {
		Objeto objeto1 = new Objeto();
		List<Objeto> v_list_usuario = new ArrayList<Objeto>();
	
		try {
			//processo para ler
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			// obtem os nodes para le-los, versao para obter o id e nodes para os objetos
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList versao = doc.getElementsByTagName("Versao");
			NodeList nodes = doc.getElementsByTagName("Objeto");
			System.out.println("==========================");

			System.out.println("id: " + getValue("id", (Element) versao.item(0)));
			System.out.println();
			

			
			// loop para ler todos os objetos
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					objeto1 = new Objeto();

                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();
                    String codSistema = element.getElementsByTagName("codSistema").item(0).getTextContent();
                    String tipo = element.getElementsByTagName("tipo").item(0).getTextContent();
                    String erro = element.getElementsByTagName("erro").item(0).getTextContent();
                    String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                    
     
                    ///
                    /*as tags do xml estao todas como BG, a funçao tagCerta corrige-as com base
                     * no codSistema delas
                     */
					String id = tagCerta(codSistema, versao);
					objeto1.setId(id);

                    //seta o valor do nome, codsistema,erro e codigo no objeto1 que em seguida
					//é adicionado na lista
                    objeto1.setNome(nome);
                    objeto1.setCodSistema(codSistema);
                    objeto1.setTipo(tipo);
                    objeto1.setErro(erro);
                    objeto1.setCodigo(codigo);
                    
                    v_list_usuario.add(objeto1);	
					
					

				}
			}
			
			
		}catch (Exception e) {
            System.out.println("!!!!!!!!  Exception while reading xml file :" + e.getMessage());
        }
		return v_list_usuario;
		
	}

	// funcao para obter o valor dos nodes
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();

	}
	
	//funcao para obter a tag certa
	private static String tagCerta(String cod, NodeList version) {
		String id2;
		String id1;
	
		id2 = new String();
		id1 = new String();		

		if(cod.equals("0"))
		{
			id2 = "tag_"+ getValue("id", (Element) version.item(0));
		}
		if(cod.equals("2"))
		{
			id1 = getValue("id", (Element) version.item(0));
		 	id2 = "tag_" + id1.replace("BG","ES");

		}		
		if(cod.equals("3"))
		{

			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG","DB");
		}
		if(cod.equals("9"))
		{
			 id1 = getValue("id", (Element) version.item(0));
			 id2 = "tag_" + id1.replace("BG","IS");
		}
		if(cod.equals("10"))
		{
			 id1 = getValue("id", (Element) version.item(0));
			 id2 = "tag_" + id1.replace("BG","CI");
		}
		if(cod.equals("11"))
		{
			 id1 = getValue("id", (Element) version.item(0));
			 id2 = "tag_" + id1.replace("BG","CE");
		}
		if(cod.equals("21"))
		{
			 id1 = getValue("id", (Element) version.item(0));
			 id2 = "tag_" + id1.replace("BG","BS");
		}
		if(cod.equals("500"))
		{
			 id1 = getValue("id", (Element) version.item(0));
			 id2 = "tag_" + id1.replace("BG","IO");
		}

		
		return id2;
	}
	
	
	
	
}
