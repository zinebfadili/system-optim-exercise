package mcp;

import java.util.ArrayList;

public class TestParser {
    /**
     * Method which tests the xml parser by running it on the small example, and by printing the
     * objects that were created
     * @param argv
     */
    public static void main(String argv[]){
        String path = "small.xml";
        ArrayList<Task> tasks = Parser.createTasksFromXml(path);
        for(Task task : tasks){
            System.out.println(task.toString());
        }

        ArrayList<MCP> mcps = Parser.createMCPsFromXml(path);
        for(MCP mcp : mcps){
            System.out.println(mcp);
        }
    }
}
