package br.ufpe.cin.rii.test.utils;

import java.io.File;

public final class FolderUtils
{
	public static boolean
	DeleteRecursive(String filepath)
	{
		return DeleteRecursive(new File(filepath));
	}

	public static boolean
	DeleteRecursive(File file)
	{
		if (!file.exists()) return false;
		if (file.isDirectory())
		{
			File[] innerFiles = file.listFiles();
			if (innerFiles != null)
			{
				for (File innerFile : innerFiles)
				{
					DeleteRecursive(innerFile);
				}
			}
		}
		return file.delete();
	}
}
