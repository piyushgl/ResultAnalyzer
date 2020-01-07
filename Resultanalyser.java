import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

interface Constants
{
	int IGNORABLE = 1, PASS = 2, RE = 3, OTHER = 4, COLLEGENAME = 5;
	int BRANCHNAMESAME = 6, STUDENT = 7, BRANCHNAMECHANGED = 8;
}

class Record implements Constants
{
	String brName;
	static String studentName ="";
	long roll = 0;
	static String rollNo = "";
	static String collegeName;
	static String branchName = "";
	Record(String line)
	{

		int recordType;
        int length = line.length();
        if(length>0)
        {
			if(line.contains("UNIVERSITY"))
			{
				recordType = IGNORABLE;
				return;
			}
			else if (line.contains("---------"))
			{
				recordType = IGNORABLE;
				return;
			}
			else if (line.contains("B.TECH"))
			{
				recordType = IGNORABLE;
				return;
			}
			else if(line.contains("Roll No"))
			{
				recordType = IGNORABLE;
				return;
			}
			else if(line.contains("College"))
			{

				collegeName = line.substring(0,50);
				brName = line.substring(60,110);

				if(brName.equals(branchName))
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					return;
				}
				else
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					recordType = BRANCHNAMECHANGED;
                    branchName = brName;
					ob = new Onebranchdata(recordType);
					return;
				}
			}
			else if(line.contains("Institute"))
			{
				collegeName = line.substring(0,50);
				brName = line.substring(60,110);

				if(brName.equals(branchName))
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					return;
				}
				else
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					recordType = BRANCHNAMECHANGED;
                    branchName = brName;
					ob = new Onebranchdata(recordType);
					return;
				}
			}
			else if(line.contains("Inst"))
			{
				collegeName = line.substring(0,50);
				brName = line.substring(60,110);
				if(brName.equals(branchName))
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					return;
				}
				else
				{
					recordType = BRANCHNAMESAME;
					Onebranchdata ob = new Onebranchdata(recordType);
					recordType = BRANCHNAMECHANGED;
					branchName = brName;
					ob = new Onebranchdata(recordType);
					return;
				}
			}
			else if(line.contains(""))
			{
				recordType = BRANCHNAMESAME;
				Onebranchdata ob = new Onebranchdata(recordType);
				recordType = BRANCHNAMECHANGED;
				branchName = brName;
				ob = new Onebranchdata(recordType);
				return;
			}
			else
			{
				StringTokenizer st = new StringTokenizer(line,"|");
				rollNo = st.nextToken();
				try
				{
					 roll = Long.parseLong(rollNo);
				}
				catch(NumberFormatException e)
				{
				}
				recordType = STUDENT;
				String resultLine="";
				while(st.hasMoreTokens())
				{
                    st.nextToken();
                    studentName = st.nextToken();
                    st.nextToken();
                    resultLine = st.nextToken();
				}
				Result rt = new Result(resultLine);
				return;
			}
		}
	}
	Record()
	{
	}
	String getRollno()
	{
		return(rollNo);
	}
	String getStudentname()
	{
		return(studentName);
	}
	String getCollegename()
	{
		return(collegeName);
	}
	String getBranchname()
	{
		return(branchName);
	}
}

