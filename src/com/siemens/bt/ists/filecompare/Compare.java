package com.siemens.bt.ists.filecompare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import difflib.Delta;
import difflib.DeltaComparator;
import difflib.DiffUtils;
import difflib.Patch;




public class Compare  {

	private static List<String> fileToLines(String filename) {
	    List<String> lines = new LinkedList<String>();
	    String line = "";
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(filename));
	        while ((line = in.readLine()) != null) {
	            lines.add(line);
	        }
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return lines;
	}

//	public static void main(String[] args) {
//	            // At first, parse the unified diff file and get the patch
//	    Patch patch = DiffUtils.parseUnifiedDiff(fileToLines("example.diff"));
//
//	            // Then apply the computed patch to the given text
//	            List result = DiffUtils.patch(original, patch);
//	            /// Or we can call patch.applyTo(original). There is no difference.
//	}
	
	public static void main(String[] args) {
		
//		List<String> orginalString  =  new ArrayList<String>();
//		List<String> ChangeString  =  new ArrayList<String>();
	
		
				
        List<String> original = fileToLines("C:\\Conf\\httpd_changed.conf");
        List<String> revised  = fileToLines("C:\\Conf\\httpd.conf");

        
//        for (String originalString : original) {
//        	//revised.contains(originalString);
//		}
        
        
        /*for (int i = 0; i < original.size(); i++) {
			String stringToBeCompared = args[i];
			compareStringline(original , revised , i);
		}*/
        
        
        
        
        
        
        //// Compute diff. Get the Patch object. Patch is the container for computed deltas.
        Patch patch = DiffUtils.diff(original, revised);

        for (Delta delta: patch.getDeltas()) {
          //  System.out.println(delta);
        	//System.out.println("Keyword : "+delta.getType() +"data : " +  "\t From \t " +delta.getOriginal().getLines()   +" \t to \t " + delta.getRevised().getLines());
        	//System.out.println("MasterData: " +delta.getOriginal().getLines());
        	String Master=delta.getOriginal().getLines().toString();
        	Master=Master.substring(Master.indexOf("[")+1, Master.indexOf("]"));
        	Master=Master.replace(", ", "\n");
        	String Project =delta.getRevised().getLines().toString();
        	Project=Project.replace("[]","Empty");
        	//Project=Project.substring(Project.indexOf("[")+1, Project.indexOf("]"));
        	
        	System.out.println("MasterData:"+ Master);
        	System.out.println("ProjectData:"+ delta.getRevised().getLines());
        	
        	
        }
}

/*private static void compareStringline(List<String> original, List<String> revised, int i) {
	
	boolean prev =  false ; 
	boolean next =  false; 
    String prevString =  original.get(i-1);
    String nextString  = original.get(i+1);
    String compareString = original.get(i);
     
     
     if(revised.contains(compareString)){
    	 
    	  	if(revised.contains(prevString)){
    	  		  int position  =  findPosition(revised,prevString);
    	  		  if(position == i-1){
    	  			prev =  true;
    	  		  } 
    	  	}
    	 
    		if(revised.contains(nextString)){
  	  		  int position  =  findPosition(revised,nextString);
  	  		  if(position == i+1){
  	  			next =  true;
  	  		  }else{
  	  			  String  addedString = callRecursive(revised , compareString ,  nextString);
  	  		  } 
  	  		} 
    	 
     }
      
     
	
	 
	
}

private static S callRecursive(List<String> revised, String compareString, String nextString) {
	// TODO Auto-generated method stub
	
}

private static int findPosition(List<String> revised, String prevString) {
	int position  =  0 ;
	
	  for (int i = 0; i < revised.size(); i++) {
		     revised.get(i).equals(prevString);
	         return i;  
	}
	
	
	return position;
}
*/	    
	  
	
	
	
	  // list dupliacte   
	  //  eruku :  illa 
	  //  eruku  :  extra va eruku  
	  //  illa   : eruku  
	  //  neraya  eruku    :  kamiya eruku 
	
	
	   
}



