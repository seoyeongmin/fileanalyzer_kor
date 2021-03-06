package gov.nara.nwts.ftapp.gui;

import gov.nara.nwts.ftapp.gui.MyTableModel;
//원래 존재하는 import이지만 없어도 실행에는 문제가 없음, ->쓰인 적이 없기 때문
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * User interface tab displaying the files that have been processed by a FileTest
 * @author TBrady
 *
 */
class DetailsPanel extends MyBorderPanel {
	private static final long serialVersionUID = 1L;
	JTable jt;
	MyTableModel tm;
	JTextField jtfRoot;
	TableRowSorter<TableModel> sorter;
	DirectoryTable parent;
	
	DetailsPanel(DirectoryTable dt) {
		parent = dt;
		JPanel ph = addPanel("", BorderLayout.NORTH);
		ph.setLayout(new BorderLayout());
		jtfRoot = new JTextField("",50);
		jtfRoot.setEditable(false);
		ph.add(jtfRoot, BorderLayout.NORTH);
		
		JPanel p = addBorderPanel("자세한분석");//Details	//gui 위에 뜨는 작은 탭
		tm = new MyTableModel();
		jt = new JTable(tm);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sorter = new TableRowSorter<TableModel>(tm);
		jt.setRowSorter(sorter);
		tm.setColumns(jt);
		jt.setPreferredScrollableViewportSize(new Dimension(600,400));
		p.add(new JScrollPane(jt),BorderLayout.CENTER);
		ph.add(new DetailsFilter(), BorderLayout.SOUTH);
		
		p = addPanel((String)null, BorderLayout.SOUTH);
		dt.countLabel = new JLabel();
		p.add(dt.countLabel);
		JButton save = new JButton("다른이름으로 저장");//Export Table
		save.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new TableSaver(parent,tm,jt,"Details");
				}
			}
		);
		p.add(save);
	}
	class DetailsFilter extends JPanel {
		private static final long serialVersionUID = 1L;
		ArrayList<JTextField> tfs;

		DetailsFilter() { //디테일 탭 필터
			tfs = new ArrayList<JTextField>();
			createField("경로(Dir)",15);//Dir
			createField("파일명",10);//File
			createField("파일타입",10);//Type
			createField("Other",10);//Other
			JButton bf = new JButton("필터링");//Filter
			add(bf);
			bf.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					setFilter();
				}
			});
			JButton bc = new JButton("지우기");//Clear
			bc.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					for(JTextField tf: tfs) {
						tf.setText("");
					}
					setFilter();
				}				
			});
			add(bc);
		}
		
		JTextField createField(String name, int len) {
			JTextField tf = new JTextField(len);
			tf.setBorder(BorderFactory.createTitledBorder(name));
			add(tf);
			tfs.add(tf);
			return tf;
		}
		
		void setFilter() {
			DetailsPanel.this.sorter.setRowFilter(
				new RowFilter<TableModel,Integer>(){
				public boolean include(
						javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> row) {
					boolean b = true;
					b = b && compare(row.getStringValue(0), tfs.get(0).getText());
					b = b && compare(row.getStringValue(1), tfs.get(1).getText());
					b = b && compare(row.getStringValue(2), tfs.get(2).getText());
					b = b && compare(row.getStringValue(5), tfs.get(3).getText());
					return b;
				}
				public boolean compare(String val, String filter) {
					if (filter==null) return true;
					if (filter.trim().equals("")) return true;
					if (val == null) return false;
					return (val.trim().contains(filter.trim()));
				}
			}
		);
		}
		
		
	}
}
