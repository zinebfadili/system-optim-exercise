package mcp;

import java.util.ArrayList;

public class TestParser {
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
