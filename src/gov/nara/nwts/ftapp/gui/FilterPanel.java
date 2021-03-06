package gov.nara.nwts.ftapp.gui;

import gov.nara.nwts.ftapp.filter.FileTestFilter;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Panel presenting the various filter values that can be overridden from the user interface
 * @author TBrady
 *
 */
class FilterPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	JTextField prefix;
	JTextField contains;
	JTextField suffix;
	JTextField exclusion;
	
	JCheckBox rePrefix;
	JCheckBox reContains;
	JCheckBox reSuffix;
	JCheckBox reExclusion;
	
	FilterPanel(DirectoryTable dt, FileTestFilter filter) {
		JPanel p = addPanel("파일 이름 접두사 필터"); //Filename Prefix Filter
		prefix = new JTextField(filter.getPrefix(), 50);
		p.add(prefix);
		rePrefix = new JCheckBox();
		rePrefix.setText("Regex");//정규표현식
		rePrefix.setSelected(filter.isRePrefix());
		p.add(rePrefix);

		p = addPanel("파일이름 포함 필터");//Filename Contains Filter
		contains = new JTextField(filter.getContains(), 50);
		p.add(contains);
		reContains = new JCheckBox();
		reContains.setText("Regex");
		reContains.setSelected(filter.isReContains());
		p.add(reContains);

		p = addPanel("파일이름 접미사 필터");//Filename Suffix Filter
		suffix = new JTextField(filter.getSuffix(), 50);
		p.add(suffix);
		reSuffix = new JCheckBox();
		reSuffix.setText("Regex");
		reSuffix.setSelected(filter.isReSuffix());
		p.add(reSuffix);

		p = addPanel("파일이름 제외 필터");//Filename Exclusion Filter
		exclusion = new JTextField(filter.getExclusion(), 50);
		p.add(exclusion);
		reExclusion = new JCheckBox();
		reExclusion.setText("Regex");
		reExclusion.setSelected(filter.isReExclusion());
		p.add(reExclusion);

		dt.criteriaPanel.filterTabs.add(filter.getName(), this);
	}
}
