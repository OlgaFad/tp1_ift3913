import java.io.*;
import java.util.*;

public class Parser {

    private ArrayList fileList = new ArrayList();
    Tree<String> model;

    //CLASS HANDLER
    private void addClass(){

        //Add class to Tree
        String[] classParts = fileList.get(0).toString().split(" ");
        Tree newClass = new Tree(classParts[1]);
        model.addChild(newClass);
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

        for(int i = 1; i<subclasses.length; i++){
            if(i<(subclasses.length-1)){
                generalization.add(subclasses[i].substring(0,(subclasses[i].length()-1)));
            } else{
                generalization.add(subclasses[i]);
            }
        }
        fileList.remove(0);
        model.findClass(generalizationParts[1]).addChildren(generalization);
    }

    //RELATION HANDLER
    private void addRelation() {
        String relationParts[] = fileList.get(0).toString().split(" ");
        String details = fileList.get(0).toString()+"\n";
        String relationStr = "(R) "+relationParts[1];
        ArrayList<String> classes = new ArrayList();
        fileList.remove(0);

        while(!(fileList.get(0).toString().startsWith(";"))){
            details.concat(fileList.get(0).toString()+"\n");
            if(fileList.get(0).toString().startsWith("CLASS")){
                String roleParts[] = fileList.get(0).toString().split(" ");
                classes.add(roleParts[1]);
            }

            fileList.remove(0);
        }
        details.concat(";");

        for(int i = 0; i<classes.size(); i++){
            ArrayList relations = new ArrayList();
            relations.add(relationStr);
            relations.add(details);
            model.findClass(classes.get(i)).addChildren(relations);
        }
        fileList.remove(0);
    }

    //AGGREGATION
    private void addAggregation() {
        String details = fileList.get(0).toString()+"\n";
        String aggregationStr = "(A) ";
        fileList.remove(0);
        String[] aggrParts = {};

        while((!(fileList.get(0).toString().startsWith("PARTS")))){
            if(fileList.get(0).toString().startsWith(";")) break;
            if(fileList.get(0).toString().startsWith("CLASS")) {
                aggrParts = fileList.get(0).toString().split(" ");
            }
            details.concat(fileList.get(0).toString()+"\n");
            fileList.remove(0);
        }
        details.concat(fileList.get(0).toString()+"\n");
        fileList.remove(0);

        ArrayList AggrToAdd = new ArrayList();
        while(!(fileList.get(0).toString().startsWith(";"))){
            details.concat(fileList.get(0).toString()+"\n");
            String aggrClasses[] = fileList.get(0).toString().split(" ");
            AggrToAdd.add(aggrClasses[1]);
            fileList.remove(0);
        }
        details.concat(";");

        for(int i = 0; i<AggrToAdd.size(); i++){
            ArrayList toAdd = new ArrayList();
            toAdd.add(AggrToAdd.get(i).toString());
            toAdd.add(details);
            model.findClass(aggrParts[1]).addChildren(toAdd);
        }
        fileList.remove(0);
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
                addClass();
            }

            //if next block is GENERALIZATION
            else if(fileList.get(0).toString().startsWith("GENERALIZATION")){
                addGeneralization();
            }

            //if next block is RELATION
            else if(fileList.get(0).toString().startsWith("RELATION")){
                addRelation();
            }

            //if next block is AGGREGATION
            else if(fileList.get(0).toString().startsWith("AGGREGATION")){
                addAggregation();
            }

            //if next block is semi colon
            else if(fileList.get(0).toString().startsWith(";")){
                fileList.remove(0);
            }

            else{
                fileList.remove(0);
            }
        }



        return model;
    }

}
