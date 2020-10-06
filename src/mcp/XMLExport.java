package mcp;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLExport {

    private ArrayList<MCP> mcps;

    public XMLExport(){
        this.mcps = new ArrayList<MCP>();
    }

    public XMLExport(ArrayList<MCP> mcps){
        this.mcps = mcps;
    }

    public void addMCP(MCP mcp){
        this.mcps.add(mcp);
    }

    /**
     * Method
     * @param path
     */
    public void exportTasksToXML(String path){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("Solution");
            document.appendChild(root);

            // we loop through the different mcps
            for(MCP mcp : this.mcps){
                int mcpId = mcp.getId();
                // we loop through the different cores of the mcp
                for (Core core : mcp.getCores()){
                    int coreId = core.getId();
                    // we finally loop over the tasks of the core
                    for(Task task : core.getTasks()){
                        int taskId = task.getId();
                        int WCRT = core.getWCRT(taskId);

                        // creating the task node
                        Element taskNode = document.createElement("Task");
                        root.appendChild(taskNode);

                        // creating the different attributes
                        Attr xmlTaskId = document.createAttribute("id");
                        xmlTaskId.setValue(Integer.toString(taskId));

                        Attr xmlMCPId = document.createAttribute("MCP");
                        xmlMCPId.setValue(Integer.toString(mcpId));

                        Attr xmlCoreId = document.createAttribute("Core");
                        xmlCoreId.setValue(Integer.toString(coreId));

                        Attr xmlWCRT = document.createAttribute("WCRT");
                        xmlWCRT.setValue(Integer.toString(WCRT));

                        // adding the attributes to the node
                        taskNode.setAttributeNode(xmlTaskId);
                        taskNode.setAttributeNode(xmlMCPId);
                        taskNode.setAttributeNode(xmlCoreId);
                        taskNode.setAttributeNode(xmlWCRT);

                    }
                }
            }

            int totalLaxity = 0;
            for(MCP m : this.mcps) totalLaxity += m.getLaxity();

            Element element = document.getDocumentElement();
            Comment comment = document.createComment("Total Laxity: " + totalLaxity);
            document.appendChild(comment);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
