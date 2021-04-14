package sunilRNDpackage;

public class xyzatabc {

	static boolean xyz=false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//boolean xyz = false;
		System.out.println(xyz);
		for(int i=0;i<3;i++)
		{
			if(xyz)
			{
				i--;
				System.out.println("outside for"+i);
				xyz=false;
			}
			else
			{
				if(i==1)
				{				
					xyz=true;
					System.out.println(i);
				}
				else
				{
					System.out.println(i);
				}
			}
		}

	}

}
