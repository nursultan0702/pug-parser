import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static final List<String> PARENT_STACK = new LinkedList<>();
    public Boolean parsePug(String pathString){
        //TODO change path variable to dynamic
        Path path = Paths.get("/Users/nursultansadyk/Projects/pugfile.pug");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            StringBuffer stringBuffer = new StringBuffer();
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                stringBuffer.append(parseLine(currentLine));
            }
            FileActions.write(stringBuffer.toString(),"/Users/nursultansadyk/Projects/test.html");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    private StringBuffer parseLine(String line){
        StringBuffer html = new StringBuffer();
        StringBuffer tag = new StringBuffer();
        String pureTag = "";
        if(line.equals("doctype html"))
           return new StringBuffer("<!DOCTYPE html>");

        char[] lineChars = line.toCharArray();
        boolean isTagStarted = false;
        boolean isTagEnded = false;
        boolean isOpenBracket = false;
        for (char lineChar : lineChars) {
            if(lineChar=='(')
                isOpenBracket = true;

            if(lineChar==')')
                isOpenBracket = false;

            if(Character.isWhitespace(lineChar) && isTagStarted && !isOpenBracket){
                // if white space meets after started getting tag's chars
                    isTagStarted = false;
                    isTagEnded = true;
            } else if (!Character.isWhitespace(lineChar) && !isTagEnded) {
               // we are getting tag's chars
                    tag.append(lineChar);
                isTagStarted = true;
            }
        }
        //if has class set className
        StringBuffer classNames = new StringBuffer();
        String[] temp = tag.toString().split("\\.");
        if(temp.length>1) {
            if(pureTag.equals(""))
                pureTag = temp[0];
            for (int i = 1; i < temp.length; i++) {
                classNames.append("class=\"");
                classNames.append(temp[i].split("\\(")[0]);
                classNames.append('"');
                classNames.append(' ');
            }
        }

        //if has id filed
        StringBuffer idFiled = new StringBuffer();
        temp = tag.toString().split("#");
        if(temp.length>1) {
            if(pureTag.equals(""))
                pureTag = temp[0];
            idFiled.append(temp[1]);
        }

        //if has property
        StringBuffer property = new StringBuffer();
        temp = tag.toString().split("\\(");
        if(temp.length>1) {

            if(pureTag.equals(""))
                pureTag = temp[0];

            char[] propertyChars = temp[1].toCharArray();
            boolean isValue = false;
            boolean isProperty = true;

            for (char propertyChar : propertyChars) {
                if(propertyChar=='\'') {
                    propertyChar = '^';
                    property.append('"');
                    property.append(' ');
                }
               if(propertyChar=='=') {
                   isProperty = false;
                   isValue = true;
                   property.append('=');
               } else if(propertyChar==' '){
                   isProperty = true;
                   isValue = false;
               }else if(propertyChar==')'){
                   break;
               }

               if(isProperty && propertyChar != '^')
                   property.append(propertyChar);
               if(isValue && propertyChar!='=' && propertyChar != '^'){
                   property.append(propertyChar);
               }

            }
        }
        //TODO get data after tag and check attributes
    if(pureTag.equals("")){
        html.append("<").append(tag).append(">").append(data).append("</").append(tag).append(">");
    }else if(!SelfClosingTags.isSelfClosingTag(tag.toString())){
        html.append("<").append(pureTag).append(" ").append(classNames).append(" ").append(idFiled).append(" ").append(property).append(" >");
    }else{
        html.append("<").append(pureTag).append("/>");
    }
        return html;
    }


}