class Result implements Constants
{
	Record rd = new Record();
	static String topperName ="";
	static String topperRollno="" ;
	int i=0, recordType;
	long marks;
	static long maxMarks=0;
	static String reArray[] = new String[12];
	static int numtokens;
	Result(String rLine)
	{

		if(rLine.startsWith("Re."))
		{
			rLine = rLine.substring(3);
			StringTokenizer st = new StringTokenizer(rLine);
			numtokens = st.countTokens();
			while(st.hasMoreTokens())
			{
				reArray[i] = st.nextToken();
				i++;
			}
			recordType = RE;
            Onebranchdata ob = new Onebranchdata(recordType);
            return;
		}
		else if(rLine.contains("/"))
		{
			StringTokenizer st = new StringTokenizer(rLine,"/");
			String no= st.nextToken();
			try
			{
				 marks = Long.parseLong(no);
			}
			catch(NumberFormatException e)
			{
			}
			if(marks>maxMarks)
			{
				maxMarks = marks;
			    topperRollno = rd.getRollno();
			    topperName = rd.getStudentname();
			}
			else
			{
			}
     		recordType = PASS;
            Onebranchdata ob = new Onebranchdata(recordType);
			return;
		}
		else
		{
			recordType = OTHER;
            Onebranchdata ob = new Onebranchdata(recordType);
			return;
		}
	}
	Result()
	{
	}
	long getMaxmarks()
	{
		return(maxMarks);
	}
	String getTopperrollno()
	{
		return(topperRollno);
	}
	String getToppername()
	{
		return(topperName);
	}
	String[] getRearray()
	{
		return(reArray);
	}
	int getNumtokens()
	{
		return(numtokens);
	}
}

class Onebranchdata implements Constants
{
	Result rt = new Result();
	int i=0,numRe,k=0;
	static int pass=0,re=0,other=0,j=0;
	static String subjects[] = new String[19];
	static int res[] = new int[19];

	Onebranchdata(int rType)
	{
		if(rType==PASS)
		{
			pass++;
			return;
		}
		else if(rType==RE)
		{
			re++;
			String reSubjects[] = rt.getRearray();
            numRe = rt.getNumtokens();
            while(k<numRe)
            {
				while(i<j)
				{
					if(k==numRe)
					{
						return;
					}
					if(subjects[i].equals(reSubjects[k]))
					{
						res[i]++;
						i=0;
						k++;
					}
					else
					i++;
				}
				if(i==j)
				{
					if(k==numRe)
					{
						return;
					}
					subjects[j] = reSubjects[k];
					res[j]++;
					j++;
					k++;
					i=0;
			    }
			}
		}
		else if(rType==BRANCHNAMECHANGED)
		{
			Allcollegedata ac = new Allcollegedata(rType);
			Branchtopper bt = new Branchtopper(rType);
			for(i=0;i<19;i++)
			{
				subjects[i] = "";
			}
			j = 0;
			return;
		}
		else if(rType==BRANCHNAMESAME)
		{
			Allcollegedata ac = new Allcollegedata(rType);
			Branchtopper bt = new Branchtopper(rType);
			rt.maxMarks = 0;
			pass = 0;
			re = 0;
			other = 0;
			for(i=0;i<19;i++)
			{
				res[i] = 0;
			}
			return;
		}
		else
		{
			other++;
			return;
		}
	}
	Onebranchdata()
	{
	}
	int getPass()
	{
		return(pass);
	}
	int getRe()
	{
		return(re);
	}
	int getOther()
	{
		return(other);
	}
	String[] getSubjects()
	{
		return(subjects);
    }
    int[] getRes()
    {
		return(res);
	}
	int getJ()
	{
		return(j);
	}
}

class Allcollegedata implements Constants
{
	Record rd = new Record();
	static String clgName = "";
	static String clgBranch = "";
	static int k=2,i=0;
	String clgSubjects[];
	static String clgData[] = new String[20];
	Onebranchdata ob = new Onebranchdata();

