import java.io.*;
import java.util.*;

public class Parser {

    ArrayList fileList = new ArrayList();

    //CLASS HANDLER
    public void addClass(ArrayList list){

        //Add class to Tree
        String[] classParts = list.get(0).toString().split(" ");
        Tree<String> newClass = new Tree<>(classParts[1]);
	//NEED TO FIX TREE CLASS, MODEL CANNOT BE RESOLVED
        newClass.setParent(model);
        fileList.remove(0);

        //Parses the block until the next semicolon
        String firstLine = list.get(0).toString();
        while(!(firstLine.startsWith(";"))){

            //ATTRIBUTES
            if(list.get(0).toString().startsWith("ATTRIBUTES")){
                fileList.remove(0);

                ArrayList attributes = new ArrayList();
                while(!(fileList.get(0).toString().startsWith("OPERATIONS"))){
                    attributes.add(convertAttributes(fileList));
                    fileList.remove(0);
                }
                newClass.addChildren(attributes);
            }

            //OPERATIONS
            if(list.get(0).toString().startsWith("OPERATIONS")){
                fileList.remove(0);

                ArrayList operations = new ArrayList();
                while(!(fileList.get(0).toString().startsWith(";"))){
                    operations.add(convertOperations(fileList));
                    fileList.remove(0);
                }
                newClass.addChildren(operations);
            }
        }
    }

    public String convertAttributes(ArrayList file){
        String[] attrParts = file.get(0).toString().split(" : ");
        return (attrParts[0]+" "+ attrParts[1]);
    }

    public String convertOperations(ArrayList file){
        String[] opParts = file.get(0).toString().split(" : ");
        if (opParts[1].endsWith(",")){
            opParts[1].substring(0,(opParts[1].length()-1));
        }
        return (opParts[0]+" "+ opParts[1]);
    }


    //GENERALIZATION HANDLER
    public void addGeneralization(ArrayList file) {
        ArrayList generalization = new ArrayList();

    }

    //RELATION HANDLER
    public void addRelation(ArrayList file) {

    }

    //AGGREGATION
    public void addAggregation(ArrayList file) {

    }


    // Parser
    public Tree<String> parseFile(String file) throws Exception{

        //Reading the ucd file
        FileReader ucdFile = new FileReader("C:\\Users\\Mathieu\\Desktop\\test.ucd");
        BufferedReader br = new BufferedReader(ucdFile);


        //Initializing the model parsing into Tree
        String modelStr = br.readLine();
        String[] modelParts = modelStr.split(" ");
        Tree<String> model = new Tree<>(modelParts[1]);


        //Scanning the file into ArrayList
        String i;
        while((i = br.readLine()) != null){
            fileList.add(i);
        }


        //Run the ArrayList
        while(fileList.size()>0){

            //if next block is CLASS
            if(fileList.get(0).toString().startsWith("CLASS")){
                addClass(fileList);
            }

            //if next block is GENERALIZATION
            if(fileList.get(0).toString().startsWith("GENERALIZATION")){
                addGeneralization(fileList);
            }
            
            //if next block is RELATION
            if(fileList.get(0).toString().startsWith("RELATION")){
                addRelation(fileList);
            }

            //if next block is AGGREGATION
            if(fileList.get(0).toString().startsWith("AGGREGATION")){
                addAggregation(fileList);
            }
        }



        return model;
    }

}
