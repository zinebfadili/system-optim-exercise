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
    public static ArrayList<Task> createTasksFromXml(String pathToXml){
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            File fXmlFile = new File(pathToXml);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList tasksNodes = doc.getElementsByTagName("task");

            for (int i = 0; i < tasksNodes.getLength(); i++){
                Node task = tasksNodes.item(i);
                Element e = (Element) task;
                Task tsk = new Task(Integer.parseInt(e.getAttribute("Id")),
                                    Integer.parseInt(e.getAttribute("Deadline")),
                                    Integer.parseInt(e.getAttribute("Period")),
                                    Integer.parseInt(e.getAttribute("WCET")));
                tasks.add(tsk);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return tasks;
    }


}
