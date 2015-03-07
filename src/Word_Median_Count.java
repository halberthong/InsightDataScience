import java.io.*;
import java.util.*;

public class Word_Median_Count {
	SortedMap<String, Integer> wordmap = new TreeMap<String, Integer>();
	ArrayList<Integer> linelens = new ArrayList<Integer>();
	ArrayList<Double> medians = new ArrayList<Double>();

	public static void main(String[] args) throws Exception{
		Word_Median_Count wc = new Word_Median_Count();
	    File f = new File("../wc_input");
		FilenameFilter textFilter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return name.toLowerCase().endsWith(".txt");
	        }
	    };
	    File[] files = f.listFiles(textFilter);
	    Arrays.sort(files);// alphabetically read the text file
	    for (File file : files) {
	    	wc.countWord(wc.readFile(file.toString()));
	    }
		wc.writeFile();
	}
	public ArrayList<String> readFile(String path) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(path));
		ArrayList<String> lines = new ArrayList<String>(); 
		String line = br.readLine();
		while (line != null) {
			lines.add(line);
			line = br.readLine();
		}
		br.close();
		return lines;
	}
	public void countWord(ArrayList<String> lists){
		for(String templine : lists){
			templine = preprocess(templine);
			if(templine.isEmpty()){
				medianCount(0);
				continue;
			}
			templine = templine.trim();
			String[] tempwords = templine.split("\\s+");
			medianCount(tempwords.length);
			for(String tempword : tempwords){
				if(wordmap.containsKey(tempword)){
					wordmap.put(tempword, wordmap.get(tempword) + 1);
				}else{
					wordmap.put(tempword, 1);
				}
			}
		}
	}
	public String preprocess(String line){
		StringBuilder newline = new StringBuilder();
		for(int i = 0; i < line.length(); i++){
			char tempchar = line.charAt(i); 
			if((tempchar <= 'z' && tempchar >= 'a') || (tempchar == ' ' || tempchar <= '9' && tempchar >= '0')){
				newline.append(tempchar);
			}else if(tempchar <= 'Z' && tempchar >= 'A'){
				tempchar = (char)(tempchar - 'A' + 'a');
				newline.append(tempchar);
			}else{
				continue;
			}
		}
		return newline.toString();
	}
	public void medianCount(int len){
		linelens.add(len);
		Collections.sort(linelens);
		int totallen = linelens.size();
		if(totallen % 2 == 0 && totallen > 0){
			medians.add(((double)linelens.get(totallen / 2 - 1) + (double)linelens.get(totallen / 2 )) / 2);
		}else if(totallen % 2 != 0 && totallen > 0){
			medians.add((double)linelens.get((totallen - 1) / 2));
		}
	}
	public void writeFile() throws Exception{
		BufferedWriter out = new BufferedWriter(new FileWriter("../wc_output/wc_result.txt"));
		for(String key : wordmap.keySet()){
			out.write(key);
			StringBuilder space = new StringBuilder();
			if(key.length() > 20) space.append(" ");
			else{
				for(int i = 0; i < 20 - key.length(); i++){
					space.append(" ");
				}
			}
			out.write(space.toString());
			out.write(wordmap.get(key).toString());
			out.newLine();
		}
		out.close();
		
		BufferedWriter out2 = new BufferedWriter(new FileWriter("../wc_output/med_result.txt"));
		for(double temp : medians){
			out2.write(Double.toString(temp));
			out2.newLine();
		}
		out2.close();
	}
}
