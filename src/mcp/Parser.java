package mcp;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Parser {
    public static ArrayList<ConcreteTask> createTasksFromXml(String pathToXml){
        // the list of tasks that are described in the xml file
        ArrayList<ConcreteTask> tasks = new ArrayList<ConcreteTask>();
        try {
            // we open the xml file with the data
            File fXmlFile = new File(pathToXml);

            // we create the objects needed for the parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            // we get all the Task nodes
            NodeList tasksNodes = doc.getElementsByTagName("Task");

            // we loop through the tasks
            for (int i = 0; i < tasksNodes.getLength(); i++){
                Node task = tasksNodes.item(i);
                Element e = (Element) task;
                // we create the task objects using the values inside the xml
                ConcreteTask tsk = new ConcreteTask(Integer.parseInt(e.getAttribute("Id")),
                                    Integer.parseInt(e.getAttribute("WCET")),
                                    Long.parseLong(e.getAttribute("Deadline")),
                                    Long.parseLong(e.getAttribute("Period"))
                                    );
                // we add the task to the list of tasks
                tasks.add(tsk);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public static ArrayList<MCP> createMCPsFromXml(String pathToXml){
        // the final array where all the mcps object will be stored
        ArrayList<MCP> mcps = new ArrayList<MCP>();

        try{
            // we open the xml file
            File fXmlFile = new File(pathToXml);

            // we create the objects needed for the parsing
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            // we get all the nodes that represent an MCP
            NodeList mcpNodes = doc.getElementsByTagName("MCP");

            // we loop through the different MCPs
            for (int i = 0; i < mcpNodes.getLength(); i++){
                // we get the current mcp
                Node mcp = mcpNodes.item(i);

                // we get all the children from the current mcp node
                NodeList coresNodes = mcp.getChildNodes();

                // array to store the different cores for the current node
                ArrayList<Core> cores = new ArrayList<Core>();

                // we loop through the cores of the mcp
                for (int j = 0; j < coresNodes.getLength(); j++){
                    Node core = coresNodes.item(j);
                    if (core.getNodeType() == Node.ELEMENT_NODE){
                        // we construct the node object from the data inside the xml
                        Element e = (Element) core;
                        Core c = new Core(Integer.parseInt(e.getAttribute("Id")),
                                Double.parseDouble(e.getAttribute("WCETFactor")));
                        cores.add(c);
                    }

                }
                // we build the mcp object based on the data of the xml and the different nodes we created
                Element e = (Element) mcp;
                MCP mcp_res = new MCP(Integer.parseInt(e.getAttribute("Id")),
                                      cores);
                // we add the mcp to the result array
                mcps.add(mcp_res);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // we return the result
        return mcps;
    }


}
