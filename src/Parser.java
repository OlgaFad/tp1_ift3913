import java.io.*;
import java.util.*;


public class Parser {

    private ArrayList fileList = new ArrayList();
    Tree<String> model;
    
    //CLASS HANDLER
    private void addClass(){

        //Add class to Tree
        String[] classParts = fileList.get(0).toString().split(" ");
        Tree<String> newClass = new Tree<String>(classParts[1]);
        newClass.setParent(model);
        fileList.remove(0);

        //Parses the block until the next semicolon
        String firstLine = fileList.get(0).toString();
        while(!(firstLine.startsWith(";"))){

            //ATTRIBUTES
            if(fileList.get(0).toString().startsWith("ATTRIBUTES")){
                fileList.remove(0);

                ArrayList attributes = new ArrayList();
                while(!(fileList.get(0).toString().startsWith("OPERATIONS"))){
                    attributes.add(convertAttributes());
                    fileList.remove(0);
                }
                newClass.addChildren(attributes);
            }

            //OPERATIONS
            if(fileList.get(0).toString().startsWith("OPERATIONS")){
                fileList.remove(0);

                ArrayList operations = new ArrayList();
                while(!(fileList.get(0).toString().startsWith(";"))){
                    operations.add(convertOperations());
                    fileList.remove(0);
                }
                newClass.addChildren(operations);
            }
        }
    }

    private String convertAttributes(){
        String[] attrParts = fileList.get(0).toString().split(" : ");
        return (attrParts[0]+" "+ attrParts[1]);
    }

    private String convertOperations(){
        String[] opParts = fileList.get(0).toString().split(" : ");
        if (opParts[1].endsWith(",")){
            opParts[1].substring(0,(opParts[1].length()-1));
        }
        return (opParts[0]+" "+ opParts[1]);
    }


    //GENERALIZATION HANDLER
    private void addGeneralization() {
        ArrayList generalization = new ArrayList();
        String generalizationParts[] = fileList.get(0).toString().split(" ");
        fileList.remove(0);
        String subclasses[] = fileList.get(0).toString().split(" ");

    }

    //RELATION HANDLER
    private void addRelation() {

    }

    //AGGREGATION
    private void addAggregation() {

    }


    // Parser
    public Tree<String> parseFile(String file) throws Exception{

        //Reading the ucd file
        FileReader ucdFile = new FileReader("C:\\Users\\Mathieu\\Desktop\\test.ucd");
        BufferedReader br = new BufferedReader(ucdFile);


        //Initializing the model parsing into Tree
        String modelStr = br.readLine();
        String[] modelParts = modelStr.split(" ");
        model = new Tree<String>(modelParts[1]);


        //Scanning the file into ArrayList
        String i;
        while((i = br.readLine()) != null){
            fileList.add(i);
        }


        //Run the ArrayList
        while(fileList.size()>0){

            //if next block is CLASS
            if(fileList.get(0).toString().startsWith("CLASS")){
                addClass();
            }

            //if next block is GENERALIZATION
            if(fileList.get(0).toString().startsWith("GENERALIZATION")){
                addGeneralization();
            }

            //if next block is RELATION
            if(fileList.get(0).toString().startsWith("RELATION")){
                addRelation();
            }

            //if next block is AGGREGATION
            if(fileList.get(0).toString().startsWith("AGGREGATION")){
                addAggregation();
            }

            //if next block is semi colon
            if(fileList.get(0).toString().startsWith(";")){
                fileList.remove(0);
            }
        }



        return model;
    }

}
