package mcp;

import java.util.ArrayList;

public class TestExport {
    public static void main(String[] args){
        ArrayList<MCP> mcps = new ArrayList<MCP>();
        int nbMCP = 5;
        int nbCores = 10;
        int nbTasks = 3;
        for(int i = 0; i < nbMCP; i++){
            MCP mcp = new MCP(i);
            for(int j = 0; j < nbCores; j++){
                Core core = new Core(j, 12);
                mcp.addCore(core);
                for(int k = 0; k < nbTasks; k++){
                    Task task = new Task(k, 1000 * (k +1) * (j +1), (long) 2000*(k + 1) *(j +1), (long) 3000*(k + 1) * (j + 1));
                    core.addTask(task);
                }
            }
            mcps.add(mcp);
        }

        XMLExport xml = new XMLExport(mcps);
        xml.exportTasksToXML("result.xml");
    }
}
