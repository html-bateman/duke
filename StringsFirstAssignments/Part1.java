import edu.duke.*;
// A protein-coding gene is one that meets the following requirements (which are approximations of the exact constraints used in computational biology):

// Begins with a valid start codon (ATG).
// Ends with a valid stop codon (TAA, TAG, or TGA).
// Contains at least 5 codons including the start and stop codon.
// Cytosine (C) and Guanine (G) combined account for at least 30% of its total mass.
// Each character in the sequence will be A, C, G, T (lower case or upper case) or a dash ('-').

// Here are some examples of DNA sequences:

// seq1 = ('cure for cancer protein', 'ATGCCACTATGGTAG')
// seq2 = ('captain picard hair growth protein', 'ATgCCAACATGgATGCCcGATAtGGATTgA')
// seq3 = ('bogus protein', 'CCATt-AATgATCa-CAGTt')
public class Part1 {
    String startCodon = "ATG";
    String stopCodon1 = "TAA";
    String stopCodon2 = "TAG";
    String stopCodon3 = "TGA";
    
    String findSimpleGene(String dna){
        int startIndex= dna.indexOf("ATG");
        if(startIndex==-1){
            return "";
        }
        int endIndex= dna.indexOf("TAA",startIndex+3);
        if(endIndex==-1){
            return "";
        }
        String gene= dna.substring(startIndex,endIndex+3);
        if(gene.length()%3==0){
            return gene;
        }
        return "";
    }
    
    String findGene(String dna){
        int startIndex = dna.indexOf("ATG");
        //System.out.println(startIndex);
        int currIndex = dna.indexOf("TAA",startIndex+3);
        //System.out.println(currIndex);
        while(currIndex != -1){
            if((currIndex - startIndex)%3 ==0){
                String gene=dna.substring(startIndex,currIndex+3);
                //System.out.println(gene);
                return gene;
            }else{
                currIndex = dna.indexOf("TAA",currIndex+1);
            }
        }
        return "No gene in this strand";
    }
    
    String findGeneV2(String dna){
        int startIndex = dna.indexOf(startCodon);
        if(startIndex==-1){
            return "";
        }
        int currIndex = findStopCodon(dna,startIndex,stopCodon1);
        int currIndex2 = findStopCodon(dna,startIndex,stopCodon2);
        int currIndex3 = findStopCodon(dna,startIndex,stopCodon3);
        int smallest = Math.min(Math.min(currIndex,currIndex2),currIndex3);
        if(smallest==dna.length()){
            return "";
        }
        return dna.substring(startIndex,smallest+3);
    }
    
        String findGeneV2(String dna,int startIndex){
        int currIndex = findStopCodon(dna,startIndex,stopCodon1);
        int currIndex2 = findStopCodon(dna,startIndex,stopCodon2);
        int currIndex3 = findStopCodon(dna,startIndex,stopCodon3);
        int smallest = Math.min(Math.min(currIndex,currIndex2),currIndex3);
        if(smallest==dna.length()){
            return "";
        }
        return dna.substring(startIndex,smallest+3);
    }
    
