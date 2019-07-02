package racingGems;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class racingGems {
	public static void main(String[] args) throws IOException {
	int val=0, n = 10;
	double temp_x=0, temp_y=0, v = 17.41488555933587;
	Gem[] gemList = null;
	String[] race = null;
	Stack<Gem> path = new Stack<Gem>();
	List<String> list = getInput(); //Formats each line as string entity
	
	for (int i=0; i<list.size(); i++) { //Set up the array and establish values
		String str = list.get(i);
		List<String> tempList = Arrays.asList(str.split(","));
		if (i == 0) {
			n = Integer.parseInt(tempList.get(0));
			v = Double.parseDouble(tempList.get(1));
			gemList = new Gem[n];
			System.out.println(n+"   "+v);
		} else {
			temp_x = Double.parseDouble(tempList.get(0));
			temp_y = Double.parseDouble(tempList.get(1));
			val = Integer.parseInt(tempList.get(2));
			gemList[i-1] = new Gem(temp_x,temp_y,val,i);
		}
	}
	
	for (int i=0; i<gemList.length; i++) { //Add viable paths to each gem
		for (int j=0; j<gemList.length; j++) {
			if (i != j) {
				double y = Math.abs(v *(gemList[j].getX() - gemList[i].getX())) + gemList[i].getY();
				if(hasPath(gemList[j],y)) {
					gemList[j].addPath(gemList[i]);
				}
			}
		}
	}
	sort(gemList, 0, gemList.length-1); //Sort gem list
	
	int greatest = 0;
	for (int i=0; i<gemList.length; i++) { //Find greatest path for each gem
		if(gemList[i].getnumPaths() != 0) {
			greatest = gemList[i].getPath(0).getVal();
			gemList[i].setSource(gemList[i].getPath(0));
			for (int j=1; j<gemList[i].getnumPaths(); j++) {		
				if (gemList[i].getPath(j).getVal() > greatest) {
					greatest = gemList[i].getPath(j).getVal();
					gemList[i].setSource(gemList[i].getPath(j));
				}
			}
			gemList[i].updateVal(greatest);
		}
	}
	greatest = gemList[0].getVal();
	Gem src = null, head = null;
	for (int i=1; i<gemList.length; i++) { //Find last Gem of race
		if(gemList[i].getVal() > greatest) {
			greatest = gemList[i].getVal();
			src = gemList[i];
		}
	}
	head = src;
	src = head.getSource();
	do {					//Get all sources and find path
		path.push(head);
		head = src;
		src = head.getSource();
	} while(head != src);
	path.push(src);
	race = new String[path.size()];
	int i=0;
	while(path.size() != 0) {
		race[i] = path.peek().toString();
		System.out.println(path.pop());
		i++;
	}	

	list = Arrays.asList(race);	//Output to file
	Charset utf8 = StandardCharsets.UTF_8;
	try {
		Files.write(Paths.get("race.txt"), list, utf8);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	
	public static void sort(Gem arr[], int l, int r) {
		if (l < r) {
			int m = (l+r)/2;
			sort(arr, l,m);
			sort(arr, m+1, r);			
			merge(arr, l, m, r);
		}
	}
	
	public static void merge(Gem arr[], int l, int m, int r) {
		int n1 = m - l + 1;
		int n2 = r - m;
		
		Gem L[] = new Gem[n1];
		Gem R[] = new Gem[n2];
		
		for (int i=0; i<n1; ++i) L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) R[j] = arr[m +1 +j]; 

        int i = 0, j = 0; 
        int k = l; 
        while (i < n1 && j < n2) { 
            if (L[i].getY() <= R[j].getY()) { 
                arr[k] = L[i]; 
                i++; 
            } else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } while (i < n1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        }  while (j < n2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
	}
	
	public static boolean hasPath(Gem g1, double y) {
		if(g1.getY() >= y) return true;
		else return false;
	}
	
	public static List<String> getInput() throws IOException {
		List<String> list = new ArrayList<String>();
		ZipFile zipFile = new ZipFile("C:\\Users\\Randall_Thornton\\Downloads\\input.zip");
		ZipEntry zipEntry = zipFile.getEntry("gems.txt");
		InputStream iStream = zipFile.getInputStream(zipEntry);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(iStream));
			String text = null;

			while ((text = br.readLine()) != null) {
				list.add(text);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return list;		
	}
}
