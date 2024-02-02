
/**
 * 在这里给出对类 Part2 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import edu.duke.*;
/**
 * 在这里给出对类 Part1 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Part2 {
    public void findAbc(String input){
       int index = input.indexOf("abc");
       while (true){
           if (index == -1 || index >= input.length() - 3){
               break;
           }
           String found = input.substring(index+1, index+4);
           System.out.println(found);
           System.out.println("index " + index);
           index = input.indexOf("abc",index+3);
           System.out.println("index after updating " + index);
       }
   }

   public void test(){
       findAbc("abcabcabcabca");
       //findAbc("abcdkfjsksioehgjfhsdjfhksdfhuwabcabcajfieowj");
   }

   
   float cgRatio(String dna){
 
        int index=0;
        int count=0;
        while(index<dna.length()){
           String found = dna.substring(index,index+1);
           //System.out.println(found);
            if(found.equals("C") || found.equals("G")){
               count=count+1; 
            }
            index = index+1; 
        }
        return (float)index/count;
    }
    
    int countCTG(String input){
        int index=input.indexOf("CTG");
        int count=0;
            while (true){
           if (index == -1 || index >= input.length() - 3){
               break;
           }
           //String found = input.substring(index+1, index+4);
           count=count+1;
           index = input.indexOf("CTG",index+3);
           
       }
       return count;
    }
    
    void processGenes(StorageResource sr){
        int count9=0;
        int countCG=0;
        String longestGene="";
        for(String gene: sr.data()){
            System.out.println("-------------------");
            if(gene.length()>9){
                count9+=1;
                System.out.println("\n>9 length:"+gene);
            } 
            if(cgRatio(gene)>0.35){
                countCG+=1;
                System.out.println("\n>0.35 ratio:"+gene);
            }
            if(gene.length()>longestGene.length()){
                longestGene=gene;
            }
            System.out.println("-------------------");
        }
        System.out.println(">9 length count:"+count9);
        System.out.println(">0.35 ratio:"+countCG);
        System.out.println("the longest gene:"+longestGene);
    }
    
    void testProcessGenes(){
        FileResource fr = new FileResource();
        String dna=fr.asString();
    }
   
    void testcgRatio(){
        String dna="CCCQQQCCC";
        System.out.println(cgRatio(dna));
    }
   
    String findSimpleGene(String dna,String startCodon,String endCodon){
        int startIndex= dna.indexOf(startCodon);
        if(startIndex==-1){
            return "";
        }
        int endIndex= dna.indexOf(endCodon,startIndex+3);
        if(endIndex==-1){
            return "";
        }
        String gene= dna.substring(startIndex,endIndex+3);
        if(gene.length()%3==0){
            return gene;
        }
        return "";
    }
    
    void testSimpleGene(){
        String dna1="AAAATTTGGTGGGTTT";
        String dna2="ATGTTTTTTTTTTTTT";
        String dna3="TTTTTTTTTTTTTTTT";
        String dna4="AATGAAAAAAAAATAA";
        String dna5="AATGAAAAAAAATAAA";    
        findSimpleGene(dna1,"ATG","TAA");
        findSimpleGene(dna2,"ATG","TAA");
        findSimpleGene(dna3,"ATG","TAA");
        findSimpleGene(dna4,"ATG","TAA");
        findSimpleGene(dna5,"ATG","TAA");
    }
       
    public static void main(String[] args){
        Part1 p1 = new Part1();
        Part2 p2 = new Part2();
        String dna1="ATGCTGCTGCTGCTGTAATAA";
        String dna2="ATGCTGCTGCTGCTGTAG";
        String dna3="ATGCTGC";
        String dna4="CCCATGCCaagaga";
        StorageResource genelist = new StorageResource();
        genelist.add(dna1);
        genelist.add(dna2);
        genelist.add(dna3);
        genelist.add(dna4);
        p2.processGenes(genelist);
    }
}
