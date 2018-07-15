package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class LoggerUtils
{
	private static final String LOG_PATH = "../../logs/";

	public LoggerUtils()
	{

	}

	public static void writeException(Exception pException) throws IOException
	{

		String logFilePath = "ExceptionDetails.log";
		FileWriter fileWriter = null;
		BufferedWriter output = null;
		try
		{
			File fichier = new File(logFilePath);
			if (!fichier.exists())
			{
				fichier.createNewFile();
			}
			fileWriter = new FileWriter(fichier, true);
			output = new BufferedWriter(fileWriter);
			Date dateOfError = new Date();
			DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			output.write(shortDateFormat.format(dateOfError) + " : " + pException.getMessage() + "\n");
			for (StackTraceElement stackTraceElement : pException.getStackTrace())
			{
				output.write("\t" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()
						+ "(line: " + stackTraceElement.getLineNumber() + ")\n");
			}
			output.flush();
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
