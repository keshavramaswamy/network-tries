

import java.io.File;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class routing {

	public static void main(String args[]) throws Exception{
		
	
	AdjList adjList = new AdjList();
	Map<Integer, String> iplist = new HashMap<Integer, String>();
	//file1 is the file with input graph
	String fileinput1 = args[0];
	//adjacency list created with file1 as parameter
	adjList.createGraph(fileinput1);
	int numv;
	numv = adjList.getNumVertices();
	//source id is read as commandline input
	int source_node = Integer.parseInt(args[2]);
	Integer ntemp = source_node;
	//destination id is read as commandline input
	int dest_node   = Integer.parseInt(args[3]);
	//file2 is the file with list of IP addresses
	String fileinput2 = args[1];
	//map list of ips to vertices
	iplist = createip(fileinput2,numv);
	
	int minDistance = -1;
	int count1 =0;
	int count2=0;
	
	DjikstrasAlg alg = new DjikstrasAlg();
	
	//construction of routing table
	while(count1<adjList.getNumVertices())
	{
		
		Map.Entry<Integer[], Integer[]> result = alg.invoke(adjList, count1);
		
		Integer[] reskey = result.getKey();
		Integer[] resval = result.getValue();
		
		
		count2=0;
		while(count2<adjList.getNumVertices())
		{
			if(count1 != count2)
			{	
 				int temp1 = count2;
 				while(!resval[temp1].equals(adjList.getSource()))
 				{
 					temp1 = resval[temp1];
 				}
 				adjList.addEntryRoutingTable(count1, iplist.get(count2), temp1);
			}
			
			if(count1 ==  source_node && count2 == dest_node)
			{
 				minDistance = reskey[dest_node];
 			}
 				
	        count2++;	
		}
		
		adjList.traverseNode(count1);
		count1++;
	}
	
	 
	
		//print minimum distance to standard output
		System.out.println(minDistance);
		
		// print path to standard output with matched prefixes
		while(ntemp != dest_node)
		{
			Map.Entry<Integer, String> map1  = adjList.getNextHop(ntemp, iplist.get(dest_node));
			System.out.print(map1.getValue()+" ");
			ntemp = map1.getKey();
		}
	
	
	
	}
	
	
	private static Map<Integer, String> createip(String fileinput2, int numv) throws Exception 
	{
		// method to map list of ip addresses to vertices
		Map<Integer, String> iplist1 = new HashMap<Integer, String>();
		Scanner scan = new Scanner(new File(fileinput2));
		for(int i=0;i<numv;i++) {
	        String binaryIP =  changeIp(convertIp(scan.next()));
            iplist1.put(i, binaryIP);
		}
		scan.close();
		return iplist1;
	
	}
	
		
		
		
		
		
		
		
		

		
	private static String changeIp(String ipToBinary) {
		//method to modify ip address to binary form
		while(ipToBinary.length() < 32)
		{
			ipToBinary = "0"+ipToBinary;
		}

		return ipToBinary;
	}

	
	private static String convertIp(String strLine) throws Exception {
		//method to convert ip addresses
		InetAddress ipadd = InetAddress.getByName(strLine);
		byte[] listbytes = ipadd.getAddress();	
		String data_out_string = new BigInteger(1, listbytes).toString(2);
		return data_out_string;
	}
	
}
