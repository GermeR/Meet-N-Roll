package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerUtils
{
	private static final String LOG_PATH = "/Meet-N-Roll/logs/";

	public LoggerUtils()
	{

	}

	public static void writeException(Exception pException) throws IOException
	{
		String logFilePath = LOG_PATH + "ExceptionDetail.log";
		FileWriter fileWriter = null;
		BufferedWriter output = null;
		try
		{
			fileWriter = new FileWriter(logFilePath, true);
			output = new BufferedWriter(fileWriter);

			output.write(pException.getMessage());
			output.write(pException.getStackTrace().toString());
			output.flush();

			//System.out.println("fichier créé");
		}
		finally
		{
			if (fileWriter != null)
			{
				fileWriter.close();
			}
			if (output != null)
			{
				output.close();
			}
		}
	}
}