    String findGeneV3(String dna){
        int startIndex = dna.indexOf(startCodon);
        if(startIndex==-1){
            return "";
        }
        int taaIndex = findStopCodonNoLength(dna,startIndex,stopCodon1);
        int tagIndex = findStopCodonNoLength(dna,startIndex,stopCodon2);
        int tgaIndex = findStopCodonNoLength(dna,startIndex,stopCodon3);
        int minIndex;
        if(taaIndex==-1 || (tgaIndex!=-1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        }else{
            minIndex = taaIndex;
        }
        if(minIndex==-1 || (tagIndex!=-1 && tagIndex < minIndex)){
            minIndex = tagIndex;
        }
        if(minIndex==-1){
            return "";
        }
        return dna.substring(startIndex,minIndex+3);
    }
    
        String findGeneV3(String dna,int startIndex){
        int taaIndex = findStopCodonNoLength(dna,startIndex,stopCodon1);
        int tagIndex = findStopCodonNoLength(dna,startIndex,stopCodon2);
        int tgaIndex = findStopCodonNoLength(dna,startIndex,stopCodon3);
        int minIndex=-1;
        if(taaIndex==-1 || (tgaIndex!=-1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        }else{
            minIndex = taaIndex;
        }
        if(minIndex==-1 || (tagIndex!=-1 && tagIndex < minIndex)){
            minIndex = tagIndex;
        }
        if(minIndex==-1){
            return "";
        }
        return dna.substring(startIndex,minIndex+3);
    }
    
    int findStopCodon(String dna,int startIndex, String stopCodon){
        //int startIndex=dna.indexOf(startCodon);
        int currIndex=dna.indexOf(stopCodon,startIndex+3);
        while(currIndex!=-1){
            if((currIndex-startIndex)%3==0){
                return currIndex;
            }else{
                currIndex=dna.indexOf(stopCodon,currIndex+1);
            }
        }
        return dna.length();
    }
    
    //changed the return value, the findgene should change accordingly
    int findStopCodonNoLength(String dna,int startIndex, String stopCodon){
        //int startIndex=dna.indexOf(startCodon);
        int currIndex=dna.indexOf(stopCodon,startIndex+3);
        while(currIndex!=-1){
            if((currIndex-startIndex)%3==0){
                return currIndex;
            }else{
                currIndex=dna.indexOf(stopCodon,currIndex+1);
            }
        }
        return -1;
    }
    
    void printAllGenes(){
        //String dna="ATGAAATAAxxxATGTAGxxxATGTAATGAxxxATGxxxxxxTAA";
        String dna="ATGAAATAAATGTAGATGTAATGAATGTAA"; 
        String gene=findGeneV2(dna);
        //print first gene
        System.out.println(gene+"\n");
        int nextGeneStartIndex=gene.length();
        while(true){
            String currentGene=findGeneV2(dna.substring(nextGeneStartIndex,dna.length()));
            if(currentGene==""){
                break;
            }else{
                System.out.println(currentGene+"\n");
            }
            nextGeneStartIndex = nextGeneStartIndex+ currentGene.length();
        }
    }
    
    public StorageResource getAllGenes(String dna){
        StorageResource geneList = new StorageResource();
        //int startIndex=0;
        int startIndex = dna.indexOf(startCodon);
   
        while(true){
            String currentGene = findGeneV3(dna,startIndex);
            if(currentGene.isEmpty()){
                break;
            }
            geneList.add(currentGene);
            startIndex=dna.indexOf(currentGene,startIndex)+currentGene.length();
        }
        return geneList;
    }
    
    void testGetAllGenes(String dna){
        StorageResource genes = getAllGenes(dna);
        for(String s:genes.data()){
            System.out.println(s);
        }
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
           if (index == -1){
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
            if(gene.length()>60){
                count9+=1;
                System.out.println("\n>9 length "+gene);
            } 
            if(cgRatio(gene)>0.35){
                countCG+=1;
                System.out.println("\n>0.35 ratio "+gene);
            }
            if(gene.length()>longestGene.length()){
                longestGene=gene;
            }
        }
        System.out.println(">9 length count:"+count9);
        System.out.println(">0.35 ratio:"+countCG);
        System.out.println("the longest gene:"+longestGene);
    }

    void testProcessGenes(){
        //FileResource fr = new FileResource();
        //String dna=fr.asString();
        //dna = dna.toUpperCase();
        String dna = "AACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTCACCCTTCTAACTGGACTCTGACCCTGATTGTTGAGGGCTGCAAAGAGGAAGAATTTTATTTACCGTCGCTGTGGCCCCGAGTTGTCCCAAAGCGAGGTAATGCCCGCAAGGTCTGTGCTGATCAGGACGCAGCTCTGCCTTCGGGGTGCCCCTGGACTGCCCGCCCGCCCGGGTCTGTGCTGAGGAGAACGCTGCTCCGCCTCCGCGGTACTCCGGACATATGTGCAGAGAAGAACGCAGCTGCGCCCTCGCCATGCTCTGCGAGTCTCTGCTGATGAGAACACAGCTTCACTTTCGCAAAGGCGCAGCGCCGGCGCAGGCGCGGAGGGGCGCGCAGCGCCGGCGCAGGCGCGGAGGGGCGCGCCCGAACCCGAACCCTAATGCCGTCATAAGAGCCCTAGGGAGACCTTAGGGAACAAGCATTAAACTGACACTCGATTCTGTAGCCGGCTCTGCCAAGAGACATGGCGTTGCGGTGATATGAGGGCAGGGGTCATGGAAGAAAGCCTTCTGGTTTTAGACCCACAGGAAGATCTGTGACGCGCTCTTGGGTAGAGCACACGTTGCTGGGCGTGCGCTTGAAAAGAGCCTAAGAAGAGGGGGCGTCTGGAAGGAACCGCAACGCCAAGGGAGGGTGTCCAGCCTTCCCGCTTCAACACCTGGACACATTCTGGAAAGTTTCCTAAGAAAGCCAGAAAAATAATTTAAAAAAAAATCCAGAGGCCAGACGGGCTAATGGGGCTTTACTGCGACTATCTGGCTTAATCCTCCAAACAACCTTGCCATACCAGCCCATCAGTCCTCTGAGACAGGTGAAGAACCTGAGGTCGCAGGAGGACACCCAGAAGGTCCAGAGAGAGCCTCCTAGGCCCCCCACCTCCCCCCGTGGCAGCTCCAACCCCAGCTTTTTCACTAGTAAGGCAGTCGGGCCCCTGGGCCACGCCCACTCCCCCAAGCGGGGAAGGAGCTTCGCGCTGCCGCTTGGCTGGGGACTGGGCACCGCCCTCCCGCGGCTCCTGAGCCGGCTGCCACCAGGGGGCGCGCCAGCGGTGTCCGGGAGCCTAGCGGCGCGTGTGCAGCGGCCAGTGCACCTGCTCTGGCCCTCGCCGCGGTCTCTGCCAGGACCCCGACGCCCAGCCTGACCCTGCCATTCAGCGGGGCTGCGGCTCCACGGCCTGCGACAGCAGCCCCACCTGGCATTCAGCGCGCTCCCGGGGGCAGAGGTCGCGGTGTCCTCACGCTGTGGTGCCGGCCTACAACCCCCACGCCGGGCTCGGGCCCGGCGGAGGAGGGCGATGCTCCCCGGGTAGGACAAACCGGTCACCTGGGCTGCGACGGCGGCTTAGGGGCAGAAGCGGCGGTCCAGGGCCGCCTGGCGCAGCAGCCTGTCCCAGCCGCGGTCCCTGCAGTCCCTCCCTGGCGGCTGCGCAGCCGTCCCACGACAGGGGCCATAAACTCTCCAGAGCGGAAAGCCGCACCCTGGTGGCCCGGCCCCGCGCCCAGACCTGGCGGCCGCTGGCACCTGACCCGCTGCATGGGTCTCCAGGGAGCTCGCTGCCCACCCGGCGCTGCAGGCTCGGCTCCCTCGTACACTCTCTGGTAGGTGCTAGGGACGACCCTATGGGCCAGCTTGCCATGCCCAGTCCCCAGGCCGCACCCACCCTGGCTCCCTGGGCTAGGGGACTGGCTCCTCCTGTGAGTCGTGGGTCTGGGAGGCAGGGGCGTTAGGGGAGAGTGAGGGACCGAGGGCAGCCCCTGCTGTGTGCACAGCGAGGTCGTGCACAGGCGTCTGTTGCAGAGCGTGCAGCTTCAGATGAGACTGGATTGCAGGTGGAGATGACTGTGGGTGCGCACACCTGGAGGTGAAGGGGAGGCAGCCTGTCTACCTGACCCATGAAATACAGGAGACTGTACCCCAGAAGCAGCGGGTTCACTGCTCCATTGATTAAGCAAGTCTGGGACACACATGTAGCTAAGCTGTGAGTTCTGTACCAGCGATCCCAACACCCACGCCCTCAGAAAGACACTGGTGTGGGGCCTGGGTGCTTGTCAGGCCTGAAAGTGGAGAGCACGGGCCAGAGACACTGAGTAGGGGGAACCCACCCTAGGGCTCTGAGGGACGACGATGTGGGGAGCTGGTGACAGAGCCTGAGCTGGCCCAATGTTGCACGGTGGGGACAGATTCGAGGTACAGTGGGGACTGGTGACCTCAGTTCCCAGTGTCCCAGCCTGGCCTCCCAGTCCACCCAGCAATTAGTGGGTGCTGCCCTGCAAAGACTCTGGGGGTGCCTCAGCCCTCCTCATCACACGTGACTGGTGACTTCTGTGTCCACCCGCACAATAAGAGGGATCTTCTCTCACTTTCAGGCAAGCCCAAGAAAGTCAGGGGCCTATGTGAGCCAAAGAGGAGAGAAGGTGATGCCTCAGCCCAGTGTTTCTGCCCCACCTCGCTTGTGGCCTTCGGAACTTGATTTGCACCGCAGGAAAATGGGCAATGAAAACCCCTCCCTAACTGGCTTCTCAGTCCACTCTGACCAGCCCACTGCACAGCGCCCACCCTGCAGCTCCAGGTACAGAGGCTGGGATGGCTCTGGGCTGACCTAAGGGCCTTCTGATGGCTCCAACCCTCGGGATGCCTCATGCTCACCCTTTGGCACCCACCTGACAGCTCAGCATCTCTGCTCTCTGCCATCCTCAATGCCTGCTCTAGACAAGCCCAAGTCCCCCAGGAGTGGCAGAGGGAACTGAGCCGAAAACTAAGTCTCGGCTCACTGAACCCCAAGTGGGCTGTCCAGCCTCGCCCTTCAGTTCACAACCCCAGGCAGGTTCCCTCCAGGGATGTGATCCCAGGGGCCACAGCAGCACATTCTGGCCTAACCTATCCACTATTTAAACAGTTACTGAAAAGGCCAGGATGGCCGTGGGCCCTGACATTAATCCCCTTTCTCTGTGAGGGGGCTGGGTTGGGTTTGCCATCCTGATGTCTTTGTGGAAAGAGCTGGCAGGTGAAGCAAGTCTCAGGGGCCAGCCATGGGACAAGGAACCTAGGACTGGCCTCTGCTGGAACCCTCTGAGGCCCCTGCGGACAGGAGGATCCAATGGAGGTCTAGCCACCCCTCCCAGGTTGGTGCTCACAGCCCCTCCCTGGCCCACTCCCTGCACACCTGCACCTGCTGGTCTCTGGGAGAGGAGCATCCATCCATCTTGTGCGCATAGCTTTCGGCTCCATTTTCATGAGGATGGTCTCCTTGGCAGAAATGCCCATTAGGGGATCCTGAGCCTGTGCTAGCTCTTCTCTAAGTGCCAAAGCCAGTGAGAGGGACTTGAAAACTCAAGACTTATTAACAGTATTTTCTGCATTTTGTGCTTTCAGGGTTGTTTTTTCCTTAAAATGTGTAAAAACAAACATTGAGATTTCTATCTTTTATATAATTTGGATTCTGTTATCACACGGACTTTTCCTGAAATTTATTTTTATGTATGTATATCAAACATTGAATTTCTGTTTTCTTCTTTACTGGAATTGTTAACTGTTTTATAGGCCAAATCTTTTAAAAAAAACACATCTCTCTAATTTCTCTAAACATTTCTAATTACATATATATTTACTATACCTAATACACTACTTTGGAATTCCTTGAGGCCTAAATGCATCGGGGTGCTCTGGTTTTGTTGTTGTTATTTCTGAATGACATTTACTTTGGTGCTCTTTATTTTGCGTATTTAAAACTATTAGATCGTGTGATTATATTTGACAGGTCTTAATTGACGCGCTGTTCAGCCCTTTGAGTTCGGTTGAGTTTTGGGTTGGAGAATTTTCTTCCACAAGGGATTGTCTTGGATTTTTCTGTTTCTCCCTCAATATCCACCTGGAAAACATTTCAATTAATTTATATTTACTTAAATATTTCTGTGCAAAAACTGTGTACAAAAGCCCCAAAGCATAATTTGTGCAGTTGAGCGCATGTTCTGTTGTTCAGCATTTATGGTGGTTGGTAGTGGAAAAGATTTTTAGAATATGTGGATTTTCGGGATATTCCCAGAAGCCCAGATAGCGACACTTTACCTTTGGAGGAATTACTTCTCAGAATATTGCACACAATCAATCGCCTTTGGAAGGAGCATATATCCCCAGCAAAAGCTCTGGTTTTTTGAAGTCTGTATTGTGTGTTACTTCCAGGAGAATATGCAATGATGACAATGTTATTAGATGATTCAAATATGAAGTGCTGTTATGCCAAACAATGAATCTTTGTGTTATACATTATGCCTAACTATAAATCTTTGTGTTATACATTTTAATGTCATTGGAGAGTACTCCTGTCTTCTTGGCATTATTGATAATTAGATTCTAATTGCTAATAAGTCAGAAAAATTAGGAACACCAAATTTCAGTTGTCTCAAAAGCACTCCTCTTATTAAATTTGGATGTTTACCTTTATCACATCAAAAGAAATATTGTTAGAAAGGTGTTTAATGTTTTGCAGATGGATAGATTACTGTTATTAGTTCTCATTTCATTGTTAATTTTTAAAACCATAAGGTTGGAAGTATCAATATGCCTTTCAATATACCTTAGTGGAATTTATTAAATTTTCATGGATGTCCTTTAGGGGGTTCAGGAAGTTATTTCTATTGCTAGATTTCTGGAAGATTTATCAGGAATGAGTGTCAGACATTGTCAGACGTCCATTGAAATCATCATGGTCTTTTCCTTTATTCTATTAATATGATGTATTACACTGATTGATTTTTAAATTTGTATTGGTAGGATAATTCCACTTGGTTATATTGTCTAACTTTTTTCTAATTTTCTTTCATTTTTATTACAGATGAGGCCTCACTCTGTCACCCAGGTTGGGGTGGAGTGGCACAGTCACAGCTCACTATAACCTCAAGCTCCTGGGCTCAAGTGATCCTGCCACCTCAGCCTCCTAAGTAGCTGGAACTACAGATGTGCACTGCCATGCCAGGCTTGTCTAACATTTTTATGTGTTGCTTCATCCAGTTTGCTAGAGTTTTTGGAGATTTCTGTCTTCATTCATGAGGGATAATAGTCTGCACTTTTATTTTCTTGTGATACTTTTGTCTGATTTGTTATCTGGGTAATACTGGCCTTGAAAATGAATTGATGTTTTCCTGCTTCTCTGCTTTGCAAGTGTTTGTGAAGGATTGGTTATTCATTAAGTGTTTAATAGAATTCACTAGTGAAGCTATGTGAGCCAGGGCTAGACTGATGAAGAGTTTTCATTAGTCTAATCTGTTTACTTGCTGTATAAGTACGCATATATTCTCTTTCTTCTTGATTTAATTTTACACTTTGTGTATAGCAGGGAATCTGTGTCTAATTTGTAGTATTTCATGCTTCTAGGTTTTCATGGCAGTTGAGATGTAAGAATAACAATAATGTTGGGAGAAGGAAGTTGTGGACAATCCATGAATATCCCAACATCTGTTGTAGGAAGGTTAAGATTACTTTTTTTTTTTTTGCTGTACTGAACTGAATACTCTTATTTATAATGTCAGACAAATGTAATGTTGTATATAAATAGAACTAGGAAAATGTGCCATTTGTCTTAGTATTTAATCAAGATGGAAGTCTGGGCCTACCTCCTCTCTTTTATTAATATGTAGACAGGACACCAACACAAATTAGAATGAAGACAAACAAAATGTTAGCAAATGAAGAATGGTATCAATTGGTTAAAATGTGATGAAATAGAGTGGTGAATATTTACATAGAATCCATGATGTGTTAGGTGCTATTTCAAGCTATTTGCACATATAGTTTTAATACCAATGACGTTAAAATGTATAACACAAAGATTCATATAAATAAAAATTACAACATTGTAAATAATATTAGGTGACACTAAAACTGTCATAGAAATACACATTTATATAAAACATAAAGTAACATGAAGTATTAAATTTTAGAAACTTTGATTACTAATCAGATGAACAACTGATTAGCCTTTTTATCCAGTAAAAAAGGCATACATATTATTTTCAAATTCCAGAGACAAATATTTTAAATATTGAAGTTGAAGACCTAAAAATGTGTCACTGACCTCATGGAAGTAGATATTCACTAGGTGATATTTTCTAGGCTCTCTGAAATTATATCAGAAAAATGTGAATTAGAATATAACCCATAAATAATATCTGGCCACATACAAAGTAATTGAAGATCAATTTAAATGGCTATTGGATTAAGAAATAGGGACTGAGGTAAATTTGCAGTGTCAGGGAGGATCTAAGGAGGAAGCATTGACACTGGAGCCCAAGGACCTGGGATCACAGAACAGATTCTACCAGTGCTAACTTACTGCTCCACAGAAAACATCAATTCTGCTCATGCGCAGGTACAATTCATCAAGAAAGGAATTACAACTTCAGAAATGTGTTCAAAATATATCCATACTTTGACATATTAATGAAGTAATCACATTCTACACATAACTACTCCATATGGAATACTGGGGAGGAGGTGTTCCAAATAAAGAGACTGAGGATTTCTCATGAGAACTCAGTGTCTGCTAGAAAATATCTAAGTAAAATATTTTACTTATGTGGAAAGTGTGGATGTTTGTGCATCAAAAGTTTCAAGAATCCCTAAAATTTACAATGGAGATGAGGAGAAAATATCAGAATTTCCCAGCACCAGAAATAAGGCAAGAAAAAATTCAGAGGGGTTGTAAATGTGAAAAGCCAATGGCTGGTCACACAGCAACATTGATAACCTTGTGCCTGGACAACTAGAATAAATACATAAACATACACATTGAAAATATTTCCAATATTAGATCTCCCTCATGTGAGAACTAAATTATAAAGATTGAAGCATAGAAGAAAATAAGCTACCAGAATAAATTTGATTACACATAAATTTCTGATATTGAAACTGTCACAAATGTTTAAGTTGGTAGTGGAAGACAAAGGACATATAATCTTGGGAGTCCTAAGGCCCTGCCCACTGCCAGTCCCTCCACACTACTACAGCTGATGCTTTCTGGAAATCACCACCTCCTGGCAGGAGCCCAACCAGCACAAATATAGAGCATTAAACCACCAAAGCTAAGGAGGCTCACAGAGTCTATTGCACCCTTCACCACCTCCACTGGAACAGGCGCTGGTATCCATGGCTCAGAGACCCAAAGATGGTTCACATCACAGGGCTCTATGCAGACAACCCCCAGTACCAGCCCAAAGCCACGTAGACCTGCTGGGTGGCTAGACCCAGAAGAGAGACAACAATCAATGCACTTTGGCTTACAGGAAGCCATGCCCATAGGAAAAAGGGGAGAGTACTACGTCAAGGGAACACCCCGTGGGATGAAAGAGTCTGAACAACAGTCTTCAGCCCTAGACCTTTCCTCTGACAGAGTCTACCAAAATGAGAAGGAACCAGAAAACCAACCCTGGTAATCTGACAAAACAAGAATCTTCAACACCCCCCAAAAAATCACACCAGTTCATCACCAATGGATCCAAACAAAGAAGAAATCACTGATTCATCTAAAAAAAAATTCAGGTTAGTTATTAAGCTAATCAGGGAGGGGCCAGAGAAAGATGAAGCCCAATGCAAGAAAATCCAAAAAATGATACAATACGTGAAGGGAGAATTATTCAAGGAAATAGATAGCTTAAATAAAAAAATAAAAAATCAGGAAACTTTGGACGTACTTTTAGAAATGTGAAATGCTCTGGAAAGTCTCAGCAATAGAATTGAACAAGTAGAAGAAAGAAATTCAGAATTCGAAGACAAGGTCTTTGATTTAACCCAATCCAATAAAGACAAAGAAAAAAGAATAAGAAAATATGAGCAAAGTCTCCAAGGAGTCTGGCATTCTGTTAAATGATGAAACCTAACACTAATTGGTGTACCTGAGGAAGAAGTGAATTCTAAAAGCCAGGAAAACATATTTGGGAGAATAATCTAGGAAAACTTCCATGGCCTTGTGAGAGACCTAGACATCCAAATACAAGAACCACAAATAACACCTGGGAAATTCATCACAAAAAGATCTTAGCCTAGGCACATTGTCATTAGGTTATCCAAAGTTAAGACAAAGGAAAGAATCTTAAGAGCTGTGAGACAGAAGCACTAGGTAACCTATAAAGGAAAACCTGTCAAATTAACAGCAGATTTCACAGCAGGAACCTTACAAGCTAGATGGGATTGGGGCCCTTTCTTCAGCCTCCTCAAACAAAACAATTATCAGCCAAGAATTTTGTATCCAGCAAAACTAAACATCATATATGAAGGAAAGATACAGTCATTTTCAGACAAACAAATGCTGACAGAATTTGCCATTACCAAGCCAGGACTCTAAGAACTGCTAAAAGGAGCTCTAAATCATGAAACAAATCCTGGAAACACATCAAAACAGAACTTCATTAACGCATAAATCACACAGGACCTATAAAACAAAAATACAAGTTAAAAAACAAAAACAAAGTACAGAGGCAACAAAGAGCATGATGAAAGCAATGGTACCTCACTTTTTAATACTAATGTTGGTTGTAAATGGCTTAAATGCTCCACTTACAAGATACAGAACCACAGAATGGATAACAACTCACCAACTAACTATCTGCTGCCTTCAGGAGACTCACCTAACACATAACGACTTACATAAACTTAAGGAAAGTGGTAGAAAAAGGCATTTCATGCAAATGGACACCAAAAGCAAGCAGCAGTAACTATTCTCATATGAGACAAAACAAACTTTAAAGCAACAGTAGCTAAAAGAGACAAAGAGAGACAGTATATCATCTGTCACCTGACAGTCTCATCCAACAGAAAAATATGACAATCCTAAACATATGTGAACCTAACACTGGAGCTCCCAAATTTATAAAACAATTACTAGTAGACATAAGAAATAAGATAGACAGCAACACAATAATAGTGGGGGACTTCAATACTCCACTGACAGCACTAGACAGGTCATCAAGACAGAAAGTCAACAAAGAAACAATGGATTTAAACTATACTTTGGAACAAATGGACTTAACAGATATATATAGAACATTTCATCCAACAACCACAGAATACACATTCTATTCAACAGCACATGGAATTTTCTCCAAGATAGACCATATGATAGGCCATAAAATGAGTCTCAATAAATTTAAGAAAATTGAAATTGTATCACGCACTCTCTCACATCACAATGGAATAAAACTGAAAATCAACTCCAAAAGGAATCTTCGAAACCATGCAAATACATGGAAATTAAATAACCTGCTCCTGAATGAGCATTGGGTGAAAAACGAAATCAAGATGGAAATGTAAAAAATTTCTTCGAACTGGATGACACAACCTATCAAGACCTCTGGGATACAGCAAAGGCAGTGCTAAGAGGAAAGTTTATAGCACTAAACACCTACGTCGAAAAGTCTGAAAGAGCACAGACAATCTAAGTTCACATCTCAGGGAACTAGAGAAGGAGGAACAAGCCAAACCCAATCCCAGCAAACAAAGGAAATAACCAAGATCAGAGCAGAACTAAATGAAATTGACACAACAACAACAACAACAAAAATACAAAACATAAATAAAACAAAAATTTGGTTATTTGAAAAGATA";
        StorageResource genelist = getAllGenes(dna);
        //for(String g:genelist.data()){
        //    System.out.println(g);
        //}
        processGenes(getAllGenes(dna));
        System.out.println("amount of the genes found:"+genelist.size());
        System.out.println("amount of the CTG found:"+countCTG(dna));
    }
    
    void testSimpleGene(){
        String dna1="AAAATTTGGTGGGTTT";
        String dna2="ATGTTTTTTTTTTTTT";
        String dna3="TTTTTTTTTTTTTTTT";
        String dna4="AATGAAAAAAAAATAA";
        String dna5="AATGAAAAAAAATAAA";    
        findSimpleGene(dna1);
        findSimpleGene(dna2);
        findSimpleGene(dna3);
        findSimpleGene(dna4);
        findSimpleGene(dna5);
    }
    
    void testFindGene(){
        //----------1234***123456789***1
        String dna="AATTATGTTTTATTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene = findGene(dna);
        System.out.println("Gene is "+gene);
        
        String dna2="AATGATAATTTATTTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene2 = findGene(dna2);
        System.out.println("Gene is "+gene);
        
        String dna3="AATTATTTTTTATTTAAT";
        System.out.println("DNA strand is "+dna3);
        String gene3 = findGene(dna3);
        System.out.println("Gene is "+gene3);   
    }
    
    void testFindGeneV2(){
        //test case for ATG with one valid stop codon
        //----------1234***123456789***1
        String dna="AATTATGTTTTATTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene = findGeneV2(dna);
        System.out.println("Gene is "+gene);
        
        //test case for no ATG
        String dna2="AAATAATTTATTTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene2 = findGeneV2(dna2);
        System.out.println("Gene is "+gene2);
        
        //test case for ATG with multiple valid stop codon
        //-----------0123000123000123
        String dna3="AATGATTTGATATTAGAT";
        System.out.println("DNA strand is "+dna3);
        String gene3 = findGeneV2(dna3);
        System.out.println("Gene is "+gene3); 
        
        //test case for ATG with no valid stop codon
        //-----------01231231231231231231
        String dna4="AATGATTTTTGATATTAGAT";
        System.out.println("DNA strand is "+dna4);
        String gene4 = findGeneV2(dna4);
        System.out.println("Gene is "+gene4); 
    }
    
    
    void testFindGeneV3(){
        //test case for ATG with one valid stop codon
        //----------1234***123456789***1
        String dna="AATTATGTTTTATTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene = findGeneV3(dna);
        System.out.println("Gene is "+gene);
        
        //test case for no ATG
        String dna2="AAATAATTTATTTTTTAAT";
        System.out.println("DNA strand is "+dna);
        String gene2 = findGeneV3(dna2);
        System.out.println("Gene is "+gene2);
        
        //test case for ATG with multiple valid stop codon
        //-----------0123000123000123
        String dna3="AATGATTTGATATTAGAT";
        System.out.println("DNA strand is "+dna3);
        String gene3 = findGeneV3(dna3);
        System.out.println("Gene is "+gene3); 
        
        //test case for ATG with no valid stop codon
        //-----------01231231231231231231
        String dna4="AATGATTTTTGATATTAGAT";
        System.out.println("DNA strand is "+dna4);
        String gene4 = findGeneV3(dna4);
        System.out.println("Gene is "+gene4);  
    }
    
    void testFindStopCodon(){
        //----------0123456789012345678901234
        String dna="_________TAA_________TAA";
        int index=findStopCodon(dna,0,"TAA");
        if(index!=9) System.out.println("test case1 error");
        index=findStopCodon(dna,12,"TAA");
        if(index!=21) System.out.println("test case2 error");
        index=findStopCodon(dna,22,"TAA");
        if(index!=22) System.out.println("no stop condon");
        System.out.println("test finished");
    }
    
    public static void main(String[] args){
        Part1 fgene = new Part1();
        fgene.testSimpleGene();
    }
}