	Allcollegedata(int Type)
	{
		if(k==0)
		{
			try
			{
				RandomAccessFile raf = new RandomAccessFile("result analyser output.txt","rws");

				if(Type==BRANCHNAMECHANGED)
				{
					long len = raf.length();
					String clgSubjects[] = ob.getSubjects();
					int count = ob.getJ();
				    raf.seek(len);
					raf.writeBytes(clgBranch+"\t");
					raf.writeBytes("TOTAL"+"\t"+"PASS"+"\t"+"RE"+"\t"+"OTHERS"+"\t");
					for(int j=0;j<count;j++)
				    {
						raf.writeBytes(clgSubjects[j]+"\t");
					}
					raf.writeBytes("\n");
					for(int j=0;j<i;j++)
					{
						raf.writeBytes(clgData[j]);
						raf.writeBytes("\n");
					}
					raf.writeBytes("\n");
                    for(int j=0;j<i;j++)
                    {
						clgData[j] = "";
					}
					i=0;
                    raf.close();
				}
				else if(Type==BRANCHNAMESAME)
				{
					int clgPass = ob.getPass();
					int clgRe = ob.getRe();
					int clgOther = ob.getOther();
					int clgRes[] = ob.getRes();
					int total = clgPass+clgRe+clgOther;
					clgData[i] = "  ";
					clgData[i] = clgData[i]+clgName+"\t"+total+"\t"+clgPass+"\t"+clgRe+"\t"+clgOther+"\t";
					for(int j=0;j<19;j++)
					{
						if(clgRes[j]>0)
						{
							clgData[i] = clgData[i]+clgRes[j];
							clgData[i] = clgData[i]+"\t";
						}
						else
						clgData[i] = clgData[i]+" \t";
					}
					i++;
				}
			}
			catch(IOException e)
			{
				System.out.print(e);
			}
		}
		else
		{
			k--;
		}
		clgName = rd.getCollegename();
		clgBranch = rd.getBranchname();
	}
}

class Branchtopper implements Constants
{
	Result rt = new Result();
	Record rd = new Record();
	static String clgName="";
	static int i=0,k=2;
    static String topperData[] = new String[20];

	Branchtopper(int type)
	{
		if(k==0)
		{
			try
			{
				RandomAccessFile raf = new RandomAccessFile("result analyser output.txt","rws");
				if(type==BRANCHNAMECHANGED)
				{
					long len = raf.length();
					raf.seek(len);
					raf.writeBytes(" TOPPERS"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"ROLLNO"+"\t"+"\t"+"NAME"+"\t"+"\t"+"\t"+"\t"+"MARKS"+"\n");
    				for(int j=0;j<i;j++)
					{
						raf.writeBytes(topperData[j]);
						raf.writeBytes("\n");
					}
					raf.writeBytes("\n"+"\n");
					for(int j=0;j<i;j++)
					{
						topperData[j] = "";
					}
					i=0;
					raf.close();
				}
				else if(type==BRANCHNAMESAME)
				{
					long marks = rt.getMaxmarks();
					String rollno = rt.getTopperrollno();
					String name = rt.getToppername();
					topperData[i] = "  ";
					topperData[i] = topperData[i]+clgName+"\t"+rollno+"\t"+name+"\t"+marks;
					i++;
				}
			}
			catch(IOException e)
			{
				System.out.print(e);
			}
		}
		else
		{
			k--;
		}
		clgName = rd.getCollegename();
	}
}

class ResultFrame extends Frame
{
	ResultFrame(String title)
	{
		super(title);
		addWindowListener(new WindowAdapter()
						 {
							 public void windowclosing(WindowEvent we)
							 {
								 System.exit(0);
							 }
						 });
	 }
}

class Resultanalyser
{
	public static void main(String args[])
	throws IOException
	{
		Frame f=new  ResultFrame("FileDialogBox");
		f.setVisible(true);
		f.setSize(100,100);
		FileDialog fd=new FileDialog(f,"Choose Result File");
		fd.setVisible(true);
		String filename = fd.getFile();
		String dirname = fd.getDirectory();
//		File fileobj = new File(dirname + "\\" +  filename);
		FileReader fr=new FileReader(filename);
		BufferedReader br=new BufferedReader(fr);
		String str;
		while((str=br.readLine())!=null)
		{
			Record rd = new Record(str);
		}
		fr.close();
		System.out.println("RESULT ACCESSED");
	}
}













