package gov.nara.nwts.ftapp.stats;

import java.io.File;

import gov.nara.nwts.ftapp.filetest.FileTest;

/**
 * Status class showing accumulated counts by directory and by file type.
 * This is useful in helping users discover the contents of a large volume of data.
 * @author TBrady
 *
 */
public class DirTypeStats extends Stats {
	public static Object[][] details = {
			{String.class,"확장자+경로(Key)",100},//Key
			{String.class,"파일확장자",80},//Type
			{String.class,"경로",300},//Path
			{Long.class,"총합",100},//Count
			{Long.class,"누적 계수",100},//Cumulative Count
		};

	
	public DirTypeStats(String key) {
		super(key);
		vals.add("");
		vals.add("");
		vals.add(new Long(0));
		vals.add(new Long(0));
	}
	
	public Object compute(File f, FileTest fileTest) {
		File root = fileTest.getRoot();
		for(File ftest = f.getParentFile(); ftest!=null; ftest = ftest.getParentFile()){
			DirTypeStats stats = (DirTypeStats)fileTest.getStats(fileTest.getKey(f,ftest));
			stats.accumulate(f, fileTest, ftest);
			if (ftest.equals(root)){
				break;
			}
		}
		return fileTest.fileTest(f);
	}
	
	public void accumulate(File f, FileTest fileTest, File parentdir) {
		Long count = (Long)vals.get(2);
		if (f.getParentFile().equals(parentdir)){
			vals.set(2, count.longValue()+1);
		}
		count = (Long)vals.get(3);
		vals.set(3, count.longValue()+1);
		vals.set(1, (parentdir==null) ? "" : parentdir.getAbsolutePath().substring(fileTest.getRoot().getAbsolutePath().length()));
		vals.set(0, fileTest.getExt(f));		
	}
}
